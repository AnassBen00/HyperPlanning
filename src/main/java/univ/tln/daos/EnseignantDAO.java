package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.LoginController;
import univ.tln.entities.creneaux.Creneau;
import univ.tln.entities.utilisateurs.Enseignant;
import univ.tln.entities.utilisateurs.Utilisateur;
import univ.tln.exceptions.DataAccessException;

import java.sql.*;

public class EnseignantDAO extends AbstractDAO<Enseignant>{
    public EnseignantDAO() {
        super("INSERT INTO UTILISATEUR(LOGIN, NOM, PRENOM, PASSWORD, EMAIL) VALUES (?,?,?,?,?)",
                "UPDATE UTILISATEUR SET LOGIN=?, NOM=?, PRENOM=?, PASSWORD=?, EMAIL=? WHERE LOGIN=?");
    }

    @Override
    public String getTableName() {
        return "CRENEAUX";
    }

    @Override
    protected Enseignant fromResultSet(ResultSet resultSet) throws SQLException {
        return (Enseignant) Utilisateur.builder()
                .login(resultSet.getString("LOGIN"))
                .nom(resultSet.getString("NOM"))
                .prenom(resultSet.getString("PRENOM"))
                .password(resultSet.getString("PASSWORD"))
                .email(resultSet.getString("EMAIL"))
                .build();
    }

    public boolean checkEnseignant(String username, String password) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT  count(1) from UTILISATEUR join ENSEIGNANT on (UTILISATEUR.LOGIN = ENSEIGNANT.LOGIN) where ENSEIGNANT.LOGIN= '" + username + "' AND PASSWORD = HASH('SHA256','" + password + "',1000)");
            while ((resultSet.next())) {
                if (resultSet.getInt(1) == 1) {
                    return true;
                } else return false;//loginmessage.setText("invalid try again");
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getEnseignantNameBylogin(String l) {
        String m = null;
        try {
            PreparedStatement statement = connection.prepareStatement("select nom from UTILISATEUR where login = ?");
            statement.setString(1, l);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                m = resultSet.getString("NOM");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return m;
    }

    @Override
    public Enseignant persist(Enseignant enseignant) throws DataAccessException {
        try {
            persistPS.setString(1, enseignant.getLogin());
            persistPS.setString(2, enseignant.getNom());
            persistPS.setString(3,enseignant.getPrenom());
            persistPS.setString(4,enseignant.getPassword());
            persistPS.setString(5,enseignant.getEmail());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }

    @Override
    public void remove(Object enseignant) throws DataAccessException {
        try {
            connection.createStatement().execute("DELETE FROM " + getTableName() + " WHERE LOGIN=" + ((Enseignant)enseignant).getLogin());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void update(Enseignant enseignant) throws DataAccessException {
        try {
            updatePS.setString(1, enseignant.getLogin());
            updatePS.setString(2, enseignant.getNom());
            updatePS.setString(3,enseignant.getPrenom());
            updatePS.setString(4,enseignant.getPassword());
            updatePS.setString(5,enseignant.getEmail());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();

    }
}
