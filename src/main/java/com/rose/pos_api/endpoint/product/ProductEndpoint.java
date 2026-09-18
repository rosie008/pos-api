package com.rose.pos_api.endpoint.product;

import com.rose.pos_api.dto.request.product.RequestNewProductDTO;
import com.rose.pos_api.dto.response.product.ResponseProductDTO;
import com.rose.pos_api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductEndpoint {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> addNewProduct(@Valid @RequestBody RequestNewProductDTO requestNewProductDTO) {
        try {
            ResponseProductDTO response = productService.addNewProduct(requestNewProductDTO);
            return ResponseEntity.ok(response);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
