package univ.tln.daos;

import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.utilisateurs.Etudiant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EtudiantDAO extends AbstractDAO<Etudiant> {
    public EtudiantDAO() {
        super("INSERT INTO ETUDIANT(LOGIN, NVX_ETUDE, PROMO, ID_F) VALUES (?,?,?,?)",
                "UPDATE UTILISATEUR SET LOGIN=?, NOM=?, PRENOM=?, PASSWORD=?, EMAIL=? WHERE LOGIN=?",
                "SELECT * from ETUDIANT join UTILISATEUR ON ETUDIANT.login = UTILISATEUR.login  ");
    }

    @Override
    public String getTableName() {
        return "ETUDIANT";
    }

    @Override
    protected Etudiant fromResultSet(ResultSet resultSet) throws SQLException {
        return new Etudiant(resultSet.getString("login"), resultSet.getString("password"), resultSet.getString("nom"),
                resultSet.getString("prenom"), resultSet.getString("email"),
                resultSet.getString("nvx_etude"), resultSet.getString("promo"), resultSet.getInt("id_f"));
    }

    public boolean checkEtudiant(String username, String password) {
        try {
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT  count(1) from UTILISATEUR join ETUDIANT on (UTILISATEUR.LOGIN = ETUDIANT.LOGIN) where ETUDIANT.LOGIN= '" + username + "' AND PASSWORD = HASH('SHA256','" + password + "')");
            while ((queryResult.next())) {
                if (queryResult.getInt(1) == 1) {
                    return true;
                } else return false;
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getEtudiantNameBylogin(String l) {
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

    public List<Etudiant> findAll() {
        List<Etudiant> etudiants = new ArrayList<Etudiant>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from ETUDIANT join UTILISATEUR  ON ETUDIANT.login = UTILISATEUR.login ");
            ResultSet resultset = statement.executeQuery();
            while (resultset.next()) {

                System.out.println(resultset.getString("NOM"));
                System.out.println(resultset.getString("PRENOM"));
                Etudiant etudiant = new Etudiant(resultset.getString("login"), resultset.getString("password"), resultset.getString("nom"),
                        resultset.getString("prenom"), resultset.getString("email"),
                        resultset.getString("nvx_etude"), resultset.getString("promo"), resultset.getInt("id_f"));
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return etudiants;
    }

    public Optional<Etudiant> find(String login) throws SQLException {
        Etudiant etudiant = null;

        PreparedStatement findPS = connection.prepareStatement("SELECT * from ETUDIANT join UTILISATEUR ON ETUDIANT.login = ?");
        findPS.setString(1, login);

        ResultSet rs = findPS.executeQuery();
        while (rs.next())
            etudiant = fromResultSet(rs);

        return Optional.ofNullable(etudiant);
    }

    /*public Etudiant persist() throws DataAccessException, SQLException {
        String login = null;
        try {
            persistPS.executeUpdate();
            ResultSet rs = persistPS.executeQuery("");
            if (rs.next()) {
                //
                login = rs.getString(1);

            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return find(login).orElseThrow(NotFoundException::new);
    }*/


    // LOGIN, NVX_ETUDE, PROMO, ID_F

    @Override
    public void persist(Etudiant etudiant) throws DataAccessException, SQLException {
        try {
            persistPS.setString(1, etudiant.getLogin());
            persistPS.setString(2, etudiant.getNvxEtude());
            persistPS.setString(3, etudiant.getPromo());
            persistPS.setInt(4, etudiant.getIdFiliere());
            persistPS.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void remove(Object etudiant) throws DataAccessException {
        try {
            connection.createStatement().execute("DELETE FROM " + getTableName() + " WHERE LOGIN=" + ((Etudiant) etudiant).getLogin());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void update(Etudiant etudiant) throws DataAccessException {
        return;
    }
}
