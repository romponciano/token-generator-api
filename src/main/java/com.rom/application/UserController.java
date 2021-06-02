package com.rom.application;

import com.rom.domain.entity.User;
import com.rom.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tg")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/user/{id}")
    public User getById(@PathVariable String id) {
        return service.getById(id);
    }
    
    @PostMapping("/user")
    public User create(@RequestBody User user) {
        return service.create(user);
    }

    @PutMapping("/user")
    public User update(@RequestBody User user) {
        return service.update(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }
}
