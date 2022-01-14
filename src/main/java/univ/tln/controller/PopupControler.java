package univ.tln.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import univ.tln.daos.AbsenceDAO;
import univ.tln.daos.CreneauxDAO;
import univ.tln.daos.EtudiantDAO;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.utilisateurs.Absence;
import univ.tln.entities.utilisateurs.Etudiant;
import univ.tln.entities.utilisateurs.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class PopupControler implements Initializable {
    CreneauxDAO c = new CreneauxDAO();
    String grp;
    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "oui",
                    "non"
            );

    @FXML
    private TextField batimentold;
    @FXML
    private TextField datedebutold;
    @FXML
    private TextField salleold;
    @FXML
    private TextField datefinold;
    @FXML
    private Button supbtn;
    @FXML
    private ComboBox<String> md_bat;

    @FXML
    private ComboBox<String> md_vp;
    @FXML
    private DatePicker md_date;

    @FXML
    private Spinner<Integer> md_h_d;

    @FXML
    private Spinner<Integer> md_h_f;

    @FXML
    private Label loginmessage;

    @FXML
    private Spinner<Integer> md_m_d;

    @FXML
    private Spinner<Integer> md_m_f;

    @FXML
    private ComboBox<String> md_s;
    @FXML
    private AnchorPane abscence;

    @FXML
    private AnchorPane update;

    @FXML
    private Button btnabs;

    @FXML
    private Button btnupdate;
    @FXML
    private TableView listEtudiantId;

    @FXML
    private Button Applybtn;

    @FXML
    private Button clbtn;

    @FXML
    private PasswordField newPassField;

    @FXML
    private PasswordField oldPassField;


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initold();
        md_h_d.setValueFactory(valuehoure);
        md_m_d.setValueFactory(valueminute);
        setspinner();
        initBat();
        initSalle();
        initAbsence();
        disabledate();


    }

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


    public void initold()  {
        if(TeacherController.old[0]!=null){
            datedebutold.setText(TeacherController.old[0]);
            datefinold.setText(TeacherController.old[1]);
            batimentold.setText(TeacherController.old[2]);
            salleold.setText(TeacherController.old[3]);

            grp=TeacherController.old[7];
        }else {

            datedebutold.setText(ManagerController.old[0]);
            datefinold.setText(ManagerController.old[1]);
            batimentold.setText(ManagerController.old[2]);
            salleold.setText(ManagerController.old[3]);
            grp=ManagerController.old[5];
        }
    }
    public void cancelbtnOnAction() {
        Stage stage = (Stage) supbtn.getScene().getWindow();

        try(CreneauxDAO d = new CreneauxDAO();) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/univ/tln/teacherscreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1305, 782);

            LoginController.managerstage.setScene(scene);
            LoginController.managerstage.show();



            d.RemoveCreneauByDated(TeacherController.d1);
            TeacherController teachercontroller = loader.getController();
            teachercontroller.updatewindow();


        } catch (IOException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }

        stage.close();

    }


    public void cancelbtnOnAction2(ActionEvent e) {
        Stage stage = (Stage) supbtn.getScene().getWindow();

        try( CreneauxDAO d = new CreneauxDAO();) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/univ/tln/managerscreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1305, 782);

            LoginController.managerstage.setScene(scene);
            LoginController.managerstage.show();

            d.RemoveCreneauByDated(ManagerController.d2);
            ManagerController managerController = loader.getController();
            managerController.validatebuttononaction(e);


        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }

        stage.close();

    }

    @FXML
    public void validateupdate(ActionEvent e) throws IOException, ParseException {


        Stage stage = (Stage) btnupdate.getScene().getWindow();
        try(CreneauxDAO d = new CreneauxDAO();) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/univ/tln/teacherscreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1305, 782);

            LoginController.managerstage.setScene(scene);
            LoginController.managerstage.show();




            d.updateCreneaux(md_date, md_h_d, md_m_d, md_h_f, md_m_f, md_bat, md_s, TeacherController.d1);
            TeacherController t =  loader.getController();
            t.updatewindow();



        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }

        stage.close();

    }


    @FXML
    public void validateupdate2(ActionEvent e) {


        Stage stage = (Stage) btnupdate.getScene().getWindow();
        try (CreneauxDAO d = new CreneauxDAO();) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/univ/tln/managerscreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1305, 782);

            LoginController.managerstage.setScene(scene);
            LoginController.managerstage.show();




            d.updateCreneaux(md_date, md_h_d, md_m_d, md_h_f, md_m_f, md_bat, md_s, ManagerController.d2);
            ManagerController managerController = loader.getController();
            managerController.validatebuttononaction(e);



        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }

        stage.close();

    }


    public void setspinner() {
        md_vp.setDisable(true);
        md_bat.setDisable(true);
        md_s.setDisable(true);

        md_h_d.valueProperty().addListener((obs, oldValue, newValue) -> {
            SpinnerValueFactory<Integer> valuehoure2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue, 19, 1);
            md_h_f.setValueFactory(valuehoure2);


        });
        md_m_d.valueProperty().addListener((obs, oldValue, newValue) -> {
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
        });
        md_bat.valueProperty().addListener((obs, oldValue, newValue) ->{
            if (md_bat!=null)
                md_s.setDisable(false);
        });

         md_h_f.valueProperty().addListener((obs, oldValue3, newValue3) ->
            md_m_f.valueProperty().addListener((obs2, oldValue2, newValue2) -> {

                if (md_m_f.getValue() != null && md_h_f.getValue() != null) {
                    md_vp.setDisable(false);
                    md_vp.setItems(options);
                }
            })
        );
    }

    public void initBat() {
        md_vp.setEditable(true);
        md_vp.valueProperty().addListener((options, oldValue, newValue) ->
                c.initialize_batiment(md_m_f,md_m_d,md_h_f,md_h_d,md_vp,md_bat, md_date)

        );
    }
    public void initSalle() {
        md_bat.setEditable(true);
        md_bat.valueProperty().addListener((options, oldValue, newValue) ->
            c.initialize_salle(md_m_f, md_m_d, md_h_f, md_h_d,md_vp ,md_bat, md_date, md_s)

        );
    }

    public void handleclicks(ActionEvent e) { //pour changer l'ecran

        if (e.getSource() == btnupdate) {
            btnupdate.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            update.toFront();

        }
        if (e.getSource() == btnabs) {
            abscence.toFront();
        }
    }

    public void initAbsence()  {

        listEtudiantId.setEditable(true);
        TableColumn<Utilisateur, String> nomCol//
                = new TableColumn<>("nom");

        TableColumn<Utilisateur, String> prenomCol//
                = new TableColumn<>("prenom");

        TableColumn<Etudiant, Boolean> absenceCol//
                = new TableColumn<>("absence");

        //nom

        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        nomCol.setCellFactory(TextFieldTableCell.<Utilisateur>forTableColumn());

        nomCol.setMinWidth(200);

        // prenom

        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        prenomCol.setCellFactory(TextFieldTableCell.<Utilisateur>forTableColumn());

        prenomCol.setMinWidth(200);


        // ==== absence (CHECH BOX) ===
        absenceCol.setCellValueFactory(param -> {
            Etudiant etudiant = param.getValue();



            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty();
            String login = etudiant.getLogin();
            // verifier si etudiant by login est dans la table absence si oui return true else false

            try {
                AbsenceDAO absenceDAO = new AbsenceDAO();
                booleanProp.set(absenceDAO.find(login, TeacherController.d1));


                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @SneakyThrows
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        // appel de la methode qui permet ajout absence en fonction de login et date creneau

                        Absence absence = new Absence(TeacherController.d1, TeacherController.b1, TeacherController.s1, TeacherController.g1, login);
                        if (Boolean.TRUE.equals(newValue))
                            absenceDAO.persist(absence);
                        if (Boolean.FALSE.equals(newValue))
                            absenceDAO.remove(absence);
                    }
                });
            }
            catch(SQLException | DataAccessException e){
                e.printStackTrace();
            }
            return booleanProp;
        });

        absenceCol.setEditable(true);
        absenceCol.setCellFactory(p -> {
            CheckBoxTableCell<Etudiant, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        ObservableList<Utilisateur> list = null;
        try {
            list = afficherEtudiants();
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }

        listEtudiantId.setItems(list);
        listEtudiantId.getColumns().addAll(nomCol, prenomCol, absenceCol);
    }


    public ObservableList<Utilisateur> afficherEtudiants() throws DataAccessException, SQLException {

        try( EtudiantDAO etudiantDAO = new EtudiantDAO();) {


            return FXCollections.observableArrayList(etudiantDAO.findbygrps(grp));
        }
    }

    SpinnerValueFactory<Integer> valuehoure = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 1);
    SpinnerValueFactory<Integer> valueminute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);

}
