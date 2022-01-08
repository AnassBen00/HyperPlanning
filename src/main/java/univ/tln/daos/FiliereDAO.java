package univ.tln.daos;

import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.filieres.Filiere;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FiliereDAO extends AbstractDAO<Filiere>{

    PreparedStatement preparedStatement;

    public FiliereDAO() throws DataAccessException, SQLException {
        super("", "", "");
    }

    public List<Filiere> findAll() throws SQLException {
        List<Filiere> entityList = new ArrayList<>();

        preparedStatement = connection.prepareStatement("SELECT * FROM FILIERE");

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next())
            entityList.add(new Filiere(rs.getInt("ID_F"),rs.getString("NOM")));
        preparedStatement.close();
        return entityList;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    protected Filiere fromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    public Filiere find(String nom) throws SQLException {
        Filiere filiere = null;

        preparedStatement = connection.prepareStatement("SELECT * from FILIERE WHERE NOM = ?");
        preparedStatement.setString(1, nom);

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next())
            filiere = new Filiere(rs.getInt("ID_F"),rs.getString("NOM"));

        preparedStatement.close();
        return filiere;
    }

    @Override
    public void persist(Filiere filiere) throws DataAccessException, SQLException {
        // methode n'est pas utilise pour l'instant
    }

    @Override
    public void update(Filiere filiere) throws DataAccessException {
        // methode n'est pas utilise pour l'instant
    }
}
