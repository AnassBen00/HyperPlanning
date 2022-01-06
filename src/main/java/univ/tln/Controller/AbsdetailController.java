package univ.tln.Controller;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import lombok.SneakyThrows;
import univ.tln.daos.AbsenceDAO;
import univ.tln.entities.utilisateurs.Absence;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class AbsdetailController implements Initializable  {

    @FXML
    private TableView<Absence> absdetails;

    @FXML
    private Label managerabstitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initmanagertitle();
        initabsdetail();
        Absence a =absdetails.getSelectionModel().getSelectedItem();
    }


    public void initmanagertitle(){
        managerabstitle.setText("liste d'absence pour l'etudiant : "+ManagerController.d);

    }
    public void initabsdetail(){

        absdetails.setEditable(true);

        TableColumn<Absence, String> date_debut//
                = new TableColumn<Absence, String>("date debut cours");
        date_debut.setEditable(false);
        TableColumn<Absence, String> nomcr//
                = new TableColumn<Absence, String>("nom matiere");
        nomcr.setEditable(false);
        TableColumn<Absence, String> nature//
                = new TableColumn<Absence, String>("nature ");
        nature.setEditable(false);
        TableColumn<Absence, Boolean> absenceCol//
                = new TableColumn<Absence, Boolean>("Absence");
        absenceCol.setEditable(true);
    
        //nom

        date_debut.setCellValueFactory(new PropertyValueFactory<>("date_d"));

        date_debut.setCellFactory(TextFieldTableCell.<Absence>forTableColumn());

        date_debut.setMinWidth(200);

        // prenom

        nomcr.setCellValueFactory(new PropertyValueFactory<>("nom"));

        nomcr.setCellFactory(TextFieldTableCell.<Absence>forTableColumn());

        nomcr.setMinWidth(200);

        nature.setCellValueFactory(new PropertyValueFactory<>("nature"));

        nature.setCellFactory(TextFieldTableCell.<Absence>forTableColumn());

        nature.setMinWidth(200);

        absenceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Absence, Boolean>, ObservableValue<Boolean>>() {

            @SneakyThrows
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Absence, Boolean> param) {
                Absence abs = (Absence) absdetails.getSelectionModel().getSelectedItem();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty();


                booleanProp.addListener(new ChangeListener<Boolean>() {
                    AbsenceDAO a = new AbsenceDAO();
                    @SneakyThrows
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        //booleanProp.set(newValue);
                        if(newValue == true)
                            a.persist(abs);
                        if(newValue == false);
                            a.remove(abs);
                    }
                });
                return booleanProp;
            }
        });


        absenceCol.setCellFactory(new Callback<TableColumn<Absence, Boolean>, //
                TableCell<Absence, Boolean>>() {
            @Override
            public TableCell<Absence, Boolean> call(TableColumn<Absence, Boolean> p) {
                CheckBoxTableCell<Absence, Boolean> cell = new CheckBoxTableCell<Absence, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });


        ObservableList<Absence> list = afficherAbsences();
        absdetails.setItems(list);

        absdetails.getColumns().addAll(date_debut,nomcr, nature, absenceCol);

    }
    public ObservableList<Absence> afficherAbsences() {
        AbsenceDAO absenceDAO = new AbsenceDAO();
        ObservableList<Absence> absences = FXCollections.observableArrayList(absenceDAO.findAllabs(ManagerController.m));
        return absences;
        }


    public void delete_abs(ActionEvent e) throws IOException, ParseException {
        for (Absence a :absdetails.getItems()){
            System.out.println(absdetails.getColumns().get(3).getCellObservableValue(a).getValue());
        }
    }
}
