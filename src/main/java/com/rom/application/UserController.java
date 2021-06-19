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
@RequestMapping("/tg/user")
public class UserController {

    @Autowired
    private UserService service;

    @CrossOrigin(originPatterns = "*")
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        String id = service.login(user);
        if(id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return id;
    }

    @CrossOrigin(originPatterns = "*")
    @PostMapping("/")
    public User save(@RequestBody User user) {
        try {
            return service.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(originPatterns = "*")
    @PutMapping("/")
    public void update(@RequestBody UserRequest user) {
        try {
            if(!service.update(user))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(originPatterns = "*")
    @GetMapping("/{username}/exists")
    public ResponseEntity<String> exists(@PathVariable String username) {
        return service.existsByUsername(username)
            ? new ResponseEntity<String>(HttpStatus.FORBIDDEN)
            : new ResponseEntity<String>(HttpStatus.OK);
    }

    @CrossOrigin(originPatterns = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(
            @PathVariable String id,
            @RequestBody UserRequest request
    ) {
        if(!service.exists(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.deleteById(id, request)
                ? new ResponseEntity<String>(HttpStatus.OK)
                : new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
}
