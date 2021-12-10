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

    public String getUsernametxt() {
        return LoginController.user1;
    }

    @FXML
    public TextField usernametxt;


    public void cancelbtnOnAction(ActionEvent e){
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

  public void validatelogin(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();
        String prof ="PROF";
        String etud="ETU";
        String res="RES";
            //if(getuser(username.txt,password.tx) == true switchtomanagerscene();
        String verifylogin=  "SELECT  count(1) from UTILISATEUR where LOGIN= '"+usernametxt.getText()+"' AND PASSWORD = HASH('SHA256','"+passwrdtxt.getText()+"',1000)";

          LoginController.user1= String.valueOf(usernametxt.getText());

      System.out.println(getUsernametxt()+"wtffffffffff");
        try {
            Statement statement = connection1.createStatement();
            Statement statement2 = connection1.createStatement();
            ResultSet queryResult = statement.executeQuery(verifylogin);

            while ((queryResult.next())){
                if( queryResult.getInt(1)==1){
                    switchtomanagerscene();

                }else loginmessage.setText("invalid try again");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
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

    public void switchtomanagerscene()  {

        try {
            Parent root = FXMLLoader.load(App.class.getResource("managerscreen.fxml"));

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
