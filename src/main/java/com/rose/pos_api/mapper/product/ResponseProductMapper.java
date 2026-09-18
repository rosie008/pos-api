package com.rose.pos_api.mapper.product;

import com.rose.pos_api.dto.response.product.ResponseProductDTO;
import com.rose.pos_api.model.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ResponseProductMapper {

    public ResponseProductDTO convert (Product product){
        return ResponseProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productCategory(product.getProductCategory())
                .sku(product.getSku())
                .stock(product.getStock())
                .build();
    }
}
