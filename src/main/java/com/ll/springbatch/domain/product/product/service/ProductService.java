package com.ll.springbatch.domain.product.product.service;


import com.ll.springbatch.domain.product.product.entity.Product;
import com.ll.springbatch.domain.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public void create(String name) {
        Product product = Product
                .builder()
                .name(name)
                .build();

        productRepository.save(product);
    }
}