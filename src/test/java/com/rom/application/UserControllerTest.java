package com.rom.application;

import com.google.gson.Gson;
import com.rom.Utils;
import com.rom.domain.entity.User;
import com.rom.domain.service.UserService;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {
    @MockBean
    private UserService service;

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();
    private User user;

    @Before
    public void setup() {
        user = gson.fromJson(Utils.resource("user.json"), User.class);
    }

    @Test
    public void return_hash_when_loginSuccessfully() throws Exception {
        String hash = String.valueOf(user.hashCode());
        Mockito.when(service.login(any(User.class))).thenReturn(hash);

        String json = Utils.resource("user.json");
        assert json != null;

        String url = "/tg/user/login";
        String response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(hash, gson.fromJson(response, String.class));
    }

    @Test
    public void return_hash_when_wrong_login() throws Exception {
        Mockito.when(service.login(any(User.class))).thenReturn(null);

        String json = Utils.resource("user.json");
        assert json != null;

        String url = "/tg/user/login";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void return_ok_when_getById_correct() throws Exception {
        Mockito.when(service.exists(user.getUsername())).thenReturn(true);
        Mockito.when(service.getById(user.getUsername())).thenReturn(user);

        String url = "/tg/user/" + user.getUsername();
        String response = mockMvc
                .perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(user, gson.fromJson(response, User.class));
    }

    @Test
    public void return_ok_when_getById_notExists() throws Exception {
        Mockito.when(service.exists(user.getUsername())).thenReturn(false);

        String url = "/tg/user/" + user.getUsername();
        mockMvc
                .perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(service, times(0)).getById(user.getUsername());
    }

    @Test
    public void return_saved_when_userCorrect() throws Exception {
        Mockito.when(service.save(any(User.class))).thenReturn(user);

        String json = Utils.resource("user.json");
        assert json != null;

        String url = "/tg/user";
        String response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(user, gson.fromJson(response, User.class));
    }

    @Test
    public void return_ok_when_deleteById_correct() throws Exception {
        Mockito.when(service.exists(user.getUsername())).thenReturn(true);
        Mockito.doNothing().when(service).deleteById(user.getUsername());

        String url = "/tg/user/" + user.getUsername();
        mockMvc
                .perform(MockMvcRequestBuilders.delete(url))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).deleteById(user.getUsername());
    }


    @Test
    public void return_badRequest_when_deleteById_useranemeNotExists() throws Exception {
        Mockito.when(service.exists(user.getUsername())).thenReturn(false);

        String url = "/tg/user/" + user.getUsername();
        mockMvc
                .perform(MockMvcRequestBuilders.delete(url))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(service, times(0)).deleteById(anyString());
    }
}
