package univ.tln;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import univ.tln.daos.EtudiantDAO;
import univ.tln.entities.utilisateurs.Etudiant;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.*;

//import static univ.tln.LoginController.getUsernametxt;

public class TeacherController implements Initializable {
    public int i ;
    int max_cours = 60;
    private String[][] creneau= new String[max_cours][7];
    @FXML
    private AnchorPane scene1 ; //le planning
    @FXML
    private AnchorPane scene2 ; //la partie pour modifier ajouter
    @FXML
    private AnchorPane scene3 ; //le compte
    @FXML
    private AnchorPane scene4 ; //les parametre
    @FXML
    private AnchorPane scene5 ; //l'abcenses
    @FXML
    private Button btnaccount;
    @FXML
    private Button btnmodify;
    @FXML
    private Button btnplanning;
    @FXML
    private Button btnsettings;
    @FXML
    private Button btnabsences;
    @FXML
    private Label lblstatus;
    @FXML
    private Label lblstatusmini;
    @FXML
    private Label iddimanche;

    @FXML
    private Label idjeudi;

    @FXML
    private Label idlundi;

    @FXML
    private Label idmardi;

    @FXML
    private Label idmercredi;

    @FXML
    private Label idsamedi;

    @FXML
    private Label idvendredi;

    @FXML
    private TableView listEtudiantId;

    @FXML
    private Label name;


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // FXMLLoader loader =new FXMLLoader(App.class.getResource("hello-view.fxml"));
        castdatetime();
        try {
            drawrect(); //on dessine l'emploie du temps
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setcalendar();
    }

public void handleclicks (ActionEvent e){ //pour changer l'ecran

    if(e.getSource()==btnaccount){
        lblstatus.setText("Account");
        lblstatusmini.setText("/home/account");
        btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99),CornerRadii.EMPTY, Insets.EMPTY)));
        scene3.toFront();
    }
    else if(e.getSource()==btnmodify){
        lblstatus.setText("Modify");
        lblstatusmini.setText("/home/modify");
        btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99),CornerRadii.EMPTY, Insets.EMPTY)));
        scene2.toFront();
    }
    else if(e.getSource()==btnplanning){
        lblstatus.setText("Planning");
        lblstatusmini.setText("/home/planning");
        btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99),CornerRadii.EMPTY, Insets.EMPTY)));
        scene1.toFront();
    }
    else if(e.getSource()==btnsettings){
        lblstatus.setText("Settings");
        lblstatusmini.setText("/home/settings");
        btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99),CornerRadii.EMPTY, Insets.EMPTY)));
        scene4.toFront();
    }
    else if(e.getSource()==btnabsences){
        btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99),CornerRadii.EMPTY, Insets.EMPTY)));
        scene5.toFront();
        lblstatus.setText("Absences");
        lblstatusmini.setText("/home/absences");

        TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("Fist name");
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                // for first column
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });

        TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>("Last name");
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                // for second column
                return new SimpleStringProperty(p.getValue().getValue());
            }
        });

        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(afficherEtudiants().entrySet());
        listEtudiantId.setItems(items);
        listEtudiantId.getColumns().setAll(column1, column2);
    }
}

    public Map<String,String> afficherEtudiants(){
        Map <String,String> etudiants = new TreeMap<>() {};
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        for(Etudiant etudiant : etudiantDAO.findall()) {
            etudiants.put(etudiant.getNom(), etudiant.getPrenom());
        }
        return etudiants;
    }

    @FXML
    Group group = new Group();

    public double houretopxl(double a ){return (a-8)*45.9+47;} //fonction qui donne le pixel exact de l'heure

    public int datetopxl(int a ){return (a-2)*126+125;} // fonction qui retourne le pixel exact de la date

    public Calendar getmonday(){ //retoune le lundi de cette semaine
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return c;
    }
    public Calendar getsunday(){ // retourne le dimache
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.add(Calendar.DATE, 7);
        return c;
    }

    public void setcalendar(){ //pour afficher les dates sous les jours
        Label Tlabel[]=new Label[7];
        Tlabel[0]= idlundi;
        Tlabel[1]=idmardi;
        Tlabel[2]=idmercredi;
        Tlabel[3]=idjeudi;
        Tlabel[4]=idvendredi;
        Tlabel[5]=idsamedi;
        Tlabel[6]=iddimanche;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            Tlabel[i].setText(df.format(c.getTime()));
            Tlabel[i].setTextAlignment(TextAlignment.CENTER);
            Tlabel[i].setTextFill(Color.rgb(255, 255, 255));
            c.add(Calendar.DATE, 1);
        }


    }

    public void castdatetime() { //fonction qui remplie une liste des creneaux d'une semaine
         i = 0;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();


        try {

            Statement statement = connection1.createStatement();
            PreparedStatement pstmt =connection1.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) where LOGIN =? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            //PreparedStatement pstmt =connection1.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) join GROUPE on (CRENEAUX.ID_G = GROUPE.ID_G) where GROUPE.LOGIN =? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            //System.out.println(LoginController.user1);
            pstmt.setString(1,LoginController.user1);
            name.setText("User: "+LoginController.user1);
            name.setTextFill(Color.rgb(255, 255, 255));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(2, java.sql.Date.valueOf(df.format(getmonday().getTime())));
            //System.out.println(df.format(getmonday().getTime()));
            pstmt.setDate(3, java.sql.Date.valueOf(df.format(getsunday().getTime())));
            ResultSet queryResult = pstmt.executeQuery();
            //System.out.println(queryResult.getInt(1));
            while ((queryResult.next())) {
                creneau[i][0] = String.valueOf(queryResult.getTimestamp("DATE_D"));
                creneau[i][1]= String.valueOf(queryResult.getTimestamp("DATE_F"));
                creneau[i][2] = queryResult.getString("BATIMENT");
                creneau[i][3] = String.valueOf(queryResult.getInt("NUM"));
                creneau[i][4] = String.valueOf(queryResult.getBoolean("VIDEO_P"));
                creneau[i][5] = queryResult.getString("NOM");
                creneau[i][6] = queryResult.getString("NATURE");
                i++;
            }


            System.out.println(Arrays.deepToString(creneau));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



        @FXML
    public void drawrect() throws ParseException { //fonction qui dessine l'emlpoie du temps





    Calendar x = Calendar.getInstance();


    for (int r = 0;r<=i-1;r++) {
        //System.out.println(i);
        String m ;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Label cours = new Label();

        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[r][0]);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[r][1]);
        x.setTime(date1);
        int dayOfWeek = x.get(x.DAY_OF_WEEK);
        datetopxl(dayOfWeek);
        double hourofday = x.get(x.HOUR_OF_DAY) + (float) x.get(x.MINUTE) / 60;

        int z = datetopxl(dayOfWeek);
        double y = houretopxl(hourofday);
        x.setTime(date2);
        double hourofday2 = x.get(x.HOUR_OF_DAY) + (float) x.get(x.MINUTE) / 60;
        //System.out.println(hourofday2);
        double w = houretopxl(hourofday2) - y;
        //System.out.println(w);

        cours.setTranslateX(z);
        cours.setTranslateY(y);
        cours.setMinWidth(126);
        cours.setMinHeight(w);
        if(creneau[r][4].equals("true")) m="Oui";
        else m="Non";
        cours.setText("Battiment: "+creneau[r][2] +"\nSalle N°: "+  creneau[r][3]+"\nVideo projecteur: "+ m+"\n"+ creneau[r][5]+"\n"+ creneau[r][6] +"\n");

        cours.setTextFill(Color.rgb(255, 255, 255));
        cours.setTextAlignment(TextAlignment.CENTER);
        cours.setAlignment(Pos.CENTER);
        //System.out.println(creneau[r][6]);

        if(creneau[r][6].trim().equals("TP"))cours.setBackground(new Background(new BackgroundFill(Color.rgb(50, 18, 71), CornerRadii.EMPTY, Insets.EMPTY)));
        else if(creneau[r][6].trim().equals("TD"))cours.setBackground(new Background(new BackgroundFill(Color.rgb(5, 52, 14), CornerRadii.EMPTY, Insets.EMPTY)));
        else if(creneau[r][6].trim().equals("CM"))cours.setBackground(new Background(new BackgroundFill(Color.rgb(26, 31, 38), CornerRadii.EMPTY, Insets.EMPTY)));

        scene1.getChildren().add(cours);
    }

    }


    @FXML
    private Label welcomeText;

    @FXML
    private  Label loginmessage;

    @FXML
    private TextField usernametxt;
    @FXML
    private  PasswordField passwrdtxt;

}