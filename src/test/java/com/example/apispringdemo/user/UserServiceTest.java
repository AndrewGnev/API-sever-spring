package com.example.apispringdemo.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(UserService.class)
public class UserServiceTest {

    @MockBean
    private UserRepository repository;

    @Autowired
    private UserService service;

    @Test
    public void whenAddUserPassedBusiedUsername_thenThrowsException() {
        when(repository.existsByUsername(anyString())).thenReturn(true);
        when(repository.existsByEmail(anyString())).thenReturn(false);

        try {
            service.addUser("oaoaoa", "mmmm");
            fail("exception doesn't thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("username is already busy", e.getMessage(), "wrong exception message");
        } catch (Throwable e) {
            fail("wrong exception thrown");
        }

        verify(repository, atMost(1)).existsByUsername(eq("oaoaoa"));
        verify(repository, atMost(1)).existsByEmail(eq("mmmm"));
        verify(repository, atMost(0)).saveAndFlush(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenAddUserPassedBusiedEmail_thenThrowsException() {
        when(repository.existsByUsername(anyString())).thenReturn(false);
        when(repository.existsByEmail(anyString())).thenReturn(true);

        try {
            service.addUser("oaoaoa", "mmmm");
            fail("exception doesn't thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("email is already busy", e.getMessage(), "wrong exception message");
        } catch (Throwable e) {
            fail("wrong exception thrown");
        }

        verify(repository, atMost(1)).existsByUsername(eq("oaoaoa"));
        verify(repository, atMost(1)).existsByEmail(eq("mmmm"));
        verify(repository, atMost(0)).saveAndFlush(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenAddUserPassedFreeUsernameAndEmail_thenReturnNewEntityId() {
        when(repository.existsByUsername(anyString())).thenReturn(false);
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.saveAndFlush(eq(new UserEntity("oaoaoa", "mmmm"))))
                .thenReturn(new UserEntity(3301, "oaoaoa", "mmmm"));

        assertEquals(3301, service.addUser("oaoaoa", "mmmm"), "wrong id returned");

        verify(repository, atMost(1)).existsByUsername(eq("oaoaoa"));
        verify(repository, atMost(1)).existsByEmail(eq("mmmm"));
        verify(repository, atMost(1))
                .saveAndFlush(eq(new UserEntity("oaoaoa", "mmmm")));
        verifyNoMoreInteractions(repository);
    }

    // TODO test getUser, setUserStatus
}
