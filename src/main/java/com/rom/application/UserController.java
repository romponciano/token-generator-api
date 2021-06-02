package com.rom.application;

import com.rom.domain.entity.User;
import com.rom.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tg")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/user/login")
    public String login(@RequestBody User user) {
        String hash = service.login(user);
        if(hash == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return hash;
    }
    
    @PostMapping("/user")
    public User create(@RequestBody User user) {
        if(service.exists(user.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.create(user);
    }

    @PutMapping("/user")
    public User update(@RequestBody User user) {
        if(!service.exists(user.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.update(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteById(@PathVariable String id) {
        if(!service.exists(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        service.deleteById(id);
    }
}
