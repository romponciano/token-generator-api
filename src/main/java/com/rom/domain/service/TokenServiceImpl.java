package com.rom.domain.service;

import com.rom.domain.entity.Token;
import com.rom.domain.entity.User;
import com.rom.domain.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository repository;

    @Override
    public void save(String username, String modelName, List<Token> tokens) {
        User user = userService.getById(username);
        user.getModels().get(modelName).setTokens(tokens);
        userService.save(user);
    }

    @Override
    public boolean exists(String username, String modelName) {
        User user = userService.getById(username);
        if(user == null) return false;
        return user.getModels().get(modelName) != null;
    }

    @Override
    public Token getById(String tokenId) {
        return repository.findById(tokenId).get();
    }

    @Override
    public boolean exists(String tokenId) {
        return repository.existsById(tokenId);
    }

    @Override
    public List<Token> getByUser(String username) {
        return repository.findByUser(username + "*");
    }
}
