package com.rom.application;

import com.rom.domain.entity.Token;
import com.rom.domain.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tg")
public class TokenController {

    @Autowired
    private TokenService service;

    @GetMapping("/token")
    public List<Token> getAll() {
        return service.getAll();
    }

    @GetMapping("/token/{id}")
    public Token getById(@PathVariable String id) {
        return service.getById(id);
    }
    
    @PostMapping("/token")
    public Token create(@RequestBody Token token) {
        return service.create(token);
    }

    @PutMapping("/token")
    public Token update(@RequestBody Token token) {
        return service.update(token);
    }

    @DeleteMapping("/token/{id}")
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }
}