package com.example.apispringdemo.statuschange;

import com.example.apispringdemo.user.OnlineStatus;
import com.example.apispringdemo.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(StatusChangeService.class)
public class StatusChangeServiceTest {

    private static final UserEntity[] TEST_USERS = new UserEntity[] {
            new UserEntity(1L, "a", "a", OnlineStatus.OFFLINE),
            new UserEntity(2L, "b", "b", OnlineStatus.OFFLINE),
            new UserEntity(3L, "c", "c", OnlineStatus.ONLINE),
            new UserEntity(4L, "d", "d", OnlineStatus.ONLINE)
    };

    @MockBean
    private StatusChangeRepository repository;

    @Autowired
    private StatusChangeService service;

    @Test
    public void whenStatusAndTimestampBothNull_thenReturnsCorrectStatisticsAndFilterWorksCorrectly() {
        List<StatusChangeEntity> repAns = new ArrayList<>(Arrays.asList(
                new StatusChangeEntity(2L, TEST_USERS[0], OnlineStatus.OFFLINE, 229L),
                new StatusChangeEntity(1L, TEST_USERS[0], OnlineStatus.ONLINE, 228L),
                new StatusChangeEntity(3L, TEST_USERS[2], OnlineStatus.ONLINE, 22L)
        ));

        List<StatusChangeEntity> filterAns = Arrays.asList(
                new StatusChangeEntity(2L, TEST_USERS[0], OnlineStatus.OFFLINE, 229L),
                new StatusChangeEntity(3L, TEST_USERS[2], OnlineStatus.ONLINE, 22L)
        );

        when(repository.findByTimestampIsGreaterThanEqualOrderByTimestampDesc(eq(0L))).thenReturn(repAns);

        assertEquals(filterAns, service.getStatistics(null, null), "filter works incorrectly");

        verify(repository, atMost(1)).findByTimestampIsGreaterThanEqualOrderByTimestampDesc(eq(0L));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenStatusNull_thenReturnsCorrectStatisticsAndFilterWorksCorrectly() {
        List<StatusChangeEntity> repAns = new ArrayList<>(Arrays.asList(
                new StatusChangeEntity(2L, TEST_USERS[0], OnlineStatus.OFFLINE, 229L),
                new StatusChangeEntity(1L, TEST_USERS[0], OnlineStatus.ONLINE, 228L),
                new StatusChangeEntity(4L, TEST_USERS[3], OnlineStatus.ONLINE, 227L)
        ));

        List<StatusChangeEntity> filterAns = Arrays.asList(
                new StatusChangeEntity(2L, TEST_USERS[0], OnlineStatus.OFFLINE, 229L),
                new StatusChangeEntity(4L, TEST_USERS[3], OnlineStatus.ONLINE, 227L)
        );

        when(repository.findByTimestampIsGreaterThanEqualOrderByTimestampDesc(eq(200L))).thenReturn(repAns);

        assertEquals(filterAns, service.getStatistics(null, 200L), "filter works incorrectly");

        verify(repository, atMost(1)).findByTimestampIsGreaterThanEqualOrderByTimestampDesc(eq(200L));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenStatusIsNotNullAndTimestampIsNull_thenReturnsCorrectStatisticsAndFilterWorksCorrectly() {
        List<StatusChangeEntity> repAns = new ArrayList<>(Arrays.asList(
                new StatusChangeEntity(5L, TEST_USERS[1], OnlineStatus.OFFLINE, 230L),
                new StatusChangeEntity(2L, TEST_USERS[0], OnlineStatus.OFFLINE, 229L),
                new StatusChangeEntity(4L, TEST_USERS[1], OnlineStatus.OFFLINE, 227L)
        ));

        List<StatusChangeEntity> filterAns = Arrays.asList(
                new StatusChangeEntity(5L, TEST_USERS[1], OnlineStatus.OFFLINE, 230L),
                new StatusChangeEntity(2L, TEST_USERS[0], OnlineStatus.OFFLINE, 229L)
        );

        when(repository.findByUser_StatusAndTimestampIsGreaterThanEqualOrderByTimestampDesc(
                OnlineStatus.OFFLINE, 0L)).thenReturn(repAns);

        assertEquals(filterAns, service.getStatistics(OnlineStatus.OFFLINE, 0L), "filter works incorrectly");

        verify(repository, atMost(1)).
                findByUser_StatusAndTimestampIsGreaterThanEqualOrderByTimestampDesc(OnlineStatus.OFFLINE, 0L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenStatusAndTimestampBothNotNull_thenReturnsCorrectStatisticsAndFilterWorksCorrectly() {
        List<StatusChangeEntity> repAns = new ArrayList<>(Arrays.asList(
                new StatusChangeEntity(5L, TEST_USERS[2], OnlineStatus.ONLINE, 230L),
                new StatusChangeEntity(2L, TEST_USERS[3], OnlineStatus.ONLINE, 229L),
                new StatusChangeEntity(4L, TEST_USERS[3], OnlineStatus.ONLINE, 227L)
        ));

        List<StatusChangeEntity> filterAns = Arrays.asList(
                new StatusChangeEntity(5L, TEST_USERS[2], OnlineStatus.ONLINE, 230L),
                new StatusChangeEntity(2L, TEST_USERS[3], OnlineStatus.ONLINE, 229L)
        );

        when(repository.findByUser_StatusAndTimestampIsGreaterThanEqualOrderByTimestampDesc(
                OnlineStatus.ONLINE, 220L)).thenReturn(repAns);

        assertEquals(filterAns, service.getStatistics(OnlineStatus.ONLINE, 220L));

        verify(repository, atMost(1)).
                findByUser_StatusAndTimestampIsGreaterThanEqualOrderByTimestampDesc(OnlineStatus.ONLINE, 220L);
        verifyNoMoreInteractions(repository);
    }
}
