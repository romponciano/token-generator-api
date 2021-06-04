package com.rom.application;

import com.google.gson.Gson;
import com.rom.Utils;
import com.rom.domain.entity.Model;
import com.rom.domain.entity.User;
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

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ModelController.class)
public class ModelControllerTest {
    @MockBean
    private ModelService service;

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();
    private User user;

    @Before
    public void setup() {
        user = gson.fromJson(Utils.resource("user.json"), User.class);
    }

    @Test
    public void return_ok_when_getAll_correct() throws Exception {
        Mockito.when(service.getAll(user.getUsername())).thenReturn(user.getModels());

        String url = "/tg/" + user.getUsername() + "/model/";
        String response = mockMvc
                .perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(user.getModels().size(), gson.fromJson(response, HashMap.class).size());

        verify(service, times(1)).getAll(user.getUsername());
    }

    @Test
    public void return_badRequest_when_getAll_incorrect() throws Exception {
        Mockito.when(service.getAll(user.getUsername())).thenThrow(NullPointerException.class);

        String url = "/tg/" + user.getUsername() + "/model/";
        mockMvc
                .perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void return_ok_when_getById_correct() throws Exception {
        Mockito.when(service.getById(user.getUsername(), "MODEL1"))
                .thenReturn(user.getModels().get("MODEL1"));

        String url = "/tg/" + user.getUsername() + "/model/MODEL1";
        String response = mockMvc
                .perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(
                user.getModels().get("MODEL1"),
                gson.fromJson(response, Model.class)
        );
    }

    @Test
    public void return_badRequest_when_getById_incorrect() throws Exception {
        Mockito.when(service.getById(user.getUsername(), "MODEL1"))
                .thenThrow(NullPointerException.class);

        String url = "/tg/" + user.getUsername() + "/model/MODEL1";
        mockMvc
                .perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void return_ok_when_save_correct() throws Exception {
        Mockito.doNothing().when(service).save(anyString(), anyString(), any(Model.class));

        String json = gson.toJson(user.getModels().get("MODEL1"));

        String url = "/tg/" + user.getUsername() + "/model/MODEL1";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).save(anyString(), anyString(), any(Model.class));
    }

    @Test
    public void return_badRequest_when_save_incorrect() throws Exception {
        Mockito.doThrow(NullPointerException.class)
                .when(service)
                .save(anyString(),anyString(), any(Model.class));

        String json = gson.toJson(user.getModels().get("MODEL1"));

        String url = "/tg/" + user.getUsername() + "/model/MODEL1";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void return_ok_when_deleteById_correct() throws Exception {
        Mockito.when(service.exists(user.getUsername(), "MODEL1")).thenReturn(true);
        Mockito.doNothing().when(service).deleteById(user.getUsername(), "MODEL1");

        String url = "/tg/" + user.getUsername() + "/model/MODEL1";
        mockMvc
                .perform(MockMvcRequestBuilders.delete(url))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).deleteById(user.getUsername(), "MODEL1");
    }


    @Test
    public void return_badRequest_when_deleteById_useranemeNotExists() throws Exception {
        Mockito.when(service.exists(user.getUsername(), "MODEL1"))
                .thenReturn(false);

        String url = "/tg/" + user.getUsername() + "/model/MODEL1";
        mockMvc
                .perform(MockMvcRequestBuilders.delete(url))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(service, times(0)).deleteById(anyString(), anyString());
    }
}
