package ru.qmbo.messageserver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.qmbo.messageserver.model.User;

import java.util.Optional;

/**
 * UserRepository
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 29.11.2022
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * Find user by name and password.
     *
     * @param name     the name
     * @param password the password
     * @return the optional user
     */
    Optional<User> findUserByNameAndPassword(String name, String password);

    /**
     * Find user by name.
     *
     * @param name the name
     * @return the optional user
     */
    Optional<User> findByName(String name);
}
