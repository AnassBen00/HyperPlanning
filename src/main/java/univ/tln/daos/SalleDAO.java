package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.entities.salles.Salle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class SalleDAO {

    /*public Salle getSalleByNom(String nom) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();
        try {
            PreparedStatement statement = connection1.prepareStatement("select * from salle where nom = ?");
            statement.setString(1,nom);
            ResultSet resultSet = statement.executeQuery();
            //Salle salle = new Salle();
            while (resultSet.next()) {
                salle.setNomSalle(resultSet.getString("id"));
                salle.setNomSalle(resultSet.getString("nom"));
                salle.setNomSalle(resultSet.getString("video_p"));
                }
            return salle;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }


    public void removeSalle(String id) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();

        try {
            String queryString = "delete from salle where id = ? ";
            PreparedStatement statement = connection1.prepareStatement(queryString);
            statement.setString(1, id);
            statement.executeUpdate();
            System.out.println("Data deleted Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertSalle(Salle salle) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();
        try {


            if (salle.getId() != 0 ) {
                PreparedStatement statement = connection1.prepareStatement("update salle set num = ?, video_p = ? where id_s = ? ");
                statement.setInt(1,salle.getId());
                statement.setString(2,salle.getNomSalle());
                statement.setBoolean(3,salle.isVideoProjecteur());
                statement.executeQuery();
            } else {
                PreparedStatement statement = connection1.prepareStatement("insert into salle (id_s , num , video-p) values (?,?,?) ");
                statement.setInt(1,salle.getId());
                statement.setString(2,salle.getNomSalle());
                statement.setBoolean(3,salle.isVideoProjecteur());
                statement.executeQuery();
            }

            System.out.println(salle.getId() + salle.getNomSalle() + salle.isVideoProjecteur() + "saved into database");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("unable to save salle");
        }
    }
*/


}
