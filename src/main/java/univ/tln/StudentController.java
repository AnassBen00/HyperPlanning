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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import univ.tln.daos.EtudiantDAO;
//import univ.tln.entities.utilisateurs.Etudiant;

import java.io.IOException;
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

public class StudentController implements Initializable {
    public int i ;
    int max_cours = 60;
    EtudiantDAO etudiantDAO = new EtudiantDAO();

    private String[][] creneau= new String[max_cours][10];
    @FXML
    private AnchorPane scene1 ; //le planning
    @FXML
    private AnchorPane scene3 ; //le compte
    @FXML
    private AnchorPane scene4 ; //les parametre

    @FXML
    private Pane scenecmt;

    @FXML
    private Pane scenepln;

    @FXML
    private Pane sceneset;

    @FXML
    private Button btnaccount;

    @FXML
    private Button btnplanning;
    @FXML
    private Button btnsettings;
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

    @FXML
    private TextField nameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView backarrow;

    @FXML
    private ImageView frontarrow;

    @FXML
    private TableView<?> absenceetud;

    private int r=-7;
    private int w = 7;

    List<Label> l = new ArrayList<>();
    List<Label> l2 = new ArrayList<>();


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader =new FXMLLoader(App.class.getResource("hello-view.fxml"));
        castdatetime(0);

        try {
            drawrect(); //on dessine l'emploie du temps
        } catch (ParseException e) {
            e.printStackTrace();
        }
        arrowinputback();
        arrowinputfront();
        setcalendar(0);
        try {
            AffichageInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public void handleclicks (ActionEvent e){ //pour changer l'ecran

    if(e.getSource()==btnaccount){
        btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99),CornerRadii.EMPTY, Insets.EMPTY)));
        scene3.toFront();
        scenecmt.toFront();
    }
    else if(e.getSource()==btnplanning){
        btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99),CornerRadii.EMPTY, Insets.EMPTY)));
        scene1.toFront();
        scenepln.toFront();
    }
    else if(e.getSource()==btnsettings){
        btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99),CornerRadii.EMPTY, Insets.EMPTY)));
        scene4.toFront();
        sceneset.toFront();
    }

}

public void arrowinputback(){ // pour voir la semaine precedente

    backarrow.setOnMouseClicked((mouseEvent) -> {
        getmonday(r);
        getmonday(r+7);

        for (Label g : l) {
            scene1.getChildren().remove(g);
        }
        castdatetime(r);

        try {
            drawrect();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setcalendar(r);
        System.out.println("loool");

        r=r-7;
        w=w-7;
    });

}

    public void arrowinputfront(){ //pour voir la semaine suivante

        frontarrow.setOnMouseClicked((mouseEvent) -> {
            getmonday(w);
            getmonday(w+7);

            for (Label g : l) {
                scene1.getChildren().remove(g);
            }
            castdatetime(w);

            try {
                drawrect();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setcalendar(w);
            System.out.println("loool");

            w=w+7;
            r=r+7;
        });

    }



    @FXML
    Group group = new Group();

    public double houretopxl(double a ){return (a-8)*45.9+47;} //fonction qui donne le pixel exact de l'heure

    public int datetopxl(int a ){return (a-2)*126+125;} // fonction qui retourne le pixel exact de la date

    public Calendar getmonday(int i ){ //retoune le lundi de cette semaine
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY  );
        c.add(Calendar.DATE, i);
        System.out.println(c.getTime());
        return c;
    }




    public void setcalendar(int a){ //pour afficher les dates sous les jours
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
        c.add(Calendar.DATE, a);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            Tlabel[i].setText(df.format(c.getTime()));
            Tlabel[i].setTextAlignment(TextAlignment.CENTER);
            Tlabel[i].setTextFill(Color.rgb(255, 255, 255));
            c.add(Calendar.DATE, 1);
        }


    }

    public void castdatetime(int z) { //fonction qui remplie une liste des creneaux d'une semaine
         i = 0;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();


        try {

            Statement statement = connection1.createStatement();
            PreparedStatement pstmt =connection1.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,U.NOM,U.PRENOM,U.EMAIL,C2.NOM as profnom,C2.NATURE from SALLE join CRENEAUX C on SALLE.ID_S = C.ID_S join GROUP_COURS GC on C.ID_G = GC.ID_G and C.ID_C = GC.ID_C JOIN GROUP_ETUDIANT GE on GC.ID_G=GE.ID_G JOIN COURS C2 on GC.ID_C = C2.ID_C JOIN UTILISATEUR U on C2.LOGIN = U.LOGIN WHERE GE.LOGIN=? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            //PreparedStatement pstmt =connection1.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) join GROUPE on (CRENEAUX.ID_G = GROUPE.ID_G) where GROUPE.LOGIN =? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            //System.out.println(LoginController.user1);
            pstmt.setString(1,LoginController.user1);
            name.setText("Login: " + LoginController.user1 + "\n" +LoginController.name1);
            name.setTextFill(Color.rgb(255, 255, 255));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(2, java.sql.Date.valueOf(df.format(getmonday(z).getTime())));
            //System.out.println(df.format(getmonday().getTime()));
            pstmt.setDate(3, java.sql.Date.valueOf(df.format(getmonday(z+7).getTime())));
            ResultSet queryResult = pstmt.executeQuery();
            //System.out.println(queryResult.getInt(1));
            while ((queryResult.next())) {
                creneau[i][0] = String.valueOf(queryResult.getTimestamp("DATE_D"));
                creneau[i][1]= String.valueOf(queryResult.getTimestamp("DATE_F"));
                creneau[i][2] = queryResult.getString("BATIMENT");
                creneau[i][3] = String.valueOf(queryResult.getInt("NUM"));
                creneau[i][4] = String.valueOf(queryResult.getBoolean("VIDEO_P"));
                creneau[i][5] = queryResult.getString("NOM");
                creneau[i][6] = queryResult.getString("PRENOM");
                creneau[i][7] = queryResult.getString("EMAIL");
                creneau[i][8] = queryResult.getString("profnom");
                creneau[i][9] = queryResult.getString("NATURE");
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
        l.add(cours);
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
        cours.setMaxWidth(126);
        cours.setMaxHeight(w);
        cours.setMinHeight(w);
        //cours.setFont(new Font("Serif", 14));
        if (creneau[r][4].equals("true")) m = "Oui";
        else m = "Non";
        System.out.println("hhhhhhhh"+creneau[r][9]);
        cours.setText("Salle: " + creneau[r][2] + " " + creneau[r][3] + "\n" + creneau[r][5] + " " + creneau[r][6] + "\n"+creneau[r][8]+ "\n"+creneau[r][9]+ "\nprojecteur: " + m );

        cours.setTextFill(Color.rgb(255, 255, 255));
        cours.setTextAlignment(TextAlignment.CENTER);
        cours.setAlignment(Pos.CENTER);
        //System.out.println(creneau[r][6]);

        if(creneau[r][9].trim().equals("TP"))cours.setBackground(new Background(new BackgroundFill(Color.rgb(50, 18, 71), CornerRadii.EMPTY, Insets.EMPTY)));
        else if(creneau[r][9].trim().equals("TD"))cours.setBackground(new Background(new BackgroundFill(Color.rgb(5, 52, 14), CornerRadii.EMPTY, Insets.EMPTY)));
        else if(creneau[r][9].trim().equals("CM"))cours.setBackground(new Background(new BackgroundFill(Color.rgb(26, 31, 38), CornerRadii.EMPTY, Insets.EMPTY)));

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

    public void AffichageInfo() throws SQLException {
        nameField.setText(etudiantDAO.findbyLogin(LoginController.user1).getPrenom());
        lastnameField.setText(etudiantDAO.findbyLogin(LoginController.user1).getNom());
        emailField.setText(etudiantDAO.findbyLogin(LoginController.user1).getEmail());
        passwordField.setText(LoginController.psswrd);
        loginField.setText(LoginController.user1);
    }

    public void swithtoPasseditscene() {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("PassEditpopup.fxml"));

            Stage managerstage = new Stage();

            Scene scene = new Scene(root);


            managerstage.setScene(scene);
            managerstage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    void SaveOnAction(ActionEvent event) {

    }

    @FXML
    void editPass(ActionEvent event) {
        swithtoPasseditscene();

    }



}