package univ.tln.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import univ.tln.daos.EnseignantDAO;
import univ.tln.daos.EtudiantDAO;
import univ.tln.daos.ResponsableDAO;
import univ.tln.daos.exceptions.DataAccessException;

public class Passeditcontroller {

    @FXML
    private Button Applybtn;

    @FXML
    private Button clbtn;

    @FXML
    private PasswordField newPassField;

    @FXML
    private PasswordField oldPassField;

    @FXML
    private Label errormsg;


    @FXML
    void ApplybtnOnAction(ActionEvent event) {
        if(!oldPassField.getText().isBlank() && !newPassField.getText().isBlank()) {
            validePassword();


        } else {
            errormsg.setText("Please enter the old and the new password");
        }


    }

    void validePassword() {


        try(        EnseignantDAO enseignantDAO = new EnseignantDAO();
                    EtudiantDAO etudiantDAO = new EtudiantDAO();
                    ResponsableDAO responsableDao = new ResponsableDAO();) {


            if (enseignantDAO.checkEnseignantPass(oldPassField.getText())) {
                Stage stage = (Stage) clbtn.getScene().getWindow();
                stage.close();

            } else if (etudiantDAO.checkEtudiantPass(oldPassField.getText())) {
                Stage stage = (Stage) clbtn.getScene().getWindow();
                stage.close();
            } else if (responsableDao.checkResPass(oldPassField.getText())) {
                Stage stage = (Stage) clbtn.getScene().getWindow();
                stage.close();
            } else {
                errormsg.setText("Old password unvalid try again");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void clbtnOnAction(ActionEvent event) {
        Stage stage = (Stage) clbtn.getScene().getWindow();
        stage.close();
    }


}
