package com.example.apispringdemo.statuschange;

import com.example.apispringdemo.user.OnlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusChangeRepository extends JpaRepository<StatusChangeEntity, Long> {

    List<StatusChangeEntity> findByStatusAndTimestampAfter(OnlineStatus status, long timestamp);
}
