package univ.tln.daos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.paint.Color;
import lombok.extern.java.Log;
import univ.tln.DatabaseConnection;
import univ.tln.controller.LoginController;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.creneaux.Creneau;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

@Log
public class CreneauxDAO extends AbstractDAO<Creneau>{
    public static final String BAT = "BATIMENT";
    public static final String VID = "VIDEO_P";
    public static final String NAT = "NATURE";
    public static final String DATED ="DATE_D";
    public static final String DATEF = "DATE_F";
    PreparedStatement preparedStatement ;
    public CreneauxDAO() {
        super("INSERT INTO CRENEAUX(DATE_D,DATE_F,ID_S,ID_G,ID_C) VALUES (?,?,?,?,?)",
                "UPDATE CRENEAUX SET DATE_D=?,DATE_F=? ID_S=?,ID_G=?,ID_C=? WHERE DATE_D=?,ID_G=?,ID_C=?",
                "SELECT * FROM CRENEAUX WHERE DATE_D=?,ID_G=?,ID_C=?");
    }

    @Override
    public String getTableName() {
        return "CRENEAUX";
    }


    @Override
    protected Creneau fromResultSet(ResultSet resultSet) throws SQLException {

        return Creneau.builder()
                .dateDebut(resultSet.getDate( DATED))
                .dateFin(resultSet.getDate( DATEF))
                .idCours(resultSet.getInt("ID_S"))
                .idGroupe(resultSet.getInt("ID_G"))
                .idSalle(resultSet.getInt("ID_C"))
                .build();
    }

    @Override
    public void persist(Creneau creneau) throws DataAccessException {
        try {
            persistPS.setDate(1, (java.sql.Date) creneau.getDateDebut());
            persistPS.setDate(2, (java.sql.Date) creneau.getDateFin());
            persistPS.setInt(3,creneau.getIdSalle());
            persistPS.setInt(4,creneau.getIdGroupe());
            persistPS.setInt(5,creneau.getIdCours());
            persistPS.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    public void getCreneaux(String[][] creneau) {
        int i = 0;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();
        String crenaux = "select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G) join COURS ON (GROUP_COURS.ID_C = COURS.ID_C)\n" +
                "where LOGIN ='FFF' ";
        try {
            statement = connection1.createStatement();
            ResultSet queryResult = statement.executeQuery(crenaux);

            while ((queryResult.next())) {
                creneau[i][0] = String.valueOf(queryResult.getTimestamp( DATED));
                creneau[i][1] = String.valueOf(queryResult.getTimestamp( DATEF));
                creneau[i][2] = queryResult.getString(BAT);
                creneau[i][3] = String.valueOf(queryResult.getInt("NUM"));
                creneau[i][4] = String.valueOf(queryResult.getBoolean(VID));
                creneau[i][5] = queryResult.getString("NOM");
                creneau[i][6] = queryResult.getString(NAT);
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void remove(Object creneau) throws DataAccessException {
        try {
            connection.createStatement().execute("DELETE FROM " + getTableName() + " WHERE DATE_D=" + ((Creneau)creneau).getDateDebut()  + ", ID_G= " + ((Creneau)creneau).getDateFin()  + ", ID_C="  + ((Creneau)creneau).getIdCours()  + "" );
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void RemoveCreneauByDated(String d) {
        try {
             preparedStatement= connection.prepareStatement("DELETE FROM CRENEAUX WHERE DATE_D=? ");
            preparedStatement.setString(1, d);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void update(Creneau creneau) throws DataAccessException {
        try {
            updatePS.setDate(1, (java.sql.Date) creneau.getDateDebut());
            updatePS.setDate(2, (java.sql.Date) creneau.getDateFin());
            updatePS.setInt(3,creneau.getIdSalle());
            updatePS.setInt(4,creneau.getIdGroupe());
            updatePS.setInt(5,creneau.getIdCours());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        try {
            super.update();
        } catch (univ.tln.daos.exceptions.DataAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @params date debut et fin (heure,minute et jour) et batiment
     * @return void
     *
     * cette methode affiche les batiment dont il'y a une salle libre pour une date donnéé
     */
    public void initialize_batiment(Spinner<Integer> md_m_f, Spinner<Integer> md_m_d, Spinner <Integer> md_h_f, Spinner<Integer> md_h_d, ComboBox<String> md_bat, DatePicker md_date) {

        String[] Salle_libre = new String[13];

        if ((  md_m_f.getValue() >   md_m_d.getValue() && Objects.equals(md_h_f.getValue(), md_h_d.getValue())) || (  md_h_f.getValue() >   md_h_d.getValue())) {
            int i = 0;
            try {
                preparedStatement = connection.prepareStatement(" select distinct batiment from salle where ID_S not in ( select ID_S FROM CRENEAUX WHERE(DATE_D <= ? and date_f >= ?)or ((date_d between ? and ?)or (date_f between ? and ?)))");
                String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
                String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
                preparedStatement.setString(1, date1);
                preparedStatement.setString(3, date1);
                preparedStatement.setString(5, date1);
                preparedStatement.setString(2, date2);
                preparedStatement.setString(4, date2);
                preparedStatement.setString(6, date2);

                ResultSet queryResult = preparedStatement.executeQuery();

                while ((queryResult.next())) {
                    Salle_libre[i] = String.valueOf(queryResult.getString(BAT));
                    i++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            md_bat.getItems().removeAll(md_bat.getItems());
            for (int r = 0; r < i; r++) {

                md_bat.getItems().add(Salle_libre[r]);
            }

        }




    }

    /**
     *
     * @params date debut et fin (heure,minute et jour) , batiment et salle
     * @return void
     *
     * cette methode affiche les salle  libre pour une date et un batiment donnéé
     */
    public void initialize_salle(Spinner<Integer> md_m_f, Spinner<Integer> md_m_d, Spinner<Integer> md_h_f, Spinner<Integer> md_h_d, ComboBox<String> md_bat, DatePicker md_date,ComboBox<String> md_s) {

        String[] Salle_libre = new String[13];

        if ((  md_m_f.getValue() >   md_m_d.getValue() &&   md_h_f.getValue().equals(md_h_d.getValue())) || (  md_h_f.getValue() >   md_h_d.getValue())) {
            int i = 0;
            try {
                preparedStatement = connection.prepareStatement(" select distinct num from salle where batiment =? and ID_S not in ( select ID_S FROM CRENEAUX WHERE (DATE_D <= ? and date_f >= ?)or ((date_d between ? and ?)or (date_f between ? and ?)))");
                String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
                String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
                preparedStatement.setString(1, md_bat.getValue());
                preparedStatement.setString(2, date1);
                preparedStatement.setString(4, date1);
                preparedStatement.setString(6, date1);
                preparedStatement.setString(3, date2);
                preparedStatement.setString(5, date2);
                preparedStatement.setString(7, date2);

                ResultSet queryResult = preparedStatement.executeQuery();

                while ((queryResult.next())) {
                    Salle_libre[i] = String.valueOf(queryResult.getString("num"));
                    i++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            md_s.getItems().removeAll(md_s.getItems());
            for (int r = 0; r < i; r++) {

                md_s.getItems().add(Salle_libre[r]);
            }

        }

    }

    /**
     *
     * @params date debut et fin (heure,minute et jour) et group
     * @return void
     *
     * cette methode affiche les groupe libre pour une date donnéé
     */
    public void initialize_formation(Spinner<Integer> md_m_f, Spinner<Integer> md_m_d, Spinner<Integer> md_h_f, Spinner<Integer> md_h_d, ComboBox<String> md_f, DatePicker md_date)  {

        String[] formation_libre = new String[13];

        if (( md_m_f.getValue() > md_m_d.getValue() && md_h_f.getValue().equals(md_h_d.getValue())) || ( md_h_f.getValue() > md_h_d.getValue())) {
            int i = 0;
            try {
                preparedStatement = connection.prepareStatement(" select nom from groups where ID_G not in ( select ID_G FROM CRENEAUX WHERE(DATE_D <= ? and date_f >= ?)and ((date_d between ? and ?)or (date_f between ? and ?)))");
                String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
                String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
                preparedStatement.setString(1, date1);
                preparedStatement.setString(3, date1);
                preparedStatement.setString(5, date1);
                preparedStatement.setString(2, date2);
                preparedStatement.setString(4, date2);
                preparedStatement.setString(6, date2);

                ResultSet queryResult = preparedStatement.executeQuery();

                while ((queryResult.next())) {
                    formation_libre[i] = String.valueOf(queryResult.getString("nom"));
                    i++;
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }


            md_f.getItems().removeAll(md_f.getItems());
            for (int r = 0; r < i; r++) {

                md_f.getItems().add(formation_libre[r]);
            }

        }
    }

    /**
     *
     * @params
     * @return void
     *
     * cette methode affiche touts formation pour filtrer les planing par formation
     */
    public void initialize_pickformation(ComboBox<String>  pickfomation)  {

        String[] formation_libre = new String[13];


            int i = 0;
            try {
                preparedStatement = connection.prepareStatement(" select nom from groups");
                ResultSet queryResult = preparedStatement.executeQuery();

                while ((queryResult.next())) {
                    formation_libre[i] = String.valueOf(queryResult.getString("nom"));
                    i++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        pickfomation.getItems().removeAll(pickfomation.getItems());
            for (int r = 0; r < i; r++) {

                pickfomation.getItems().add(formation_libre[r]);
            }
    }

    /**
     *
     * @params
     * @return void
     *
     * cette methode affiche les enseignant pour filtrer les planing par enseignant
     */
    public void initialize_pickenseignant(ComboBox<String>  pickteacher)  {

        String[] formation_libre = new String[13];


        int i = 0;
        try {
            preparedStatement = connection.prepareStatement(" select LOGIN from ENSEIGNANT");
            ResultSet queryResult = preparedStatement.executeQuery();

            while ((queryResult.next())) {
                formation_libre[i] = String.valueOf(queryResult.getString("LOGIN"));

                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        pickteacher.getItems().removeAll(pickteacher.getItems());
        for (int r = 0; r < i; r++) {

            pickteacher.getItems().add(formation_libre[r]);
        }
    }


    /**
     *
     * @params group ,cours
     * @return void
     *
     * cette methode affiche cours d'un group
     */
    public void initialize_cours(ComboBox<String> md_f,ComboBox<String> md_c)  {

        String[] cours_libre = new String[13];
        int i = 0;
            try {
                preparedStatement= connection.prepareStatement(" select c.nom from cours c join group_cours gc on c.id_c=gc.id_c join groups g on gc.id_g=g.id_g where g.nom=?");preparedStatement.setString(1, md_f.getValue());

                ResultSet queryResult = preparedStatement.executeQuery();

                while ((queryResult.next())) {
                    cours_libre[i] = String.valueOf(queryResult.getString("nom"));
                    i++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        md_c.getItems().removeAll(md_c.getItems());
            for (int r = 0; r < i; r++) {

                md_c.getItems().add(cours_libre[r]);
            }

        }

    /**
     *
     * @params group,cours et nature
     * @return void
     *
     * cette methode affiche nature des cours d'un group
     */
    public void initialize_nature_cours(ComboBox<String> md_f,ComboBox<String> md_c,ComboBox<String> md_n)  {

        String[] ncours_libre = new String[13];
        int i = 0;
        try {
            preparedStatement = connection.prepareStatement(" select c.nature from cours c join group_cours gc on c.id_c=gc.id_c join groups g on gc.id_g=g.id_g where g.nom=? and c.nom=?");

            preparedStatement.setString(1, md_f.getValue());
            preparedStatement.setString(2, md_c.getValue());

            ResultSet queryResult = preparedStatement.executeQuery();

            while ((queryResult.next())) {
                ncours_libre[i] = String.valueOf(queryResult.getString(NAT));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        md_n.getItems().removeAll(md_n.getItems());
        for (int r = 0; r < i; r++) {

            md_n.getItems().add(ncours_libre[r]);
        }

    }

    /**
     *
     * @params date debut et fin (heure,minute et jour) group,cours,et nature
     * @return void
     *
     * cette methode affiche les enseignat d un group pour un cours d une nature donnée
     */
    public void initialize_enseignant( DatePicker md_date,Spinner<Integer> md_m_f, Spinner<Integer> md_m_d, Spinner <Integer> md_h_f, Spinner<Integer> md_h_d,ComboBox<String> md_ens,ComboBox<String> md_c,ComboBox<String> md_n,ComboBox<String> md_f)  {

        String[] ens_libre = new String[20];
        int i = 0;
        try {
            preparedStatement= connection.prepareStatement(" select distinct U.nom,U.prenom from utilisateur U join cours C on C.login=U.login join group_cours gc on C.id_c=gc.id_c join groups g on g.id_g=gc.id_g where C.nom=? and g.nom=? and C.nature=? and U.login not in(select login from creneaux CR join cours C1 on CR.id_c=C1.id_c and ((DATE_D <= ? and date_f >= ?)and ((date_d between ? and ?)or (date_f between ? and ?))))");
            preparedStatement.setString(1, md_c.getValue());
            preparedStatement.setString(2, md_f.getValue());
            preparedStatement.setString(3, md_n.getValue());
            String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
            String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
            preparedStatement.setString(4, date1);
            preparedStatement.setString(8, date1);
            preparedStatement.setString(6, date1);
            preparedStatement.setString(9, date2);
            preparedStatement.setString(5, date2);
            preparedStatement.setString(7, date2);


            ResultSet queryResult = preparedStatement.executeQuery();

            while ((queryResult.next())) {
                ens_libre[i] = queryResult.getString("nom") + " " + queryResult.getString("prenom");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        md_ens.getItems().removeAll(md_ens.getItems());
        for (int r = 0; r < i; r++) {

            md_ens.getItems().add(ens_libre[r]);
        }

    }


    public void insertcreneau(DatePicker md_date, Spinner<Integer>md_h_d, Spinner<Integer>md_m_d, Spinner<Integer>md_h_f, Spinner<Integer>md_m_f, ComboBox<String>md_bat, ComboBox<String>md_s, ComboBox<String>md_f, ComboBox<String>md_c, ComboBox<String>md_n, ComboBox<String>md_ens, Label ajoutermessage) {
        String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
        String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
        try {
            preparedStatement = connection.prepareStatement("insert into creneaux values( ?,?,(select id_s from salle where num=? and batiment=?),(select id_g from groups where nom=? ),(select id_c from cours where nom=? and nature =? and login in(select login from utilisateur where CONCAT(nom, ' ', prenom)=?)))");

        if (md_bat.getValue() != null && md_s.getValue() != null && md_f.getValue() != null && md_c.getValue() != null && md_n.getValue() != null && md_ens.getValue() != null && md_date.getValue() != null) {
            preparedStatement.setString(1, date1);
            preparedStatement.setString(2, date2);
            preparedStatement.setString(3, md_s.getValue());
            preparedStatement.setString(4, md_bat.getValue());
            preparedStatement.setString(5, md_f.getValue());
            preparedStatement.setString(6, md_c.getValue());
            preparedStatement.setString(7, md_n.getValue());
            preparedStatement.setString(8, md_ens.getValue());
            preparedStatement.executeUpdate();
            ajoutermessage.setTextFill(Color.color(0, 1, 0));
            ajoutermessage.setText("le créneau a bien été ajouté");

        } else {
            ajoutermessage.setTextFill(Color.color(1, 0, 0));
            ajoutermessage.setText("veuillez remplir tout le formulaire");
        }
    } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void insertcreneau2(DatePicker md_date, Spinner<Integer>md_h_d, Spinner<Integer>md_m_d, Spinner<Integer>md_h_f, Spinner<Integer>md_m_f, ComboBox<String>md_bat, ComboBox<String>md_s, ComboBox<String>md_f, ComboBox<String>md_c, ComboBox<String>md_n, ComboBox<String>md_ens, Label ajoutermessage)  {
        String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
        String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
        try {

            preparedStatement = connection.prepareStatement("insert into creneaux values( ?,?,(select id_s from salle where num=? and batiment=?),(select id_g from groups where nom=? ),(select id_c from cours where nom=? and nature =? and login in(select login from utilisateur where CONCAT(nom, ' ', prenom)=?)))");
            if (md_bat.getValue() != null && md_s.getValue() != null && md_f.getValue() != null && md_c.getValue() != null && md_n.getValue() != null && md_ens.getValue() != null && md_date.getValue() != null) {
                preparedStatement.setString(1, date1);
                preparedStatement.setString(2, date2);
                preparedStatement.setString(3, md_s.getValue());
                preparedStatement.setString(4, md_bat.getValue());
                preparedStatement.setString(5, md_f.getValue());
                preparedStatement.setString(6, md_c.getValue());
                preparedStatement.setString(7, md_n.getValue());
                preparedStatement.setString(8, md_ens.getValue());
                ajoutermessage.setTextFill(Color.rgb(0, 133, 33));
                ajoutermessage.setText("le créneau a bien été ajouté");
            } else {
                ajoutermessage.setTextFill(Color.rgb(220, 0, 0));
                ajoutermessage.setText("données saisies invalide");
            }
        }
catch (Exception e) {
        e.printStackTrace();
        }
    }

    public void updateCreneaux(DatePicker md_date,Spinner<Integer>heureD,Spinner<Integer>minuteD,Spinner<Integer>heureF,Spinner<Integer>minuteF,ComboBox<String>md_bat,ComboBox<String>nums,String datecondition) throws SQLException {
        String date1 = md_date.getValue().toString() + " " + heureD.getValue().toString() + ":" + minuteD.getValue().toString() + ":00";
        String date2 = md_date.getValue().toString() + " " + heureF.getValue().toString() + ":" + minuteF.getValue().toString() + ":00";
        preparedStatement = connection.prepareStatement("update creneaux set DATE_D = ? , DATE_F = ? , ID_S = (select ID_S from SALLE where NUM = ? and BATIMENT = ?) where DATE_D = ?");
        preparedStatement.setString(1, date1);
        preparedStatement.setString(2, date2);
        preparedStatement.setString(3, nums.getValue());
        preparedStatement.setString(4, md_bat.getValue());
        preparedStatement.setString(5,datecondition);
        preparedStatement.executeUpdate();

    }

public static String dateformat = "yyyy-MM-dd";

    /**
     *
     * @params login ,semaine
     * @return void
     *
     * cette methode affiche les creneau d un enseignant donnée
     */
    public  int   castdatetimebyteacherlogin(String login, Calendar monday,Calendar sunday, String[][] creneau,int i) {//fonction qui remplie une liste des creneaux d'une semaine
try {
            preparedStatement = connection.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) where LOGIN =? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            preparedStatement.setString(1,login);
            DateFormat df = new SimpleDateFormat(dateformat);
            preparedStatement.setDate(2, java.sql.Date.valueOf(df.format(monday.getTime())));
            preparedStatement.setDate(3, java.sql.Date.valueOf(df.format(sunday.getTime())));
            ResultSet queryResult = preparedStatement.executeQuery();
            while ((queryResult.next())) {
                creneau[i][0] = String.valueOf(queryResult.getTimestamp( DATED));
                creneau[i][1] = String.valueOf(queryResult.getTimestamp( DATEF));
                creneau[i][2] = queryResult.getString(BAT);
                creneau[i][3] = String.valueOf(queryResult.getInt("NUM"));
                creneau[i][4] = String.valueOf(queryResult.getBoolean(VID));
                creneau[i][5] = queryResult.getString("NOM");
                creneau[i][6] = queryResult.getString(NAT);
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     *
     * @params group formation ,semaine
     * @return void
     *
     * cette methode affiche les creneau d un group donnée
     */
    public int castdatetimebyformation(String formation, Calendar monday,Calendar sunday, String[][] creneau,int i) {//fonction qui remplie une liste des creneaux d'une semaine

        try  {
            preparedStatement = connection.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,cours.NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) join GROUPS on GROUPS.ID_G=GROUP_COURS.ID_G where GROUPS.NOM=? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            preparedStatement.setString(1,formation);
            DateFormat df = new SimpleDateFormat(dateformat);
            preparedStatement.setDate(2, java.sql.Date.valueOf(df.format(monday.getTime())));
            preparedStatement.setDate(3, java.sql.Date.valueOf(df.format(sunday.getTime())));
            ResultSet queryResult = preparedStatement.executeQuery();
            while ((queryResult.next())) {
                creneau[i][0] = String.valueOf(queryResult.getTimestamp( DATED));
                creneau[i][1] = String.valueOf(queryResult.getTimestamp( DATEF));
                creneau[i][2] = queryResult.getString(BAT);
                creneau[i][3] = String.valueOf(queryResult.getInt("NUM"));
                creneau[i][4] = String.valueOf(queryResult.getBoolean(VID));
                creneau[i][5] = queryResult.getString("NOM");
                creneau[i][6] = queryResult.getString(NAT);
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     *
     * @params login ,semaine
     * @return void
     *
     * cette methode affiche les creneau d un utilisateur donnée
     */
    public int castdatetime(Calendar monday,Calendar sunday, String[][] creneau){ //fonction qui remplie une liste des creneaux d'une semaine
       int j = 0;
        try {
            preparedStatement =connection.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,U.NOM,U.PRENOM,U.EMAIL,C2.NOM as profnom,C2.NATURE from SALLE join CRENEAUX C on SALLE.ID_S = C.ID_S join GROUP_COURS GC on C.ID_G = GC.ID_G and C.ID_C = GC.ID_C JOIN GROUP_ETUDIANT GE on GC.ID_G=GE.ID_G JOIN COURS C2 on GC.ID_C = C2.ID_C JOIN UTILISATEUR U on C2.LOGIN = U.LOGIN WHERE GE.LOGIN=? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            preparedStatement.setString(1,LoginController.user1);
            DateFormat df = new SimpleDateFormat(dateformat);
            preparedStatement.setDate(2, java.sql.Date.valueOf(df.format(monday.getTime())));
            preparedStatement.setDate(3, java.sql.Date.valueOf(df.format(sunday.getTime())));
            ResultSet queryResult = preparedStatement.executeQuery();
            while ((queryResult.next())) {
                creneau[j][0] = String.valueOf(queryResult.getTimestamp( DATED));
                creneau[j][1]= String.valueOf(queryResult.getTimestamp( DATEF));
                creneau[j][2] = queryResult.getString(BAT);
                creneau[j][3] = String.valueOf(queryResult.getInt("NUM"));
                creneau[j][4] = String.valueOf(queryResult.getBoolean(VID));
                creneau[j][5] = queryResult.getString("NOM");
                creneau[j][6] = queryResult.getString("PRENOM");
                creneau[j][7] = queryResult.getString("EMAIL");
                creneau[j][8] = queryResult.getString("profnom");
                creneau[j][9] = queryResult.getString(NAT);
                j++;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return j;
    }

    public int castdatetime2(Calendar monday,Calendar sunday, String[][] creneau) {//fonction qui remplie une liste des creneaux d'une semaine
       int j=0;

        try {
            preparedStatement = connection.prepareStatement("select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,C2.NOM,C2.NATURE,G.nom as grpname from SALLE join CRENEAUX C on SALLE.ID_S = C.ID_S join GROUP_COURS GC on C.ID_G = GC.ID_G and C.ID_C = GC.ID_C join COURS C2 on GC.ID_C = C2.ID_C join GROUPS G on GC.ID_G = G.ID_G where C2.LOGIN =? AND FORMATDATETIME(DATE_D ,'yyyy-MM-dd')>=?  AND FORMATDATETIME(DATE_F ,'yyyy-MM-dd') <=?  ");
            preparedStatement.setString(1, LoginController.user1);
            DateFormat df = new SimpleDateFormat(dateformat);
            preparedStatement.setDate(2, java.sql.Date.valueOf(df.format(monday.getTime())));


            preparedStatement.setDate(3, java.sql.Date.valueOf(df.format(sunday.getTime())));
            ResultSet queryResult = preparedStatement.executeQuery();

            while ((queryResult.next())) {
                creneau[j][0] = String.valueOf(queryResult.getTimestamp( DATED));
                creneau[j][1] = String.valueOf(queryResult.getTimestamp( DATEF));
                creneau[j][2] = queryResult.getString(BAT);
                creneau[j][3] = queryResult.getString("NUM");
                creneau[j][4] = String.valueOf(queryResult.getBoolean(VID));
                creneau[j][5] = queryResult.getString("NOM");
                creneau[j][6] = queryResult.getString(NAT);
                creneau[j][7] = queryResult.getString("grpname");
                j++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return j;
    }


    }

