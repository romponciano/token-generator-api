package com.rom.domain.service;

import com.rom.domain.entity.Model;

import java.util.List;

public interface ModelService {
    List<Model> getAll();

    Model getById(String id);

    Model create(Model model);

    Model update(Model model);

    void deleteById(String id);
}
