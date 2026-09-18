package com.rose.pos_api.service;

import com.rose.pos_api.dto.request.product.RequestNewProductDTO;
import com.rose.pos_api.dto.response.product.ResponseProductDTO;
import com.rose.pos_api.mapper.product.NewProductMapper;
import com.rose.pos_api.mapper.product.ResponseProductMapper;
import com.rose.pos_api.model.product.Product;
import com.rose.pos_api.repository.IProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;
    private final NewProductMapper newProductMapper;
    private final ResponseProductMapper responseProductMapper;

    @Transactional
    public ResponseProductDTO addNewProduct(RequestNewProductDTO requestNewProductDTO)throws ServiceException {
        Product product = newProductMapper.convert(requestNewProductDTO);
        return responseProductMapper.convert(productRepository.save(product));
    }


}
