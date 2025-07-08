package com.aalperen.pharmacy_application.entity;

import com.aalperen.pharmacy_application.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name="pharmacy_users")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 4622233553923085871L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Role role = Role.EMPLOYEE;


}
