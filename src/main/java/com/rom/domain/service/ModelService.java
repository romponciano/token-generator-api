package com.rom.domain.service;

import com.rom.domain.entity.Model;

import java.util.HashMap;

public interface ModelService {
    HashMap<String, Model> getAll(String username);

    Model getById(String username, String modelName);

    void save(String username, String modelName, Model model);

    void deleteById(String username, String modelName);

    boolean exists(String username, String modelName);
}
