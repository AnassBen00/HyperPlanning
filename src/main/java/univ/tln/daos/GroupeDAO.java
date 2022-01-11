package univ.tln.daos;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.groupes.Groupe;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupeDAO extends AbstractDAO<Groupe>{

    PreparedStatement preparedStatement;

    public GroupeDAO() throws DataAccessException, SQLException {
        super("insert into GROUPS values(?,?)",
                "",
                "SELECT * FROM GROUPS");
    }

    @Override
    public String getTableName() {
        return "GROUPS";
    }


    /**
     *
     * @param
     * @return  List<Groupe>
     *
     * cette methode retourne tous les groupes
     */
    public List<Groupe> findAll() throws SQLException {
        List<Groupe> entityList = new ArrayList<>();

        ResultSet rs = findPS.executeQuery();
        while (rs.next())
            entityList.add(new Groupe(rs.getInt("ID_G"),rs.getString("NOM")));
        return entityList;
    }

    /**
     *
     * @params nom
     * @return Groupe
     *
     * cette methode retourne un groupe en passant son nom comme parametre
     */
    public Groupe find(String nom) throws SQLException {
        Groupe groupe = null;

        preparedStatement = connection.prepareStatement("SELECT * from GROUPS WHERE NOM = ?");
        preparedStatement.setString(1, nom);

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next())
            groupe = new Groupe(rs.getInt("ID_G"),rs.getString("NOM"));

        preparedStatement.close();
        return groupe;
    }

    @Override
    protected Groupe fromResultSet(ResultSet resultSet){
        return null;
    }

    @Override
    public void persist(Groupe groupe){
        //  methode n'est pas utilise pour l'instant
    }

    @Override
    public void update(Groupe groupe){
        //  methode n'est pas utilise pour l'instant
    }
}
