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
        Token res = service.getById(tokenId);
        if(res == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return res;
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
        List<Token> res = service.getByModel(modelId);
        if(res == null || res.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return res;
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
        List<Token> res = service.getByUser(userId);
        if(res == null || res.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return res;
    }

    @CrossOrigin(originPatterns = "*")
    @DeleteMapping("/user/{userId}")
    public void deleteByUser(@PathVariable String userId) {
        service.deleteByUser(userId);
    }
}
