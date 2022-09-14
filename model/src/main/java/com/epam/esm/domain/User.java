package com.epam.esm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String name;

    @Column(unique = true)
    private String username;

    private String password;
    private boolean blocked;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User(UUID id, LocalDateTime createdDate, LocalDateTime updatedDate, String name, String username, String password, boolean blocked, List<Order> orders) {
        super(id, createdDate, updatedDate);
        this.name = name;
        this.username = username;
        this.password = password;
        this.blocked = blocked;
        this.orders = orders;
    }

    public User(UUID uuid, LocalDateTime of, LocalDateTime now, String test_name, String test_password, boolean b, List<Order> orders) {
    }

}
