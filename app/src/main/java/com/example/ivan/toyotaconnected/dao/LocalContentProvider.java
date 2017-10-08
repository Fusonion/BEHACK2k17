package com.example.ivan.toyotaconnected.dao;

import com.example.ivan.toyotaconnected.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Content provider that stores information locally.
 *
 * @author tejun
 */
public class LocalContentProvider extends SessionContentProvider implements ContentProvider {
    protected LocalContentProvider() {
        // Do nothing
    }

    @Override
    public void getAllUsers(Consumer<List<User>> callback) {
        callback.accept(new ArrayList<>());
    }

    @Override
    public void getSingleUser(Consumer<User> callback, String username) {
        callback.accept(null);
    }

    @Override
    public void setUser(User user) {
        // Do nothing
    }

}
