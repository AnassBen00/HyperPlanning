package univ.tln.daos;

import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.utilisateurs.Etudiant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EtudiantDAO extends AbstractDAO<Etudiant> {
    
    private PreparedStatement preparedStatement;

    public EtudiantDAO() throws DataAccessException, SQLException {
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

    /**
     *
     * @params login and password
     * @return boolean
     *
     * cette methode verifie si un etudiant existe dans la bdd
     */
    public boolean checkEtudiant(String username, String password) throws SQLException {
        try {
            statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT  count(1) from UTILISATEUR join ETUDIANT on (UTILISATEUR.LOGIN = ETUDIANT.LOGIN) where ETUDIANT.LOGIN= '" + username + "' AND PASSWORD = HASH('SHA256','" + password + "')");
            while ((queryResult.next())) {
                if (queryResult.getInt(1) == 1) {
                    return true;
                } else return false;
            }
        } catch (
                SQLException e) {
            e.getLocalizedMessage();
        }
        return false;
    }

    /**
     *
     * @params login
     * @return String : login
     *
     * cette methode retourne le nom d'un etudiant en passant son login comme parametre
     */
    public String getEtudiantNameBylogin(String l) throws SQLException {
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
        }
        return m;
    }

    /**
     *
     * @params
     * @return List<Etudiant>
     *
     * cette methode retourne tous les etudiants
     */
    public List<Etudiant> findAll() {
        List<Etudiant> etudiants = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * from ETUDIANT join UTILISATEUR  ON ETUDIANT.login = UTILISATEUR.login ");
            ResultSet resultset = preparedStatement.executeQuery();
            while (resultset.next()) {
                Etudiant etudiant = new Etudiant(resultset.getString("login"), resultset.getString("password"), resultset.getString("nom"),
                        resultset.getString("prenom"), resultset.getString("email"),
                        resultset.getString("nvx_etude"), resultset.getString("promo"), resultset.getInt("id_f"));
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.getLocalizedMessage();
        }
        return etudiants;
    }


    /**
     *
     * @params
     * @return List<Etudiant>
     *
     * cette methode retourne tous les etudiants qui etaient absent par groupe
     */
    public List<Etudiant> findbygrp(String grp) {
        List<Etudiant> etudiants = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("select distinct u.login,u.nom,u.prenom,nbabs, from UTILISATEUR u join (SELECT abs.login,count(abs.login) as nbabs from absence abs join groups g on abs.id_g=g.ID_G where nom=? group by abs.login)  absn on u.LOGIN=absn.login ");
            preparedStatement.setString(1, grp);
            ResultSet resultset = preparedStatement.executeQuery();
            while (resultset.next()) {
                Etudiant etudiant = new Etudiant(resultset.getString("login"), resultset.getString("nom"),
                        resultset.getString("prenom"), resultset.getString("nbabs"));
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.getLocalizedMessage();

        }
        return etudiants;
    }

    public List<Etudiant> findbygrps(String grp) {
        List<Etudiant> etudiants = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("select distinct u.login,u.nom,u.prenom from UTILISATEUR u join group_etudiant ge on u.LOGIN=ge.login join groups g on ge.id_g=g.id_g  where g.nom=?");
            preparedStatement.setString(1, grp);
            ResultSet resultset = preparedStatement.executeQuery();
            while (resultset.next()) {
                Etudiant etudiant = new Etudiant(resultset.getString("login"), resultset.getString("nom"),
                        resultset.getString("prenom"));
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.getLocalizedMessage();

        }
        return etudiants;
    }
    /**
     *
     * @params login
     * @return Etudiant
     *
     * cette methode retourne un etudiant en passant son login comme parametre
     */
    public Optional<Etudiant> find(String login) throws SQLException {
        Etudiant etudiant = null;

        preparedStatement = connection.prepareStatement("SELECT * from ETUDIANT join UTILISATEUR ON ETUDIANT.login = ?");
        preparedStatement.setString(1, login);

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next())
            etudiant = fromResultSet(rs);

        return Optional.ofNullable(etudiant);
    }

    /**
     *
     * @params etudiant
     * @return void
     *
     * cette methode ajoute un etudiant dans la bdd
     */
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

    /**
     *
     * @params etudiant
     * @return void
     *
     * cette methode supprime un etudiant dans la bdd
     */
    @Override
    public void remove(Object etudiant) throws DataAccessException {
        try {
            statement = connection.createStatement();
            statement.execute("DELETE FROM " + getTableName() + " WHERE LOGIN=" + ((Etudiant) etudiant).getLogin());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }


    @Override
    public void update(Etudiant etudiant) throws DataAccessException {
        return; // methode non utilie
    }

    /**
     *
     * @params password
     * @return boolean
     *
     * cette methode permet de verifie le mot de passe d'un etudiant
     */
    public boolean checkEtudiantPass(String passwrd) {
        try {
            statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT  count(1) from UTILISATEUR join ETUDIANT on (UTILISATEUR.LOGIN = ETUDIANT.LOGIN) where PASSWORD = HASH('SHA256','"+passwrd+"')");

            while ((queryResult.next())){
                if( queryResult.getInt(1)==1){
                    return true;
                }else return false;
            }
        }
        catch (SQLException e) {
            e.getLocalizedMessage();
        }
        return false;
    }

    /**
     *
     * @params login
     * @return Etudiant
     *
     * cette methode retourne un etudiant par login
     */
    public Etudiant findbyLogin(String login) throws SQLException {
        Etudiant etudiant = new Etudiant();
        preparedStatement = connection.prepareStatement("select * from ETUDIANT join UTILISATEUR on ETUDIANT.login = UTILISATEUR.login where ETUDIANT.login = ?");
        preparedStatement.setString(1,login);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            etudiant.setLogin(resultSet.getString("LOGIN"));
            etudiant.setEmail(resultSet.getString("EMAIL"));
            etudiant.setNom(resultSet.getString("NOM"));
            etudiant.setPrenom(resultSet.getString("PRENOM"));
            etudiant.setPassword(resultSet.getString("PASSWORD"));
        }
        return etudiant;
    }

    /**
     *
     * @params password et login
     * @return void
     *
     * cette methode permet modifier le mot de passe
     */
    public void updatePassByLogin(String psswrd,String login) throws  SQLException{
        PreparedStatement pstmt = connection.prepareStatement("update UTILISATEUR set PASSWORD = HASH('SHA256','"+psswrd+"') where LOGIN = ?");
        pstmt.setString(1,login);
        pstmt.executeUpdate();
    }
}
