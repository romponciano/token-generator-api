package com.rom.domain.service;

import com.rom.domain.dto.UserRequest;
import com.rom.domain.entity.User;

public interface UserService {

    User getById(String id);

    User getByUsername(String username);

    User save(User user);

    String login(User request);

    boolean exists(String id);

    boolean existsByUsername(String username);

    boolean update(UserRequest user);

    boolean deleteById(String id, UserRequest request);
}
