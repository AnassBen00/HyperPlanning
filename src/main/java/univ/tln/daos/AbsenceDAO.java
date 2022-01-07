package univ.tln.daos;

import univ.tln.Controller.TeacherController;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.utilisateurs.Absence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class
AbsenceDAO extends AbstractDAO<Absence>{

    public AbsenceDAO() {
        super("insert into absence values(?,select id_s from salle where batiment =? and num=?,select id_g from groups where nom=?,?)",
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

    public boolean find(String login, String date) throws SQLException {
        PreparedStatement findPS = connection.prepareStatement("SELECT * FROM absence WHERE login=? and date_d = ?");
        findPS.setString(1, login);
        findPS.setString(2, date);
        ResultSet rs = findPS.executeQuery();
        while (rs.next())
            return true;
        return false;
    }

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

    public List<Absence> findAllabs(String login) {
        List<Absence> absences = new ArrayList<Absence>();
        try {
            PreparedStatement statement = connection.prepareStatement("select cr.date_d ,nom ,nature,a.id_s,a.id_g,a.login from absence a join CRENEAUX cr on a.date_d=cr.DATE_D and a.id_s=cr.ID_S and a.ID_G=cr.ID_G join cours c on c.ID_C=cr.ID_C where a.login=?");
            statement.setString(1, login);
            ResultSet resultset = statement.executeQuery();
            while (resultset.next()) {

                Absence absence = new Absence(resultset.getString("date_d"), resultset.getString("nom"), resultset.getString("nature"), resultset.getString("id_s"), resultset.getString("id_g"),resultset.getString("login"));
                absences.add(absence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return absences;
    }

    public void remove(Absence absence) throws DataAccessException {
        try {
            connection.createStatement().execute("DELETE FROM " + getTableName() + " WHERE LOGIN='" + absence.getLogin()+"' and date_d = '"+ TeacherController.d1 +"'");
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void update(Absence absence) throws DataAccessException, DataAccessException {

    }
}
