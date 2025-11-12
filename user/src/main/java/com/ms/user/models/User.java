package com.ms.user.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.aspectj.lang.annotation.RequiredTypes;

@jakarta.persistence.Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @NotBlank
    @Column(nullable = true)
    private String email;

    private String status;

    public User() {

    }

    public User(String nome, String email, String status) {
        this.nome = nome;
        this.email = email;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
