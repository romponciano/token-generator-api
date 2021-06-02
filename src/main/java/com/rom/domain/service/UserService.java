package com.rom.domain.service;

import com.rom.domain.entity.User;

import java.util.List;

public interface UserService {
    
    User getById(String id);

    User create(User user);

    User update(User user);
    
    void deleteById(String id);
}
