package com.rose.pos_api.dto.request.product;

import com.rose.pos_api.statval.enumeration.EProductCategory;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestUpdateProductDTO {
    private String name;
    private BigDecimal price;
    private Long stock;
    private String sku;
    private EProductCategory productCategory;
}
