package com.integration.test.service.impl;

import com.integration.test.entity.Product;
import com.integration.test.repository.ProductRepository;
import com.integration.test.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Created 09/10/2022 - 13:27
 * @Package com.integration.test.service.impl
 * @Project integration-test-exemple-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Service
@Transactional
@Slf4j
public class ProductService implements IProductService {

    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getProducts() {
        return repository.findAll();
    }
     @Override
    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }
    @Override
    public Product getProductByName(String name) {
        return repository.findByName(name);
    }
    @Override
    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }
    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }
    @Override
    public Product updateProduct(Long productId, Product product) {
        Product existingProduct = repository.findById(productId).orElse(null);
        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        return repository.save(existingProduct);
    }
    @Override
    public String deleteProduct(Long id) {
        repository.deleteById(id);
        return "product removed !! " + id;
    }


}
