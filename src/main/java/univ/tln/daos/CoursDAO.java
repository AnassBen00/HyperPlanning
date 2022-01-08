package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.entities.creneaux.Cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CoursDAO {

    public Cours getCours() {
        return null;
    }

    DatabaseConnection connection = new DatabaseConnection();
    PreparedStatement statement;

    public void removeCours(String id) throws SQLException {
        Connection connection1 = connection.connectDB();

        try {
            String queryString = "delete from cours where id = ? ";
            statement = connection1.prepareStatement(queryString);
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getLocalizedMessage();
        }finally {
            connection1.close();
        }
    }

    public void insertCours(Cours cours) throws SQLException {
        Connection connection1 = connection.connectDB();
        try {
            if (cours.getId() != null ) {
                statement = connection1.prepareStatement("update salle set nature = ?, nom = ? where id_c = ? ");
                statement.setString(1,cours.getId());
                statement.setString(2,cours.getNature());
                statement.setString(3,cours.getNomduCours());
                statement.executeQuery();
            } else {
                statement = connection1.prepareStatement("insert into salle (id_c , nature , nom) values (?,?,?) ");
                statement.setString(1,cours.getId());
                statement.setString(2,cours.getNature());
                statement.setString(3,cours.getNomduCours());
                statement.executeQuery();

            }
        } catch (SQLException e) {
            e.getLocalizedMessage();
        }finally {
            statement.close();
        }
    }
}


