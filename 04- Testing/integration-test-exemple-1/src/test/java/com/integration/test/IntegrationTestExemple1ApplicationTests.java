package com.integration.test;

import com.integration.test.entity.Product;
import com.integration.test.service.IProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTestExemple1ApplicationTests {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    private IProductService iProductService;

    @Autowired
    public IntegrationTestExemple1ApplicationTests(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setup() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/products");
    }

    @Test
    @Sql(statements = "INSERT INTO PRODUCTS (id,name, quantity, price) VALUES (4,'SAMSUNG', 1, 34000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM PRODUCTS WHERE name='SAMSUNG'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetProducts() {
        List<Product> productList = restTemplate.getForObject(baseUrl, List.class);
        assertEquals(1, productList.size());
        assertEquals(1, iProductService.getProducts().size());
    }

    @Test
    @Sql(statements = "INSERT INTO PRODUCTS (id,name, quantity, price) VALUES (6,'Acer', 1, 84000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM PRODUCTS WHERE id=6", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetProductById() {
        Product product = restTemplate.getForObject(baseUrl + "/{id}", Product.class, 6);
        assertAll(
                () -> assertNotNull(product),
                () -> assertEquals(6L, product.getId()),
                () -> assertEquals("Acer", product.getName())
        );
        assertEquals(6, iProductService.getProductById(6L).getId());
    }

    @Test
    public void testAddProduct() {
        Product savedProduct = new Product("IPhone", 20, 9899);
        restTemplate.postForObject(baseUrl, savedProduct, Product.class);
        assertEquals("IPhone", savedProduct.getName());
        assertEquals(1, iProductService.getProducts().size());
    }

    @Test
    @Sql(statements = "INSERT INTO PRODUCTS (id,name, quantity, price) VALUES (3,'Laptop',22, 96000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM PRODUCTS WHERE id=3", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateProduct() {
        Product updatedProduct = new Product("Laptop HD", 5, 98000);
        restTemplate.put(baseUrl + "/update/{id}", updatedProduct, 3);
        Product productFromDB = iProductService.getProductById(3L);
        assertAll(
                () -> assertNotNull(productFromDB),
                () -> assertEquals(98000, productFromDB.getPrice()),
                () -> assertEquals("Laptop HD", productFromDB.getName())
        );
    }

    @Test
    @Sql(statements = "INSERT INTO PRODUCTS (id,name, quantity, price) VALUES (3,'Laptop',22, 96000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteProduct() {
//        Product fetchedProduct = iProductService.getProductById(3L);
//        restTemplate.delete(baseUrl + "/delete/{id}", fetchedProduct.getId());
//        Product productFromDB = iProductService.getProductById(3L);
//        assertNull(productFromDB);
        Long count = iProductService.getProducts().stream().count();
        assertEquals(1, count);
        restTemplate.delete(baseUrl + "/delete/{id}", 3);
        assertEquals(0, iProductService.getProducts().size());
    }

}
