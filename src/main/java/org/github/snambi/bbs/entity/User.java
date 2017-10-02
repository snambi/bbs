package org.github.snambi.bbs.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity Object to Store an User
 */
@Entity
@Table(name = "users")
public class User {

    // Auto-generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // The user's email
    @NotNull
    private String email;

    // The user's name
    @NotNull
    private String name;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
