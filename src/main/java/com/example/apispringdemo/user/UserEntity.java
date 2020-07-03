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

    @Enumerated(value = EnumType.STRING)
    @Column(unique = true, nullable = false)
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
}
