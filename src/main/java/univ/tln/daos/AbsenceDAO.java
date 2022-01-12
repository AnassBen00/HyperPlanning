package univ.tln.daos;

import univ.tln.controller.TeacherController;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.utilisateurs.Absence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class
AbsenceDAO extends AbstractDAO<Absence>{
    PreparedStatement preparedStatement;

    public AbsenceDAO() throws DataAccessException, SQLException {
        super("insert into absence values(?,select id_s from salle where batiment =? and num=?,select id_g from groups where nom=?,?,false)",
                "",
                "SELECT * FROM absence WHERE login=?");
    }

    @Override
    public String getTableName() {
        return "ABSENCE";
    }

    @Override
    protected Absence fromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    /**
     *
     * @params login et date d'absence
     * @return boolean
     *
     * cette methode permet de trouver si un etuadiant est absent en donnant son login et la date
     */
    public boolean find(String login, String date) throws SQLException {
        PreparedStatement findPS = connection.prepareStatement("SELECT * FROM absence WHERE login=? and date_d = ?");
        findPS.setString(1, login);
        findPS.setString(2, date);
        ResultSet rs = findPS.executeQuery();
        while (rs.next()){
            return true;
        }
        return false;
    }

    /**
     *
     * @params absence
     * @return void
     *
     * cette methode permet d'ajouter absence
     */
    @Override
    public void persist(Absence absence) throws DataAccessException, SQLException {
        try {
            persistPS.setString(1, absence.getDate_d());
            persistPS.setString(2, absence.getNomBatiment());
            persistPS.setString(3, absence.getNomSalle());
            persistPS.setString(4, absence.getNomGroupe());
            persistPS.setString(5, absence.getLogin());
            persistPS.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    /**
     *
     * @params login
     * @return List<Absence>
     *
     * cette methode retourne tous les absences d un etudiant donné
     */
    public List<Absence> findAllabs(String login) {
        List<Absence> absences = new ArrayList<Absence>();
        try {
            preparedStatement = connection.prepareStatement("select cr.date_d ,nom ,nature,a.id_s,a.id_g,a.login,justified from absence a join CRENEAUX cr on a.date_d=cr.DATE_D and a.id_s=cr.ID_S and a.ID_G=cr.ID_G join cours c on c.ID_C=cr.ID_C where a.login=?");
            preparedStatement.setString(1, login);
            ResultSet resultset = preparedStatement.executeQuery();
            while (resultset.next()) {

                Absence absence = new Absence(resultset.getString("date_d"), resultset.getString("nom"), resultset.getString("nature"), resultset.getString("id_s"), resultset.getString("id_g"),resultset.getString("login"),resultset.getBoolean("justified"));
                absences.add(absence);
            }
        } catch (SQLException e) {
            e.getLocalizedMessage();
        }
        return absences;
    }
    /**
     *
     * @params login
     * @return List<Absence>
     *
     * cette methode retourne tous les absences non justifié d un etudiant donné
     */
    public List<Absence> findAllabsN(String login) {
        List<Absence> absences = new ArrayList<Absence>();
        try {
            preparedStatement = connection.prepareStatement("select cr.date_d ,nom ,nature,a.id_s,a.id_g,a.login,justified from absence a join CRENEAUX cr on a.date_d=cr.DATE_D and a.id_s=cr.ID_S and a.ID_G=cr.ID_G join cours c on c.ID_C=cr.ID_C where a.login=? and justified=false");
            preparedStatement.setString(1, login);
            ResultSet resultset = preparedStatement.executeQuery();
            while (resultset.next()) {

                Absence absence = new Absence(resultset.getString("date_d"), resultset.getString("nom"), resultset.getString("nature"), resultset.getString("id_s"), resultset.getString("id_g"),resultset.getString("login"),resultset.getBoolean("justified"));
                absences.add(absence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return absences;
    }

    /**
     *
     * @params absence
     * @return void
     *
     * cette methode supprime l absence d'un etudiant pour un creneau
     */
    public void remove(Absence absence) throws DataAccessException {
        try {
            statement = connection.createStatement();
            statement.execute("DELETE FROM " + getTableName() + " WHERE LOGIN='" + absence.getLogin()+"' and date_d = '"+ TeacherController.d1 +"'");
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    /**
     *
     * @params login et date et boolean
     * @return void
     *
     * cette methode modifie une absence en changant son etat justifie on non
     */
    public void update(String login,String date_d,boolean justified)  {
        try {
            preparedStatement =connection.prepareStatement("update absence set justified =? WHERE LOGIN=? and date_d = ?");
            preparedStatement.setBoolean(1, justified);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, date_d);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Absence absence) throws DataAccessException, DataAccessException {
        //methode non utilisee
    }
}
