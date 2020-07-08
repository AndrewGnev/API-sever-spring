package com.example.apispringdemo.user.api.v1;


import com.example.apispringdemo.user.OnlineStatus;
import com.example.apispringdemo.user.UserEntity;
import com.example.apispringdemo.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService service;

    @Autowired
    private UserController controller;

    @Test
    public void whenPostWithUsernameThatExists_thenTrowsCorrectException() {
        IllegalArgumentException ex = new IllegalArgumentException("username is already busy");
        when(service.addUser(anyString(), anyString())).thenThrow(ex);

        try {
            controller.post("u", "e");
            fail("exception wasn`t thrown");
        } catch (Exception e) {
            assertSame(ResponseStatusException.class, e.getClass());
            assertSame(HttpStatus.CONFLICT, ((ResponseStatusException) e).getStatus());
            assertEquals(ex.getMessage(), ((ResponseStatusException) e).getReason());
            assertSame(ex, e.getCause());
        }

        verify(service, atMost(1)).addUser(eq("u"), eq("e"));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenPostWithEmailThatExists_thenTrowsCorrectException() {
        IllegalArgumentException ex = new IllegalArgumentException("email is already busy");
        when(service.addUser(anyString(), anyString())).thenThrow(ex);

        try {
            controller.post("u", "e");
            fail("exception wasn`t trown");
        } catch (Exception e) {
            assertSame(ResponseStatusException.class, e.getClass());
            assertSame(HttpStatus.CONFLICT, ((ResponseStatusException) e).getStatus());
            assertEquals(ex.getMessage(), ((ResponseStatusException) e).getReason());
            assertSame(ex, e.getCause());
        }

        verify(service, atMost(1)).addUser(eq("u"), eq("e"));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenPostPassedFreeUsernameAndEmail_thenReturnsCorrectId() {
        when(service.addUser(anyString(), anyString())).thenReturn(228L);

        assertEquals(228L, controller.post("u", "e"), "wrong id returned");

        verify(service, atMost(1)).addUser("u", "e");
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenGetWithIdThatDontExist_thenThrowsCorrectException() {
        IllegalArgumentException ex = new IllegalArgumentException("user doesn't exist");
        when(service.getUser(228L)).thenThrow(ex);

        try {
            controller.get(228L);
            fail("exception wasn`t thrown");
        } catch (Exception e) {
            assertSame(ResponseStatusException.class, e.getClass());
            assertSame(HttpStatus.NOT_FOUND, ((ResponseStatusException) e).getStatus());
            assertEquals(ex.getMessage(), ((ResponseStatusException) e).getReason());
            assertSame(ex, e.getCause());
        }

        verify(service, atMost(1)).getUser(228L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenGetWithIdThatExists_thenReturnsCorrectDto() {
        UserDto dto = new UserDto(228L, "u", "e", OnlineStatus.ONLINE);
        when(service.getUser(228L)).thenReturn(
                new UserEntity(228L, "u", "e", OnlineStatus.ONLINE));

        assertEquals(dto, controller.get(228L));

        verify(service, atMost(1)).getUser(228L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenPutWithIllegalId_thenThrowsCorrectException() {
        IllegalArgumentException ex = new IllegalArgumentException("user doesn`t exist");
        when(service.setUserStatus(anyLong(), any(OnlineStatus.class))).thenThrow(ex);

        try {
            controller.put(228L, OnlineStatus.ONLINE);
            fail("exception wasn`t trown");
        } catch (Exception e) {
            assertSame(ResponseStatusException.class, e.getClass());
            assertSame(HttpStatus.NOT_FOUND, ((ResponseStatusException) e).getStatus());
            assertEquals(ex.getMessage(), ((ResponseStatusException) e).getReason());
            assertSame(ex, e.getCause());
        }
    }

    @Test
    public void whenPutWithIdThatExists_thenReturnsCorrectData() {
        UserStatusDto dto = new UserStatusDto(228L, OnlineStatus.OFFLINE, OnlineStatus.ONLINE);
        when(service.setUserStatus(anyLong(), any(OnlineStatus.class))).thenReturn(
                OnlineStatus.OFFLINE);

        assertEquals(dto, controller.put(228L, OnlineStatus.ONLINE));

        verify(service, atMost(1)).setUserStatus(228L, OnlineStatus.ONLINE);
        verifyNoMoreInteractions(service);
    }
}
