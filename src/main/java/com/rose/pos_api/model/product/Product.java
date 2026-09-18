package com.rose.pos_api.model.product;

import com.rose.pos_api.statval.enumeration.EProductCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @SequenceGenerator(
            name = "product_seq_gen",
            sequenceName = "product_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Long stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private EProductCategory productCategory;

}
