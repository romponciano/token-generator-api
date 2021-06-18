package com.rom.application;

import com.rom.domain.entity.Model;
import com.rom.domain.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/tg/model")
public class ModelController {

    @Autowired
    private ModelService service;

    @CrossOrigin(originPatterns = "*")
    @GetMapping("/user/{userId}")
    public List<Model> getByUserId(@PathVariable String userId) {
        return service.getByUserId(userId);
    }

    @CrossOrigin(originPatterns = "*")
    @GetMapping("/{id}")
    public Model getById(@PathVariable String id) {
        return service.getById(id);
    }

    @CrossOrigin(originPatterns = "*")
    @PostMapping("/")
    public Model save(@RequestBody Model model) {
        if(model.getUserId() == null || model.getName() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.save(model);
    }

    @CrossOrigin(originPatterns = "*")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }
}
