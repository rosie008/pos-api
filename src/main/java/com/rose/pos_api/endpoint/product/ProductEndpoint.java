package com.rose.pos_api.endpoint.product;

import com.rose.pos_api.dto.request.product.RequestNewProductDTO;
import com.rose.pos_api.dto.request.product.RequestUpdateProductDTO;
import com.rose.pos_api.dto.response.product.ResponseProductDTO;
import com.rose.pos_api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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

    @GetMapping("/{id}")
    private ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            ResponseProductDTO response = productService.findById(id);
            if (Objects.nonNull(response)) {
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.noContent().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody RequestUpdateProductDTO requestUpdateProductDTO) {
        try {
            ResponseProductDTO response = productService.updateProduct(id, requestUpdateProductDTO);
            if (Objects.nonNull(response)) {
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.noContent().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
