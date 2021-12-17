package univ.tln.daos;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import lombok.extern.java.Log;
import univ.tln.DatabaseConnection;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.datasource.DBCPDataSource;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.logging.Level;





@Log
public class DaoajoutCreneau {


    public Connection _connection = null;


    public void connect() {
        DatabaseConnection connection = new DatabaseConnection();
        _connection = connection.connectDB();

    }

    public void initialize_batiment1() throws SQLException {


        String query = " select distinct batiment from salle";

        Statement s = null;
        try {
            s = _connection.createStatement();
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
       PreparedStatement pstmt = _connection.prepareStatement(" select distinct batiment from salle where ID_S not in ( select ID_S FROM CRENEAUX WHERE(DATE_D <= ? and date_f >= ?)and ((date_d between ? and ?)or (date_f between ? and ?)))");

        if (((int) md_m_f.getValue() > (int) md_m_d.getValue() && (int) md_h_f.getValue() == (int) md_h_d.getValue()) || ((int) md_h_f.getValue() > (int) md_h_d.getValue())) {
            int i = 0;
            try {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
                String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
                System.out.println("date1" + date1);
                System.out.println("date2" + date2);
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

                System.out.println(Salle_libre);

            md_bat.getItems().removeAll(md_bat.getItems());
            for (int r = 0; r < i; r++) {

                md_bat.getItems().add(Salle_libre[r]);
            }

        }


    }

    public void initialize_salle(Spinner<Integer> md_m_f, Spinner<Integer> md_m_d, Spinner md_h_f, Spinner<Integer> md_h_d, ComboBox<String> md_bat, DatePicker md_date,ComboBox<String> md_s) throws SQLException, ParseException {

        String[] Salle_libre = new String[13];
        PreparedStatement pstmt = _connection.prepareStatement(" select distinct num from salle where batiment =? and ID_S not in ( select ID_S FROM CRENEAUX WHERE (DATE_D <= ? and date_f >= ?)and ((date_d between ? and ?)or (date_f between ? and ?)))");

        if (((int) md_m_f.getValue() > (int) md_m_d.getValue() && (int) md_h_f.getValue() == (int) md_h_d.getValue()) || ((int) md_h_f.getValue() > (int) md_h_d.getValue())) {
            int i = 0;
            try {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                String date1 = md_date.getValue().toString() + " " + md_h_d.getValue().toString() + ":" + md_m_d.getValue().toString() + ":00";
                String date2 = md_date.getValue().toString() + " " + md_h_f.getValue().toString() + ":" + md_m_f.getValue().toString() + ":00";
                System.out.println("date1" + date1);
                System.out.println("date2" + date2);
                pstmt.setString(1, md_s.getValue().toString());
                pstmt.setString(2, date1);
                pstmt.setString(4, date1);
                pstmt.setString(6, date1);
                pstmt.setString(3, date2);
                pstmt.setString(5, date2);
                pstmt.setString(7, date2);

                ResultSet queryResult = pstmt.executeQuery();

                while ((queryResult.next())) {
                    Salle_libre[i] = String.valueOf(queryResult.getString("batiment"));
                    i++;
                }
            }catch (Exception e){
                System.out.println("aaaaaaaaa"+e);
            }

            System.out.println(Salle_libre);

            md_bat.getItems().removeAll(md_bat.getItems());
            for (int r = 0; r < i; r++) {

                md_s.getItems().add(Salle_libre[r]);
            }

        }


    }

}



