package com.rom.application;

import com.rom.domain.entity.Model;
import com.rom.domain.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tg")
public class ModelController {

    @Autowired
    private ModelService service;

    @GetMapping("/model")
    public List<Model> getAll() {
        return service.getAll();
    }

    @GetMapping("/model/{id}")
    public Model getById(@PathVariable String id) {
        return service.getById(id);
    }
    
    @PostMapping("/model")
    public Model create(@RequestBody Model model) {
        return service.create(model);
    }

    @PutMapping("/model")
    public Model update(@RequestBody Model model) {
        return service.update(model);
    }

    @DeleteMapping("/model/{id}")
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }
}
