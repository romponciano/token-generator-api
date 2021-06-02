package com.rom.domain.service;

import com.rom.domain.entity.Model;
import com.rom.domain.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Model> res = repository.findById(id);
        return res.orElse(null);
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

    @Override
    public boolean exists(String id) {
        return repository.existsById(id);
    }
}
