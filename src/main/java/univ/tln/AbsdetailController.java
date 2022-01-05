package univ.tln;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AbsdetailController implements Initializable  {
    @FXML
    private TableView<?> absdetails;

    @FXML
    private Label managerabstitle;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initmanagertitle();
    }


    public void initmanagertitle(){
        managerabstitle.setText("liste d'absence pour l'etudiant : "+ManagerController.d);
        System.out.println(ManagerController.m);// m cest le login de l'etudiant selectioner
    }
}
