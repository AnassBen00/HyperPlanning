package univ.tln.daos;



import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.utilisateurs.Utilisateur;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UtilisateurDAO extends AbstractDAO<Utilisateur> {
    public UtilisateurDAO() {
        super("INSERT INTO UTILISATEUR (LOGIN, NOM, PRENOM, PASSWORD, EMAIL) VALUES(?,?,?,?,?);",
                "UPDATE UTILISATEUR SET NOM=?, PRENOM=?, PASSWORD=?, EMAIL=? WHERE LOGIN=?",
                "SELECT * from ENSEIGNANT WHERE LOGIN = ?");
    }

    @Override
    public String getTableName() {
        return "UTILISATEUR";
    }

    @Override
    protected Utilisateur fromResultSet(ResultSet resultSet) throws SQLException {
        return  Utilisateur.builder()
                .login(resultSet.getString("LOGIN"))
                .nom(resultSet.getString("NOM"))
                .prenom(resultSet.getString("PRENOM"))
                .password(resultSet.getString("PASSWORD"))
                .email(resultSet.getString("EMAIL"))
                .build();
    }

    public void findAll() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from ENSEIGNANT join UTILISATEUR ON ENSEIGNANT.login = UTILISATEUR.login ");
            ResultSet resultset = statement.executeQuery();

            while (resultset.next()) {
                System.out.println("id" + resultset.getString("id") + ",Nom" + resultset.getString("nom") +
                        ",Prenom" + resultset.getString("prenom") + ",Email" + resultset.getString("prenom"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Utilisateur> find(String login) throws SQLException {
        Utilisateur utilisateur = null;

        PreparedStatement findPS = connection.prepareStatement("SELECT * from UTILISATEUR WHERE UTILISATEUR.login = ?");
        findPS.setString(1, login);

        ResultSet rs = findPS.executeQuery();
        while (rs.next())
            utilisateur = fromResultSet(rs);

        return Optional.ofNullable(utilisateur);
    }

    /*public Utilisateur persist() throws DataAccessException, SQLException {
        String login = null;
        try {

            ResultSet rs = persistPS.executeQuery();
            if (rs.next()) {
                login = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return find(login).orElseThrow(NotFoundException::new);
    }*/

    @Override
    public void persist(Utilisateur utilisateur) throws DataAccessException, SQLException {
        try {
            persistPS.setString(1, utilisateur.getLogin());
            persistPS.setString(2, utilisateur.getNom());
            persistPS.setString(3, utilisateur.getPrenom());
            persistPS.setString(4, utilisateur.getPassword());
            persistPS.setString(5, utilisateur.getEmail());
            persistPS.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void remove(Object utilisateur) throws DataAccessException {
        try {
            connection.createStatement().execute("DELETE FROM " + getTableName() + " WHERE LOGIN=" + ((Utilisateur) utilisateur).getLogin());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void update(Utilisateur utilisateur) throws DataAccessException {
        try {
            updatePS.setString(1, utilisateur.getNom());
            updatePS.setString(2, utilisateur.getPrenom());
            updatePS.setString(3, utilisateur.getPassword());
            updatePS.setString(4, utilisateur.getEmail());
            updatePS.setString(5, utilisateur.getLogin());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();

    }
}
