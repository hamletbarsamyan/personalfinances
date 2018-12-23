package am.jsl.personalfinances.dao;

import java.util.List;

/**
 * Base DAO interface for of interacting with the domain Entity objects with generic way.
 * @author hamlet
 * @param <T> the parametrisation entity type.
 */
public interface BaseDao<T> {
    /**
     * Returns all instances of the type for the given user.
     * @param userId the user id associated with entity
     * @return the results
     */
    List<T> list(long userId);

    /**
     * Returns whether an entity with the given id and user id can be deleted.
     * @param id the entity id
     * @param userId the user id associated with entity
     * @return true if an entity with the given id and user id can be deleted
     */
    boolean canDelete(long id, long userId);

    /**
     * Deletes the entity with the given id and user id.
     * @param id the entity id
     * @param userId the user id associated with entity
     */
    void delete(long id, long userId);

    /**
     * Returns whether an entity with the given id and user id exists.
     * @param name the entity name
     * @param id the entity id
     * @param userId the user id associated with entity
     * @return true if an entity with the given id and user id exists
     */
    boolean exists(String name, long id, long userId);

    /**
     * Creates the given entity.
     * @param object the entity
     */
    void create(T object);

    /**
     * Updates the given entity.
     * @param object the entity
     */
    void update(T object);

    /**
     * Retrieves an entity by its id and user id.
     * @param id the entity id
     * @param userId the user id associated with entity
     * @return the entity
     */
    T get(long id, long userId);

    /**
     * Returns all instances of the type associated with the given user id.
     * Will be queried instance id and name.
     * @param userId the user id associated with entity
     * @return the result
     */
    List<T> lookup(long userId);
}
