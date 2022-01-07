package univ.tln.Controller;


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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;                                  
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import univ.tln.App;
import univ.tln.DatabaseConnection;
import univ.tln.daos.*;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.filieres.Filiere;

import univ.tln.entities.groupes.Groupe;
import univ.tln.entities.groupes.GroupeEtudiant;
import univ.tln.entities.utilisateurs.Enseignant;
import univ.tln.entities.utilisateurs.Etudiant;
import univ.tln.entities.utilisateurs.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.*;

import static com.google.common.hash.Hashing.sha256;

//import static univ.tln.Controller.LoginController.getUsernametxt;

public class ManagerController implements Initializable {
    public int i;
    int max_cours = 60;
    CreneauxDAO c = new CreneauxDAO();

    ResponsableDAO responsableDAO = new ResponsableDAO();

    private String[][] creneau = new String[max_cours][7];




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
    private AnchorPane scenea;

    @FXML
    private AnchorPane sceneabs;

    @FXML
    private AnchorPane scenem;

    @FXML
    private AnchorPane scenep;

    @FXML
    private AnchorPane scenes;

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
    private Button btnvaliderfiltre;

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
    private Label infomessage;

    @FXML
    private ComboBox<String> md_bat;
    @FXML
    private ComboBox<String> pickfomation;

    @FXML
    private ComboBox<String> pickteacher;

    @FXML
    private ComboBox<String> md_c;

    @FXML
    private DatePicker md_date;

    @FXML
    private ComboBox<String> md_ens;

    @FXML
    private ComboBox<String> md_f;

    @FXML
    private Spinner<Integer> md_h_d;

    @FXML
    private Spinner<Integer> md_h_f;

    @FXML
    private Spinner<Integer> md_m_d;

    @FXML
    private Spinner<Integer> md_m_f;

    @FXML
    private ComboBox<String> md_n;

    @FXML
    private ComboBox<String> md_s;
    @FXML
    private ImageView frontarrow;
    @FXML
    private ImageView backarrow;


    @FXML
    private TextField prenomId;
    @FXML
    private TextField nomId;
    @FXML
    private TextField emailId;
    @FXML
    private TextField passwordId;
    @FXML
    private TextField loginId;
    @FXML
    private Button confirmAjoutEnseignantId;

    @FXML
    private TextField prenomEtdId;
    @FXML
    private TextField nomEtdId;
    @FXML
    private TextField emailEtdId;
    @FXML
    private TextField passwordEtdId;
    @FXML
    private TextField loginEtdId;
    @FXML
    private Button confirmAjoutEtudiantId;
    @FXML
    private ComboBox idNiveau;
    @FXML
    private ComboBox idFiliere;
    @FXML
    private TextField idPromo;

    @FXML
    private TextField nameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField loginField;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> filiereidd;

    @FXML
    private ComboBox<String> idGroupe;

    @FXML
    private Label ajoutermessage;


    private int r=-7;
    private int w = 7;
    List<Label> l = new ArrayList<>();
    public static String d2;
    public static String d;
    public static String m;



    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        disabledate();
        castdatetime(0);
        setspinner();
        initSalle();
        initcours();
        initnature();
        initens();
        inittableAbsence();
        setPickfomation();
        try {
            AffichageInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idNiveau.getItems().addAll(
                "licence 1",
                "licence 2",
                "licence 3",
                "master 1",
                "master 2"
        );


        GroupeDAO groupeDAO = new GroupeDAO();
        List<Groupe> groupes = new ArrayList<>();
        try {
            groupes = groupeDAO.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<String> nomGroupes = new ArrayList<>();
        for(Groupe g : groupes){

            nomGroupes.add(g.getNomDuGroupe());
        }


        idGroupe.getItems().addAll(nomGroupes);

        FiliereDAO filiereDAO = new FiliereDAO();
        List<Filiere> filieres = new ArrayList<>();
        try {
            filieres = filiereDAO.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<String> nomFilieres = new ArrayList<>();
        for(Filiere f : filieres)
            nomFilieres.add(f.getNomDuFiliere());
        idFiliere.getItems().addAll(nomFilieres);

        try {
            md_h_d.setValueFactory(valuehoure);
            md_m_d.setValueFactory(valueminute);
            drawrect(); //on dessine l'emploie du temps
        } catch (ParseException e) {
            e.printStackTrace();
        }
        arrowinputback();
        arrowinputfront();
        setcalendar(0);
    }

    @FXML
    public void ConfirmAction (ActionEvent e) throws SQLException, DataAccessException {
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        EnseignantDAO enseignantDAO = new EnseignantDAO();
        utilisateurDAO.persist(new Utilisateur(loginId.getText(), sha256()
                .hashString(passwordId.getText(), StandardCharsets.UTF_8)
                .toString(), nomId.getText() , prenomId.getText(), emailId.getText()));
        enseignantDAO.persist(new Enseignant(loginId.getText(), sha256()
                .hashString(passwordId.getText(), StandardCharsets.UTF_8)
                .toString(), nomId.getText(), prenomId.getText(), emailId.getText()));
    }

    @FXML
    public void ConfirmEtudAction (ActionEvent e) throws SQLException, DataAccessException {
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        FiliereDAO filiereDAO = new FiliereDAO();
        GroupeEtudiantDAO groupeEtudiantDAO = new GroupeEtudiantDAO();
        GroupeDAO groupeDAO = new GroupeDAO();
        utilisateurDAO.persist(new Utilisateur(loginEtdId.getText(), sha256()
                .hashString(passwordEtdId.getText(), StandardCharsets.UTF_8)
                .toString(), nomEtdId.getText(), prenomEtdId.getText(), emailEtdId.getText()));
        etudiantDAO.persist(new Etudiant(loginEtdId.getText(), sha256()
                .hashString(passwordEtdId.getText(), StandardCharsets.UTF_8)
                .toString(), nomEtdId.getText(), prenomEtdId.getText(), emailEtdId.getText(), idNiveau.getValue().toString(), idPromo.getText(), filiereDAO.find(idFiliere.getValue().toString()).getId()));
        groupeEtudiantDAO.persist(new GroupeEtudiant(groupeDAO.find(idGroupe.getValue()).getId(),loginEtdId.getText()));
    }




    public void arrowinputback(){ // pour voir la semaine precedente

        backarrow.setOnMouseClicked((mouseEvent) -> {
            getmonday(r);
            for (Label g : l) {
                scene1.getChildren().remove(g);
            }
            if(pickfomation.getValue() == null && pickteacher.getValue() != null ){
                for (Label g : l) {
                    scene1.getChildren().remove(g);
                }

                castdatetimebyteacherlogin(r,pickteacher.getValue());
            }
            else if (pickfomation.getValue() != null && pickteacher.getValue() == null ){
                for (Label g : l) {
                    scene1.getChildren().remove(g);
                }

                castdatetimebyformation(r,pickfomation.getValue());
            }
            try {
                drawrect();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setcalendar(r);


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
            if(pickfomation.getValue() == null && pickteacher.getValue() != null ){

                castdatetimebyteacherlogin(w,pickteacher.getValue());
            }
            else if (pickfomation.getValue() != null && pickteacher.getValue() == null ){

                castdatetimebyformation(w,pickfomation.getValue());
            }
            try {
                drawrect();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setcalendar(w);

            w=w+7;
            r=r+7;
        });

    }


    public void disabledate() {
        md_date.setValue(java.time.LocalDate.now());
        md_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }


        });
    }

    public void initens(){


        md_n.valueProperty().addListener((options, oldValue, newValue) ->{
            try {
                c.initialize_enseignant(md_date,md_m_f,md_m_d,md_h_f,md_h_d, md_ens,md_c,md_n,md_f);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }

    public void initSalle(){
        md_bat.valueProperty().addListener((options, oldValue, newValue) ->{
            try {
                c.initialize_salle(md_m_f,md_m_d,md_h_f,md_h_d,md_bat, md_date,md_s);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }
    public void initnature(){
        md_c.valueProperty().addListener((options1, oldValue1, newValue1) ->{
            try {
                c.initialize_nature_cours(md_f,md_c,md_n);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

    }
    public void initcours(){

        md_f.valueProperty().addListener((options, oldValue, newValue) ->{
            try {
                md_n.getItems().removeAll(md_n.getItems());
                c.initialize_cours(md_f,md_c);
                md_c.valueProperty().addListener((options1, oldValue1, newValue1) ->{
                    try {
                        c.initialize_nature_cours(md_f,md_c,md_n);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                });
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }
    public void setspinner(){

        md_h_d.valueProperty().addListener((obs, oldValue, newValue) ->{
            SpinnerValueFactory<Integer> valuehoure2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue, 19, 1);
            md_h_f.setValueFactory(valuehoure2);



        });
        md_m_d.valueProperty().addListener((obs, oldValue, newValue) ->{
            SpinnerValueFactory<Integer> valuehoure2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue, 59, 1);
            md_m_f.setValueFactory(valuehoure2);



        });
                md_date.valueProperty().addListener((ov, oldValue, newValue) -> {
                md_h_f.valueProperty().addListener((obs, oldValue3, newValue3) -> {
                    md_m_f.valueProperty().addListener((obs2, oldValue2, newValue2) -> {
                        try {

                            c.initialize_batiment(md_m_f,md_m_d,md_h_f,md_h_d,md_bat, md_date);
                            c.initialize_formation(md_m_f,md_m_d,md_h_f,md_h_d,md_f, md_date);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (md_m_f.getValue() != null && md_h_f.getValue() != null) {

                            try {
                                c.initialize_batiment(md_m_f,md_m_d,md_h_f,md_h_d,md_bat, md_date);
                                c.initialize_formation(md_m_f,md_m_d,md_h_f,md_h_d,md_f, md_date);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                });
                });





    }

    SpinnerValueFactory<Integer> valuehoure = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 1);
    SpinnerValueFactory<Integer> valueminute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);

    SpinnerValueFactory<Integer> valueminute2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);


    public void setPickfomation(){
        try {
            c.initialize_pickformation(filiereidd);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pickfomation.setOnMouseClicked(mouseEvent -> {
            try {
                pickteacher.valueProperty().set(null);
                c.initialize_pickformation(pickfomation);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            boolean disable = !pickteacher.isDisabled();
            pickteacher.setDisable(disable);

        });
        pickteacher.setOnMouseClicked(mouseEvent -> {
            try {
                pickfomation.valueProperty().set(null);
                c.initialize_pickenseignant(pickteacher);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            boolean disable = !pickfomation.isDisabled();
            pickfomation.setDisable(disable);

        });
    }

    public void handleclicks(ActionEvent e) { //pour changer l'ecran

        if (e.getSource() == btnaccount) {

            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene3.toFront();
            scenea.toFront();

        } else if (e.getSource() == btnmodify) {


            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene2.toFront();
            scenem.toFront();

        } else if (e.getSource() == btnplanning) {

            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));

            scene1.toFront();
            scenep.toFront();

        } else if (e.getSource() == btnsettings) {

            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));

            scene4.toFront();
            scenes.toFront();
        } else if (e.getSource() == btnabsences) {


            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene5.toFront();
            sceneabs.toFront();


        }
    }

    public void inittableAbsence(){
        //listEtudiantId.setEditable(true);

        TableColumn<Etudiant, String> nomCol//
                = new TableColumn<Etudiant, String>("nom");

        TableColumn<Etudiant, String> prenomCol//
                = new TableColumn<Etudiant, String>("prenom");

        TableColumn<Etudiant, String> nbabs//
                = new TableColumn<>("nbabs");
        filiereidd.valueProperty().addListener((options, oldValue, newValue) ->{


            //nom

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

            nomCol.setCellFactory(TextFieldTableCell.<Etudiant>forTableColumn());

            nomCol.setMinWidth(200);

            // prenom

            prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));

            prenomCol.setCellFactory(TextFieldTableCell.<Etudiant>forTableColumn());

            prenomCol.setMinWidth(200);

            // nbabs

            nbabs.setCellValueFactory(new PropertyValueFactory<>("nbabs"));

            nbabs.setCellFactory(TextFieldTableCell.<Etudiant>forTableColumn());

            nbabs.setMinWidth(200);

            ObservableList<Etudiant> list = afficherEtudiants();
            listEtudiantId.setItems(list);

            listEtudiantId.setRowFactory(tv -> {
                TableRow<String> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                            && event.getClickCount() == 2) {
                        Etudiant person = (Etudiant) listEtudiantId.getSelectionModel().getSelectedItem();
                        d = person.getNom();
                        m = person.getLogin();
                        switchtodetailabs();
                    }
                });
                return row ;
            });

        });

        listEtudiantId.getColumns().addAll(nomCol, prenomCol, nbabs);
    }


    public ObservableList<Etudiant> afficherEtudiants() {
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        ObservableList<Etudiant> etudiants = FXCollections.observableArrayList(etudiantDAO.findbygrp(filiereidd.getValue()));
        return etudiants;
    }


    @FXML
    public  void validatebuttononaction (ActionEvent e) throws IOException, ParseException {
        r=-7;
        w =7;
        for (Label g : l) {
            scene1.getChildren().remove(g);

        }

        if(pickfomation.getValue() == null && pickteacher.getValue() != null ) {

            for (Label g : l) {
                scene1.getChildren().remove(g);

            }
            castdatetimebyteacherlogin(0, pickteacher.getValue());
            drawrect();
            setcalendar(0);
        }
        else if (pickfomation.getValue() != null && pickteacher.getValue() == null ){

            for (Label g : l) {
                scene1.getChildren().remove(g);

            }
            castdatetimebyformation(0, pickfomation.getValue());
            drawrect();
            setcalendar(0);
        }
        else if (pickfomation.getValue() == null && pickteacher.getValue() == null ){

        }

    }



    @FXML
    Group group = new Group();

    public double houretopxl(double a) {
        return (a - 8) * 45.9 + 47;
    } //fonction qui donne le pixel exact de l'heure

    public int datetopxl(int a) {
        return (a - 2) * 126 + 125;
    } // fonction qui retourne le pixel exact de la date

    public Calendar getmonday(int i) { //retoune le lundi de cette semaine
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.DATE, i);
        return c;
    }

    public void setcalendar(int z) { //pour afficher les dates sous les jours
        Label Tlabel[] = new Label[7];
        Tlabel[0] = idlundi;
        Tlabel[1] = idmardi;
        Tlabel[2] = idmercredi;
        Tlabel[3] = idjeudi;
        Tlabel[4] = idvendredi;
        Tlabel[5] = idsamedi;
        Tlabel[6] = iddimanche;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.DATE, z);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            Tlabel[i].setText(df.format(c.getTime()));
            Tlabel[i].setTextAlignment(TextAlignment.CENTER);
            Tlabel[i].setTextFill(Color.rgb(255, 255, 255));
            c.add(Calendar.DATE, 1);
        }
    }

    public void castdatetime(int w) { //fonction qui remplie une liste des creneaux d'une semaine
        i = 0;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();

        try {
            Statement statement = connection1.createStatement();
            PreparedStatement pstmt = connection1.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) where LOGIN =? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            pstmt.setString(1, LoginController.user1);
            name.setText("Login: " + LoginController.user1 + "\n" +LoginController.name1);
            name.setTextFill(Color.rgb(255, 255, 255));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(2, java.sql.Date.valueOf(df.format(getmonday(w).getTime())));
            pstmt.setDate(3, java.sql.Date.valueOf(df.format(getmonday(w+7).getTime())));
            ResultSet queryResult = pstmt.executeQuery();
            while ((queryResult.next())) {
                creneau[i][0] = String.valueOf(queryResult.getTimestamp("DATE_D"));
                creneau[i][1] = String.valueOf(queryResult.getTimestamp("DATE_F"));
                creneau[i][2] = queryResult.getString("BATIMENT");
                creneau[i][3] = String.valueOf(queryResult.getInt("NUM"));
                creneau[i][4] = String.valueOf(queryResult.getBoolean("VIDEO_P"));
                creneau[i][5] = queryResult.getString("NOM");
                creneau[i][6] = queryResult.getString("NATURE");
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void castdatetimebyteacherlogin(int w,String login) { //fonction qui remplie une liste des creneaux d'une semaine
        i = 0;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();

        try {
            Statement statement = connection1.createStatement();
            PreparedStatement pstmt = connection1.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) where LOGIN =? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            pstmt.setString(1,login);
            name.setText("Name: " + LoginController.user1 );
            name.setTextFill(Color.rgb(255, 255, 255));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(2, java.sql.Date.valueOf(df.format(getmonday(w).getTime())));
            pstmt.setDate(3, java.sql.Date.valueOf(df.format(getmonday(w+7).getTime())));
            ResultSet queryResult = pstmt.executeQuery();
            while ((queryResult.next())) {
                creneau[i][0] = String.valueOf(queryResult.getTimestamp("DATE_D"));
                creneau[i][1] = String.valueOf(queryResult.getTimestamp("DATE_F"));
                creneau[i][2] = queryResult.getString("BATIMENT");
                creneau[i][3] = String.valueOf(queryResult.getInt("NUM"));
                creneau[i][4] = String.valueOf(queryResult.getBoolean("VIDEO_P"));
                creneau[i][5] = queryResult.getString("NOM");
                creneau[i][6] = queryResult.getString("NATURE");
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void castdatetimebyformation(int w,String formation) { //fonction qui remplie une liste des creneaux d'une semaine
        i = 0;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();

        try {
            Statement statement = connection1.createStatement();
            //TODO pour abderezak : pstmt a changer pour filtrer le planning par formation "formation dans le parametre
            PreparedStatement pstmt = connection1.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,cours.NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) join GROUPS on GROUPS.ID_G=GROUP_COURS.ID_G where GROUPS.NOM=? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            pstmt.setString(1,formation);
            name.setText("Name: " + LoginController.user1 );
            name.setTextFill(Color.rgb(255, 255, 255));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(2, java.sql.Date.valueOf(df.format(getmonday(w).getTime())));
            pstmt.setDate(3, java.sql.Date.valueOf(df.format(getmonday(w+7).getTime())));
            ResultSet queryResult = pstmt.executeQuery();
            while ((queryResult.next())) {
                creneau[i][0] = String.valueOf(queryResult.getTimestamp("DATE_D"));
                creneau[i][1] = String.valueOf(queryResult.getTimestamp("DATE_F"));
                creneau[i][2] = queryResult.getString("BATIMENT");
                creneau[i][3] = String.valueOf(queryResult.getInt("NUM"));
                creneau[i][4] = String.valueOf(queryResult.getBoolean("VIDEO_P"));
                creneau[i][5] = queryResult.getString("NOM");
                creneau[i][6] = queryResult.getString("NATURE");
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public  void addcreneau (ActionEvent e) throws IOException, SQLException {
        c.insertcreneau(md_date,md_h_d,md_m_d,md_h_f,md_m_f,md_bat,md_s,md_f,md_c,md_n,md_ens,ajoutermessage);
    }


    @FXML
    public void drawrect() throws ParseException { //fonction qui dessine l'emlpoie du temps

        Calendar x = Calendar.getInstance();

        for (int r = 0; r <= i - 1; r++) {
            String m;

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
            double w = houretopxl(hourofday2) - y;


            cours.setTranslateX(z);
            cours.setTranslateY(y);
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
            cours.setOnMouseClicked((mouseEvent) -> {

                d2 = creneau[finalR][0];
                switchtopopupscene();
            });

            scene1.getChildren().add(cours);
        }
    }

    public void switchtopopupscene() { // on change l'ecran si c'est bon

        try {
            Parent root = FXMLLoader.load(App.class.getResource("Popupscene2.fxml"));

            Stage managerstage = new Stage();

            Scene scene = new Scene(root, 919, 667);


            managerstage.setScene(scene);
            managerstage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void switchtodetailabs() { // on change l'ecran si c'est bon

        try {
            Parent root = FXMLLoader.load(App.class.getResource("Popupscene3.fxml"));

            Stage managerstage = new Stage();

            Scene scene = new Scene(root, 919, 667);


            managerstage.setScene(scene);
            managerstage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
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
    private Label welcomeText;

    @FXML
    private Label loginmessage;

    @FXML
    private TextField usernametxt;
    @FXML
    private PasswordField passwrdtxt;


    public void AffichageInfo() throws SQLException {
        nameField.setText(responsableDAO.findbyLogin(LoginController.user1).getPrenom());
        lastnameField.setText(responsableDAO.findbyLogin(LoginController.user1).getNom());
        emailField.setText(responsableDAO.findbyLogin(LoginController.user1).getEmail());
        passwordField.setText(LoginController.psswrd);
        loginField.setText(LoginController.user1);




    }

    @FXML
    void SaveOnAction(ActionEvent event) {

    }

    @FXML
    void editPass(ActionEvent event) {
        swithtoPasseditscene();

    }
}
