package com.rom.application;

import com.rom.domain.entity.Token;
import com.rom.domain.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/tg/token")
public class TokenController {

    @Autowired
    private TokenService service;

    @CrossOrigin(originPatterns = "*")
    @PostMapping("/")
    public Token save(@RequestBody Token token) {
        if(token.getUserId() == null || token.getModelId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.save(token);
    }

    @CrossOrigin(originPatterns = "*")
    @GetMapping("/{tokenId}")
    public Token getById(@PathVariable String tokenId) {
        return service.getById(tokenId);
    }

    @CrossOrigin(originPatterns = "*")
    @DeleteMapping("/{tokenId}")
    public void deleteById(@PathVariable String tokenId) {
        service.deleteById(tokenId);
    }

    // ############## by model
    @CrossOrigin(originPatterns = "*")
    @GetMapping("/model/{modelId}")
    public List<Token> getByModel(@PathVariable String modelId) {
        return service.getByModel(modelId);
    }

    @CrossOrigin(originPatterns = "*")
    @DeleteMapping("/model/{modelId}")
    public void deleteByModel(@PathVariable String modelId) {
        service.deleteByModel(modelId);
    }

    // ############## by user
    @CrossOrigin(originPatterns = "*")
    @GetMapping("/user/{userId}")
    public List<Token> getByUser(@PathVariable String userId) {
        return service.getByUser(userId);
    }

    @CrossOrigin(originPatterns = "*")
    @DeleteMapping("/user/{userId}")
    public void deleteByUser(@PathVariable String userId) {
        service.deleteByUser(userId);
    }
}
