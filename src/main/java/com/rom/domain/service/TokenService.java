package com.rom.domain.service;

import com.rom.domain.entity.Token;

import java.util.List;

public interface TokenService {
    void save(String username, String modelId, List<Token> tokens);

    boolean exists(String username, String modelName);
}
