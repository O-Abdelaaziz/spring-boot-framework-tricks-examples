package com.integration.test.repository;

import com.integration.test.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Created 09/10/2022 - 13:26
 * @Package com.integration.test.repository
 * @Project integration-test-exemple-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByName(String name);
}