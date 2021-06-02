package com.rom.application;

import com.google.gson.Gson;
import com.rom.Utils;
import com.rom.domain.entity.Model;
import com.rom.domain.service.ModelService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ModelController.class)
public class ModelControllerTest {
    @MockBean
    private ModelService service;

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();
    private Model model;

    @Before
    public void setup() {
        model = gson.fromJson(Utils.resource("model.json"), Model.class);
    }

    @Test
    public void return_all_when_getAll_correct() throws Exception {
        List<Model> models = new ArrayList<>();
        models.add(model);
        models.add(model);
        models.add(model);
        Mockito.when(service.getAll()).thenReturn(models);

        String response = mockMvc
                .perform(MockMvcRequestBuilders.get("/tg/model"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(models.size(), (gson.fromJson(response, List.class)).size());
    }

    @Test
    public void return_correctModel_when_getById_correct() throws Exception {
        Mockito.when(service.getById(model.getId())).thenReturn(model);

        String response = mockMvc
                .perform(MockMvcRequestBuilders.get("/tg/model/" +  model.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(model, (gson.fromJson(response, Model.class)));
    }

    @Test
    public void return_created_when_post_correct() throws Exception {
        Mockito.when(service.create(any(Model.class))).thenReturn(model);
        Mockito.when(service.exists(model.getId())).thenReturn(false);

        String json = Utils.resource("model.json");
        assert json != null;

        String response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tg/model")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(model, gson.fromJson(response, Model.class));
    }

    @Test
    public void return_badRequest_when_post_existentId() throws Exception {
        Mockito.when(service.create(any(Model.class))).thenReturn(model);
        Mockito.when(service.exists(model.getId())).thenReturn(true);

        String json = Utils.resource("model.json");
        assert json != null;

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tg/model")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void return_updated_when_put_correct() throws Exception {
        Mockito.when(service.update(any(Model.class))).thenReturn(model);
        Mockito.when(service.exists(model.getId())).thenReturn(true);

        String json = Utils.resource("model.json");
        assert json != null;

        String response = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/tg/model")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(model, gson.fromJson(response, Model.class));
    }

    @Test
    public void return_badRequest_when_put_inexistentId() throws Exception {
        Mockito.when(service.exists(model.getId())).thenReturn(false);

        String json = Utils.resource("model.json");
        assert json != null;

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/tg/model")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void return_ok_when_delete_correct() throws Exception {
        Mockito.doNothing().when(service).deleteById(model.getId());
        Mockito.when(service.exists(model.getId())).thenReturn(true);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/tg/model/" + model.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void return_badRequest_when_delete_inexistentId() throws Exception {
        Mockito.when(service.exists(model.getId())).thenReturn(false);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/tg/model/" + model.getId()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
