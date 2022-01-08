package univ.tln.daos;


import univ.tln.daos.exceptions.DataAccessException;
import java.sql.SQLException;


/**
 * This interface defines the generic method for DAO Objects.
 *
 * @param <E> is the type of entities managed by the DAO
 */
public interface DAO<E extends Object> extends AutoCloseable {

    /**
     * Persists a new entity in the database.
     *
     * @param e The entity to be persisted.
     * @return The entity or a clone if the id has been updated by the database.
     * @throws DataAccessException If there is a data access error (see message).
     */
    void persist(E e) throws DataAccessException, SQLException;

    /**
     * A default method to persist a list of entities.
     *
     * @param list The list of entities to be persisted.
     * @return The list of entities or clones if their id has been updated by the database.
     * @throws DataAccessException If there is a data access error (see message).
     */

    /**
     * Update the entity in the database with the parameter.
     *
     * @param e The entity to be updated. The id is used and cannot be updated.
     * @throws DataAccessException If there is a data access error (see message).
     */
    void update(E e) throws DataAccessException, univ.tln.daos.exceptions.DataAccessException;

    /**
     * Removes the entity with the given id from the database.
     *
     * @param id the id of the entity to remove
     * @throws DataAccessException If there is a data access error (see message).
     */
    void remove(long id) throws DataAccessException, univ.tln.daos.exceptions.DataAccessException, SQLException;

    /**
     * Removes the given entity from the database using its ID.
     *
     * @param e the entity to be removed.
     * @throws DataAccessException If there is a data access error (see message).
     */
    default void remove(Object e) throws DataAccessException, SQLException {
        remove(e);
    }

    /**
     * Removes all the entities managed by the DAO from the database.
     *
     * @throws DataAccessException If there is a data access error (see message).
     */
    void clean() throws DataAccessException, univ.tln.daos.exceptions.DataAccessException, SQLException;

    /**
     * Release the connection to the connection pool
     */
    void close() throws DataAccessException, univ.tln.daos.exceptions.DataAccessException, SQLException;

}
