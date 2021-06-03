package com.rom.domain.service;

import com.google.gson.Gson;
import com.rom.Utils;
import com.rom.domain.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ModelService.class)
public class ModelServiceTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ModelService service;

    private final Gson gson = new Gson();
    private User user;

    @Before
    public void setup() {
        user = gson.fromJson(Utils.resource("user.json"), User.class);
    }

    @Test
    public void return_all_when_getAll() {
        Mockito.when(userService.getById(user.getUsername())).thenReturn(user);
        assertEquals(
                user.getModels(),
                service.getAll(user.getUsername())
        );
    }

    @Test
    public void return_exception_when_getAllInlivad() {
        Mockito.when(userService.getById("invalid")).thenReturn(null);
        assertThrows(
                NullPointerException.class,
                () -> service.getAll("invalid")
        );
    }

    @Test
    public void return_correctModel_when_getByIdIsValid() {
        Mockito.when(userService.getById(user.getUsername())).thenReturn(user);
        assertEquals(
                user.getModels().get("MODEL1"),
                service.getById(user.getUsername(), "MODEL1")
        );
    }

    @Test
    public void return_exception_when_getByIdInvalid() {
        Mockito.when(userService.getById("invalid")).thenReturn(null);
        assertThrows(
                NullPointerException.class,
                () -> service.getById("invalid", "MODEL1")
        );
    }

   @Test
    public void return_false_when_existsUserButNoModel() {
        Mockito.when(userService.getById(user.getUsername())).thenReturn(user);
        assertFalse(service.exists(user.getUsername(), "inexistent model"));
    }

    @Test
    public void return_false_when_NoexistsUserButWithModel() {
        Mockito.when(userService.getById(user.getUsername())).thenReturn(null);
        assertFalse(service.exists(user.getUsername(), "MODEL 1"));
    }

    @Test
    public void return_false_when_NoexistsUserAndNoModel() {
        Mockito.when(userService.getById(user.getUsername())).thenReturn(null);
        assertFalse(service.exists(user.getUsername(), "inexistent model"));
    }

    @Test
    public void return_ok_when_usernameAndModelExists() {
        Mockito.when(userService.getById(user.getUsername())).thenReturn(user);

        service.save(user.getUsername(), "MODEL1", user.getModels().get("MODEL1"));

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    public void return_exception_when_NousernameButModelExists() {
        Mockito.when(userService.getById(user.getUsername())).thenReturn(user);

        assertThrows(
                NullPointerException.class,
                () -> service.save(
                        "invalid username",
                        "MODEL1",
                        user.getModels().get("MODEL1")
                )
        );

        verify(userService, times(0)).save(any(User.class));
    }

    @Test
    public void return_exception_when_NousernameAndNoModelExists() {
        Mockito.when(userService.getById(user.getUsername())).thenReturn(user);

        assertThrows(
                NullPointerException.class,
                () -> service.save(
                        "invalid username",
                        "invalid model",
                        user.getModels().get("MODEL1")
                )
        );

        verify(userService, times(0)).save(any(User.class));
    }

    @Test
    public void return_ok_when_deleteCorrect() {
        Mockito.when(userService.getById(user.getUsername())).thenReturn(user);
        service.deleteById(user.getUsername(), "MODEL1");
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    public void return_exception_when_user_incorrect() {
        Mockito.when(userService.getById(user.getUsername())).thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> service.deleteById(user.getUsername(), "MODEL1")
        );

        verify(userService, times(0)).save(any(User.class));
    }
}
