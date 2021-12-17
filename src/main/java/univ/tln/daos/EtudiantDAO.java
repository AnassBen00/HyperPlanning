package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.entities.utilisateurs.Etudiant;
import univ.tln.entities.utilisateurs.Utilisateur;
import univ.tln.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO extends AbstractDAO<Etudiant> {
    public EtudiantDAO() {
        super("INSERT INTO UTILISATEUR(LOGIN, NOM, PRENOM, PASSWORD, EMAIL) VALUES (?,?,?,?,?)",
                "UPDATE UTILISATEUR SET LOGIN=?, NOM=?, PRENOM=?, PASSWORD=?, EMAIL=? WHERE LOGIN=?",
                "SELECT * from ENSEIGNANT joint UTILISATEUR ON ENSEIGNANT.login = UTILISATEUR.login  ");
    }

    @Override
    public String getTableName() {
        return "ETUDIANT";
    }

    @Override
    protected Etudiant fromResultSet(ResultSet resultSet) throws SQLException {
        return (Etudiant) Utilisateur.builder()
                .login(resultSet.getString("LOGIN"))
                .nom(resultSet.getString("NOM"))
                .prenom(resultSet.getString("PRENOM"))
                .password(resultSet.getString("PASSWORD"))
                .email(resultSet.getString("EMAIL"))
                .build();
    }

    public boolean checkEtudiant(String username, String password) {

        try {
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT  count(1) from UTILISATEUR join ETUDIANT on (UTILISATEUR.LOGIN = ETUDIANT.LOGIN) where ETUDIANT.LOGIN= '" + username + "' AND PASSWORD = HASH('SHA256','" + password + "',1000)");

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
            while(resultset.next()) {

                System.out.println(resultset.getString("NOM"));
                System.out.println(resultset.getString("PRENOM"));
                Etudiant etudiant = new Etudiant("", "", resultset.getString("NOM"), resultset.getString("PRENOM"),"", "");
                etudiants.add(etudiant);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return etudiants;
    }

    @Override
    public Etudiant persist(Etudiant etudiant) throws DataAccessException {
       return null;
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
