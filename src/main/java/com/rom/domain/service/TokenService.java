package com.rom.domain.service;

import com.rom.domain.entity.Token;

import java.util.List;

public interface TokenService {
    Token save(Token token);

    Token getById(String tokenId);

    boolean exists(String tokenId);

    List<Token> getByUser(String username);
}
