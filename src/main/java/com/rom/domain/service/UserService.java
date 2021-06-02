package com.rom.domain.service;

import com.rom.domain.entity.User;

public interface UserService {

    User create(User user);

    User update(User user);
    
    void deleteById(String id);

    String login(User request);

    boolean exists(String id);
}
