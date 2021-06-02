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
    public void return_hash_when_login_correct() throws Exception {
        String hash = String.valueOf(user.hashCode());
        Mockito.when(service.login(user)).thenReturn(hash);

        String json = Utils.resource("user.json");

        String response = mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/tg/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(hash, gson.fromJson(response, String.class));

    }

    @Test
    public void return_badRequest_when_login_incorrect() throws Exception {
        Mockito.when(service.login(user)).thenReturn(null);

        String json = Utils.resource("user.json");

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/tg/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void return_created_when_post_correct() throws Exception {
        Mockito.when(service.create(any(User.class))).thenReturn(user);
        Mockito.when(service.exists(user.getUsername())).thenReturn(false);

        String json = Utils.resource("user.json");
        assert json != null;

        String response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tg/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(user, gson.fromJson(response, User.class));
    }

    @Test
    public void return_badRequest_when_post_existentId() throws Exception {
        Mockito.when(service.create(any(User.class))).thenReturn(user);
        Mockito.when(service.exists(user.getUsername())).thenReturn(true);

        String json = Utils.resource("user.json");
        assert json != null;

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tg/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void return_updated_when_put_correct() throws Exception {
        Mockito.when(service.update(any(User.class))).thenReturn(user);
        Mockito.when(service.exists(user.getUsername())).thenReturn(true);

        String json = Utils.resource("user.json");
        assert json != null;

        String response = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/tg/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals(user, gson.fromJson(response, User.class));
    }

    @Test
    public void return_badRequest_when_put_inexistentId() throws Exception {
        Mockito.when(service.exists(user.getUsername())).thenReturn(false);

        String json = Utils.resource("user.json");
        assert json != null;

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/tg/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void return_ok_when_delete_correct() throws Exception {
        Mockito.doNothing().when(service).deleteById(user.getUsername());
        Mockito.when(service.exists(user.getUsername())).thenReturn(true);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/tg/user/" + user.getUsername()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void return_badRequest_when_delete_inexistentId() throws Exception {
        Mockito.when(service.exists(user.getUsername())).thenReturn(false);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/tg/user/" + user.getUsername()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
