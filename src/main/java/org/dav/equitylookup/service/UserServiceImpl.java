package org.dav.equitylookup.service;

import org.dav.equitylookup.model.User;
import org.dav.equitylookup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(long id) {
        Optional<User> optional = userRepository.findById(id);
        User user = null;
        if ( optional.isPresent()){
            user = optional.get();
        }else{
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByNickname(String nickname) {
        for (User user : userRepository.findAll()){
            if ( user.getNickname().equals(nickname)){
                return user;
            }
        }
        return null;
    }
}
