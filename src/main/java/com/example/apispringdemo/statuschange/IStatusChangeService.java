package com.example.apispringdemo.statuschange;

import com.example.apispringdemo.user.OnlineStatus;

import java.util.List;

public interface IStatusChangeService {

    List<StatusChangeEntity> getStatistics(OnlineStatus status, Long timestamp);

    // TODO addStatusChange
}
