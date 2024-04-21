package com.muacidev.demoparkapi.entity;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "usuarios")
public class Usuario  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String username;
    private String password;
    private Role role;

    public enum Role{
        ROLE_ADMIN, ROLE_CLIENTE
    }


}
