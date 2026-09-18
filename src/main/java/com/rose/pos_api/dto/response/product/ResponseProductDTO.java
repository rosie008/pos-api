package com.rose.pos_api.dto.response.product;

import com.rose.pos_api.statval.enumeration.EProductCategory;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ResponseProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long stock;
    private String sku;
    private EProductCategory productCategory;
}
