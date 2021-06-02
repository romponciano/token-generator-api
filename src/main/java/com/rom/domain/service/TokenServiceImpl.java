package com.rom.domain.service;

import com.rom.domain.entity.Token;
import com.rom.domain.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository repository;

    @Override
    public List<Token> getAll() {
        return repository.findAll();
    }

    @Override
    public Token getById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public Token create(Token token) {
        return repository.save(token);
    }

    @Override
    public Token update(Token token) {
        return repository.save(token);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(String id) {
        return repository.existsById(id);
    }
}
