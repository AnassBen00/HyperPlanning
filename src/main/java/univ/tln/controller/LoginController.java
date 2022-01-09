package univ.tln.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import univ.tln.App;
import univ.tln.daos.EnseignantDAO;
import univ.tln.daos.EtudiantDAO;
import univ.tln.daos.ResponsableDAO;
import univ.tln.daos.exceptions.DataAccessException;

import java.io.IOException;

import java.sql.SQLException;
import java.util.Objects;


public class LoginController  {

    @FXML
    private Button cancelbtn;

    @FXML
    private Button loginbtn;

    @FXML
    private Label loginmessage;

    @FXML
    private PasswordField passwrdtxt;


    static  Stage   managerstage = new Stage();




    @Setter
    public  static String user1;
    @Setter
    public static String name1;
    @Setter
    public static String psswrd;

    public String getUsernametxt() {
        return LoginController.user1;
    }

    @FXML
    public TextField usernametxt;


    public void cancelbtnOnAction(){
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    public static void setnom(String a,String b,String c){
        setUser1(a);
        setName1(b);
        setPsswrd(c);
    }


  public void validatelogin(){ // on verifie le login
String a ;
String b ;
String c ;

      try( EnseignantDAO enseignantDAO = new EnseignantDAO() ;
           EtudiantDAO etudiantDAO = new EtudiantDAO() ;
           ResponsableDAO responsableDao = new ResponsableDAO()) {
          if (enseignantDAO.checkEnseignant(usernametxt.getText(), passwrdtxt.getText())) {
              a = usernametxt.getText();
              b = enseignantDAO.getEnseignantNameBylogin(usernametxt.getText()) + "\nENSEIGNANT";
              c = passwrdtxt.getText();
              setnom(a,b,c);
              switchtoteacherscene();
          } else if (etudiantDAO.checkEtudiant(usernametxt.getText(), passwrdtxt.getText())) {

             a =   usernametxt.getText();
              b =  etudiantDAO.getEtudiantNameBylogin(usernametxt.getText()) + "\nETUDIANT";
              System.out.println(usernametxt.getText());
              c  = passwrdtxt.getText();
              setnom(a,b,c);
              switchtostudentscene();
          } else if (responsableDao.checkResponsable(usernametxt.getText(), passwrdtxt.getText())) {

              a  = usernametxt.getText();
              b  = responsableDao.getResponsableNameBylogin(usernametxt.getText()) + "\nRESPONSABLE";
              c  = passwrdtxt.getText();
              setnom(a,b,c);
              switchtomanagerscene();
          } else {
              loginmessage.setText("invalid try again");
          }
      } catch (DataAccessException | SQLException e) {
          e.printStackTrace();
      }

  }

    @FXML
    public  void loginbuttononaction ()  {

        if (!usernametxt.getText().isBlank() && !passwrdtxt.getText().isBlank()){
            validatelogin();
        }
        else {
            loginmessage.setText("pleas enter username and password");
        }
    }

    public void switchtomanagerscene()  { // on change l'ecran si c'est bon

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("managerscreen.fxml")));


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
            Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("studentrscreen.fxml")));

            Stage managerstage1 = new Stage();
            Scene scene = new Scene(root, 1305, 782);

            managerstage1.setScene(scene);
            managerstage1.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void switchtoteacherscene()  { // on change l'ecran si c'est bon


        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("teacherscreen.fxml")));

            Scene scene = new Scene(root, 1305, 782);

            managerstage.setScene(scene);
            managerstage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
