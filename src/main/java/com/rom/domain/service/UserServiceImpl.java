package com.rom.domain.service;

import com.rom.domain.entity.User;
import com.rom.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public String login(User login) {
        Optional<User> official =  repository.findById(login.getUsername());

        if(!official.isPresent()) return null;

        User user = official.get();
        return login.getPassword().equals(user.getPassword())
                ? String.valueOf(user.hashCode())
                : null;
    }

    @Override
    public boolean exists(String id) {
        return repository.existsById(id);
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
