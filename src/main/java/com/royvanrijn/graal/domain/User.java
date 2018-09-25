package com.royvanrijn.graal.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "USER")
@SequenceGenerator(name = "USER_SEQ", allocationSize = 1, sequenceName = "USER_SEQ")
public class User implements Serializable {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQ")
    @Column(name = "ID")
    private Long id;

    @Expose
    @Column(name = "NAME")
    private String name;

    @Expose
    @Column(name = "EMAIL")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
