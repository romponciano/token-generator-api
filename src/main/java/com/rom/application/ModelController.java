package com.rom.application;

import com.rom.domain.entity.Model;
import com.rom.domain.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequestMapping("/tg")
public class ModelController {

    @Autowired
    private ModelService service;

    @GetMapping("/{username}/model")
    public HashMap<String, Model> getAll(@PathVariable String username) {
        try {
            return service.getAll(username);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{username}/model/{modelName}")
    public Model getById(
            @PathVariable String username,
            @PathVariable String modelName
    ) {
        try {
            return service.getById(username, modelName);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{username}/model/{modelName}")
    public void save(
            @PathVariable String username,
            @PathVariable String modelName,
            @RequestBody Model model
    ) {
        try {
            service.save(username, modelName, model);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{username}/model/{modelName}")
    public void deleteById(
            @PathVariable String username,
            @PathVariable String modelName
    ) {
        if(!service.exists(username, modelName))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        service.deleteById(username, modelName);
    }
}
