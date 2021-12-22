package univ.tln.daos;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import univ.tln.DatabaseConnection;
import univ.tln.entities.creneaux.Creneau;
import univ.tln.exceptions.DataAccessException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CreneauxDAO extends AbstractDAO<Creneau>{
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
                .dateDebut(resultSet.getDate("DATE_D"))
                .dateFin(resultSet.getDate("DATE_F"))
                .idCours(resultSet.getInt("ID_S"))
                .idGroupe(resultSet.getInt("ID_G"))
                .idSalle(resultSet.getInt("ID_C"))
                .build();
    }

    @Override
    public Creneau persist(Creneau creneau) throws DataAccessException {
        try {
            persistPS.setDate(1, (java.sql.Date) creneau.getDateDebut());
            persistPS.setDate(2, (java.sql.Date) creneau.getDateFin());
            persistPS.setInt(3,creneau.getIdSalle());
            persistPS.setInt(4,creneau.getIdGroupe());
            persistPS.setInt(5,creneau.getIdCours());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }

    public void getCreneaux(String[][] creneau) {
        int i = 0;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();
        String crenaux = "select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G) join COURS ON (GROUP_COURS.ID_C = COURS.ID_C)\n" +
                "where LOGIN ='FFF' ";
        try {
            Statement statement = connection1.createStatement();
            ResultSet queryResult = statement.executeQuery(crenaux);

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

            System.out.println(Arrays.deepToString(creneau));
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
    }

    public void RemoveCreneauByDated(String d) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM CRENEAUX WHERE DATE_D=? ");
            statement.setString(1, d);
            statement.executeUpdate();
            System.out.println("deleted succesflly");
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
        super.update();

    }


    public void initialize_batiment1() throws SQLException {
        String query = " select distinct batiment from salle";

        Statement s = null;
        try {
            s = connection.createStatement();
            s.execute(query);
            ResultSet queryResult = s.executeQuery(query);
            while (queryResult.next()) {
                System.out.println(queryResult);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void initialize_batiment(Spinner<Integer> md_m_f, Spinner<Integer> md_m_d, Spinner md_h_f, Spinner<Integer> md_h_d, ComboBox<String> md_bat, DatePicker md_date) throws SQLException, ParseException {

        String[] Salle_libre = new String[13];
        PreparedStatement pstmt = connection.prepareStatement(" select distinct batiment from salle where ID_S not in ( select ID_S FROM CRENEAUX WHERE(DATE_D <= ? and date_f >= ?)and ((date_d between ? and ?)or (date_f between ? and ?)))");

        if (((int) md_m_f.getValue() > (int) md_m_d.getValue() && (int) md_h_f.getValue() == (int) md_h_d.getValue()) || ((int) md_h_f.getValue() > (int) md_h_d.getValue())) {
            int i = 0;
            try {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
                String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
                pstmt.setString(1, date1);
                pstmt.setString(3, date1);
                pstmt.setString(5, date1);
                pstmt.setString(2, date2);
                pstmt.setString(4, date2);
                pstmt.setString(6, date2);

                ResultSet queryResult = pstmt.executeQuery();

                while ((queryResult.next())) {
                    Salle_libre[i] = String.valueOf(queryResult.getString("batiment"));
                    i++;
                }
            }catch (Exception e){
                System.out.println("aaaaaaaaa"+e);
            }


            md_bat.getItems().removeAll(md_bat.getItems());
            for (int r = 0; r < i; r++) {

                md_bat.getItems().add(Salle_libre[r]);
            }

        }




    }

    public void initialize_salle(Spinner<Integer> md_m_f, Spinner<Integer> md_m_d, Spinner md_h_f, Spinner<Integer> md_h_d, ComboBox<String> md_bat, DatePicker md_date,ComboBox<String> md_s) throws SQLException, ParseException {

        String[] Salle_libre = new String[13];
        PreparedStatement pstmt = connection.prepareStatement(" select distinct num from salle where batiment =? and ID_S not in ( select ID_S FROM CRENEAUX WHERE (DATE_D <= ? and date_f >= ?)and ((date_d between ? and ?)or (date_f between ? and ?)))");

        if (((int) md_m_f.getValue() > (int) md_m_d.getValue() && (int) md_h_f.getValue() == (int) md_h_d.getValue()) || ((int) md_h_f.getValue() > (int) md_h_d.getValue())) {
            int i = 0;
            try {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
                String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
                pstmt.setString(1, md_bat.getValue().toString());
                pstmt.setString(2, date1);
                pstmt.setString(4, date1);
                pstmt.setString(6, date1);
                pstmt.setString(3, date2);
                pstmt.setString(5, date2);
                pstmt.setString(7, date2);

                ResultSet queryResult = pstmt.executeQuery();

                while ((queryResult.next())) {
                    Salle_libre[i] = String.valueOf(queryResult.getString("num"));
                    i++;
                }
            }catch (Exception e){
                System.out.println("aaaaaaaaa"+e);
            }

            md_s.getItems().removeAll(md_s.getItems());
            for (int r = 0; r < i; r++) {

                md_s.getItems().add(Salle_libre[r]);
            }

        }

    }
    public void initialize_formation(Spinner<Integer> md_m_f, Spinner<Integer> md_m_d, Spinner md_h_f, Spinner<Integer> md_h_d, ComboBox<String> md_f, DatePicker md_date) throws SQLException, ParseException {

        String[] formation_libre = new String[13];
        PreparedStatement pstmt = connection.prepareStatement(" select nom from groups where ID_G not in ( select ID_G FROM CRENEAUX WHERE(DATE_D <= ? and date_f >= ?)and ((date_d between ? and ?)or (date_f between ? and ?)))");

        if (((int) md_m_f.getValue() > (int) md_m_d.getValue() && (int) md_h_f.getValue() == (int) md_h_d.getValue()) || ((int) md_h_f.getValue() > (int) md_h_d.getValue())) {
            int i = 0;
            try {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
                String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
                pstmt.setString(1, date1);
                pstmt.setString(3, date1);
                pstmt.setString(5, date1);
                pstmt.setString(2, date2);
                pstmt.setString(4, date2);
                pstmt.setString(6, date2);

                ResultSet queryResult = pstmt.executeQuery();

                while ((queryResult.next())) {
                    formation_libre[i] = String.valueOf(queryResult.getString("nom"));
                    System.out.println(formation_libre);
                    i++;
                }
            } catch (Exception e) {
                System.out.println("aaaaaaaaa" + e);
            }


            md_f.getItems().removeAll(md_f.getItems());
            for (int r = 0; r < i; r++) {

                md_f.getItems().add(formation_libre[r]);
            }

        }
    }

    public void initialize_cours(ComboBox<String> md_f,ComboBox<String> md_c) throws SQLException, ParseException {

        String[] cours_libre = new String[13];
        PreparedStatement pstmt = connection.prepareStatement(" select c.nom from cours c join group_cours gc on c.id_c=gc.id_c join groups g on gc.id_g=g.id_g where g.nom=?");
        int i = 0;
            try {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                pstmt.setString(1, md_f.getValue().toString());

                ResultSet queryResult = pstmt.executeQuery();

                while ((queryResult.next())) {
                    cours_libre[i] = String.valueOf(queryResult.getString("nom"));
                    i++;
                }
            } catch (Exception e) {
                System.out.println("aaaaaaaaa" + e);
            }


            md_c.getItems().removeAll(md_c.getItems());
            for (int r = 0; r < i; r++) {

                md_c.getItems().add(cours_libre[r]);
            }

        }

    public void initialize_nature_cours(ComboBox<String> md_f,ComboBox<String> md_c,ComboBox<String> md_n) throws SQLException, ParseException {

        String[] ncours_libre = new String[13];
        PreparedStatement pstmt = connection.prepareStatement(" select c.nature from cours c join group_cours gc on c.id_c=gc.id_c join groups g on gc.id_g=g.id_g where g.nom=? and c.nom=?");
        int i = 0;
        try {
            pstmt.setString(1, md_f.getValue().toString());
            pstmt.setString(2, md_c.getValue().toString());

            ResultSet queryResult = pstmt.executeQuery();

            while ((queryResult.next())) {
                ncours_libre[i] = String.valueOf(queryResult.getString("nature"));
                i++;
            }
        } catch (Exception e) {
            System.out.println("aaaaaaaaa" + e);
        }


        md_n.getItems().removeAll(md_n.getItems());
        for (int r = 0; r < i; r++) {

            md_n.getItems().add(ncours_libre[r]);
        }

    }
    public void initialize_enseignant( DatePicker md_date,Spinner<Integer> md_m_f, Spinner<Integer> md_m_d, Spinner md_h_f, Spinner<Integer> md_h_d,ComboBox<String> md_ens,ComboBox<String> md_c,ComboBox<String> md_n) throws SQLException, ParseException {

        String[] ens_libre = new String[20];
        PreparedStatement pstmt = connection.prepareStatement(" select distinct U.nom,U.prenom from utilisateur U join cours C on C.login=U.login where C.nom=? and C.nature=? and U.login not in(select login from creneaux CR join cours C1 on CR.id_c=C1.id_c and ((DATE_D <= ? and date_f >= ?)and ((date_d between ? and ?)or (date_f between ? and ?))))");
        int i = 0;
        try {

            pstmt.setString(1, md_c.getValue());
            pstmt.setString(2, md_n.getValue());
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
            String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
            pstmt.setString(3, date1);
            pstmt.setString(7, date1);
            pstmt.setString(5, date1);
            pstmt.setString(8, date2);
            pstmt.setString(4, date2);
            pstmt.setString(6, date2);


            ResultSet queryResult = pstmt.executeQuery();

            while ((queryResult.next())) {
                ens_libre[i] = String.valueOf(queryResult.getString("nom"))+" "+String.valueOf(queryResult.getString("prenom"));
                i++;
                System.out.println(ens_libre[i]);
            }
        } catch (Exception e) {
            System.out.println("aaaaaaaaa" + e);
        }


        md_ens.getItems().removeAll(md_ens.getItems());
        for (int r = 0; r < i; r++) {

            md_ens.getItems().add(ens_libre[r]);
        }

    }

    public void insertcreneau(DatePicker md_date,Spinner<Integer>md_h_d,Spinner<Integer>md_m_d,Spinner<Integer>md_h_f,Spinner<Integer>md_m_f,ComboBox<String>md_bat,ComboBox<String>md_s,ComboBox<String>md_f,ComboBox<String>md_c,ComboBox<String>md_n,ComboBox<String>md_ens) {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
        String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
        try {
            PreparedStatement pstmt = connection.prepareStatement("insert into creneaux values(?,?,select id_s from salle where num=? and bat=?,select id_g from group where nom=? ,select id_c from cours where nom=? and nature =? and login in(select login from utilisateur where CONCAT(nom, ' ', prenom)=?))");
            pstmt.setString(1, date1);
            pstmt.setString(2, date2);
            pstmt.setString(3, md_s.getValue());
            pstmt.setString(4, md_bat.getValue());
            pstmt.setString(5, md_f.getValue());
            pstmt.setString(6, md_c.getValue());
            pstmt.setString(7, md_n.getValue());
            pstmt.setString(8, md_ens.getValue());
            pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
