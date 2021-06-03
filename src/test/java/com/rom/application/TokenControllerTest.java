package com.rom.application;

import com.google.gson.Gson;
import com.rom.Utils;
import com.rom.domain.entity.User;
import com.rom.domain.service.TokenService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TokenController.class)
public class TokenControllerTest {
    @MockBean
    private TokenService service;

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();
    private User user;

    @Before
    public void setup() {
        user = gson.fromJson(Utils.resource("user.json"), User.class);
    }

    @Test
    public void return_ok_when_save_correct() throws Exception {
        Mockito.when(service.exists(user.getUsername(), "MODEL1")).thenReturn(true);
        Mockito.doNothing().when(service).save(any(), any(), any());

        String json = Utils.resource("tokens.json");
        assert json != null;

        String url = "/tg/" + user.getUsername() + "/model/MODEL1/token";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).save(any(), any(), any());
    }

    @Test
    public void return_badRequest_when_usernameNoExist() throws Exception {
        Mockito.when(service.exists(user.getUsername(), "MODEL1")).thenReturn(false);

        String json = Utils.resource("tokens.json");
        assert json != null;

        String url = "/tg/" + user.getUsername() + "/model/MODEL1/token";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(service, times(0)).save(any(), any(), any());
    }

    @Test
    public void return_badRequest_when_modelNoExist() throws Exception {
        Mockito.when(service.exists(user.getUsername(), "invalid")).thenReturn(false);

        String json = Utils.resource("tokens.json");
        assert json != null;

        String url = "/tg/" + user.getUsername() + "/model/invalid/token";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(service, times(0)).save(any(), any(), any());
    }

    @Test
    public void return_badRequest_when_wrongJson() throws Exception {
        Mockito.when(service.exists(user.getUsername(), "MODEL1")).thenReturn(true);

        String json = Utils.resource("user.json");
        assert json != null;

        String url = "/tg/" + user.getUsername() + "/model/MODEL1/token";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(service, times(0)).save(any(), any(), any());
    }
}
