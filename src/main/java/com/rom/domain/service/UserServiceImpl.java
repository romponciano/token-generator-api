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
    public boolean exists(String username, String modelName) {
        Optional<User> user = repository.findById(username);
        return user.filter(u -> u.getModels().get(modelName) != null).isPresent();
    }

    @Override
    public User save(User user) {
        User res = repository.save(user);
        res.setPassword(null);
        return res;
    }

    @Override
    public User getById(String id) {
        if(!exists(id)) return null;
        User res = repository.findById(id).get();
        res.setPassword(null);
        return res;
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
