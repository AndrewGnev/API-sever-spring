package com.example.apispringdemo.statuschange;

import com.example.apispringdemo.user.OnlineStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StatusChangeService implements IStatusChangeService {

    private final StatusChangeRepository repository;

    public StatusChangeService(StatusChangeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<StatusChangeEntity> getStatistics(OnlineStatus status, Long timestamp) {
        return null; // TODO
    }
}
