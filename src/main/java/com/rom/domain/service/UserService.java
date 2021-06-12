package com.rom.domain.service;

import com.rom.domain.dto.UserRequest;
import com.rom.domain.entity.User;

public interface UserService {

    User getById(String id);

    User save(User user);
    
    void deleteById(String id);

    String login(User request);

    boolean exists(String id);

    boolean exists(String username, String modelName);

    boolean update(UserRequest user);
}
