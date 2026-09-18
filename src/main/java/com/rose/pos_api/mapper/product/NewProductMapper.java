package com.rose.pos_api.mapper.product;

import com.rose.pos_api.dto.request.product.RequestNewProductDTO;
import com.rose.pos_api.model.product.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NewProductMapper {

    public Product convert(RequestNewProductDTO request) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .productCategory(request.getProductCategory())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
