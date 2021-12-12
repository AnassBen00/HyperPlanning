package univ.tln.daos;



//import univ.tln.entities.creneaux.Cours;

import univ.tln.DatabaseConnection;
import univ.tln.entities.creneaux.Cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CoursDAO {

    public Cours getCours() {
        return null;
    }

    public void removeCours(String id) {
            DatabaseConnection connection = new DatabaseConnection();
            Connection connection1 = connection.connectDB();

            try {
                String queryString = "delete from cours where id = ? ";
                PreparedStatement statement = connection1.prepareStatement(queryString);
                statement.setString(1, id);
                statement.executeUpdate();
                System.out.println("Data deleted Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public void insertCours(Cours cours) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();
        try {


            if (cours.getId() != null ) {
                PreparedStatement statement = connection1.prepareStatement("update salle set nature = ?, nom = ? where id_c = ? ");
                statement.setString(1,cours.getId());
                statement.setString(2,cours.getNature());
                statement.setString(3,cours.getNomduCours());
                statement.executeQuery();
            } else {
                PreparedStatement statement = connection1.prepareStatement("insert into salle (id_c , nature , nom) values (?,?,?) ");
                statement.setString(1,cours.getId());
                statement.setString(2,cours.getNature());
                statement.setString(3,cours.getNomduCours());
                statement.executeQuery();
            }

            System.out.println(cours.getId() + cours.getNature() + cours.getNomduCours() + "saved into database");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("unable to save salle");
        }
    }
}


