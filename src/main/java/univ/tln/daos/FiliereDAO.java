package univ.tln.daos;

import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.filieres.Filiere;
import univ.tln.entities.utilisateurs.Utilisateur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FiliereDAO extends AbstractDAO<Filiere>{

    public FiliereDAO() {
        super("", "", "");
    }

    public List<Filiere> findAll() throws SQLException {
        List<Filiere> entityList = new ArrayList<>();

        PreparedStatement findAllPS = connection.prepareStatement("SELECT * FROM FILIERE");

        ResultSet rs = findAllPS.executeQuery();
        while (rs.next())
            entityList.add(new Filiere(rs.getInt("ID_F"),rs.getString("NOM")));
        System.out.println(entityList);
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

        PreparedStatement findPS = connection.prepareStatement("SELECT * from FILIERE WHERE NOM = ?");
        findPS.setString(1, nom);

        ResultSet rs = findPS.executeQuery();
        while (rs.next())
            filiere = new Filiere(rs.getInt("ID_F"),rs.getString("NOM"));
        return filiere;
    }

    @Override
    public void persist(Filiere filiere) throws DataAccessException, SQLException {

    }

    @Override
    public void update(Filiere filiere) throws DataAccessException {

    }
}
