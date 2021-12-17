package univ.tln.daos;

import univ.tln.DatabaseConnection;
import univ.tln.entities.creneaux.Creneau;
import univ.tln.exceptions.DataAccessException;

import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CreneauxDAO extends AbstractDAO<Creneau>{
    public CreneauxDAO() {
        super("INSERT INTO CRENEAUX(DATE_D,DATE_F,ID_S,ID_G,ID_C) VALUES (?,?,?,?,?)",
                "UPDATE CRENEAUX SET DATE_D=?,DATE_F=? ID_S=?,ID_G=?,ID_C=? WHERE DATE_D=?,ID_G=?,ID_C=?",
                "SELECT * FROM CRENEAUX WHERE DATE_D=?,ID_G=?,ID_C=?");
    }

    @Override
    public String getTableName() {
        return "CRENEAUX";
    }

    @Override
    protected Creneau fromResultSet(ResultSet resultSet) throws SQLException {
        return Creneau.builder()
                .dateDebut(resultSet.getDate("DATE_D"))
                .dateFin(resultSet.getDate("DATE_F"))
                .idCours(resultSet.getInt("ID_S"))
                .idGroupe(resultSet.getInt("ID_G"))
                .idSalle(resultSet.getInt("ID_C"))
                .build();
    }

    @Override
    public Creneau persist(Creneau creneau) throws DataAccessException {
        try {
            persistPS.setDate(1, (java.sql.Date) creneau.getDateDebut());
            persistPS.setDate(2, (java.sql.Date) creneau.getDateFin());
            persistPS.setInt(3,creneau.getIdSalle());
            persistPS.setInt(4,creneau.getIdGroupe());
            persistPS.setInt(5,creneau.getIdCours());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }

    public void getCreneaux(String[][] creneau) {
        int i = 0;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.connectDB();
        String crenaux = "select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G) join COURS ON (GROUP_COURS.ID_C = COURS.ID_C)\n" +
                "where LOGIN ='FFF' ";
        try {
            Statement statement = connection1.createStatement();
            ResultSet queryResult = statement.executeQuery(crenaux);

            while ((queryResult.next())) {
                creneau[i][0] = String.valueOf(queryResult.getTimestamp("DATE_D"));
                creneau[i][1] = String.valueOf(queryResult.getTimestamp("DATE_F"));
                creneau[i][2] = queryResult.getString("BATIMENT");
                creneau[i][3] = String.valueOf(queryResult.getInt("NUM"));
                creneau[i][4] = String.valueOf(queryResult.getBoolean("VIDEO_P"));
                creneau[i][5] = queryResult.getString("NOM");
                creneau[i][6] = queryResult.getString("NATURE");
                i++;
            }

            System.out.println(Arrays.deepToString(creneau));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Object creneau) throws DataAccessException {
        try {
            connection.createStatement().execute("DELETE FROM " + getTableName() + " WHERE DATE_D=" + ((Creneau)creneau).getDateDebut()  + ", ID_G= " + ((Creneau)creneau).getDateFin()  + ", ID_C="  + ((Creneau)creneau).getIdCours()  + "" );
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    public void RemoveCreneauByDated(String d) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM CRENEAUX WHERE DATE_D=? ");
            statement.setString(1, d);
            statement.executeUpdate();
            System.out.println("deleted succesflly");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Creneau creneau) throws DataAccessException {
        try {
            updatePS.setDate(1, (java.sql.Date) creneau.getDateDebut());
            updatePS.setDate(2, (java.sql.Date) creneau.getDateFin());
            updatePS.setInt(3,creneau.getIdSalle());
            updatePS.setInt(4,creneau.getIdGroupe());
            updatePS.setInt(5,creneau.getIdCours());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();

    }
}
