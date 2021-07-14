package org.dav.equitylookup.service;

import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(long id);
    void deleteUserById(long id);
    User getUserByNickname(String nickname);
}
