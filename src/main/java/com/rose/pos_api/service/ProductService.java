package com.rose.pos_api.service;

import com.rose.pos_api.dto.request.product.RequestNewProductDTO;
import com.rose.pos_api.dto.request.product.RequestUpdateProductDTO;
import com.rose.pos_api.dto.response.product.ResponseProductDTO;
import com.rose.pos_api.mapper.product.NewProductMapper;
import com.rose.pos_api.mapper.product.ResponseProductMapper;
import com.rose.pos_api.mapper.product.UpdateProductMapper;
import com.rose.pos_api.model.product.Product;
import com.rose.pos_api.repository.IProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;
    private final NewProductMapper newProductMapper;
    private final ResponseProductMapper responseProductMapper;
    private final UpdateProductMapper updateProductMapper;

    @Transactional
    public ResponseProductDTO addNewProduct(RequestNewProductDTO requestNewProductDTO) throws ServiceException {

        if (productRepository.existsBySku(requestNewProductDTO.getSku())) {
            throw new ServiceException(String.format("Item with SKU %s already exists!", requestNewProductDTO.getSku())
            );
        }

        Product product = newProductMapper.convert(requestNewProductDTO);
        return responseProductMapper.convert(productRepository.save(product));
    }

    public ResponseProductDTO findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (Objects.nonNull(product)) {
            return responseProductMapper.convert(product);
        }

        return null;
    }

    @Transactional
    public ResponseProductDTO updateProduct(Long id, RequestUpdateProductDTO requestUpdateProductDTO) throws ServiceException {
        Product product = productRepository.findById(id).orElse(null);
        if (Objects.isNull(product)) {
            throw new ServiceException(String.format("Product with ID %d not found!", id));
        }

        if (!product.getSku().equals(requestUpdateProductDTO.getSku()) && productRepository.existsBySku(requestUpdateProductDTO.getSku())) {
            throw new ServiceException(String.format("Item with SKU %s already exists!", requestUpdateProductDTO.getSku()));
        }

        Product updatedProduct = updateProductMapper.convert(product, requestUpdateProductDTO);
        return responseProductMapper.convert(productRepository.save(updatedProduct));
    }

    @Transactional
    public void deleteById(Long id) throws ServiceException {
        Product product = productRepository.findById(id).orElse(null);
        if (Objects.isNull(product)) {
            throw new ServiceException(String.format("Product with ID %d not found!", id));
        }
        productRepository.delete(product);
    }

}
