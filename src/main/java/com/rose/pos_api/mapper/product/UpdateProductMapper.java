package com.rose.pos_api.mapper.product;

import com.rose.pos_api.dto.request.product.RequestUpdateProductDTO;
import com.rose.pos_api.model.product.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UpdateProductMapper {

    public Product convert(Product product, RequestUpdateProductDTO request) {
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setSku(request.getSku());
        product.setProductCategory(request.getProductCategory());
        product.setUpdatedAt(LocalDateTime.now());
        return product;
    }

}
