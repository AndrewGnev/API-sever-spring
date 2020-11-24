package com.example.apispringdemo.statuschange;

import com.example.apispringdemo.user.OnlineStatus;
import com.example.apispringdemo.user.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class StatusChangeService implements IStatusChangeService {

    private final StatusChangeRepository repository;

    public StatusChangeService(StatusChangeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<StatusChangeEntity> getStatistics(OnlineStatus status, Long timestamp) {
        final long realTimestamp = Optional.ofNullable(timestamp).orElse(0L);
        final List<StatusChangeEntity> statistics;

        if (status == null) {
            statistics = repository.findByTimestampIsGreaterThanEqualOrderByTimestampDesc(realTimestamp);
        } else {
            statistics = repository.findByUser_StatusAndTimestampIsGreaterThanEqualOrderByTimestampDesc(status,
                    realTimestamp);
        }

        final Set<UserEntity> users = new HashSet<>();
        final Iterator<StatusChangeEntity> it = statistics.iterator();
        while (it.hasNext()) {
            final StatusChangeEntity entity = it.next();

            if (users.contains(entity.getUser())) {
                it.remove();
            } else {
                users.add(entity.getUser());
            }
        }

        return statistics;
    }

    @Override
    public void addStatusChange(UserEntity user, OnlineStatus status, long timestamp) {
        repository.save(new StatusChangeEntity(user, status, timestamp));
    }
}
