package com.rom.domain.service;

import com.rom.domain.entity.Model;
import com.rom.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private UserService service;

    @Override
    public HashMap<String, Model> getAll(String username) {
        return service.getById(username).getModels();
    }

    @Override
    public Model getById(String username, String modelName) {
        return service.getById(username).getModels().get(modelName);
    }

    @Override
    public void save(String username, String modelName, Model model) {
        User user = service.getById(username);
        user.getModels().put(modelName, model);
        service.save(user);
    }

    @Override
    public void deleteById(String username, String modelName) {
        User user = service.getById(username);
        user.getModels().remove(modelName);
        service.save(user);
    }

    @Override
    public boolean exists(String username, String modelName) {
        return service.exists(username, modelName);
    }


}
