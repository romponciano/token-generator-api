package com.rom.domain.service;

import com.rom.domain.entity.User;

public interface UserService {

    User getById(String id);

    User save(User user);
    
    void deleteById(String id);

    String login(User request);

    boolean exists(String id);

    boolean exists(String username, String modelName);
}
