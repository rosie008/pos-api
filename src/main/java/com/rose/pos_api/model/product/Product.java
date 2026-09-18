package com.rose.pos_api.model.product;

import com.rose.pos_api.statval.enumeration.EProductCategory;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product", schema = "master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    @Id
    @SequenceGenerator(
            name = "product_seq_gen",
            sequenceName = "product_seq",
            schema = "master",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_seq_gen"
    )
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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
