package univ.tln;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import univ.tln.daos.EnseignantDAO;
import univ.tln.daos.EtudiantDAO;
import univ.tln.daos.ResponsableDao;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController extends Application {

    public String user ;

    @FXML
    private Button cancelbtn;

    @FXML
    private Button loginbtn;

    @FXML
    private Label loginmessage;

    @FXML
    private PasswordField passwrdtxt;



    public static String user1;
    public static String name1;

    public String getUsernametxt() {
        return LoginController.user1;
    }

    @FXML
    public TextField usernametxt;


    public void cancelbtnOnAction(ActionEvent e){
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

  public void validatelogin(){ // on verifie le login

      EnseignantDAO enseignantDAO = new EnseignantDAO();
      EtudiantDAO etudiantDAO = new EtudiantDAO();
      ResponsableDao responsableDao = new ResponsableDao();

      if(enseignantDAO.checkEnseignant(usernametxt.getText() , passwrdtxt.getText())) {
          System.out.println("je suis enseignant");
          user1=usernametxt.getText();
          name1=enseignantDAO.getEnseignantNameBylogin(usernametxt.getText()) +"\n ENSEIGNANT";
          switchtoteacherscene();
      } else if (etudiantDAO.checkEtudiant(usernametxt.getText() , passwrdtxt.getText())){
          System.out.println("je suis etudiant");
          user1=usernametxt.getText();
          name1 = etudiantDAO.getEtudiantNameBylogin(usernametxt.getText()) + "\n ETUDIANT";
          switchtostudentscene();
      } else if (responsableDao.checkResponsable(usernametxt.getText() , passwrdtxt.getText())) {
          System.out.println("je suis responsable");
          user1=usernametxt.getText();
          user1 = responsableDao.getResponsableNameBylogin(usernametxt.getText()) + "\n RESPONSABLE";
          switchtomanagerscene();
      } else {
          loginmessage.setText("invalid try again");
      }

    }

    @FXML
    public  void loginbuttononaction (ActionEvent e) throws IOException {

        if (usernametxt.getText().isBlank() == false && passwrdtxt.getText().isBlank() == false){
            //loginmessage.setText("you try to login");
            validatelogin();
        }
        else {
            loginmessage.setText("pleas enter username and password");
        }
    }

    public void switchtomanagerscene()  { // on change l'ecran si c'est bon

        try {
            Parent root = FXMLLoader.load(App.class.getResource("managerscreen.fxml"));

            Stage managerstage = new Stage();

            Scene scene = new Scene(root, 1305, 782);

            managerstage.setScene(scene);
            managerstage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void switchtostudentscene()  { // on change l'ecran si c'est bon

        try {
            Parent root = FXMLLoader.load(App.class.getResource("studentrscreen.fxml"));

            Stage managerstage = new Stage();

            //VBox vBox = new VBox(group,root);
            //managerstage.initStyle(StageStyle.UNDECORATED);
            //drawrect();
            Scene scene = new Scene(root, 1305, 782);

            managerstage.setScene(scene);
            managerstage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void switchtoteacherscene()  { // on change l'ecran si c'est bon

        try {
            Parent root = FXMLLoader.load(App.class.getResource("teacherscreen.fxml"));

            Stage managerstage = new Stage();

            //VBox vBox = new VBox(group,root);
            //managerstage.initStyle(StageStyle.UNDECORATED);
            //drawrect();
            Scene scene = new Scene(root, 1305, 782);

            managerstage.setScene(scene);
            managerstage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
