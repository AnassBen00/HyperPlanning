package univ.tln;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import univ.tln.daos.CreneauxDAO;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;


public class PopupControler {
    private TeacherController teacherController;

    @FXML
    private Button supbtn;

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
        //TODO : ahmed update un crneaux (salle heure debut heure fin) les id sont modifhd , modifhf , modifsalle , le bouton valider execute deja cette fonction t'a qu'a ecrire le code :)

        System.out.println("i work");
    }


}

