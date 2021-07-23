package org.dav.equitylookup.service;

import org.dav.equitylookup.model.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    User getUserById(long id);
    void deleteUserById(long id);
    User getUserByNickname(String nickname);
    void updatePortfolioValue(User user) throws IOException;
}