package univ.tln.controller;



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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import javafx.stage.Stage;
import lombok.Setter;
import univ.tln.App;
import univ.tln.DatabaseConnection;
import univ.tln.daos.AbsenceDAO;
import univ.tln.daos.CreneauxDAO;
import univ.tln.daos.EtudiantDAO;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.utilisateurs.Absence;
import univ.tln.entities.utilisateurs.Etudiant;

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


public class StudentController implements Initializable {
    @Setter
    public static int i ;
    int max_cours = 60;


    private static String[][] creneau= new String[60][10];
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
    private Button btnabsence;
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
    private TableView absenceetud;

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
    private AnchorPane scene5;

    @FXML
    private Button btninfo;

    @FXML
    private Pane sceneinfo;


    private int r=-7;
    private int w = 7;

    List<Label> l = new ArrayList<>();
    List<Label> l2 = new ArrayList<>();



    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        castdatetime(0,creneau);
        inistudentname();
        drawrect(); //on dessine l'emploie du temps
        arrowinputback();
        arrowinputfront();
        setcalendar(0);
        AffichageInfo();
        initabsdetail();
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
    else if(e.getSource()==btnabsence){
        btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99),CornerRadii.EMPTY, Insets.EMPTY)));
        scene4.toFront();
        sceneset.toFront();
    }
    else if(e.getSource()==btninfo){
        btninfo.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99),CornerRadii.EMPTY, Insets.EMPTY)));
        scene5.toFront();
        sceneinfo.toFront();
    }

}

    public void initabsdetail(){
        absenceetud.setEditable(true);

        TableColumn<Absence, String> date_debut//
                = new TableColumn<>("date debut cours");
        date_debut.setEditable(false);
        TableColumn<Absence, String> nomcr//
                = new TableColumn<>("nom matiere");
        nomcr.setEditable(false);
        TableColumn<Absence, String> nature//
                = new TableColumn<>("nature ");
        nature.setEditable(false);


        //nom

        date_debut.setCellValueFactory(new PropertyValueFactory<>("date_d"));

        date_debut.setCellFactory(TextFieldTableCell.<Absence>forTableColumn());

        date_debut.setMinWidth(200);

        // prenom

        nomcr.setCellValueFactory(new PropertyValueFactory<>("nom"));

        nomcr.setCellFactory(TextFieldTableCell.<Absence>forTableColumn());

        nomcr.setMinWidth(200);

        nature.setCellValueFactory(new PropertyValueFactory<>("nature"));

        nature.setCellFactory(TextFieldTableCell.<Absence>forTableColumn());

        nature.setMinWidth(200);


        ObservableList<Absence> list = afficherAbsences();
        absenceetud.setItems(list);

        absenceetud.getColumns().addAll(date_debut,nomcr, nature);

    }
    public ObservableList<Absence> afficherAbsences() {

        ObservableList<Absence> absences = null;
        try (AbsenceDAO absenceDAO = new AbsenceDAO();) {


            absences = FXCollections.observableArrayList(absenceDAO.findAllabsN(LoginController.user1));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return absences;
    }


    public void arrowinputback(){ // pour voir la semaine precedente

    backarrow.setOnMouseClicked(mouseEvent -> {
        getmonday(r);
        getmonday(r+7);

        for (Label g : l) {
            scene1.getChildren().remove(g);
        }
        castdatetime(r,creneau);

        drawrect();
        setcalendar(r);

        r=r-7;
        w=w-7;
    });

}

    public void arrowinputfront(){ //pour voir la semaine suivante

        frontarrow.setOnMouseClicked( mouseEvent -> {
            getmonday(w);
            getmonday(w+7);

            for (Label g : l) {
                scene1.getChildren().remove(g);
            }
            castdatetime(w,creneau);

            drawrect();
            setcalendar(w);


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
        return c;
    }




    public void setcalendar(int a){ //pour afficher les dates sous les jours
        Label [] Tlabel=new Label[7];
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
        for (int m = 0; m < 7; m++) {
            Tlabel[m].setText(df.format(c.getTime()));
            Tlabel[m].setTextAlignment(TextAlignment.CENTER);
            Tlabel[m].setTextFill(Color.rgb(255, 255, 255));
            c.add(Calendar.DATE, 1);
        }


    }

    public void inistudentname(){
        name.setText("Login: " + LoginController.user1 + "\n" +LoginController.name1);
        name.setTextFill(Color.rgb(255, 255, 255));
    }

    public void castdatetime(int Z,String[][] cren) { //fonction qui remplie une liste des creneaux d'une semaine
        try (CreneauxDAO c2 = new CreneauxDAO();){
            setI(c2.castdatetime(getmonday(Z), getmonday(Z + 6), cren ));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }





        @FXML
    public void drawrect()  { //fonction qui dessine l'emlpoie du temps
    Calendar x = Calendar.getInstance();


    for (int g = 0;g<=i-1;g++) {
        String m ;
        Label cours = new Label();
        l.add(cours);
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[g][0]);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[g][1]);
            x.setTime(date1);
            int dayOfWeek = x.get(Calendar.DAY_OF_WEEK);
            datetopxl(dayOfWeek);
            double hourofday = x.get(Calendar.HOUR_OF_DAY) + (float) x.get(Calendar.MINUTE) / 60;

            int z = datetopxl(dayOfWeek);
            double y = houretopxl(hourofday);
            x.setTime(date2);
            double hourofday2 = x.get(Calendar.HOUR_OF_DAY) + (float) x.get(Calendar.MINUTE) / 60;
            double h = houretopxl(hourofday2) - y;


            cours.setTranslateX(z);
            cours.setTranslateY(y);
            cours.setMinWidth(126);
            cours.setMaxWidth(126);
            cours.setMaxHeight(h);
            cours.setMinHeight(h);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (creneau[g][4].equals("true")) m = "Oui";
        else m = "Non";
        cours.setText("Salle: " + creneau[g][2] + " " + creneau[g][3] + "\n" + creneau[g][5] + " " + creneau[g][6] + "\n"+creneau[g][8]+ "\n"+creneau[g][9]+ "\nprojecteur: " + m );

        cours.setTextFill(Color.rgb(255, 255, 255));
        cours.setTextAlignment(TextAlignment.CENTER);
        cours.setAlignment(Pos.CENTER);

        if(creneau[g][9].trim().equals("TP"))cours.setBackground(new Background(new BackgroundFill(Color.rgb(50, 18, 71), CornerRadii.EMPTY, Insets.EMPTY)));
        else if(creneau[g][9].trim().equals("TD"))cours.setBackground(new Background(new BackgroundFill(Color.rgb(5, 52, 14), CornerRadii.EMPTY, Insets.EMPTY)));
        else if(creneau[g][9].trim().equals("CM"))cours.setBackground(new Background(new BackgroundFill(Color.rgb(26, 31, 38), CornerRadii.EMPTY, Insets.EMPTY)));

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

    public void AffichageInfo() {
        try(EtudiantDAO etudiantDAO = new EtudiantDAO();) {
            nameField.setText(etudiantDAO.findbyLogin(LoginController.user1).getPrenom());
            lastnameField.setText(etudiantDAO.findbyLogin(LoginController.user1).getNom());
            emailField.setText(etudiantDAO.findbyLogin(LoginController.user1).getEmail());
            passwordField.setText(LoginController.psswrd);
            loginField.setText(LoginController.user1);
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
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
    void editPass(ActionEvent event) {
        swithtoPasseditscene();

    }




}
