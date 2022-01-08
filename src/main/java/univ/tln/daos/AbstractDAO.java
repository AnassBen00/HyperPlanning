package univ.tln.daos;

import lombok.extern.java.Log;
import univ.tln.DatabaseConnection;
import univ.tln.daos.exceptions.DataAccessException;
import univ.tln.daos.exceptions.NotFoundException;
import univ.tln.entities.utilisateurs.Utilisateur;

import java.sql.*;


import java.util.Optional;
import java.util.logging.Level;

@Log
public abstract class AbstractDAO<E extends Object> implements DAO<E> {
    protected final Connection connection;
    protected final PreparedStatement persistPS;
    protected final PreparedStatement updatePS;
    protected final PreparedStatement findPS;
    protected Statement statement;

    protected AbstractDAO(String persistPS, String updatePS, String findPS) {
        Connection _connection = null;
        PreparedStatement _findPS = null, _persistPS = null, _updatePS = null;
        try {
            DatabaseConnection connectionn = new DatabaseConnection();
            Connection connection1 = connectionn.connectDB();
            _connection = connection1;
            _findPS = _connection.prepareStatement(findPS);
            _persistPS = _connection.prepareStatement(persistPS, Statement.RETURN_GENERATED_KEYS);
            _updatePS = _connection.prepareStatement(updatePS);
        } catch (SQLException throwables) {
             new DataAccessException(throwables.getLocalizedMessage());
        }finally {
            this.connection = _connection;
            this.findPS = _findPS;
            this.persistPS = _persistPS;
            this.updatePS = _updatePS;
        }
    }

    public abstract String getTableName();

    public E persist() throws DataAccessException {
        long id = -1;
        try {
            persistPS.executeUpdate();
            ResultSet rs = persistPS.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return find(id).orElseThrow(NotFoundException::new);
    }

    public Optional<E> find(long id) throws DataAccessException {
        E entity = null;
        try {
            findPS.setLong(1, id);
            ResultSet rs = findPS.executeQuery();
            while (rs.next())
                entity = fromResultSet(rs);
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return Optional.ofNullable(entity);
    }

    protected abstract E fromResultSet(ResultSet resultSet) throws SQLException;

    public void remove(long id) throws DataAccessException, SQLException {
        try {
            statement = connection.createStatement();
            statement.execute("DELETE FROM " + getTableName() + " WHERE ID=" + id);
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }finally {
            statement.close();
        }
    }

    public void clean() throws DataAccessException, SQLException {
        try {
            statement = connection.createStatement();
            statement.execute("DELETE FROM " + getTableName());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        } finally {
            statement.close();
        }
    }

    @Override
    public void close() throws SQLException {
            connection.close();
    }

    public void update() throws DataAccessException {
        try {
            updatePS.executeUpdate();
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }
}
