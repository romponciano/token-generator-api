package com.rom.application;

import com.rom.domain.dto.UserRequest;
import com.rom.domain.entity.User;
import com.rom.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tg")
public class UserController {

    @Autowired
    private UserService service;

    @CrossOrigin(originPatterns = "*")
    @PostMapping("/user/login")
    public String login(@RequestBody User user) {
        String hash = service.login(user);
        if(hash == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return hash;
    }

    @CrossOrigin(originPatterns = "*")
    @GetMapping("/user/{id}")
    public User getById(@PathVariable String id) {
        if(!service.exists(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.getById(id);
    }

    @CrossOrigin(originPatterns = "*")
    @PostMapping("/user")
    public User save(@RequestBody User user) {
        return service.save(user);
    }

    @CrossOrigin(originPatterns = "*")
    @PutMapping("/user")
    public void update(@RequestBody UserRequest user) {
        if(!service.update(user))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(originPatterns = "*")
    @GetMapping("/user/{username}/exists")
    public ResponseEntity<String> exists(@PathVariable String username) {
        return service.exists(username)
            ? new ResponseEntity<String>(HttpStatus.FORBIDDEN)
            : new ResponseEntity<String>(HttpStatus.OK);
    }

    @CrossOrigin(originPatterns = "*")
    @DeleteMapping("/user/{id}")
    public void deleteById(@PathVariable String id) {
        if(!service.exists(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        service.deleteById(id);
    }
}
