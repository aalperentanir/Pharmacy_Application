package com.aalperen.pharmacy_application.entity;

import com.aalperen.pharmacy_application.enums.Gender;
import com.aalperen.pharmacy_application.enums.SkinType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "customers")
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = -6607128325026240808L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "skin_type")
    private SkinType skinType;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "identity_number")
    private String identityNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

}
