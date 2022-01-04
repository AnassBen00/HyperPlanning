package univ.tln.daos;

import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.utilisateurs.Enseignant;
import univ.tln.entities.utilisateurs.Utilisateur;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class EnseignantDAO extends AbstractDAO<Enseignant> {
    public EnseignantDAO() {
        super("INSERT INTO ENSEIGNANT(LOGIN) VALUES(?)",
                "UPDATE UTILISATEUR SET NOM=?, PRENOM=?, PASSWORD=?, EMAIL=? WHERE LOGIN=?",
                "SELECT * from ENSEIGNANT join UTILISATEUR ON ENSEIGNANT.login = UTILISATEUR.login");
    }

    @Override
    public String getTableName() {
        return "ENSEIGNANT";
    }

    @Override
    protected Enseignant fromResultSet(ResultSet resultSet) throws SQLException {
        return new Enseignant(resultSet.getString("LOGIN"),resultSet.getString("NOM"),resultSet.getString("PRENOM"),resultSet.getString("PASSWORD"),resultSet.getString("EMAIL"));
    }

    public boolean checkEnseignant(String username, String password) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT count(1) from UTILISATEUR join ENSEIGNANT on (UTILISATEUR.LOGIN = ENSEIGNANT.LOGIN) where ENSEIGNANT.LOGIN= '" + username + "' AND PASSWORD = HASH('SHA256','" + password + "')");
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

    public void findAll() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from ENSEIGNANT join UTILISATEUR ON ENSEIGNANT.login = UTILISATEUR.login ");
            ResultSet resultset = statement.executeQuery();

            while (resultset.next()) {
                System.out.println("id" + resultset.getString("id") + ",Nom" + resultset.getString("nom") +
                        ",Prenom" + resultset.getString("prenom") + ",Email" + resultset.getString("prenom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Enseignant> find(String login) throws SQLException {
        Enseignant enseignant = null;

        PreparedStatement findPS = connection.prepareStatement("SELECT * from UTILISATEUR WHERE login = ?");
        findPS.setString(1, login);

        ResultSet rs = findPS.executeQuery();
        while (rs.next())
            enseignant = fromResultSet(rs);

        return Optional.ofNullable(enseignant);
    }

/*    public Enseignant persist() throws DataAccessException, SQLException {
        String login = null;
        try {
            persistPS.executeUpdate();
            ResultSet rs = persistPS.executeQuery();
            if (rs.next()) {
                login = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return find(login).orElseThrow(NotFoundException::new);
    }*/

    @Override
    public void persist(Enseignant enseignant) throws DataAccessException, SQLException {
        try {
            persistPS.setString(1, enseignant.getLogin());
            persistPS.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void remove(Object enseignant) throws DataAccessException {
        try {
            connection.createStatement().execute("DELETE FROM " + getTableName() + " WHERE LOGIN=" + ((Enseignant) enseignant).getLogin());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void update(Enseignant enseignant) throws DataAccessException {
        try {
            updatePS.setString(1, enseignant.getNom());
            updatePS.setString(2, enseignant.getPrenom());
            updatePS.setString(3, enseignant.getPassword());
            updatePS.setString(4, enseignant.getEmail());
            updatePS.setString(5, enseignant.getLogin());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();

    }

    public boolean checkEnseignantPass(String passwrd) {
        try {
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT  count(1) from UTILISATEUR join ENSEIGNANT on (UTILISATEUR.LOGIN = ENSEIGNANT.LOGIN) where PASSWORD = HASH('SHA256','"+passwrd+"')");

            while ((queryResult.next())){
                if( queryResult.getInt(1)==1){
                    return true;
                }else return false;
            }
        }
        catch (
                SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Enseignant findbyLogin(String login) throws SQLException {
        Enseignant enseignant = new Enseignant();
        PreparedStatement pstmt = connection.prepareStatement("select * from ENSEIGNANT join UTILISATEUR on ENSEIGNANT.login = UTILISATEUR.login where ENSEIGNANT.login = ?");
        pstmt.setString(1, login);
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            enseignant.setLogin(resultSet.getString("LOGIN"));
            enseignant.setEmail(resultSet.getString("EMAIL"));
            enseignant.setNom(resultSet.getString("NOM"));
            enseignant.setPrenom(resultSet.getString("PRENOM"));
            enseignant.setPassword(resultSet.getString("PASSWORD"));
        }
        return enseignant;
    }
}
