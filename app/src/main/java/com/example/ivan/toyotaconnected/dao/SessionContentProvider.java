package com.example.ivan.toyotaconnected.dao;

import com.example.ivan.toyotaconnected.model.user.User;

/**
 * Content provider that stores global variables accessible everywhere.
 *
 * @author tejun
 */
abstract class SessionContentProvider implements ContentProvider {
    private User loggedInUser;

    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
