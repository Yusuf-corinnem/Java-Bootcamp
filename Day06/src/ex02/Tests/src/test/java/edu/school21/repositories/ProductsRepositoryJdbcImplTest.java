package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private EmbeddedDatabase database;
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "Laptop", 999.99F),
            new Product(2L, "Smartphone", 599.99F),
            new Product(3L, "Headphones", 79.99F),
            new Product(4L, "Power bank", 49.99F),
            new Product(5L, "Charger", 9.99F));

    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "Laptop", 999.99F);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(1L, "Laptop 2", 1499.99F);


    @BeforeEach
    void init() {
        database = new EmbeddedDatabaseBuilder()
                .setName("Store")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScripts("data.sql")
                .build();
    }

    @Test
    public void findById_Test() {
        ProductsRepositoryJdbcImpl repository = new ProductsRepositoryJdbcImpl(database);
        Optional<Product> foundProduct = repository.findById(1L);
        Assertions.assertTrue(foundProduct.isPresent());
        Product product = foundProduct.get();

        compareValues(EXPECTED_FIND_BY_ID_PRODUCT, product);
    }

    @Test
    public void findAll_Test() {
        ProductsRepositoryJdbcImpl repository = new ProductsRepositoryJdbcImpl(database);
        List<Product> products = repository.findAll();

        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS.size(), products.size());

        for (int i = 0; i < EXPECTED_FIND_ALL_PRODUCTS.size(); i++) {
            compareValues(EXPECTED_FIND_ALL_PRODUCTS.get(i), products.get(i));
        }
    }

    @Test
    public void update_Test() {
        ProductsRepositoryJdbcImpl repository = new ProductsRepositoryJdbcImpl(database);
        repository.update(new Product(1L, "Laptop 2", 1499.99F));
        Optional<Product> foundProduct = repository.findById(1L);

        Assertions.assertTrue(foundProduct.isPresent());

        Product product = foundProduct.get();

        compareValues(EXPECTED_UPDATED_PRODUCT, product);
    }

    private void compareValues(Product p1, Product p2) {
        Assertions.assertEquals(p1.getId(), p2.getId());
        Assertions.assertEquals(p1.getName(), p2.getName());
        Assertions.assertEquals(p1.getPrice(), p2.getPrice());
    }
}
