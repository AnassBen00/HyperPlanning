package univ.tln;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import univ.tln.daos.CreneauxDAO;
//import univ.tln.daos.DaoajoutCreneau;
import univ.tln.daos.EtudiantDAO;
//import univ.tln.entities.utilisateurs.Etudiant;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

//import static univ.tln.LoginController.getUsernametxt;

public class ManagerController implements Initializable {
    public int i;
    int max_cours = 60;
    CreneauxDAO c = new CreneauxDAO();

    private String[][] creneau = new String[max_cours][7];
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
    private AnchorPane scenea;

    @FXML
    private AnchorPane sceneabs;

    @FXML
    private AnchorPane scenem;

    @FXML
    private AnchorPane scenep;

    @FXML
    private AnchorPane scenes;

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
    private Button btnvaliderfiltre;

    @FXML
    private Label lblstatus;
    @FXML
    private Label lblstatusmini;
    @FXML
    private Label iddimanche;

    @FXML
    private Label idjeudi;

    @FXML
    private Label idlundi;

    @FXML
    private Label idmardi;

    @FXML
    private Label idmercredi;

    @FXML
    private Label idsamedi;

    @FXML
    private Label idvendredi;

    @FXML
    private TableView listEtudiantId;

    @FXML
    private Label name;
    @FXML
    private Label infomessage;

    @FXML
    private ComboBox<String> md_bat;
    @FXML
    private ComboBox<String> pickfomation;

    @FXML
    private ComboBox<String> pickteacher;

    @FXML
    private ComboBox<String> md_c;

    @FXML
    private DatePicker md_date;

    @FXML
    private ComboBox<String> md_ens;

    @FXML
    private ComboBox<String> md_f;

    @FXML
    private Spinner<Integer> md_h_d;

    @FXML
    private Spinner<Integer> md_h_f;

    @FXML
    private Spinner<Integer> md_m_d;

    @FXML
    private Spinner<Integer> md_m_f;

    @FXML
    private ComboBox<String> md_n;

    @FXML
    private ComboBox<String> md_s;
    @FXML
    private ImageView frontarrow;
    @FXML
    private ImageView backarrow;

    private int r=-7;
    private int w = 7;
    List<Label> l = new ArrayList<>();

    @Override
    @FXML


    public void initialize(URL url, ResourceBundle resourceBundle) {
        //DaoajoutCreneau c = new DaoajoutCreneau();
        //c.connect();
        disabledate();

        // FXMLLoader loader =new FXMLLoader(App.class.getResource("hello-view.fxml"));
        castdatetime(0);
        setspinner();
        initSalle();
        initcours();
        initnature();
        initens();

        try {
            md_h_d.setValueFactory(valuehoure);
            md_m_d.setValueFactory(valueminute);
            drawrect(); //on dessine l'emploie du temps
        } catch (ParseException e) {
            e.printStackTrace();
        }
        arrowinputback();
        arrowinputfront();
        setcalendar(0);
    }

    public void arrowinputback(){ // pour voir la semaine precedente

        backarrow.setOnMouseClicked((mouseEvent) -> {
            getmonday(r);
            getsunday(r);

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
            getsunday(w);

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


    public void disabledate() {
        md_date.setValue(java.time.LocalDate.now());
        md_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }


        });
    }

    public void initens(){
        md_n.setEditable(true);

        md_n.valueProperty().addListener((options, oldValue, newValue) ->{
            try {
                c.initialize_enseignant(md_date,md_m_f,md_m_d,md_h_f,md_h_d, md_ens,md_c,md_n);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }

    public void initSalle(){
        md_bat.setEditable(true);
        md_bat.valueProperty().addListener((options, oldValue, newValue) ->{
            try {
                c.initialize_salle(md_m_f,md_m_d,md_h_f,md_h_d,md_bat, md_date,md_s);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }
    public void initnature(){
        md_c.setEditable(true);
        md_c.valueProperty().addListener((options1, oldValue1, newValue1) ->{
            try {
                c.initialize_nature_cours(md_f,md_c,md_n);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

    }
    public void initcours(){
        md_f.setEditable(true);
        md_f.valueProperty().addListener((options, oldValue, newValue) ->{
            try {
                md_n.getItems().removeAll(md_n.getItems());
                c.initialize_cours(md_f,md_c);
                md_c.setEditable(true);
                md_c.valueProperty().addListener((options1, oldValue1, newValue1) ->{
                    try {
                        c.initialize_nature_cours(md_f,md_c,md_n);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                });
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }
    public void setspinner(){

        md_h_d.valueProperty().addListener((obs, oldValue, newValue) ->{
            SpinnerValueFactory<Integer> valuehoure2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue, 19, 1);
            md_h_f.setValueFactory(valuehoure2);



        });
        md_m_d.valueProperty().addListener((obs, oldValue, newValue) ->{
            SpinnerValueFactory<Integer> valuehoure2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue, 59, 1);
            md_m_f.setValueFactory(valuehoure2);



        });
                md_date.valueProperty().addListener((ov, oldValue, newValue) -> {
                md_h_f.valueProperty().addListener((obs, oldValue3, newValue3) -> {
                    md_m_f.valueProperty().addListener((obs2, oldValue2, newValue2) -> {
                        //System.out.println("lol");
                        try {

                            c.initialize_batiment(md_m_f,md_m_d,md_h_f,md_h_d,md_bat, md_date);
                            c.initialize_formation(md_m_f,md_m_d,md_h_f,md_h_d,md_f, md_date);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (md_m_f.getValue() != null && md_h_f.getValue() != null) {
                            /*
                            System.out.println(md_m_f.getValue());
                            System.out.println(md_m_d.getValue());
                            System.out.println(md_h_f.getValue());
                            System.out.println(md_h_d.getValue());
                            System.out.println(md_bat.getValue());
                            System.out.println(md_date.getValue());
*/

                            try {
                                c.initialize_batiment(md_m_f,md_m_d,md_h_f,md_h_d,md_bat, md_date);
                                c.initialize_formation(md_m_f,md_m_d,md_h_f,md_h_d,md_f, md_date);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                });
                });





    }

    SpinnerValueFactory<Integer> valuehoure = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 1);
    SpinnerValueFactory<Integer> valueminute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);

    SpinnerValueFactory<Integer> valueminute2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1);


    public void handleclicks(ActionEvent e) { //pour changer l'ecran

        if (e.getSource() == btnaccount) {

            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene3.toFront();
            scenea.toFront();

        } else if (e.getSource() == btnmodify) {


            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene2.toFront();
            scenem.toFront();
            md_date.setValue(java.time.LocalDate.now());
            md_date.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(today) < 0 );
                }


            });

        } else if (e.getSource() == btnplanning) {

            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));

            scene1.toFront();
            scenep.toFront();
            pickfomation.setOnMouseClicked(mouseEvent -> {
                boolean disable = !pickteacher.isDisabled();
                pickteacher.setDisable(disable);

            });
            pickteacher.setOnMouseClicked(mouseEvent -> {
                boolean disable = !pickfomation.isDisabled();
                pickfomation.setDisable(disable);

            });

        } else if (e.getSource() == btnsettings) {

            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));

            scene4.toFront();
            scenes.toFront();
        } else if (e.getSource() == btnabsences) {


            btnaccount.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            scene5.toFront();
            sceneabs.toFront();

            TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("Fist name");
            column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                    // for first column
                    return new SimpleStringProperty(p.getValue().getKey());
                }
            });

            TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>("Last name");
            column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                    // for second column
                    return new SimpleStringProperty(p.getValue().getValue());
                }
            });

           // ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(afficherEtudiants().entrySet());
           // listEtudiantId.setItems(items);
           // listEtudiantId.getColumns().setAll(column1, column2);
        }
    }
    @FXML
    public  void validatebuttononaction (ActionEvent e) throws IOException, ParseException {
        System.out.println("lol");
           // drawrect();
    }
/*
    public Map<String, String> afficherEtudiants() {
        Map<String, String> etudiants = new TreeMap<>() {
        };
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        for (Etudiant etudiant : etudiantDAO.findall()) {
            etudiants.put(etudiant.getNom(), etudiant.getPrenom());
        }
        return etudiants;
    }
*/
    @FXML
    Group group = new Group();

    public double houretopxl(double a) {
        return (a - 8) * 45.9 + 47;
    } //fonction qui donne le pixel exact de l'heure

    public int datetopxl(int a) {
        return (a - 2) * 126 + 125;
    } // fonction qui retourne le pixel exact de la date

    public Calendar getmonday(int i) { //retoune le lundi de cette semaine
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.DATE, i);
        return c;
    }

    public Calendar getsunday(int i) { // retourne le dimache
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.add(Calendar.DATE, 7);
        c.add(Calendar.DATE, i);
        return c;
    }

    public void setcalendar(int z) { //pour afficher les dates sous les jours
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
        c.add(Calendar.DATE, z);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            Tlabel[i].setText(df.format(c.getTime()));
            Tlabel[i].setTextAlignment(TextAlignment.CENTER);
            Tlabel[i].setTextFill(Color.rgb(255, 255, 255));
            c.add(Calendar.DATE, 1);
        }
    }

    public void castdatetime(int w) { //fonction qui remplie une liste des creneaux d'une semaine
        i = 0;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();

        try {
            Statement statement = connection1.createStatement();
            PreparedStatement pstmt = connection1.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) where LOGIN =? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            //PreparedStatement pstmt =connection1.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) join GROUPE on (CRENEAUX.ID_G = GROUPE.ID_G) where GROUPE.LOGIN =? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            //System.out.println(LoginController.user1);
            pstmt.setString(1, LoginController.user1);
            name.setText("Name: " + LoginController.user1 );
            name.setTextFill(Color.rgb(255, 255, 255));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(2, java.sql.Date.valueOf(df.format(getmonday(w).getTime())));
            //System.out.println(df.format(getmonday().getTime()));
            pstmt.setDate(3, java.sql.Date.valueOf(df.format(getsunday(w).getTime())));
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
                i++;
            }


            //System.out.println(Arrays.deepToString(creneau));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public  void addcreneau (ActionEvent e) throws IOException, SQLException {
        c.insertcreneau(md_date,md_h_d,md_m_d,md_h_f,md_m_f,md_bat,md_s,md_f,md_c,md_n,md_ens);
    }


    @FXML
    public void drawrect() throws ParseException { //fonction qui dessine l'emlpoie du temps

        Calendar x = Calendar.getInstance();

        for (int r = 0; r <= i - 1; r++) {
            //System.out.println(i);
            String m;

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Label cours = new Label();
            l.add(cours);

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

            scene1.getChildren().add(cours);
        }
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