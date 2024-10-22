package univ.tln.daos;

import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.utilisateurs.Enseignant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class EnseignantDAO extends AbstractDAO<Enseignant> {

    private Statement statement;

    private PreparedStatement preparedStatement;

    public EnseignantDAO() throws DataAccessException, SQLException {
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

    /**
     *
     * @params login and password
     * @return boolean
     *
     * cette methode verifie si un enseignant existe dans la bdd
     */
    public boolean checkEnseignant(String username, String password) throws SQLException {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT count(1) from UTILISATEUR join ENSEIGNANT on (UTILISATEUR.LOGIN = ENSEIGNANT.LOGIN) where ENSEIGNANT.LOGIN= '" + username + "' AND PASSWORD = HASH('SHA256','" + password + "')");
            while ((resultSet.next())) {
                if (resultSet.getInt(1) == 1) {
                    return true;
                } else return false;
            }
        } catch (
                SQLException e) {
            e.getLocalizedMessage();
        }finally {
            statement.close();
        }
        return false;
    }

    /**
     *
     * @params login
     * @return String : login
     *
     * cette methode retourne le nom d'un enseignant en passant son login comme parametre
     */
    public String getEnseignantNameBylogin(String l) throws SQLException {
        String m = null;
        try {
            preparedStatement = connection.prepareStatement("select nom from UTILISATEUR where login = ?");
            preparedStatement.setString(1, l);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                m = resultSet.getString("NOM");
            }
        } catch (SQLException e) {
            e.getLocalizedMessage();
            return null;
        }finally {
            preparedStatement.close();
        }
        return m;
    }

    /**
     *
     * @params login
     * @return Etudiant
     *
     * cette methode retourne un enseignant en passant son login comme parametre
     */
    public Optional<Enseignant> find(String login) throws SQLException {
        Enseignant enseignant = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * from UTILISATEUR WHERE login = ?");
            preparedStatement.setString(1, login);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                enseignant = fromResultSet(rs);

        } finally {
            findPS.close();
        }

        return Optional.ofNullable(enseignant);
    }

    /**
     *
     * @params enseignant
     * @return void
     *
     * cette methode ajoute un enseignant dans la bdd
     */
    @Override
    public void persist(Enseignant enseignant) throws DataAccessException, SQLException {
        try {
            persistPS.setString(1, enseignant.getLogin());
            persistPS.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    /**
     *
     * @params enseignant
     * @return void
     *
     * cette methode supprime un enseignant dans la bdd
     */
    @Override
    public void remove(Object enseignant) throws DataAccessException, SQLException {
        try {
            statement = connection.createStatement();
            statement.execute("DELETE FROM " + getTableName() + " WHERE LOGIN=" + ((Enseignant) enseignant).getLogin());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }finally {
            statement.close();
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

    /**
     *
     * @params password
     * @return boolean
     *
     * cette methode permet de verifie le mot de passe d'un enseignant
     */
    public boolean checkEnseignantPass(String passwrd) throws SQLException {
        try {
            statement = connection.createStatement();
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
        }finally {
            statement.close();
        }
        return false;
    }

    /**
     *
     * @params login
     * @return Etudiant
     *
     * cette methode retourne un enseignant par login
     */
    public Enseignant findbyLogin(String login) throws SQLException {
        Enseignant enseignant = new Enseignant();
        preparedStatement = connection.prepareStatement("select * from ENSEIGNANT join UTILISATEUR on ENSEIGNANT.login = UTILISATEUR.login where ENSEIGNANT.login = ?");
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            enseignant.setLogin(resultSet.getString("LOGIN"));
            enseignant.setEmail(resultSet.getString("EMAIL"));
            enseignant.setNom(resultSet.getString("NOM"));
            enseignant.setPrenom(resultSet.getString("PRENOM"));
            enseignant.setPassword(resultSet.getString("PASSWORD"));
        }
        preparedStatement.close();
        return enseignant;
    }

    /**
     *
     * @params password et login
     * @return void
     *
     * cette methode permet modifier le mot de passe
     */
    public void updatePassByLogin(String psswrd,String login) throws  SQLException{
        preparedStatement = connection.prepareStatement("update UTILISATEUR set PASSWORD = HASH('SHA256','"+psswrd+"') where LOGIN = ?");
        preparedStatement.setString(1,login);
        preparedStatement.executeUpdate();
    }


}
