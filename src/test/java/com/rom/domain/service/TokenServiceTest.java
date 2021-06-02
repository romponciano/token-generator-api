package com.rom.domain.service;


import com.google.gson.Gson;
import com.rom.Utils;
import com.rom.domain.entity.Token;
import com.rom.domain.repository.TokenRepository;
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
@WebMvcTest(value = TokenService.class)
public class TokenServiceTest {

    @MockBean
    private TokenRepository repository;

    @Autowired
    private TokenService service;

    private final Gson gson = new Gson();
    private Token token;

    @Before
    public void setup() {
        token = gson.fromJson(Utils.resource("token.json"), Token.class);
    }

    @Test
    public void return_all_when_getAll_correct() {
        List<Token> models = new ArrayList<>();
        models.add(token);
        models.add(token);
        models.add(token);

        Mockito.when(repository.findAll()).thenReturn(models);

        assertEquals(models, service.getAll());
    }

    @Test
    public void return_correctId_when_getById_correct() {
        Mockito.when(repository.findById(token.getId())).thenReturn(Optional.of(token));

        assertEquals(token, service.getById(token.getId()));
    }

    @Test
    public void return_null_when_getById_empty() {
        Mockito.when(repository.findById(token.getId())).thenReturn(Optional.empty());

        assertNull(service.getById(token.getId()));
    }

    @Test
    public void return_created_when_create_correct() {
        Mockito.when(repository.save(any(Token.class))).thenReturn(token);

        assertEquals(token, service.create(token));
    }

    @Test
    public void return_updated_when_update_correct() {
        Mockito.when(repository.save(any(Token.class))).thenReturn(token);

        assertEquals(token, service.update(token));
    }

    @Test
    public void return_ok_when_delete_correct() {
        Mockito.doNothing().when(repository).deleteById(token.getId());

        service.deleteById(token.getId());

        Mockito.verify(repository, times(1)).deleteById(token.getId());
    }

    @Test
    public void return_true_when_exists() {
        Mockito.when(repository.existsById(token.getId())).thenReturn(true);

        assertTrue(service.exists(token.getId()));
    }

    @Test
    public void return_false_when_not_exists() {
        Mockito.when(repository.existsById(token.getId())).thenReturn(false);

        assertFalse(service.exists(token.getId()));
    }
}
