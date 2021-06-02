package com.rom.domain.service;


import com.google.gson.Gson;
import com.rom.Utils;
import com.rom.domain.entity.User;
import com.rom.domain.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserService.class)
public class UserServiceTest {

    @MockBean
    private UserRepository repository;

    @Autowired
    private UserService service;

    private final Gson gson = new Gson();
    private User user;

    @Before
    public void setup() {
        user = gson.fromJson(Utils.resource("user.json"), User.class);
    }

    @Test
    public void return_hash_when_login_correct() {
        Mockito.when(repository.findById(user.getUsername())).thenReturn(Optional.of(user));

        assertEquals(String.valueOf(user.hashCode()), service.login(user));
    }

    @Test
    public void return_null_when_username_incorrect() {
        Mockito.when(repository.findById(user.getUsername())).thenReturn(Optional.empty());

        assertNull(service.login(user));
    }

    @Test
    public void return_null_when_password_incorrect() {
        Mockito.when(repository.findById(user.getUsername())).thenReturn(Optional.of(user));

        User user2 = new User(user.getUsername(), "wrong-pass");

        assertNull(service.login(user2));
    }

    @Test
    public void return_created_when_create_correct() {
        Mockito.when(repository.save(any(User.class))).thenReturn(user);

        assertEquals(user, service.create(user));
    }

    @Test
    public void return_updated_when_update_correct() {
        Mockito.when(repository.save(any(User.class))).thenReturn(user);

        assertEquals(user, service.update(user));
    }

    @Test
    public void return_ok_when_delete_correct() {
        Mockito.doNothing().when(repository).deleteById(user.getUsername());

        service.deleteById(user.getUsername());

        Mockito.verify(repository, times(1)).deleteById(user.getUsername());
    }

    @Test
    public void return_true_when_exists() {
        Mockito.when(repository.existsById(user.getUsername())).thenReturn(true);

        assertTrue(service.exists(user.getUsername()));
    }

    @Test
    public void return_false_when_not_exists() {
        Mockito.when(repository.existsById(user.getUsername())).thenReturn(false);

        assertFalse(service.exists(user.getUsername()));
    }
}
