package univ.tln.controller;


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
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.SneakyThrows;
import univ.tln.daos.AbsenceDAO;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.utilisateurs.Absence;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class
AbsdetailController implements Initializable  {

    @FXML
    private TableView<Absence> absdetails;

    @FXML
    private Label managerabstitle;
    @FXML
    private Button cancelbtn;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initmanagertitle();
        initabsdetail();
    }


    public void initmanagertitle(){
        managerabstitle.setText("liste d'absence pour l'etudiant : "+ManagerController.d);

    }
    public void initabsdetail() throws SQLException, DataAccessException {

        absdetails.setEditable(true);

        TableColumn<Absence, String> datedebut//
                = new TableColumn<>("date debut cours");
        datedebut.setEditable(false);
        TableColumn<Absence, String> nomcr//
                = new TableColumn<>("nom matiere");
        nomcr.setEditable(false);
        TableColumn<Absence, String> nature//
                = new TableColumn<>("nature ");
        nature.setEditable(false);
        TableColumn<Absence, Boolean> absenceCol//
                = new TableColumn<>("Absence");
        absenceCol.setEditable(true);

        //nom

        datedebut.setCellValueFactory(new PropertyValueFactory<>("date_d"));

        datedebut.setCellFactory(TextFieldTableCell.<Absence>forTableColumn());

        datedebut.setMinWidth(200);

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
                Absence absence = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty();
                booleanProp.set(absence.isJustified());
                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @SneakyThrows
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {

                        try (AbsenceDAO absenceDAO = new AbsenceDAO()) {
                            absenceDAO.update(absence.getLogin(), absence.getDate_d(), newValue);
                        }
                    }
                });
                return booleanProp;
            }
        });

        absenceCol.setEditable(true);
        absenceCol.setCellFactory(p -> {
            CheckBoxTableCell<Absence, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });


        ObservableList<Absence> list = afficherAbsences();
        absdetails.setItems(list);

        absdetails.getColumns().addAll(datedebut,nomcr, nature, absenceCol);

    }
    public ObservableList<Absence> afficherAbsences() throws SQLException, DataAccessException {

        try(AbsenceDAO absenceDAO = new AbsenceDAO();) {


           return FXCollections.observableArrayList(absenceDAO.findAllabs(ManagerController.m));

        }
    }


    @FXML
    void clbtnOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }



}