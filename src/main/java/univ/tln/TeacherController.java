package univ.tln;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import univ.tln.daos.EtudiantDAO;
import univ.tln.entities.utilisateurs.Etudiant;
//import univ.tln.entities.utilisateurs.Etudiant;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.*;

//import static univ.tln.LoginController.getUsernametxt;

public class TeacherController implements Initializable {
    public int i;
    int max_cours = 60;
    public String[][] creneau = new String[max_cours][8];
    @FXML
    private AnchorPane scene1; //le planning
    @FXML
    private AnchorPane scene2; //la partie pour modifier ajouter
    @FXML
    private AnchorPane scene3; //le compte
    @FXML
    private AnchorPane scene4; //les parametre
    @FXML
    private AnchorPane scene5; //l'abcenses
    @FXML
    private Button btnaccount;
    @FXML
    private Button btnmodify;
    @FXML
    private Button btnplanning;
    @FXML
    private Button btnsettings;
    @FXML
    private Button btnabsences;
    @FXML
    private Label lblstatus;
    @FXML
    private Label lblstatusmini;
    @FXML
    private Label iddimanche;

    @FXML
    private Label iddimanche1;

    @FXML
    private Label idjeudi;

    @FXML
    private Label idjeudi1;

    @FXML
    private Label idlundi;

    @FXML
    private Label idlundi1;

    @FXML
    private Label idmardi;

    @FXML
    private Label idmardi1;

    @FXML
    private Label idmercredi;

    @FXML
    private Label idmercredi1;

    @FXML
    private Label idsamedi;

    @FXML
    private Label idsamedi1;

    @FXML
    private Label idvendredi;

    @FXML
    private Label idvendredi1;

    @FXML
    private TableView listEtudiantId;

    @FXML
    private Label name;

    @FXML
    private Label messageinstruction;

    @FXML
    private Label role;

    @FXML
    private Button refrech;

    @FXML
    private Button supbtn;
    @FXML
    private Pane sceneabs;

    @FXML
    private Pane sceneajout;

    @FXML
    private Pane scenecmt;

    @FXML
    private Pane scenepln;

    @FXML
    private Pane sceneset;

    @FXML
    private ImageView frontarrow;
    @FXML
    private ImageView backarrow;

    @FXML
    private ImageView frontarrow2;
    @FXML
    private ImageView backarrow2;

    private int r=-7;
    private int w = 7;

    private int z=-7;
    private int y = 7;

    List<Label> l = new ArrayList<>();
    List<Label> l2 = new ArrayList<>();

    public static String d1;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        castdatetime(0);
        try {
            drawrect(); //on dessine l'emploie du temps
            drawrect2();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        arrowinputback();
        arrowinputfront();
        arrowinputback2();
        arrowinputfront2();
        setcalendar(0);
        setcalendar2(0);
    }
    public void arrowinputback(){ // pour voir la semaine precedente

        backarrow.setOnMouseClicked((mouseEvent) -> {
            getmonday(r);
            getmonday(r+7);

            for (Label g : l) {
                scene1.getChildren().remove(g);
            }
            castdatetime(r);

            try {
                drawrect();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setcalendar(r);
            System.out.println("loool");

            r=r-7;
            w=w-7;
        });

    }

    public void arrowinputfront(){ //pour voir la semaine suivante

        frontarrow.setOnMouseClicked((mouseEvent) -> {
            getmonday(w);
            getmonday(w+7);

            for (Label g : l) {
                scene1.getChildren().remove(g);
            }
            castdatetime(w);

            try {
                drawrect();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setcalendar(w);
            System.out.println("loool");

            w=w+7;
            r=r+7;
        });

    }

    public void arrowinputback2(){ // pour voir la semaine precedente

        backarrow2.setOnMouseClicked((mouseEvent) -> {
            getmonday(z);
            getmonday(z+7);

            for (Label g : l2) {
                scene2.getChildren().remove(g);
            }
            castdatetime(z);

            try {
                drawrect2();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setcalendar2(z);
            System.out.println("loool");

            z=z-7;
            y=y-7;
        });

    }

    public void arrowinputfront2(){ //pour voir la semaine suivante

        frontarrow2.setOnMouseClicked((mouseEvent) -> {
            getmonday(y);
            getmonday(y+7);

            for (Label g : l2) {
                scene2.getChildren().remove(g);
            }
            castdatetime(y);

            try {
                drawrect2();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setcalendar2(y);
            System.out.println("loool");

            y=y+7;
            z=z+7;
        });

    }



    public void handleclicks(ActionEvent e) throws ParseException { //pour changer l'ecran

        if (e.getSource() == btnaccount) {
            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene3.toFront();
            scenecmt.toFront();
        } else if (e.getSource() == btnmodify) {
            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene2.toFront();
            sceneajout.toFront();
        } else if (e.getSource() == btnplanning) {
            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene1.toFront();
            scenepln.toFront();
        } else if (e.getSource() == btnsettings) {
            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene4.toFront();
            sceneset.toFront();
        } else if (e.getSource() == btnabsences) {
            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene5.toFront();
            sceneabs.toFront();

            TableView<Etudiant> table = new TableView<Etudiant>();

            // Editable
            table.setEditable(true);

            TableColumn<Etudiant, String> nomCol//
                    = new TableColumn<Etudiant, String>("Nom");

            TableColumn<Etudiant, String> prenomCol//
                    = new TableColumn<Etudiant, String>("Prenom");

            TableColumn<Etudiant, Boolean> absenceCol//
                    = new TableColumn<Etudiant, Boolean>("Absence");

            //nom

            nomCol.setCellValueFactory(new PropertyValueFactory<>("Nom"));

            nomCol.setCellFactory(TextFieldTableCell.<Etudiant>forTableColumn());

            nomCol.setMinWidth(200);

            // prenom

            prenomCol.setCellValueFactory(new PropertyValueFactory<>("Prenom"));

            prenomCol.setCellFactory(TextFieldTableCell.<Etudiant>forTableColumn());

            prenomCol.setMinWidth(200);


            // ==== absence (CHECH BOX) ===
            /*absenceCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Etudiant, Boolean>, Void>() {

             *//*@Override
                public Void call(TreeTableColumn.CellDataFeatures<Etudiant, Boolean> param) {
                   *//**//* Etudiant etudiant = param.getValue();

                    SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.isSingle());

                    // Note: singleCol.setOnEditCommit(): Not work for
                    // CheckBoxTableCell.

                    // When "Single?" column change.
                    booleanProp.addListener(new ChangeListener<Boolean>() {

                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                            Boolean newValue) {
                            person.setSingle(newValue);
                        }
                    });
                    return booleanProp;*//**//*

                }*//*
            });
*/
            absenceCol.setCellFactory(new Callback<TableColumn<Etudiant, Boolean>, //
                    TableCell<Etudiant, Boolean>>() {
                @Override
                public TableCell<Etudiant, Boolean> call(TableColumn<Etudiant, Boolean> p) {
                    CheckBoxTableCell<Etudiant, Boolean> cell = new CheckBoxTableCell<Etudiant, Boolean>();
                    cell.setAlignment(Pos.CENTER);
                    return cell;
                }
            });

            ObservableList<Etudiant> list = afficherEtudiants();
            table.setItems(list);

            table.getColumns().addAll(nomCol, prenomCol, absenceCol);

        }
    }

    public ObservableList<Etudiant> afficherEtudiants() {
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        ObservableList<Etudiant> etudiants = FXCollections.observableArrayList(etudiantDAO.findAll());
        return etudiants;
    }

    @FXML
    Group group = new Group();

    public double houretopxl(double a) {
        return (a - 8) * 45.9 + 47;
    } //fonction qui donne le pixel exact de l'heure

    public int datetopxl(int a) {
        return (a - 2) * 126 + 125;
    } // fonction qui retourne le pixel exact de la date

    public Calendar getmonday(int i ){ //retoune le lundi de cette semaine
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY  );
        c.add(Calendar.DATE, i);
        System.out.println(c.getTime());
        return c;
    }


    public void setcalendar(int a) {
        //pour afficher les dates sous les jours
        Label Tlabel[] = new Label[7];
        Tlabel[0] = idlundi;
        Tlabel[1] = idmardi;
        Tlabel[2] = idmercredi;
        Tlabel[3] = idjeudi;
        Tlabel[4] = idvendredi;
        Tlabel[5] = idsamedi;
        Tlabel[6] = iddimanche;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.DATE, a);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            Tlabel[i].setText(df.format(c.getTime()));
            Tlabel[i].setTextAlignment(TextAlignment.CENTER);
            Tlabel[i].setTextFill(Color.rgb(255, 255, 255));
            c.add(Calendar.DATE, 1);
        }
    }

    public void setcalendar2(int a) { //pour afficher les dates sous les jours
        Label Tlabel[] = new Label[7];
        Tlabel[0] = idlundi1;
        Tlabel[1] = idmardi1;
        Tlabel[2] = idmercredi1;
        Tlabel[3] = idjeudi1;
        Tlabel[4] = idvendredi1;
        Tlabel[5] = idsamedi1;
        Tlabel[6] = iddimanche1;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.DATE, a);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            Tlabel[i].setText(df.format(c.getTime()));
            Tlabel[i].setTextAlignment(TextAlignment.CENTER);
            Tlabel[i].setTextFill(Color.rgb(255, 255, 255));
            c.add(Calendar.DATE, 1);
        }
    }

    public void castdatetime(int m) { //fonction qui remplie une liste des creneaux d'une semaine
        i = 0;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();


        try {

            Statement statement = connection1.createStatement();
            PreparedStatement pstmt = connection1.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,C2.NOM,C2.NATURE,G.nom from SALLE join CRENEAUX C on SALLE.ID_S = C.ID_S join GROUP_COURS GC on C.ID_G = GC.ID_G and C.ID_C = GC.ID_C join COURS C2 on GC.ID_C = C2.ID_C join GROUPS G on GC.ID_G = G.ID_G where C2.LOGIN =? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            //PreparedStatement pstmt =connection1.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) join GROUPE on (CRENEAUX.ID_G = GROUPE.ID_G) where GROUPE.LOGIN =? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            System.out.println(LoginController.user1);
            pstmt.setString(1, LoginController.user1);
            name.setText("Login: " + LoginController.user1 + "\n" +LoginController.name1);
            //role.setText("Name : " + LoginController.name1);
            name.setTextFill(Color.rgb(255, 255, 255));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(2, java.sql.Date.valueOf(df.format(getmonday(m).getTime())));
            System.out.println("younes" + java.sql.Date.valueOf(df.format(getmonday(m).getTime())) );
            //System.out.println(df.format(getmonday().getTime()));
            pstmt.setDate(3, java.sql.Date.valueOf(df.format(getmonday(m+7).getTime())));
            ResultSet queryResult = pstmt.executeQuery();
            //System.out.println(queryResult.getInt(1));
            while ((queryResult.next())) {
                creneau[i][0] = String.valueOf(queryResult.getTimestamp("DATE_D"));
                creneau[i][1] = String.valueOf(queryResult.getTimestamp("DATE_F"));
                creneau[i][2] = queryResult.getString("BATIMENT");
                creneau[i][3] = String.valueOf(queryResult.getInt("NUM"));
                creneau[i][4] = String.valueOf(queryResult.getBoolean("VIDEO_P"));
                creneau[i][5] = queryResult.getString("NOM");
                creneau[i][6] = queryResult.getString("NATURE");
                creneau[i][7] = queryResult.getString("nom");
                i++;
            }


            // System.out.println(Arrays.deepToString(creneau));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void drawrect() throws ParseException {
        //fonction qui dessine l'emlpoie du temps

        Calendar x = Calendar.getInstance();


        for (int r = 0; r <= i - 1; r++) {
            //System.out.println(i);
            String m;

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Label cours = new Label();
            l.add(cours);

            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[r][0]);

            System.out.println("this is it" + creneau[r][0]);

            Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[r][1]);
            x.setTime(date1);
            int dayOfWeek = x.get(x.DAY_OF_WEEK);
            datetopxl(dayOfWeek);
            double hourofday = x.get(x.HOUR_OF_DAY) + (float) x.get(x.MINUTE) / 60;

            int z = datetopxl(dayOfWeek);
            double y = houretopxl(hourofday);
            x.setTime(date2);
            double hourofday2 = x.get(x.HOUR_OF_DAY) + (float) x.get(x.MINUTE) / 60;
            //System.out.println(hourofday2);
            double w = houretopxl(hourofday2) - y;
            //System.out.println(w);

            cours.setTranslateX(z);
            cours.setTranslateY(y);
            cours.setMinWidth(126);
            cours.setMaxWidth(126);
            cours.setMaxHeight(w);
            cours.setMinHeight(w);
            //cours.setFont(new Font("Serif", 14));
            if (creneau[r][4].equals("true")) m = "Oui";
            else m = "Non";
            cours.setText("Salle: " + creneau[r][2] + " " + creneau[r][3] + "\n" + creneau[r][5] + " " + creneau[r][6] + "\nprojecteur: " + m +"\n prof :");

            cours.setTextFill(Color.rgb(255, 255, 255));
            cours.setTextAlignment(TextAlignment.CENTER);

            cours.setAlignment(Pos.CENTER);
            //System.out.println(creneau[r][6]);

            if (creneau[r][6].trim().equals("TP"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(50, 18, 71), CornerRadii.EMPTY, Insets.EMPTY)));
            else if (creneau[r][6].trim().equals("TD"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(5, 52, 14), CornerRadii.EMPTY, Insets.EMPTY)));
            else if (creneau[r][6].trim().equals("CM"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(26, 31, 38), CornerRadii.EMPTY, Insets.EMPTY)));

            scene1.getChildren().add(cours);

        }


    }

    @FXML
    public void drawrect2() throws ParseException { //fonction qui dessine l'emlpoie du temps
        Calendar x = Calendar.getInstance();


        for (int r = 0; r <= i - 1; r++) {
            //System.out.println(i);
            String m;

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Label cours = new Label();
            l2.add(cours);


            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[r][0]);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(creneau[r][1]);

            x.setTime(date1);
            int dayOfWeek = x.get(x.DAY_OF_WEEK);
            datetopxl(dayOfWeek);
            double hourofday = x.get(x.HOUR_OF_DAY) + (float) x.get(x.MINUTE) / 60;

            int z = datetopxl(dayOfWeek);
            double y = houretopxl(hourofday);
            x.setTime(date2);
            double hourofday2 = x.get(x.HOUR_OF_DAY) + (float) x.get(x.MINUTE) / 60;
            //System.out.println(hourofday2);
            double w = houretopxl(hourofday2) - y;
            //System.out.println(w);


            cours.setTranslateX(z);
            cours.setTranslateY(y);
            cours.setMinWidth(126);
            cours.setMaxWidth(126);
            cours.setMaxHeight(w);
            cours.setMinHeight(w);
            //cours.setFont(new Font("Serif", 14));
            if (creneau[r][4].equals("true")) m = "Oui";
            else m = "Non";
            cours.setText("Salle: " + creneau[r][2] + " " + creneau[r][3] + "\n" + creneau[r][5] + " " + creneau[r][6] + "\nprojecteur: " + m +"\n prof :");

            cours.setTextFill(Color.rgb(255, 255, 255));
            cours.setTextAlignment(TextAlignment.CENTER);
            cours.setAlignment(Pos.CENTER);
            //System.out.println(creneau[r][6]);

            if (creneau[r][6].trim().equals("TP"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(50, 18, 71), CornerRadii.EMPTY, Insets.EMPTY)));
            else if (creneau[r][6].trim().equals("TD"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(5, 52, 14), CornerRadii.EMPTY, Insets.EMPTY)));
            else if (creneau[r][6].trim().equals("CM"))
                cours.setBackground(new Background(new BackgroundFill(Color.rgb(26, 31, 38), CornerRadii.EMPTY, Insets.EMPTY)));
            int finalR = r;
            cours.setOnMouseClicked((mouseEvent) -> {
                System.out.println(creneau[finalR][0]);
                d1 = creneau[finalR][0];
                switchtopopupscene();
            });
            scene2.getChildren().add(cours);

        }
    }

    public void switchtopopupscene() { // on change l'ecran si c'est bon

        try {
            Parent root = FXMLLoader.load(App.class.getResource("Popupscene.fxml"));

            Stage managerstage = new Stage();

            Scene scene = new Scene(root, 919, 667);


            managerstage.setScene(scene);
            managerstage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void updatewindow() throws ParseException {
        //castdatetime();
        //System.out.println(l);


        for (Label g : l) {
            scene1.getChildren().remove(g);

        }
        for (Label g : l2) {
            scene2.getChildren().remove(g);

        }

        castdatetime(0);
        try {
            drawrect(); //on dessine l'emploie du temps
            drawrect2();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setcalendar(0);
        setcalendar2(0);

        //System.out.println("updated");
    }


    public void cancelbtnOnAction(ActionEvent e) {
        Stage stage = (Stage) supbtn.getScene().getWindow();
        stage.close();

    }


    @FXML
    private Label welcomeText;

    @FXML
    private Label loginmessage;

    @FXML
    private TextField usernametxt;
    @FXML
    private PasswordField passwrdtxt;

}