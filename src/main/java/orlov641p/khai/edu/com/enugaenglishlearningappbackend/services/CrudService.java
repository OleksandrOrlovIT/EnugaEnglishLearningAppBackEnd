package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services;

import java.util.List;

/**
 * The CrudService interface provides CRUD (Create, Read, Update, Delete) operations
 * for managing entities of type T.
 *
 * @param <T>  The type of entities managed by the service.
 * @param <ID> The type of the entity's identifier.
 */
public interface CrudService<T, ID> {

    /**
     * Retrieves all entities of type T.
     *
     * @return A set containing all entities.
     */
    List<T> findAll();
    /**
     * Retrieves an entity by its identifier.
     *
     * @param id The identifier of the entity to retrieve.
     * @return The entity with the specified identifier, or null if not found.
     */
    T findById(ID id);

    /**
     * Saves a new entity.
     *
     * @param object The entity to save or update.
     * @return The saved or updated entity.
     */
    T create(T object);

    /**
     * Updates a new entity.
     *
     * @param object The entity to save or update.
     * @return The saved or updated entity.
     */
    T update(T object);

    /**
     * Deletes the specified entity.
     *
     * @param object The entity to delete.
     */
    void delete(T object);

    /**
     * Deletes an entity by its identifier.
     *
     * @param id The identifier of the entity to delete.
     */
    void deleteById(ID id);
}