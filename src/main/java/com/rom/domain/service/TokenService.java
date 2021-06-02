package com.rom.domain.service;

import com.rom.domain.entity.Token;

import java.util.List;

public interface TokenService {
    List<Token> getAll();

    Token getById(String id);

    Token create(Token user);

    Token update(Token user);

    void deleteById(String id);

    boolean exists(String id);
}
