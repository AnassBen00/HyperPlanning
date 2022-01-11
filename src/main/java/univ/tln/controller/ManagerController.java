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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;                                  
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import lombok.Setter;
import univ.tln.App;
import univ.tln.daos.*;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.creneaux.Cours;
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
import java.util.Date;
import java.util.*;
import static com.google.common.hash.Hashing.sha256;


public class ManagerController implements Initializable {

    CreneauxDAO c = new CreneauxDAO();



    private static   String[][] creneau = new String[60][7];




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
    private TableView<Etudiant> listEtudiantId;

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
    private ComboBox<String> idNiveau;

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


    @FXML
    private Label ajouteretudmessage;

    @FXML
    private Label ajouterprofmessage;

    @FXML
    private ComboBox<String> idnatureComboBox;

    @FXML
    private TextField idcoursText;

    @FXML
    private ComboBox<String> ifFormationComboBox;

    private int r=-7;
    private int w = 7;
    List<Label> l = new ArrayList<>();
    @Setter
    public static String d2 ;
    @Setter
    public static String d;
    @Setter
    public static String m;

    static  final String DATEFORM = "yyyy-MM-dd";
    @Setter
    public  static   int i;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disabledate();
        initnale();
        setspinner();
        initSalle();
        initcours();
        initnature();
        initens();
        inittableAbsence();
        setPickfomation();
        affichageinfo();
        initniveaux();
        initgroups();
        initNature2();
        initfiliere();
        setminuteandhour();
        drawrect(); //on dessine l'emploie du temps
        arrowinputback();
        arrowinputfront();
        setcalendar(0);
        c.initialize_pickformation(ifFormationComboBox);
    }

    public void setminuteandhour(){


        md_h_d.setValueFactory(valuehoure);
        md_m_d.setValueFactory(valueminute);

    }

    public void initniveaux(){

        idNiveau.getItems().addAll(
                "licence 1",
                "licence 2",
                "licence 3",
                "master 1",
                "master 2"
        );
    }

    public void initNature2(){
        idnatureComboBox.getItems().addAll("CM", "TP", "TD");
    }


    public void initgroups(){


        List<Groupe> groupes = new ArrayList<>();
        try (GroupeDAO groupeDAO = new GroupeDAO();) {

            groupes = groupeDAO.findAll();
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }

        List<String> nomGroupes = new ArrayList<>();
        for (Groupe g : groupes) {

            nomGroupes.add(g.getNomDuGroupe());
        }
        idGroupe.getItems().addAll(nomGroupes);
    }


    public void initnale() { //fonction qui remplie une liste des creneaux d'une semaine

        name.setText("Login: " + LoginController.user1 + "\n" +LoginController.name1);
        name.setTextFill(Color.rgb(255, 255, 255));

    }

    public void initfiliere(){

        List<Filiere> filieres = null;
        try (FiliereDAO filiereDAO = new FiliereDAO();) {

            filieres = new ArrayList<>();

            filiereDAO.findAll();
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }

        List<String> nomFilieres = new ArrayList<>();
        for (Filiere f : filieres)
            nomFilieres.add(f.getNomDuFiliere());
    }

    @FXML
    public void ConfirmAction (ActionEvent e)  {

        try(UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            EnseignantDAO enseignantDAO = new EnseignantDAO();) {
            CoursDAO coursDAO = new CoursDAO();

        utilisateurDAO.persist(new Utilisateur(loginId.getText(), sha256()
                .hashString(passwordId.getText(), StandardCharsets.UTF_8)
                .toString(), nomId.getText(), prenomId.getText(), emailId.getText()));
        enseignantDAO.persist(new Enseignant(loginId.getText(), sha256()
                .hashString(passwordId.getText(), StandardCharsets.UTF_8)
                .toString(), nomId.getText(), prenomId.getText(), emailId.getText()));
        coursDAO.insertCours(new Cours(idnatureComboBox.getValue(),idcoursText.getText(),loginId.getText()));
        coursDAO.insertGroupeCours(ifFormationComboBox.getValue().toString(), coursDAO.find(idcoursText.getText()));
            ajouterprofmessage.setTextFill(Color.rgb(0, 133, 33));
            ajouterprofmessage.setText("enseignant bien ajouter");

        } catch (DataAccessException | SQLException ex) {
            ajouterprofmessage.setTextFill(Color.rgb(220, 0, 0));
            ajouterprofmessage.setText("données saisies invalides");
        }
    }
        

    @FXML
    public void ConfirmEtudAction ()  {
        try(        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
                    EtudiantDAO etudiantDAO = new EtudiantDAO();
                    FiliereDAO filiereDAO = new FiliereDAO();
                    GroupeEtudiantDAO groupeEtudiantDAO = new GroupeEtudiantDAO();
                    GroupeDAO groupeDAO = new GroupeDAO();) {
            
            utilisateurDAO.persist(new Utilisateur(loginEtdId.getText(), sha256()
                    .hashString(passwordEtdId.getText(), StandardCharsets.UTF_8)
                    .toString(), nomEtdId.getText(), prenomEtdId.getText(), emailEtdId.getText()));
            etudiantDAO.persist(new Etudiant(loginEtdId.getText(), sha256()
                    .hashString(passwordEtdId.getText(), StandardCharsets.UTF_8)
                    .toString(), nomEtdId.getText(), prenomEtdId.getText(), emailEtdId.getText(), idNiveau.getValue(), idPromo.getText(), 1));
            groupeEtudiantDAO.persist(new GroupeEtudiant(groupeDAO.find(idGroupe.getValue()).getId(),loginEtdId.getText()));
            ajouteretudmessage.setTextFill(Color.rgb(0, 133, 33));
            ajouteretudmessage.setText("etudiant bien ajouter");
            
        } catch (DataAccessException | SQLException ex) {
            ajouteretudmessage.setTextFill(Color.rgb(220, 0, 0));
            ajouteretudmessage.setText("données saisies invalides");
        } 
       
    }




    public void arrowinputback(){ // pour voir la semaine precedente

        backarrow.setOnMouseClicked( mouseEvent -> {
            getmonday(r);
            for (Label g : l) {
                scene1.getChildren().remove(g);
            }
            if(pickfomation.getValue() == null && pickteacher.getValue() != null ){
                for (Label g : l) {
                    scene1.getChildren().remove(g);
                }

                castdatetimebyteacherlogin(r,pickteacher.getValue(),creneau);


            }
            else if (pickfomation.getValue() != null && pickteacher.getValue() == null ){
                for (Label g : l) {
                    scene1.getChildren().remove(g);
                }

                castdatetimebyformation(r,pickfomation.getValue(),creneau);
            }
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
            if(pickfomation.getValue() == null && pickteacher.getValue() != null ){

                castdatetimebyteacherlogin(w,pickteacher.getValue(),creneau);

            }
            else if (pickfomation.getValue() != null && pickteacher.getValue() == null ){

                castdatetimebyformation(w,pickfomation.getValue(),creneau);
            }
            drawrect();
            setcalendar(w);

            w=w+7;
            r=r+7;
        });

    }


    public void disabledate() {
        md_date.setValue(java.time.LocalDate.now());
        md_date.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }


        });
    }

    public void initens(){


        md_n.valueProperty().addListener((options, oldValue, newValue) ->{
            c.initialize_enseignant(md_date,md_m_f,md_m_d,md_h_f,md_h_d, md_ens,md_c,md_n,md_f);

        });
    }

    public void initSalle(){
        md_bat.valueProperty().addListener((options, oldValue, newValue) ->{
            c.initialize_salle(md_m_f,md_m_d,md_h_f,md_h_d,md_bat, md_date,md_s);

        });
    }
    public void initnature(){
        md_c.valueProperty().addListener((options1, oldValue1, newValue1) ->{
            c.initialize_nature_cours(md_f,md_c,md_n);

        });

    }
    public void initcours(){

        md_f.valueProperty().addListener((options, oldValue, newValue) ->{
            md_n.getItems().removeAll(md_n.getItems());
            c.initialize_cours(md_f,md_c);
            md_c.valueProperty().addListener((options1, oldValue1, newValue1) ->{
                c.initialize_nature_cours(md_f,md_c,md_n);

            });

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
                md_date.valueProperty().addListener((ov, oldValue, newValue) -> md_h_f.valueProperty().addListener((obs, oldValue3, newValue3) ->
                    md_m_f.valueProperty().addListener((obs2, oldValue2, newValue2) -> {

                        c.initialize_batiment(md_m_f,md_m_d,md_h_f,md_h_d,md_bat, md_date);
                        c.initialize_formation(md_m_f,md_m_d,md_h_f,md_h_d,md_f, md_date);
                        if (md_m_f.getValue() != null && md_h_f.getValue() != null) {

                            c.initialize_batiment(md_m_f,md_m_d,md_h_f,md_h_d,md_bat, md_date);
                            c.initialize_formation(md_m_f,md_m_d,md_h_f,md_h_d,md_f, md_date);
                        }
                    })
                ));





    }

    SpinnerValueFactory<Integer> valuehoure = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 1);
    SpinnerValueFactory<Integer> valueminute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);

    SpinnerValueFactory<Integer> valueminute2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);


    public void setPickfomation(){
        c.initialize_pickformation(filiereidd);
        pickfomation.setOnMouseClicked(mouseEvent -> {
            pickteacher.valueProperty().set(null);
            c.initialize_pickformation(pickfomation);
            boolean disable = !pickteacher.isDisabled();
            pickteacher.setDisable(disable);

        });
        pickteacher.setOnMouseClicked(mouseEvent -> {
            pickfomation.valueProperty().set(null);
            c.initialize_pickenseignant(pickteacher);
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

        } else if (e.getSource() == btnabsences) {


            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene5.toFront();
            sceneabs.toFront();


        }
    }

    public void inittableAbsence(){
        TableColumn<Etudiant, String> nomCol//
                = new TableColumn<>("nom");

        TableColumn<Etudiant, String> prenomCol//
                = new TableColumn<>("prenom");

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
                TableRow<Etudiant> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                            && event.getClickCount() == 2) {
                        Etudiant person = listEtudiantId.getSelectionModel().getSelectedItem();
                        setD(person.getNom());
                        setM(person.getLogin());
                        switchtodetailabs();
                    }
                });
                return row ;
            });

        });

        listEtudiantId.getColumns().addAll(nomCol, prenomCol, nbabs);
    }


    public ObservableList<Etudiant> afficherEtudiants() {

        ObservableList<Etudiant> etudiants = null;
        try (EtudiantDAO etudiantDAO = new EtudiantDAO();) {

            etudiants = FXCollections.observableArrayList(etudiantDAO.findbygrp(filiereidd.getValue()));

        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }


    public void clearscene(){
        for (Label g : l) {
            scene1.getChildren().remove(g);

        }

    }

    @FXML
    public  void validatebuttononaction (ActionEvent e){
        r=-7;
        w =7;
        clearscene();

        if(pickfomation.getValue() == null && pickteacher.getValue() != null ) {
            clearscene();
            castdatetimebyteacherlogin(0, pickteacher.getValue(),creneau);
            drawrect();
            setcalendar(0);
        }
        else if (pickfomation.getValue() != null && pickteacher.getValue() == null ){
            clearscene();
            castdatetimebyformation(0, pickfomation.getValue(),creneau);
            drawrect();
            setcalendar(0);
        }
        else {
            if (pickfomation.getValue() == null) {
                pickteacher.getValue();
            }
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DATE, i);
        return calendar;
    }

    public void setcalendar(int z) { //pour afficher les dates sous les jours
        Label[]tlabel = new Label[7];
        tlabel[0] = idlundi;
        tlabel[1] = idmardi;
        tlabel[2] = idmercredi;
        tlabel[3] = idjeudi;
        tlabel[4] = idvendredi;
        tlabel[5] = idsamedi;
        tlabel[6] = iddimanche;
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar2.add(Calendar.DATE, z);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int f = 0; f < 7; f++) {
            tlabel[f].setText(df.format(calendar2.getTime()));
            tlabel[f].setTextAlignment(TextAlignment.CENTER);
            tlabel[f].setTextFill(Color.rgb(255, 255, 255));
            calendar2.add(Calendar.DATE, 1);
        }
    }


    public void castdatetimebyteacherlogin(int w, String login, String[][] cren) { //fonction qui remplie une liste des creneaux d'une semaine
        setI(0);
        try (CreneauxDAO c2 = new CreneauxDAO();){
           setI( c2.castdatetimebyteacherlogin(login, getmonday(w), getmonday(w + 6), cren ,i));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void castdatetimebyformation(int w,String formation, String[][] cren) {//fonction qui remplie une liste des creneaux d'une semaine
        setI(0);
        try (CreneauxDAO c2 = new CreneauxDAO();){
            setI( c2.castdatetimebyformation(formation, getmonday(w), getmonday(w + 6), cren ,i));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public  void addcreneau (ActionEvent e) {
        c.insertcreneau(md_date,md_h_d,md_m_d,md_h_f,md_m_f,md_bat,md_s,md_f,md_c,md_n,md_ens,ajoutermessage);
    }


    @FXML
    public void drawrect()  { //fonction qui dessine l'emlpoie du temps

        Calendar x = Calendar.getInstance();

        for (int  q = 0; q <= i - 1; q++) {
            String message;

            Label cours = new Label();
            l.add(cours);
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[q][0]);
                Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[q][1]);
                x.setTime(date1);
                int dayOfWeek = x.get(Calendar.DAY_OF_WEEK);
                datetopxl(dayOfWeek);
                double hourofday = x.get(Calendar.HOUR_OF_DAY) + (float) x.get(Calendar.MINUTE) / 60;

                int z = datetopxl(dayOfWeek);
                double y = houretopxl(hourofday);
                x.setTime(date2);
                double hourofday2 = x.get(Calendar.HOUR_OF_DAY) + (float) x.get(Calendar.MINUTE) / 60;
                double w2 = houretopxl(hourofday2) - y;
                cours.setTranslateX(z);
                cours.setTranslateY(y);
                cours.setMinWidth(126);
                cours.setMaxWidth(126);
                cours.setMaxHeight(w2);
                cours.setMinHeight(w2);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (creneau[q][4].equals("true")) message = "Oui";
            else message = "Non";
            cours.setText("Salle: " + creneau[q][2] + " " + creneau[q][3] + "\n" + creneau[q][5] + " " + creneau[q][6] + "\nprojecteur: " + message +"\n prof :");

            cours.setTextFill(Color.rgb(255, 255, 255));
            cours.setTextAlignment(TextAlignment.CENTER);
            cours.setAlignment(Pos.CENTER);


            if (creneau[q][6].trim().equals("TP"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(50, 18, 71), CornerRadii.EMPTY, Insets.EMPTY)));
            else if (creneau[q][6].trim().equals("TD"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(5, 52, 14), CornerRadii.EMPTY, Insets.EMPTY)));
            else if (creneau[q][6].trim().equals("CM"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(26, 31, 38), CornerRadii.EMPTY, Insets.EMPTY)));
            int finalR = q;
            cours.setOnMouseClicked (mouseEvent -> {

                setD2(creneau[finalR][0]);
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


    public void affichageinfo()  {
        try(ResponsableDAO responsableDAO = new ResponsableDAO();) {
            nameField.setText(responsableDAO.findbyLogin(LoginController.user1).getPrenom());
            lastnameField.setText(responsableDAO.findbyLogin(LoginController.user1).getNom());
            emailField.setText(responsableDAO.findbyLogin(LoginController.user1).getEmail());
            passwordField.setText(LoginController.psswrd);
            loginField.setText(LoginController.user1);

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }


    }
    @FXML
    void editPass(ActionEvent event) {
        swithtoPasseditscene();

    }




}
