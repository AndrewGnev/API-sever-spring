package com.example.apispringdemo.user;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class UserEntity {

    @Id
    @SequenceGenerator(name = "id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    private final Long id;

    @Column(unique = true, nullable = false)
    private final String username;

    @Column(unique = true, nullable = false)
    private final String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OnlineStatus status;

    public UserEntity() {
        this.id = null;
        this.username = null;
        this.email = null;
        this.status = null;
    }

    public UserEntity(String username, String email) {
        this.id = null;
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.status = OnlineStatus.OFFLINE;
    }

    public UserEntity(long id, String username, String email) {
        this.id = id;
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.status = OnlineStatus.OFFLINE;
    }

    public UserEntity(long id, String username, String email, OnlineStatus status) {
        this.id = id;
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.status = Objects.requireNonNull(status);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity entity = (UserEntity) o;
        return Objects.equals(id, entity.id) &&
                username.equals(entity.username) &&
                email.equals(entity.email) &&
                status == entity.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, status);
    }
}
