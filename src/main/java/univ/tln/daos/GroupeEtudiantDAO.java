package univ.tln.daos;

import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.filieres.Filiere;
import univ.tln.entities.groupes.GroupeEtudiant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupeEtudiantDAO extends AbstractDAO<GroupeEtudiant>{

    public GroupeEtudiantDAO() {
        super("insert into GROUP_ETUDIANT values(?,?)",
                "",
                "SELECT * FROM GROUP_ETUDIANT");
    }

    @Override
    public String getTableName() {
        return "GROUP_ETUDIANT";
    }

    @Override
    protected GroupeEtudiant fromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public void persist(GroupeEtudiant groupeEtudiant) throws DataAccessException, SQLException {
        try {
            persistPS.setInt(1, groupeEtudiant.getId());
            persistPS.setString(2, groupeEtudiant.getLogin());
            persistPS.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void update(GroupeEtudiant groupeEtudiant) throws DataAccessException, DataAccessException {

    }

}
