package daniil.tm2.api.service;

import daniil.tm2.entity.TMUser;

public interface IUserService {

    /**
     * Saves a new user with the given data to the database.
     * @return The new user or null, if a user with this name did already exist.
     **/
    TMUser addUser(String loginName, String password, String roleName);
}
