package univ.tln;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import univ.tln.daos.CreneauxDAO;

import java.text.ParseException;

public class PopupControler {
    @FXML
    private Button supbtn;

    public void cancelbtnOnAction(ActionEvent e) throws ParseException {
        Stage stage = (Stage) supbtn.getScene().getWindow();

        CreneauxDAO d = new CreneauxDAO();
        //System.out.println(TeacherController.d1);

        d.RemoveCreneauByDated(TeacherController.d1);
        TeacherController teacherController = new TeacherController();
        teacherController.drawrect();
        stage.close();

    }


}
