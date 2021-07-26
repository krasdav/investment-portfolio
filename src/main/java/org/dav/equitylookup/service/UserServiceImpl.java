package org.dav.equitylookup.service;

import lombok.RequiredArgsConstructor;
import org.dav.equitylookup.model.Stock;
import org.dav.equitylookup.model.User;
import org.dav.equitylookup.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final StockService stockService;

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
    public User getUserByNickname(String nickname) {
        for (User user : userRepository.findAll()){
            if ( user.getUsername().equals(nickname)){
                return user;
            }
        }
        return null;
    }

    @Override
    public void updatePortfolioValue(User user) throws IOException {
        for( Stock stock : user.getStocks() ){
            user.addToPortfolio(stockService.updateCurrentStockPrice(stock));
        }
    }
}
