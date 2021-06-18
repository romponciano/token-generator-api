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
    public Token save(Token token) {
        return repository.save(token);
    }

    @Override
    public Token getById(String tokenId) {
        return repository.findById(tokenId).orElse(null);
    }

    @Override
    public List<Token> getByUser(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public void deleteById(String tokenId) {
        repository.deleteById(tokenId);
    }

    @Override
    public List<Token> getByModel(String modelId) {
        return repository.findByModelId(modelId);
    }

    @Override
    public void deleteByModel(String modelId) {
        repository.deleteByModelId(modelId);
    }

    @Override
    public void deleteByUser(String userId) {
        repository.deleteByUserId(userId);
    }
}
