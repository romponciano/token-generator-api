package com.rom.application;

import com.rom.domain.entity.Model;
import com.rom.domain.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        if(service.exists(model.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.create(model);
    }

    @PutMapping("/model")
    public Model update(@RequestBody Model model) {
        if(!service.exists(model.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.update(model);
    }

    @DeleteMapping("/model/{id}")
    public void deleteById(@PathVariable String id) {
        if(!service.exists(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        service.deleteById(id);
    }
}
