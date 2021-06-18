package com.rom.domain.service;

import com.rom.domain.entity.Model;
import com.rom.domain.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private UserService service;

    @Autowired
    private ModelRepository repository;

    @Override
    public Model getById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Model save(Model model) {
        return repository.save(model);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Model> getByUserId(String userId) {
        return repository.findByUserId(userId);
    }
}
