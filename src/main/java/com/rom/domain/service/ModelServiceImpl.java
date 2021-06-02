package com.rom.domain.service;

import com.rom.domain.entity.Model;
import com.rom.domain.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelRepository repository;

    @Override
    public List<Model> getAll() {
        return repository.findAll();
    }

    @Override
    public Model getById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public Model create(Model model) {
        return repository.save(model);
    }

    @Override
    public Model update(Model model) {
        return repository.save(model);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
