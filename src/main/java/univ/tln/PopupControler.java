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
            //System.out.println(TeacherController.d1);

            d.RemoveCreneauByDated(TeacherController.d1);
            TeacherController T = new TeacherController();
            TeacherController teacherController = loader.getController();
            teacherController.updatewindow();

            System.out.println("yupi");

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        stage.close();

    }

}

