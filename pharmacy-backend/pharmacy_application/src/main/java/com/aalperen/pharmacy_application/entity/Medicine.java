package com.aalperen.pharmacy_application.entity;

import com.aalperen.pharmacy_application.enums.SkinType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "medicines")
public class Medicine implements Serializable {

    @Serial
    private static final long serialVersionUID = -4922075981188664903L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(name = "medicine_skin_types",joinColumns = @JoinColumn(name = "medicine_id"))
    @Column(name="skin_type")
    private Set<SkinType> suitableSkinTypes = new HashSet<>();

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private Integer quantity;


}
