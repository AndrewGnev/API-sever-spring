package com.example.apispringdemo.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
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
            fail("exception wasn't thrown");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass(), "wrong exception thrown");
            assertEquals("username is already busy", e.getMessage(), "wrong exception message");
        }

        verify(repository, atMost(1)).existsByUsername(eq("oaoaoa"));
        verify(repository, atMost(1)).existsByEmail(eq("mmmm"));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenAddUserPassedBusiedEmail_thenThrowsException() {
        when(repository.existsByUsername(anyString())).thenReturn(false);
        when(repository.existsByEmail(anyString())).thenReturn(true);

        try {
            service.addUser("oaoaoa", "mmmm");
            fail("exception wasn't thrown");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass(), "wrong exception thrown");
            assertEquals("email is already busy", e.getMessage(), "wrong exception message");
        }

        verify(repository, atMost(1)).existsByUsername(eq("oaoaoa"));
        verify(repository, atMost(1)).existsByEmail(eq("mmmm"));
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

    @Test
    public void whenUserDoesntExist_thenThrowsException() {
        when(repository.existsById(anyLong())).thenReturn(false);

        try {
            service.getUser(228);
            fail("exception wasn't thrown");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass(), "wrong exception thrown");
            assertEquals("user doesn't exist", e.getMessage(), "wrong exception message");
        }

        verify(repository, atMost(1)).existsById(eq(228L));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenUserExists_thenReturnsUserEntity() {
        when(repository.existsById(anyLong())).thenReturn(true);

        final UserEntity entity = new UserEntity(228, "", "");
        when(repository.findById(228)).thenReturn(entity);

        final UserEntity result = service.getUser(228);
        assertSame(entity, result, "wrong entity returned");
        assertEquals(new UserEntity(228, "", ""), result, "entity was modified");

        verify(repository, atMost(1)).existsById(eq(228L));
        verify(repository, atMost(1)).findById(eq(228L));
        verifyNoMoreInteractions(repository);

    }

    // TODO test setUserStatus
}
