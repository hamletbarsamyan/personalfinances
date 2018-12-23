package am.jsl.personalfinances.service;

import am.jsl.personalfinances.ex.CannotDeleteException;

import java.util.List;

/**
 * Service interface which defines all the methods for working with the domain objects with generic way.
 * @author hamlet
 * @param <T> the parametrisation entity type.
 */
public interface BaseService <T> {
    /**
     * Returns all instances of the type for the given user.
     * @param userId the user id associated with entity
     * @return the results
     */
    List<T> list(long userId);

    /**
     * Deletes the entity with the given id and user id.
     * Will throw {@link CannotDeleteException} if entity can not be deleted.
     * @param id the entity id
     * @param userId the user id associated with entity
     * @throws CannotDeleteException if entity can not be deleted
     */
    void delete(long id, long userId) throws CannotDeleteException;

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
    void create(T object) throws Exception;

    /**
     * Updates the given entity.
     * @param object the entity
     */
    void update(T object) throws Exception;

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

    /**
     * Creates a new file with the given name and content in user publish directory.
     * If a file with the given name exists then overwrites it.
     * The published file is a html representation of objects of a certain type
     * and directly loaded by web using user id without hitting database.
     * @param userId the user id
     * @param fileName the file name
     * @param content the file content
     */
    void publish(long userId, String fileName, String content);
}
