package com.rom.application;

import com.rom.domain.entity.Token;
import com.rom.domain.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/tg")
public class TokenController {

    @Autowired
    private TokenService service;

    @CrossOrigin(originPatterns = "*")
    @PostMapping("/token")
    public Token save(@RequestBody Token token) {
        if(token.getUsername() == null || token.getModelId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.save(token);
    }

    @CrossOrigin(originPatterns = "*")
    @GetMapping("/token/{tokenId}")
    public Token getById(@PathVariable String tokenId) {
        if(!service.exists(tokenId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.getById(tokenId);
    }

    @CrossOrigin(originPatterns = "*")
    @GetMapping("/token/{username}")
    public List<Token> getByUser(@PathVariable String username) {
        return service.getByUser(username);
    }
}
