package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    User getUserById(long id);
    void deleteUserById(long id);
    User getUserByUsername(String userName);
    User findByEmail(String email);
}
