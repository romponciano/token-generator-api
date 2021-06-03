package com.rom.domain.service;

import com.rom.domain.entity.Token;
import com.rom.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private UserService service;

    @Override
    public void save(String username, String modelName, List<Token> tokens) {
        User user = service.getById(username);
        user.getModels().get(modelName).setTokens(tokens);
        service.save(user);
    }

    @Override
    public boolean exists(String username, String modelName) {
        User user = service.getById(username);
        if(user == null) return false;
        return user.getModels().get(modelName) != null;
    }
}
