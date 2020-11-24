package com.example.apispringdemo.statuschange;

import com.example.apispringdemo.user.OnlineStatus;
import com.example.apispringdemo.user.UserEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class StatusChangeEntity {

    @Id
    @SequenceGenerator(name = "status_change_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_change_id_seq")
    private final Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private final UserEntity user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private final OnlineStatus status;

    private final long timestamp;

    public StatusChangeEntity() {
        this.id = null;
        this.user = null;
        this.status = null;
        this.timestamp = 0;
    }

    public StatusChangeEntity(UserEntity user, OnlineStatus status, long timestamp) {
        this.id = null;
        this.user = Objects.requireNonNull(user);
        this.status = Objects.requireNonNull(status);
        this.timestamp = timestamp;
    }

    public StatusChangeEntity(Long id, UserEntity user, OnlineStatus status, long timestamp) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusChangeEntity entity = (StatusChangeEntity) o;
        return timestamp == entity.timestamp &&
                Objects.equals(id, entity.id) &&
                Objects.equals(user, entity.user) &&
                status == entity.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, status, timestamp);
    }
}
