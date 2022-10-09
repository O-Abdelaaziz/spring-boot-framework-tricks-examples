package com.integration.test.service;

import com.integration.test.entity.Product;

import java.util.List;

/**
 * @Created 09/10/2022 - 13:27
 * @Package com.integration.test.service
 * @Project integration-test-exemple-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
public interface IProductService {
    List<Product> getProducts();

    Product getProductById(Long id);

    Product getProductByName(String name);

    List<Product> saveProducts(List<Product> products);

    Product saveProduct(Product product);

    Product updateProduct(Long productId, Product product);

    String deleteProduct(Long id);
}
