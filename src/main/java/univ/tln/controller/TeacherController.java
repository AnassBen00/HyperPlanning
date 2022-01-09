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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Setter;
import univ.tln.App;
import univ.tln.daos.CreneauxDAO;
import univ.tln.daos.EnseignantDAO;
import univ.tln.daos.EtudiantDAO;
import univ.tln.daos.exceptions.DataAccessException;
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



public class TeacherController implements Initializable {
    @Setter
    public static int i;
    int maxcours = 60;
    private String[][] creneau = new String[maxcours][8];

    EnseignantDAO enseignantDAO;

    {
        try {
            enseignantDAO = new EnseignantDAO();
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private AnchorPane scene1; //le planning
    @FXML
    private AnchorPane scene2; //la partie pour modifier ajouter
    @FXML
    private AnchorPane scene3; //le compte
    @FXML
    private AnchorPane scene4; //les parametre
    @FXML
    private AnchorPane scene5; //l'abcenses
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
    private Label iddimanche1;

    @FXML
    private Label idjeudi;

    @FXML
    private Label idjeudi1;

    @FXML
    private Label idlundi;

    @FXML
    private Label idlundi1;

    @FXML
    private Label idmardi;

    @FXML
    private Label idmardi1;

    @FXML
    private Label idmercredi;

    @FXML
    private Label idmercredi1;

    @FXML
    private Label idsamedi;

    @FXML
    private Label idsamedi1;

    @FXML
    private Label idvendredi;

    @FXML
    private Label idvendredi1;

    @FXML
    private TableView listEtudiantId;

    @FXML
    private Label name;

    @FXML
    private Label messageinstruction;

    @FXML
    private Label role;

    @FXML
    private Button refrech;

    @FXML
    private Button supbtn;
    @FXML
    private Pane sceneabs;

    @FXML
    private Pane sceneajout;

    @FXML
    private Pane scenecmt;

    @FXML
    private Pane scenepln;

    @FXML
    private Pane sceneset;

    @FXML
    private ImageView frontarrow;
    @FXML
    private ImageView backarrow;

    @FXML
    private ImageView frontarrow2;
    @FXML
    private ImageView backarrow2;

    @FXML
    private TextField nameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField emailField;

    @FXML
    private Button saveButton;

    private int z=-7;
    private int y = 7;

    List<Label> l = new ArrayList<>();
    List<Label> l2 = new ArrayList<>();
    @Setter
    public static String d1;
    @Setter
    public static String s1;
    @Setter
    public static String b1;
    @Setter
    public static String g1;


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initname();
        castdatetime(0,creneau);
        try {
            drawrect2();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        arrowinputback2();
        arrowinputfront2();
        setcalendar2(0);
        try{
            AffichageInfo();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void arrowinputback2(){ // pour voir la semaine precedente

        backarrow2.setOnMouseClicked(mouseEvent -> {
            getmonday(z);
            getmonday(z+7);

            for (Label g : l2) {
                scene2.getChildren().remove(g);
            }
            castdatetime(z,creneau);

            try {
                drawrect2();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setcalendar2(z);

            z=z-7;
            y=y-7;
        });

    }

    public void arrowinputfront2(){ //pour voir la semaine suivante

        frontarrow2.setOnMouseClicked(mouseEvent -> {
            getmonday(y);
            getmonday(y+7);

            for (Label g : l2) {
                scene2.getChildren().remove(g);
            }
            castdatetime(y,creneau);

            try {
                drawrect2();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setcalendar2(y);


            y=y+7;
            z=z+7;
        });

    }



    public void handleclicks(ActionEvent e) { //pour changer l'ecran

        if (e.getSource() == btnaccount) {
            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene3.toFront();
            scenecmt.toFront();
        } else if (e.getSource() == btnmodify) {
            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene2.toFront();
            sceneajout.toFront();

        } else if (e.getSource() == btnsettings) {
            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene4.toFront();
            sceneset.toFront();
        }
    }

    public ObservableList<Etudiant> afficherEtudiants() {

        ObservableList<Etudiant> etudiants = null;
        try (EtudiantDAO etudiantDAO = new EtudiantDAO();) {


            etudiants = FXCollections.observableArrayList(etudiantDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return etudiants;
    }

    @FXML
    Group group = new Group();

    public double houretopxl(double a) {
        return (a - 8) * 45.9 + 47;
    } //fonction qui donne le pixel exact de l'heure

    public int datetopxl(int a) {
        return (a - 2) * 126 + 125;
    } // fonction qui retourne le pixel exact de la date

    public Calendar getmonday(int i ){ //retoune le lundi de cette semaine
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY  );
        c.add(Calendar.DATE, i);

        return c;
    }


    public void setcalendar2(int a) { //pour afficher les dates sous les jours
        Label [] Tlabel = new Label[7];
        Tlabel[0] = idlundi1;
        Tlabel[1] = idmardi1;
        Tlabel[2] = idmercredi1;
        Tlabel[3] = idjeudi1;
        Tlabel[4] = idvendredi1;
        Tlabel[5] = idsamedi1;
        Tlabel[6] = iddimanche1;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.DATE, a);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int k = 0; k < 7; k++) {
            Tlabel[k].setText(df.format(c.getTime()));
            Tlabel[k].setTextAlignment(TextAlignment.CENTER);
            Tlabel[k].setTextFill(Color.rgb(255, 255, 255));
            c.add(Calendar.DATE, 1);
        }
    }

    void initname(){
        name.setText("Login: " + LoginController.user1 + "\n" +LoginController.name1);
        name.setTextFill(Color.rgb(255, 255, 255));
    }

    public void castdatetime(int w, String[][] cren) { //fonction qui remplie une liste des creneaux d'une semaine
        setI(0);
        try (CreneauxDAO c2 = new CreneauxDAO();){
            setI( c2.castdatetime2(getmonday(w), getmonday(w + 7), cren ,i));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    public void drawrect2() throws ParseException { //fonction qui dessine l'emlpoie du temps
        Calendar x = Calendar.getInstance();


        for (int r = 0; r <= i - 1; r++) {

            String m;
            Label cours = new Label();
            l2.add(cours);


            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[r][0]);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[r][1]);

            x.setTime(date1);
            int dayOfWeek = x.get(Calendar.DAY_OF_WEEK);
            datetopxl(dayOfWeek);
            double hourofday = x.get(Calendar.HOUR_OF_DAY) + (float) x.get(Calendar.MINUTE) / 60;

            int z1 = datetopxl(dayOfWeek);
            double y1 = houretopxl(hourofday);
            x.setTime(date2);
            double hourofday2 = x.get(Calendar.HOUR_OF_DAY) + (float) x.get(Calendar.MINUTE) / 60;
            double w = houretopxl(hourofday2) - y1;



            cours.setTranslateX(z1);
            cours.setTranslateY(y1);
            cours.setMinWidth(126);
            cours.setMaxWidth(126);
            cours.setMaxHeight(w);
            cours.setMinHeight(w);
            if (creneau[r][4].equals("true")) m = "Oui";
            else m = "Non";
            cours.setText("Salle: " + creneau[r][2] + " " + creneau[r][3] + "\n" + creneau[r][5] + " " + creneau[r][6] + "\nprojecteur: " + m +"\n prof :");

            cours.setTextFill(Color.rgb(255, 255, 255));
            cours.setTextAlignment(TextAlignment.CENTER);
            cours.setAlignment(Pos.CENTER);

            if (creneau[r][6].trim().equals("TP"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(50, 18, 71), CornerRadii.EMPTY, Insets.EMPTY)));
            else if (creneau[r][6].trim().equals("TD"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(5, 52, 14), CornerRadii.EMPTY, Insets.EMPTY)));
            else if (creneau[r][6].trim().equals("CM"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(26, 31, 38), CornerRadii.EMPTY, Insets.EMPTY)));
            int finalR = r;
            cours.setOnMouseClicked(mouseEvent -> {
                setD1(creneau[finalR][0]);

               setS1(creneau[finalR][3]);
                setB1(creneau[finalR][2]);
                setG1(creneau[finalR][7]);
                switchtopopupscene();
            });
            scene2.getChildren().add(cours);

        }
    }

    public void switchtopopupscene() { // on change l'ecran si c'est bon

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("Popupscene.fxml")));

            Stage managerstage = new Stage();

            Scene scene = new Scene(root, 919, 667);


            managerstage.setScene(scene);
            managerstage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void updatewindow() throws ParseException {


        for (Label g : l) {
            scene1.getChildren().remove(g);

        }
        for (Label g : l2) {
            scene2.getChildren().remove(g);

        }

        castdatetime(0,creneau);
        try {
            drawrect2();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setcalendar2(0);


    }


    public void cancelbtnOnAction() {
        Stage stage = (Stage) supbtn.getScene().getWindow();
        stage.close();

    }


    @FXML
    private Label welcomeText;

    @FXML
    private Label loginmessage;

    @FXML
    private TextField usernametxt;
    @FXML
    private PasswordField passwrdtxt;

    public void AffichageInfo() throws SQLException {
        nameField.setText(enseignantDAO.findbyLogin(LoginController.user1).getPrenom());
        lastnameField.setText(enseignantDAO.findbyLogin(LoginController.user1).getNom());
        emailField.setText(enseignantDAO.findbyLogin(LoginController.user1).getEmail());
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
    void editPass(ActionEvent event) {
        swithtoPasseditscene();
    }

    @FXML
    void SaveOnAction(ActionEvent event) {

    }

}
