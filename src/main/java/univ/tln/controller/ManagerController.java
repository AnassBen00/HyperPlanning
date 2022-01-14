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
import univ.tln.entities.creneaux.Creneau;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.*;
import static com.google.common.hash.Hashing.sha256;


public class ManagerController implements Initializable {

    CreneauxDAO c = new CreneauxDAO();
    @Setter
    static String [] old=new String[8];
    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "oui",
                    "non"
            );



    private  String[][] creneau = new String[60][8];




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
    private ComboBox<String> md_vp;

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
    public static String d2,f2 ;
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
        resetvalues();
        disabledate();
        initnale();
        setspinner();
        initBat();
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
        //drawrect(); //on dessine l'emploie du temps
        arrowinputback();
        arrowinputfront();
        setcalendar(0);
        c.initialize_pickformation(ifFormationComboBox);
    }
    /**
     * Cette fonction initialise le login le nom et le rôle d'un utilisateur
     */
    public void initnale() { //fonction qui remplie une liste des creneaux d'une semaine

        name.setText("Login: " + LoginController.user1 + "\n" +LoginController.name1);
        name.setTextFill(Color.rgb(255, 255, 255));

    }


    /**
     *Cette fonction concerne la partie ajouter créneaux pour un responsable.
     * Mdhf représente l'heure début, et mdmd représente minutes début
     */
    public void setminuteandhour() {

        SpinnerValueFactory<Integer> valuehoure = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 1);
        SpinnerValueFactory<Integer> valueminute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);
        md_h_d.setValueFactory(valuehoure);
        md_m_d.setValueFactory(valueminute);

    }
//la partie ajouter utilisateur

    /**
     * Cette fonction sera initialisée les différentes filières d'un étudiant
     */

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



    /**
     * Cette fonction concerne partie de la création du compte de l'étudiant, id niveaux représente le niveau de l'étudiant
     */
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
    /**
     *cette fonction concerne la partie création étudiant elle sert à initialiser les groupes
     */

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



    /**
     * Cette fonction est reliée à au bouton qui permet d'ajouter un enseignant elle appelle la DAO utilisateur pour créer l'utilisateur
     * et après la DAO enseignant pour créer l'enseignant,
     * elle a aussi une fonction pour hacher le mot de passe,
     * si tout se passe bien elle affiche le message enseignants bien ajoutez sinon elle affiche le message donné saisie invalide
     */

    @FXML
    public void ConfirmAction (ActionEvent e)  {

        try(UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            EnseignantDAO enseignantDAO = new EnseignantDAO();) {
            CoursDAO coursDAO = new CoursDAO();
            if(loginId.getText() !=null && passwordId.getText()!=null && nomId.getText() !=null && prenomId.getText() !=null && emailId.getText() !=null && idcoursText.getText() !=null && idnatureComboBox.getValue() !=null && ifFormationComboBox.getValue() !=null) {


                utilisateurDAO.persist(new Utilisateur(loginId.getText(), sha256()
                        .hashString(passwordId.getText(), StandardCharsets.UTF_8)
                        .toString(), nomId.getText(), prenomId.getText(), emailId.getText()));
                enseignantDAO.persist(new Enseignant(loginId.getText(), sha256()
                        .hashString(passwordId.getText(), StandardCharsets.UTF_8)
                        .toString(), nomId.getText(), prenomId.getText(), emailId.getText()));
                coursDAO.insertCours(new Cours(idnatureComboBox.getValue(), idcoursText.getText(), loginId.getText()));
                coursDAO.insertGroupeCours(ifFormationComboBox.getValue().toString(), coursDAO.find(idcoursText.getText()));
                ajouterprofmessage.setTextFill(Color.rgb(0, 133, 33));
                ajouterprofmessage.setText("enseignant bien ajouter");
            }
            else  {
                ajouterprofmessage.setTextFill(Color.rgb(220, 0, 0));
                ajouterprofmessage.setText("données saisies invalides");
            }
        } catch (DataAccessException | SQLException ex) {
            ajouterprofmessage.setTextFill(Color.rgb(220, 0, 0));
            ajouterprofmessage.setText("données saisies invalides");
        }
    }

    /**
     * Pareil que la dernière fonction cette fois-ci pour créer un d'étudiant
     */
    @FXML
    public void ConfirmEtudAction ()  {
        try(        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
                    EtudiantDAO etudiantDAO = new EtudiantDAO();
                    FiliereDAO filiereDAO = new FiliereDAO();
                    GroupeEtudiantDAO groupeEtudiantDAO = new GroupeEtudiantDAO();
                    GroupeDAO groupeDAO = new GroupeDAO();) {
            if(loginEtdId.getText() !=null && passwordEtdId.getText()!=null && nomEtdId.getText() !=null && prenomEtdId.getText() !=null && emailEtdId.getText() !=null && idNiveau.getValue() !=null && idPromo.getText() !=null) {


                utilisateurDAO.persist(new Utilisateur(loginEtdId.getText(), sha256()
                        .hashString(passwordEtdId.getText(), StandardCharsets.UTF_8)
                        .toString(), nomEtdId.getText(), prenomEtdId.getText(), emailEtdId.getText()));
                etudiantDAO.persist(new Etudiant(loginEtdId.getText(), sha256()
                        .hashString(passwordEtdId.getText(), StandardCharsets.UTF_8)
                        .toString(), nomEtdId.getText(), prenomEtdId.getText(), emailEtdId.getText(), idNiveau.getValue(), idPromo.getText(), 1));

                groupeEtudiantDAO.persist(new GroupeEtudiant(groupeDAO.find(idGroupe.getValue()).getId(), loginEtdId.getText()));
                ajouteretudmessage.setTextFill(Color.rgb(0, 133, 33));
                ajouteretudmessage.setText("etudiant bien ajouter");
            }
            else  {
                ajouteretudmessage.setTextFill(Color.rgb(220, 0, 0));
                ajouteretudmessage.setText("données saisies invalides");
            }

        } catch (DataAccessException | SQLException ex) {
            ajouteretudmessage.setTextFill(Color.rgb(220, 0, 0));
            ajouteretudmessage.setText("données saisies invalides");
        } 
       
    }


    /**
     * cette partie concerne les flèches qui permettent d'accéder à la semaine prochaine et la semaine dernière d'un planning
     *
     * Chaque fois qu'on appuie sur la flèche elle efface tout les fils de la scène 1 regarde si l'utilisateur veut filtrer par formation dans ce cas-là on appelle la fonction castdatetimebyformation
     *
     * et s'il veut filtrer par enseignant on appelle la fonction castdatetimebyteacherlogin
     *
     * À la fin quand notre tableau de créneau est bien rempli on appelle la fonction drawrect
     *
     * qui permet d'afficher le planning
     *
     *
     */

    public void arrowinputback(){ // pour voir la semaine precedente

        backarrow.setOnMouseClicked( mouseEvent -> {
            getmonday(r);
          clearscene();
            if(pickfomation.getValue() == null && pickteacher.getValue() != null ){
                clearscene();

                castdatetimebyteacherlogin(r,pickteacher.getValue(),creneau);

                drawrect();
            }
            else if (pickfomation.getValue() != null && pickteacher.getValue() == null ){
                clearscene();

                castdatetimebyformation(r,pickfomation.getValue(),creneau);
                drawrect();
            }
            else if (pickfomation.getValue() == null && pickteacher.getValue() == null ){
                clearscene();
            }

            setcalendar(r);


            r=r-7;
            w=w-7;
        });

    }

    public void arrowinputfront(){ //pour voir la semaine suivante

        frontarrow.setOnMouseClicked( mouseEvent -> {
            getmonday(w);
            getmonday(w+7);

            clearscene();
            if(pickfomation.getValue() == null && pickteacher.getValue() != null ){
                clearscene();
                castdatetimebyteacherlogin(w,pickteacher.getValue(),creneau);
                drawrect();

            }
            else if (pickfomation.getValue() != null && pickteacher.getValue() == null ){
                clearscene();
                castdatetimebyformation(w,pickfomation.getValue(),creneau);
                drawrect();
            }
            else if (pickfomation.getValue() == null && pickteacher.getValue() == null ){
                clearscene();
            }

            setcalendar(w);

            w=w+7;
            r=r+7;
        });
    }

    /**
     * Cette fonction concerne la partie ajouter créneau elle permet de mettre la date de aujourd'hui comme valeur par défaut quand un utilisateur va choisir une date et elle ne laisse pas le choix de choisir une date inférieure à la date de aujourd'hui
     */


    public void disabledate() {
        md_date.setValue(java.time.LocalDate.now());
        md_date.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                if (date.getDayOfWeek() == DayOfWeek.SUNDAY //
                        || date.compareTo(today) < 0 //
                ) {
                    setDisable(true);
                }
            }
        });
    }

    public void initens(){


        md_n.valueProperty().addListener((options, oldValue, newValue) ->
            c.initialize_enseignant(md_date,md_m_f,md_m_d,md_h_f,md_h_d, md_ens,md_c,md_n,md_f)
        );
    }

    public void initBat(){
        md_vp.valueProperty().addListener((options, oldValue, newValue) ->
                c.initialize_batiment(md_m_f,md_m_d,md_h_f,md_h_d,md_vp,md_bat, md_date)

        );
    }

    public void initSalle(){
        md_bat.valueProperty().addListener((options, oldValue, newValue) ->
            c.initialize_salle(md_m_f,md_m_d,md_h_f,md_h_d,md_vp,md_bat, md_date,md_s)

        );
    }
    public void initnature(){
        md_c.valueProperty().addListener((options1, oldValue1, newValue1) ->
            c.initialize_nature_cours(md_f,md_c,md_n)

        );

    }
    public void initcours(){

        md_f.valueProperty().addListener((options, oldValue, newValue) ->{
            md_n.getItems().removeAll(md_n.getItems());
            md_c.setDisable(false);
            c.initialize_cours(md_f,md_c);
            md_c.valueProperty().addListener((options1, oldValue1, newValue1) ->
                c.initialize_nature_cours(md_f,md_c,md_n)

            );

        });
    }

    /**
     * cette fonction concerne la partie ajouter créneau , on voulait que notre formulaire soit réactif donc dans cette fonction si un utilisateur augmente l’heur début d'un cours ça va automatiquement augmenter l’heur fin de ce cours et cette valeur ça va être la valeur minimum, comme ça l’heur début sera toujours supérieur à l'heure fin et c'est que après avoir choisi la date l'heure début et l’heure fin qu'on donne accès aux utilisateurs de choisir le bâtiment et la formation comme ça utilisateur ne pourra pas choisir un créneau dans une salle déjà occupé tout ça est géré dans la DAO créneau
     */
    public void setspinner(){
        md_vp.setDisable(true);
        md_bat.setDisable(true);
        md_s.setDisable(true);
        md_f.setDisable(true);
        md_c.setDisable(true);
        md_n.setDisable(true);
        md_ens.setDisable(true);

        md_h_d.valueProperty().addListener((obs, oldValue, newValue) ->{
            SpinnerValueFactory<Integer> valuehoure2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue, 19, 1);
            md_h_f.setValueFactory(valuehoure2);

        });
        md_m_d.valueProperty().addListener((obs, oldValue, newValue) ->{
            SpinnerValueFactory<Integer> valuehoure2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue, 59, 1);
            md_m_f.setValueFactory(valuehoure2);

        });


        md_h_f.valueProperty().addListener((obs, oldValue, newValue) ->{
            if(md_h_f.getValue()>md_h_d.getValue()){
                SpinnerValueFactory<Integer> valuehoure3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,1);
                md_m_f.setValueFactory(valuehoure3);
            }

        });
        md_vp.valueProperty().addListener((obs, oldValue, newValue) ->{
            if (md_vp!=null)
                md_bat.setDisable(false);
            else  md_bat.setDisable(true);
        });
        md_bat.valueProperty().addListener((obs, oldValue, newValue) ->{
            if (md_bat!=null)
                md_s.setDisable(false);
            else md_s.setDisable(false);
        });

        md_f.valueProperty().addListener((obs, oldValue, newValue) ->{
            if (md_f!=null)
                md_c.setDisable(false);
            else md_c.setDisable(true);
        });
        md_c.valueProperty().addListener((obs, oldValue, newValue) ->{
            if (md_c!=null)
                md_n.setDisable(false);
            else md_n.setDisable(true);
        });
        md_n.valueProperty().addListener((obs, oldValue, newValue) ->{
            if (md_n!=null)
                md_ens.setDisable(false);
           else md_ens.setDisable(true);
        });
        md_m_f.valueProperty().addListener((obs, oldValue, newValue) ->{
            md_vp.setDisable(false);
            md_f.setDisable(false);
            c.initialize_formation(md_m_f,md_m_d,md_h_f,md_h_d,md_f, md_date);
        });


                md_h_f.valueProperty().addListener((obs, oldValue3, newValue3) ->
                    md_m_f.valueProperty().addListener((obs2, oldValue2, newValue2) -> {


                        c.initialize_batiment(md_m_f,md_m_d,md_h_f,md_h_d,md_vp,md_bat, md_date);
                        c.initialize_formation(md_m_f,md_m_d,md_h_f,md_h_d,md_f, md_date);
                        if (md_m_f.getValue() != null && md_h_f.getValue() != null) {
                            md_vp.setItems(options);


                        }
                    })
                );

    }

    /**
     * Cette fonction concerne la partie filtrage du planning,
     * si on choisit par exemple de filtrer par formation ça va automatiquement désactiver l'option de filtrer par enseignant
     * et après ça va appelez la méthode initialize_pickformation qui prend paramètre pickformation
     * et le remplit avec une liste des formations,
     * la même chose arrives quand on choisit de filtrer par enseignant
     * cette fois-ci on appelle la méthode initialize_pickenseignant
     * qui va prendre en paramètre pickteacherR et le remplir avec les id des enseignants disponibles
     */
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

    /**
     *
     * Cette fonction permet de naviguer dans l'application pour accéder au compte de l'utilisateur, au planning, À la partie ajouter où il peut ajouter un créneau, et à la partie absences où il peut regarder les absences des formations
     */

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

    /**
     * Cette fonction permet de nettoyer le planning et enlever tous les créneaux ajoutés
     */

    public void clearscene(){
        for (Label g : l) {
            scene1.getChildren().remove(g);

        }

    }


    /**
     *
     * Ce bouton concerne la partie planning et il sert à filtrer le planning par formation ou par enseignant chaque fois qu'on clique sur ce bouton on revient à la date actuelle, on nettoie le planning en utilisant la fonction précédente, on remplit notre créneau par rapport au filtrage choisi, et à la fin on appelle la fonction qui permet d'afficher le planning
     */


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


    /**
     * Cette fonction prend en paramètre l'heure d’un certains créneaux e et retourne le pixel correspondant à cette heure (dans notre application on utilise une image de planning)
     * @param a c'est lheure debut ou fin d'un creneau
     * @return le pixel correspondent
     */
    public double houretopxl(double a) {
        return (a - 8) * 45.9 + 47;
    }

    /**Cette fonction prend paramètre la date d'un créneau et retourne le pixel correspondant à ce créneau Par exemple le pixel 304 correspond au jour lundi ou plutôt le pixel de lundi
     * @param a  la date d'un creeau
     * @return le pixel correspondent
     */
    public int datetopxl(int a) {
        return (a - 2) * 126 + 125;
    }


    /**Cette fonction retourne le lundi d'une semaine
     * @param i on utilise I pour parcourir les semaines par exemple si I égal à 0 cette fonction ça va retourner le lundi de cette semaine si I égal à moins 7 ça retourne le lundi de la semaine dernière siii égal à plus 7 ça retourne le lundi de la semaine prochaine et si égal à moins 14 ça retourne le lundi de la semaine d'avant dernière et cetera
     * @return le lundi de la semaine q'on veut
     */
    public Calendar getmonday(int i) { //retoune le lundi de cette semaine
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DATE, i);
        return calendar;
    }

    /** Cette fonction permet d'initialiser les valeurs des dates sous les jours
     * @param z Z permet de parcourir les semaines donc 6Z égal à 0 tlabel aura les jours de cette semaine si Z égal à moins 7 elle aura les jours de la semaine dernière
     */
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


    /**
     * Cette fonction concerne la partie planning, elle appelle la fonction de la DAOcréneau, cette fonction utilise une requête SQL qui permet D'initialiser un créneau d'un enseignant entre 2 dates données et à partir de son login
     * @param w W dans cette fonction sert à naviguer dans le planning par défaut W égal à 0 donc la fonction get mondays renvoi le lundi de cette semaine et get mondays plus 6 renvoie le dimanche de la semaine
     * Quand on clique sur la flèche suivante où précédente on incrémente ou bien on décrémente W comme ça on aura accès au lundi et dimanche de la semaine précédente ou des semaines suivantes
     * @param login c'est le login de l'enseignant
     * @param cren Créneau représente les créneaux de la semaine,Quand on appelle cette fonction on remplit le créneau avec le planning de la semaine sélectionnée
     */

    public void castdatetimebyteacherlogin(int w, String login, String[][] cren) { //fonction qui remplie une liste des creneaux d'une semaine
        setI(0);
        try (CreneauxDAO c2 = new CreneauxDAO();){
           setI( c2.castdatetimebyteacherlogin(login, getmonday(w), getmonday(w + 6), cren ,i));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Elle fait exactement la même chose que la fonction dernière cette fois-ci avec le nom d'une formation
     * @param w
     * @param formation
     * @param cren
     */
    public void castdatetimebyformation(int w,String formation, String[][] cren) {//fonction qui remplie une liste des creneaux d'une semaine
        setI(0);
        try (CreneauxDAO c2 = new CreneauxDAO();){
            setI( c2.castdatetimebyformation(formation, getmonday(w), getmonday(w + 6), cren ,i));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *Cette fonction concerne le bouton ajouter créneau elle prend en paramètre la date l'heure de début l'heure de fin le nom du bâtiment le numéro de la salle le nom de la formation le nom du cours la nature du cours et l'enseignant
     *
     */
    @FXML
    public  void addcreneau (ActionEvent e) {
        c.insertcreneau(md_date,md_h_d,md_m_d,md_h_f,md_m_f,md_bat,md_s,md_f,md_c,md_n,md_ens,ajoutermessage);
        resetvalues();

    }

    public void resetvalues(){
        SpinnerValueFactory<Integer> valuehoure = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 1);
        SpinnerValueFactory<Integer> valueminute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);
        md_date.setValue(java.time.LocalDate.now());
        md_h_d.setValueFactory(valuehoure);
        md_m_d.setValueFactory(valueminute);
        md_s.setDisable(true);
        md_c.setDisable(true);
        md_n.setDisable(true);
        md_vp.setDisable(true);
        md_bat.setDisable(true);
        md_f.setDisable(true);
        md_ens.setDisable(true);
        ajouterprofmessage.setText("");

    }

    /**
     * Cette fonction crée un Label avec une largeur fixe et une hauteur qui est égal à la durée du cours en utilisant la fonction hourofday
     *
     * donc on prend pixel de heures fin moins le pixel de heures début et on aura la hauteur du label
     *
     * on positionne alors ce label dans un axe XY en utilisant les 2 fonctions datetopxl et houretopxl
     *
     * on choisit la couleur du Labal en regardant le type de cours dans la table créneau
     *
     * et si on clique sur ce label on ouvre une autre fenêtre et c'est là pour on pourra modifier la date du cours
     */
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


            cours.setText("Salle: " + creneau[q][2] + " " + creneau[q][3] + "\n" + creneau[q][5] + " " + creneau[q][6] + "\nprojecteur: " + creneau[q][4] +"\n"+creneau[q][7]);

            cours.setTextFill(Color.rgb(255, 255, 255));
            cours.setTextAlignment(TextAlignment.CENTER);
            cours.setAlignment(Pos.CENTER);


            if (creneau[q][6].trim().equals("TP"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(41, 141, 141), CornerRadii.EMPTY, Insets.EMPTY)));
            else if (creneau[q][6].trim().equals("TD"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(218, 157, 38), CornerRadii.EMPTY, Insets.EMPTY)));
            else if (creneau[q][6].trim().equals("CM"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(26, 31, 38), CornerRadii.EMPTY, Insets.EMPTY)));
            else if (creneau[q][6].trim().equals("EX"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(178, 111, 217), CornerRadii.EMPTY, Insets.EMPTY)));
            int finalR = q;
            cours.setOnMouseClicked (mouseEvent -> {
                setD2(creneau[finalR][0]);
                for (int f =0;f<7;f++){
                    old[f]=creneau[finalR][f];

                }
                switchtopopupscene();
            });

            scene1.getChildren().add(cours);
        }
    }

    /**
     * Quand on clique sur un créneau dans le planning ça nous affiche une autre fenêtre c'est dans cette fenêtre où on peut modifier le planning
     */


    public void switchtopopupscene() { // on change l'ecran si c'est bon

        try {
            Parent root = FXMLLoader.load(App.class.getResource("Popupscene4.fxml"));

            Stage managerstage = new Stage();

            Scene scene = new Scene(root, 919, 667);


            managerstage.setScene(scene);
            managerstage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Dans la partie absence si on clique sur l'absence d'un étudiant ça va nous ouvrir une autre fenêtre c'est là où on pourrait justifier l'absence de cet étudiant et Regardez tous ces absences
     */

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

    /**
     * Dans la partie mon compte si on clique sur le bouton à côté du mot de passe avant nous ouvrir une autre fenêtre qui va nous permettre de changer le mot de passe
     */

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

    /**
     * Cette fonction permet d'afficher le nom le prénom le mail le mot de passe masqué et le login de l'utilisateur
     */

    public void affichageinfo()  {
        try(ResponsableDAO responsableDAO = new ResponsableDAO();) {
            nameField.setText(responsableDAO.findbyLogin(LoginController.user1).getPrenom());
            lastnameField.setText(responsableDAO.findbyLogin(LoginController.user1).getNom());
            emailField.setText(responsableDAO.findbyLogin(LoginController.user1).getEmail());
            passwordField.setText(LoginController.psswrd);
            loginField.setText(LoginController.user1);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    @FXML
    void editPass(ActionEvent event) {
        swithtoPasseditscene();

    }




}
