package univ.tln;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import univ.tln.daos.CreneauxDAO;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;


public class PopupControler implements Initializable{
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        md_h_d.setValueFactory(valuehoure);
        md_m_d.setValueFactory(valueminute);
        setspinner();
        initSalle();

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
            System.out.println(ManagerController.d2);

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
    public  void validateupdate (ActionEvent e) throws IOException, ParseException {
        //TODO : ahmed a ecrire le code ici avant

        System.out.println("i work");
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
                    //System.out.println("lol");
                    try {

                        c.initialize_batiment(md_m_f,md_m_d,md_h_f,md_h_d,md_bat, md_date);
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
                            c.initialize_batiment(md_m_f,md_m_d,md_h_f,md_h_d,md_bat, md_date);
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

    public void initSalle(){
        md_bat.setEditable(true);
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

    public void handleclicks(ActionEvent e) { //pour changer l'ecran

        if (e.getSource() == btnupdate) {
            System.out.println("haha");
            btnupdate.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            update.toFront();

        }
        if (e.getSource() == btnabs) {
            System.out.println("hihi");
            abscence.toFront();
        }
    }







    SpinnerValueFactory<Integer> valuehoure = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 1);
    SpinnerValueFactory<Integer> valueminute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);



}

