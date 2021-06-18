package com.rom.domain.service;

import com.rom.domain.entity.Model;

import java.util.List;

public interface ModelService {

    Model getById(String id);

    Model save(Model model);

    void deleteById(String id);

    List<Model> getByUserId(String userId);
}
