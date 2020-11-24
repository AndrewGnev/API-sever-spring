package com.example.apispringdemo.statuschange;

import com.example.apispringdemo.user.OnlineStatus;
import com.example.apispringdemo.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IStatusChangeService {

    List<StatusChangeEntity> getStatistics(OnlineStatus status, Long timestamp);
    void addStatusChange(UserEntity user, OnlineStatus status, long timestamp);
}
