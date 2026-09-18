package com.rose.pos_api.repository;

import com.rose.pos_api.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {

    Boolean existsBySku(String sku);
}
