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

import java.sql.SQLException;

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
            errormsg.setText("Veuillez saisir un mot de passe");
        }


    }

    void validePassword() {


        try(        EnseignantDAO enseignantDAO = new EnseignantDAO();
                    EtudiantDAO etudiantDAO = new EtudiantDAO();
                    ResponsableDAO responsableDao = new ResponsableDAO();) {


            if (enseignantDAO.checkEnseignantPass(oldPassField.getText())) {
                enseignantDAO.updatePassByLogin(newPassField.getText(),LoginController.user1);
                Stage stage = (Stage) clbtn.getScene().getWindow();
                stage.close();

            } else if (etudiantDAO.checkEtudiantPass(oldPassField.getText())) {
                etudiantDAO.updatePassByLogin(newPassField.getText(),LoginController.user1);
                Stage stage = (Stage) clbtn.getScene().getWindow();
                stage.close();
            } else if (responsableDao.checkResPass(oldPassField.getText())) {
                responsableDao.updatePassByLogin(newPassField.getText(),LoginController.user1);
                Stage stage = (Stage) clbtn.getScene().getWindow();
                stage.close();
            } else {
                errormsg.setText("Ancien mot de passe invalide Veuillez réessayer");
            }
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void clbtnOnAction(ActionEvent event) {
        Stage stage = (Stage) clbtn.getScene().getWindow();
        stage.close();
    }


}
