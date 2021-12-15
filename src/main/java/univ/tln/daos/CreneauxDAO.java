package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.entities.creneaux.Creneau;

import java.sql.*;
import java.util.Arrays;
import java.util.Date;

public class CreneauxDAO {

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


    public void insertCreaneaux(Creneau creneau) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();
        try {

            PreparedStatement statement = connection1.prepareStatement("insert into salle (date_d,date_f , id_s , id_g) values (?,?,?,?) ");
            statement.setString(1, creneau.getDateDebut());
            statement.setString(2, creneau.getDateFin());
            statement.setString(3, creneau.getIdSalle());
            statement.setString(4, creneau.getIdCours());
            statement.executeQuery();
            System.out.println(creneau.getIdCours() + "saved into database");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("unable to save creneau");
        }

    }

    public void RemoveCreneauByDated(String d) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();
        try {
            PreparedStatement statement = connection1.prepareStatement("DELETE FROM CRENEAUX WHERE DATE_D=? ");
            //long timeInMilliSeconds = d.getTime();
            //java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
            //System.out.println(date1);
            statement.setString(1, d);

            statement.executeUpdate();
            System.out.println("deleted succesflly");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
