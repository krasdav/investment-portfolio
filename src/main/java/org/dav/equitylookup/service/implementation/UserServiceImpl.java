package org.dav.equitylookup.service.implementation;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.repository.UserRepository;
import org.dav.equitylookup.service.StockService;
import org.dav.equitylookup.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StockService stockService;
    private final StockSearchService stockSearchService;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
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
    public User getUserByUsername(String userName) {
        for (User user : userRepository.findAll()){
            if ( user.getUsername().equals(userName)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
