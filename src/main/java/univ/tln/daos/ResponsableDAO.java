package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.entities.utilisateurs.Enseignant;
import univ.tln.entities.utilisateurs.Responsable;
import univ.tln.entities.utilisateurs.Utilisateur;
import univ.tln.exceptions.DataAccessException;

import java.sql.*;

public class ResponsableDAO extends AbstractDAO<Responsable>{
    public ResponsableDAO() {
        super("INSERT INTO UTILISATEUR(LOGIN, NOM, PRENOM, PASSWORD, EMAIL) VALUES (?,?,?,?,?)",
                "UPDATE UTILISATEUR SET LOGIN=?, NOM=?, PRENOM=?, PASSWORD=?, EMAIL=? WHERE LOGIN=?",
                "SELECT * from RESPONSABLE joint UTILISATEUR ON ENSEIGNANT.login = UTILISATEUR.login  ");
    }

    @Override
    public String getTableName() {
        return "RESPONSABLE";
    }

    @Override
    protected Responsable fromResultSet(ResultSet resultSet) throws SQLException {
        return (Responsable) Utilisateur.builder()
                .login(resultSet.getString("LOGIN"))
                .nom(resultSet.getString("NOM"))
                .prenom(resultSet.getString("PRENOM"))
                .password(resultSet.getString("PASSWORD"))
                .email(resultSet.getString("EMAIL"))
                .build();
    }

    public boolean checkResponsable(String username, String password){
        try {
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT  count(1) from UTILISATEUR join RESPONSABLE on (UTILISATEUR.LOGIN = RESPONSABLE.LOGIN) where RESPONSABLE.LOGIN= '"+username+"' AND PASSWORD = HASH('SHA256','"+password+"',1000)");

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

    public String getResponsableNameBylogin(String l) {
        String m = null;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();

        try {

            String queryString = "select nom from UTILISATEUR where login = ?";
            PreparedStatement statement = connection1.prepareStatement(queryString);
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
            PreparedStatement statement = connection.prepareStatement("SELECT * from RESPONSABLE joint UTILISATEUR ON RESPONSABLE.login = UTILISATEUR.login  ");
            ResultSet resultset = statement.executeQuery();

            while (resultset.next()) {
                System.out.println("id" + resultset.getString("id") + ",Nom" + resultset.getString("nom") +
                        ",Prenom" + resultset.getString("prenom") + ",Email" + resultset.getString("prenom"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Responsable persist(Responsable responsable) throws DataAccessException {
        try {
            persistPS.setString(1, responsable.getLogin());
            persistPS.setString(2, responsable.getNom());
            persistPS.setString(3,responsable.getPrenom());
            persistPS.setString(4,responsable.getPassword());
            persistPS.setString(5,responsable.getEmail());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }

    @Override
    public void remove(Object responsable) throws DataAccessException {
        try {
            connection.createStatement().execute("DELETE FROM " + getTableName() + " WHERE LOGIN=" + ((Responsable)responsable).getLogin());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void update(Responsable responsable) throws DataAccessException {
        try {
            updatePS.setString(1, responsable.getLogin());
            updatePS.setString(2, responsable.getNom());
            updatePS.setString(3,responsable.getPrenom());
            updatePS.setString(4,responsable.getPassword());
            updatePS.setString(5,responsable.getEmail());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();

    }
}