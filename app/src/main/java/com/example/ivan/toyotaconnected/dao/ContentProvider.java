package com.example.ivan.toyotaconnected.dao;

import com.example.ivan.toyotaconnected.model.user.User;

import java.util.List;
import java.util.function.Consumer;

/**
 * Any class that implements this interface must provide the requested data.
 *
 * @author tejun
 */

public interface ContentProvider {
    /**
     * Gets the user for this session.
     *
     * @return the logged in user
     */
    User getLoggedInUser();

    /**
     * Sets the user for this session
     *
     * @param loggedInUser the logged in user
     */
    void setLoggedInUser(User loggedInUser);

    /**
     * Gets all users. Calls the callback when all users have been received.
     *
     * @param callback Called with a list of users
     */
    void getAllUsers(Consumer<List<User>> callback);

    /**
     * Gets a single user object from the username. Calls the callback when the user is received.
     *
     * @param callback Called with the user object
     * @param username The username to look for
     */
    void getSingleUser(Consumer<User> callback, String username);

    /**
     * Saves the user.
     *
     * @param user The user to save
     */
    void setUser(User user);
}
