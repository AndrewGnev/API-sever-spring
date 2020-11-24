package com.example.apispringdemo.statuschange.api.v1;

import com.example.apispringdemo.statuschange.StatusChangeEntity;
import com.example.apispringdemo.statuschange.StatusChangeService;
import com.example.apispringdemo.user.OnlineStatus;
import com.example.apispringdemo.user.UserEntity;
import com.example.apispringdemo.user.api.v1.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(StatusChangeController.class)
public class StatusChangeControllerTest {

    @MockBean
    private StatusChangeService service;

    @Autowired
    private StatusChangeController controller;

    @Test
    public void whenGetIsCalled_thenReturnsRightStatistics() {
        List<StatusChangeEntity> entityList = Arrays.asList(
                new StatusChangeEntity(1L, new UserEntity(1L,"u", "e"), OnlineStatus.ONLINE, 1L),
                new StatusChangeEntity(2L, new UserEntity(2L,"v", "f"), OnlineStatus.OFFLINE, 1234L),
                new StatusChangeEntity(3L, new UserEntity(3L,"w", "h"), OnlineStatus.ONLINE, 198L)
        );

        List<StatusChangeDto> dtoList = Arrays.asList(
                new StatusChangeDto(1L, new UserDto(1L,"u", "e", OnlineStatus.ONLINE), 1L),
                new StatusChangeDto(2L, new UserDto(2L,"v", "f", OnlineStatus.OFFLINE), 1234L),
                new StatusChangeDto(3L, new UserDto(3L,"w", "h", OnlineStatus.ONLINE), 198L)
        );

        when(service.getStatistics(any(OnlineStatus.class), anyLong())).thenReturn(entityList);

        assertEquals(dtoList, controller.get(OnlineStatus.ONLINE, null));

        verify(service, atMost(1)).getStatistics(OnlineStatus.ONLINE, 0L);
        verifyNoMoreInteractions(service);
    }
}
