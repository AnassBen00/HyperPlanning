package univ.tln;

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
import javafx.util.Callback;
import lombok.SneakyThrows;
import univ.tln.daos.AbsenceDAO;
import univ.tln.daos.CreneauxDAO;
import univ.tln.daos.EtudiantDAO;
import univ.tln.entities.utilisateurs.Absence;
import univ.tln.entities.utilisateurs.Etudiant;
import univ.tln.entities.utilisateurs.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;


public class PopupControler implements Initializable {
    CreneauxDAO c = new CreneauxDAO();

    private TeacherController teacherController;

    @FXML
    private Button supbtn;
    @FXML
    private ComboBox<String> md_bat;

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

    private Date date_creneau;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        md_h_d.setValueFactory(valuehoure);
        md_m_d.setValueFactory(valueminute);
        setspinner();
        initSalle();
        initAbsence();
        System.out.println("date creneau: ----" + ManagerController.d2);

    }

    public void cancelbtnOnAction(ActionEvent e) throws ParseException, IOException {
        Stage stage = (Stage) supbtn.getScene().getWindow();
        try {
            // Parent root = FXMLLoader.load(App.class.getResource("managerscreen.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("teacherscreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1305, 782);

            LoginController.managerstage.setScene(scene);
            LoginController.managerstage.show();

            CreneauxDAO d = new CreneauxDAO();
            System.out.println(TeacherController.d1);

            d.RemoveCreneauByDated(TeacherController.d1);
            TeacherController T = new TeacherController();
            TeacherController teacherController = loader.getController();
            teacherController.updatewindow();

            //System.out.println("yupi");

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        stage.close();

    }


    public void cancelbtnOnAction2(ActionEvent e) throws ParseException, IOException {
        Stage stage = (Stage) supbtn.getScene().getWindow();
        try {
            // Parent root = FXMLLoader.load(App.class.getResource("managerscreen.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("managerscreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1305, 782);

            LoginController.managerstage.setScene(scene);
            LoginController.managerstage.show();

            CreneauxDAO d = new CreneauxDAO();
            System.out.println("en creneau" + ManagerController.d2);

            d.RemoveCreneauByDated(ManagerController.d2);
            ManagerController T = new ManagerController();
            ManagerController managerController = loader.getController();
            managerController.validatebuttononaction(e);

            //System.out.println("yupi");

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        stage.close();

    }

    @FXML
    public void validateupdate(ActionEvent e) throws IOException, ParseException {
        //TODO : ahmed a ecrire le code ici avant

        Stage stage = (Stage) btnupdate.getScene().getWindow();
        try {
            // Parent root = FXMLLoader.load(App.class.getResource("managerscreen.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("teacherscreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1305, 782);

            LoginController.managerstage.setScene(scene);
            LoginController.managerstage.show();

            CreneauxDAO d = new CreneauxDAO();
            System.out.println(TeacherController.d1);

            d.updateCreneaux(md_date, md_h_d, md_m_d, md_h_f, md_m_f, md_bat, md_s, TeacherController.d1);
            TeacherController T = new TeacherController();
            TeacherController teacherController = loader.getController();
            teacherController.updatewindow();

            //System.out.println("yupi");

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        stage.close();
        System.out.println("i work");
    }


    @FXML
    public void validateupdate2(ActionEvent e) throws IOException, ParseException {
        //TODO : ahmed a ecrire le code ici avant

        Stage stage = (Stage) btnupdate.getScene().getWindow();
        try {
            // Parent root = FXMLLoader.load(App.class.getResource("managerscreen.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("managerscreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1305, 782);

            LoginController.managerstage.setScene(scene);
            LoginController.managerstage.show();

            CreneauxDAO d = new CreneauxDAO();
            System.out.println(TeacherController.d1);

            d.updateCreneaux(md_date, md_h_d, md_m_d, md_h_f, md_m_f, md_bat, md_s, ManagerController.d2);
            ManagerController T = new ManagerController();
            ManagerController managerController = loader.getController();
            managerController.validatebuttononaction(e);

            //System.out.println("yupi");

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException | SQLException ex) {
            ex.printStackTrace();
        }

        stage.close();
        System.out.println("i work");
    }


    public void setspinner() {

        md_h_d.valueProperty().addListener((obs, oldValue, newValue) -> {
            SpinnerValueFactory<Integer> valuehoure2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue, 19, 1);
            md_h_f.setValueFactory(valuehoure2);


        });
        md_m_d.valueProperty().addListener((obs, oldValue, newValue) -> {
            SpinnerValueFactory<Integer> valuehoure2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue, 59, 1);
            md_m_f.setValueFactory(valuehoure2);


        });
        md_date.valueProperty().addListener((ov, oldValue, newValue) -> {
            md_h_f.valueProperty().addListener((obs, oldValue3, newValue3) -> {
                md_m_f.valueProperty().addListener((obs2, oldValue2, newValue2) -> {
                    try {

                        c.initialize_batiment(md_m_f, md_m_d, md_h_f, md_h_d, md_bat, md_date);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (md_m_f.getValue() != null && md_h_f.getValue() != null) {
                            /*
                            System.out.println(md_m_f.getValue());
                            System.out.println(md_m_d.getValue());
                            System.out.println(md_h_f.getValue());
                            System.out.println(md_h_d.getValue());
                            System.out.println(md_bat.getValue());
                            System.out.println(md_date.getValue());
*/

                        try {
                            c.initialize_batiment(md_m_f, md_m_d, md_h_f, md_h_d, md_bat, md_date);
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

    public void initSalle() {
        md_bat.setEditable(true);
        md_bat.valueProperty().addListener((options, oldValue, newValue) -> {
            try {
                c.initialize_salle(md_m_f, md_m_d, md_h_f, md_h_d, md_bat, md_date, md_s);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
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

    public void initAbsence() {

        listEtudiantId.setEditable(true);
        TableColumn<Utilisateur, String> nomCol//
                = new TableColumn<Utilisateur, String>("nom");

        TableColumn<Utilisateur, String> prenomCol//
                = new TableColumn<Utilisateur, String>("prenom");

        TableColumn<Etudiant, Boolean> absenceCol//
                = new TableColumn<Etudiant, Boolean>("absence");

        //nom

        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        nomCol.setCellFactory(TextFieldTableCell.<Utilisateur>forTableColumn());

        nomCol.setMinWidth(200);

        // prenom

        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        prenomCol.setCellFactory(TextFieldTableCell.<Utilisateur>forTableColumn());

        prenomCol.setMinWidth(200);


        // ==== absence (CHECH BOX) ===
        absenceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Etudiant, Boolean>, ObservableValue<Boolean>>() {

            @SneakyThrows
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Etudiant, Boolean> param) {
                Etudiant etudiant = param.getValue();
                AbsenceDAO absenceDAO = new AbsenceDAO();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty();
                String login = etudiant.getLogin();
                // verifier si etudiant by login est dans la table absence si oui return true else false

                if (absenceDAO.find(login,teacherController.d1)) {
                    booleanProp.set(true);
                } else {

                    booleanProp.set(false);
                }

                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @SneakyThrows
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        // appel de la methode qui permet ajout absence en fonction de login et date creneau

                        Absence absence = new Absence(login, TeacherController.d1);
                        if (newValue == true)
                            absenceDAO.persist(absence);
                        if (newValue == false)
                            absenceDAO.remove(absence);
                    }
                });
                return booleanProp;
            }
        });

        absenceCol.setEditable(true);
        absenceCol.setCellFactory(new Callback<TableColumn<Etudiant, Boolean>, //
                TableCell<Etudiant, Boolean>>() {
            @Override
            public TableCell<Etudiant, Boolean> call(TableColumn<Etudiant, Boolean> p) {
                CheckBoxTableCell<Etudiant, Boolean> cell = new CheckBoxTableCell<Etudiant, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        ObservableList<Utilisateur> list = afficherEtudiants();

        listEtudiantId.setItems(list);
        listEtudiantId.getColumns().addAll(nomCol, prenomCol, absenceCol);
    }


    public ObservableList<Utilisateur> afficherEtudiants() {
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        ObservableList<Utilisateur> etudiants = FXCollections.observableArrayList(etudiantDAO.findAll());
        return etudiants;
    }

    SpinnerValueFactory<Integer> valuehoure = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 1);
    SpinnerValueFactory<Integer> valueminute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);

}

