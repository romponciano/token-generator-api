package com.rom.domain.service;


import com.google.gson.Gson;
import com.rom.Utils;
import com.rom.domain.entity.Model;
import com.rom.domain.repository.ModelRepository;
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
@WebMvcTest(value = ModelService.class)
public class ModelServiceTest {

    @MockBean
    private ModelRepository repository;

    @Autowired
    private ModelService service;

    private final Gson gson = new Gson();
    private Model model;

    @Before
    public void setup() {
        model = gson.fromJson(Utils.resource("model.json"), Model.class);
    }

    @Test
    public void return_all_when_getAll_correct() {
        List<Model> models = new ArrayList<>();
        models.add(model);
        models.add(model);
        models.add(model);

        Mockito.when(repository.findAll()).thenReturn(models);

        assertEquals(models, service.getAll());
    }

    @Test
    public void return_correctId_when_getById_correct() {
        Mockito.when(repository.findById(model.getId())).thenReturn(Optional.of(model));

        assertEquals(model, service.getById(model.getId()));
    }

    @Test
    public void return_null_when_getById_empty() {
        Mockito.when(repository.findById(model.getId())).thenReturn(Optional.empty());

        assertNull(service.getById(model.getId()));
    }

    @Test
    public void return_created_when_create_correct() {
        Mockito.when(repository.save(any(Model.class))).thenReturn(model);

        assertEquals(model, service.create(model));
    }

    @Test
    public void return_updated_when_update_correct() {
        Mockito.when(repository.save(any(Model.class))).thenReturn(model);

        assertEquals(model, service.update(model));
    }

    @Test
    public void return_ok_when_delete_correct() {
        Mockito.doNothing().when(repository).deleteById(model.getId());

        service.deleteById(model.getId());

        Mockito.verify(repository, times(1)).deleteById(model.getId());
    }

    @Test
    public void return_true_when_exists() {
        Mockito.when(repository.existsById(model.getId())).thenReturn(true);

        assertTrue(service.exists(model.getId()));
    }

    @Test
    public void return_false_when_not_exists() {
        Mockito.when(repository.existsById(model.getId())).thenReturn(false);

        assertFalse(service.exists(model.getId()));
    }
}
