package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.creneaux.Cours;
import univ.tln.entities.filieres.Filiere;
import univ.tln.entities.groupes.Groupe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoursDAO {

    public Cours getCours() {
        return null;
    }

    DatabaseConnection connection = new DatabaseConnection();
    PreparedStatement statement;
    int idcours;

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
            statement = connection1.prepareStatement("insert into COURS(NATURE , NOM, LOGIN) values (?,?,?) ");

            statement.setString(1,cours.getNature());
            statement.setString(2,cours.getNomduCours());
            statement.setString(3,cours.getLogin());
            statement.execute();

        } catch (SQLException e) {
            e.getLocalizedMessage();
        }finally {
            statement.close();
        }
    }

    public int find(String nom) throws SQLException {

        Connection connection1 = connection.connectDB();
        statement = connection1.prepareStatement("SELECT * from COURS WHERE NOM = ?");
        statement.setString(1, nom);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            idcours = rs.getInt("ID_C");
        statement.close();
        return idcours;
    }

    public void insertGroupeCours(String nomGroupe, int idC) throws SQLException {
        Connection connection1 = connection.connectDB();
        try (GroupeDAO groupeDAO = new GroupeDAO()){
            Groupe groupe = groupeDAO.find(nomGroupe);
            statement = connection1.prepareStatement("insert into GROUP_COURS(ID_G, ID_C, ID_F, TAUX_H) values (?,?,?,?) ");
            statement.setInt(1,groupe.getId());
            statement.setInt(2,idC);
            statement.setInt(3,1);
            statement.setDouble(4,0.0);

            statement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            e.getLocalizedMessage();
        }finally {
            statement.close();
        }
    }
}


