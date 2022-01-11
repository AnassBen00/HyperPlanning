package univ.tln.daos;

import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.entities.utilisateurs.Responsable;
import univ.tln.entities.utilisateurs.Utilisateur;
import java.sql.*;
import java.util.Optional;

public class ResponsableDAO extends AbstractDAO<Responsable>{

    PreparedStatement preparedStatement;

    public ResponsableDAO() throws DataAccessException, SQLException {
        super("INSERT INTO UTILISATEUR(LOGIN, NOM, PRENOM, PASSWORD, EMAIL) VALUES (?,?,?,?,?);",
                "UPDATE UTILISATEUR SET LOGIN=?, NOM=?, PRENOM=?, PASSWORD=?, EMAIL=? WHERE LOGIN=?",
                "SELECT * from RESPONSABLE join UTILISATEUR ON RESPONSABLE.login = UTILISATEUR.login  ");
    }

    @Override
    public String getTableName() {
        return "RESPONSABLE";
    }

    @Override
    protected Responsable fromResultSet(ResultSet resultSet) throws SQLException {
        return  (Responsable) Utilisateur.builder()
                .login(resultSet.getString("LOGIN"))
                .nom(resultSet.getString("NOM"))
                .prenom(resultSet.getString("PRENOM"))
                .password(resultSet.getString("PASSWORD"))
                .email(resultSet.getString("EMAIL"))
                .build();
    }

    public boolean checkResponsable(String username, String password) throws SQLException {
        try {
            statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT  count(1) from UTILISATEUR join RESPONSABLE on (UTILISATEUR.LOGIN = RESPONSABLE.LOGIN) where RESPONSABLE.LOGIN= '"+username+"' AND PASSWORD = HASH('SHA256','"+password+"')");

            while ((queryResult.next())){
                if( queryResult.getInt(1)==1){
                    return true;
                }else return false;
            }
        }
        catch (
                SQLException e) {
            e.printStackTrace();
        }finally {
            statement.close();
        }
        return false;
    }

    public String getResponsableNameBylogin(String l) throws SQLException {
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
        }finally {
            preparedStatement.close();
        }
        return m;
    }

    public Optional<Responsable> find(String login) throws SQLException {
        Responsable responsable = null;

        preparedStatement = connection.prepareStatement("SELECT * from RESPONSABLE join UTILISATEUR ON RESPONSABLE.login = ?");
        findPS.setString(1, login);

        ResultSet rs = findPS.executeQuery();
        while (rs.next())
            responsable = fromResultSet(rs);

        preparedStatement.close();
        return Optional.ofNullable(responsable);
    }

    public PreparedStatement implementPS(PreparedStatement ps, Responsable responsable) throws SQLException {
        ps.setString(1, responsable.getLogin());
        ps.setString(2, responsable.getNom());
        ps.setString(3,responsable.getPrenom());
        ps.setString(4,responsable.getPassword());
        ps.setString(5,responsable.getEmail());
        return ps;
    }

    @Override
    public void persist(Responsable responsable) throws DataAccessException, SQLException {
        try {
            implementPS(persistPS, responsable);
            persistPS.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public void remove(Object responsable) throws DataAccessException, SQLException {
        try {
            statement = connection.createStatement();
            statement.execute("DELETE FROM " + getTableName() + " WHERE LOGIN=" + ((Responsable)responsable).getLogin());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }finally {
            statement.close();
        }
    }

    @Override
    public void update(Responsable responsable) throws DataAccessException {
        try {
            implementPS(persistPS, responsable);
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();

    }

    public Responsable findbyLogin(String login) throws SQLException {
        Responsable responsable = new Responsable();
        preparedStatement = connection.prepareStatement("select * from RESPONSABLE join UTILISATEUR on RESPONSABLE.login = RESPONSABLE.login where RESPONSABLE.login = ?");
        preparedStatement.setString(1,login);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            responsable.setLogin(resultSet.getString("LOGIN"));
            responsable.setEmail(resultSet.getString("EMAIL"));
            responsable.setNom(resultSet.getString("NOM"));
            responsable.setPrenom(resultSet.getString("PRENOM"));
            responsable.setPassword(resultSet.getString("PASSWORD"));
        }
        return responsable;
    }

    public boolean checkResPass(String passwrd) throws SQLException {
        try {
            statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT  count(1) from UTILISATEUR join RESPONSABLE on (UTILISATEUR.LOGIN = RESPONSABLE.LOGIN) where PASSWORD = HASH('SHA256','"+passwrd+"')");

            while ((queryResult.next())){
                if( queryResult.getInt(1)==1){
                    return true;
                }else return false;
            }
        }
        catch (SQLException e) {
            e.getLocalizedMessage();
        }finally {
            statement.close();
        }
        return false;
    }

    public void updatePassByLogin(String psswrd,String login) throws  SQLException{
        PreparedStatement pstmt = connection.prepareStatement("update UTILISATEUR set PASSWORD = HASH('SHA256','"+psswrd+"') where LOGIN = ?");
        pstmt.setString(1,login);
        pstmt.executeUpdate();
    }


}

