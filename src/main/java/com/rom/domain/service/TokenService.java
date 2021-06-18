package com.rom.domain.service;

import com.rom.domain.entity.Token;

import java.util.List;

public interface TokenService {
    Token save(Token token);

    Token getById(String tokenId);

    List<Token> getByUser(String username);

    void deleteById(String tokenId);

    List<Token> getByModel(String modelId);

    void deleteByModel(String modelId);

    void deleteByUser(String userId);
}
