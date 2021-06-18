package com.rom.domain.service;

import com.rom.domain.dto.UserRequest;
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
        User official =  getByUsername(login.getUsername());

        if(official == null) return null;

        return login.getPassword().equals(official.getPassword())
                ? official.getId()
                : null;
    }

    @Override
    public boolean exists(String id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return getByUsername(username) != null;
    }

    @Override
    public boolean update(UserRequest request) {
        User savedUser = getByUsername(request.getUsername());

        if(savedUser != null && savedUser.getPassword().equals(request.getPassword())) {
            String newPassword = request.getNewPassword();
            if(newPassword != null) savedUser.setPassword(newPassword);

            String newUsername = request.getNewUsername();
            if(newUsername != null) {
                savedUser.setUsername(newUsername);
            }

            save(savedUser);
            return true;
        }
        return false;
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> user = repository.findByUsername(username);
        return user.orElse(null);
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
        return repository.findById(id).get();
    }

    @Override
    public boolean deleteById(String id, UserRequest request) {
        User user = getById(id);
        if(user.getPassword().equals(request.getPassword())) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
