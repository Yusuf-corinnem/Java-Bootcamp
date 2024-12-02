package edu.school21.repositories;

import edu.school21.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductsRepositoryJdbcImpl(EmbeddedDatabase database) {
        this.jdbcTemplate = new JdbcTemplate(database);
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT *\nFROM products;",
                (rs, rowNum) -> new Product(rs.getLong("id"), rs.getString("name"), rs.getFloat("price")));
    }

    @Override
    public Optional<Product> findById(Long id) {
        List<Product> products = jdbcTemplate.query("SELECT *\nFROM products\nWHERE id = ?;",
                (rs, rowNum) -> new Product(rs.getLong("id"), rs.getString("name"), rs.getFloat("price")), id);

        if (!products.isEmpty()) {
            return Optional.of(products.get(0));
        }

        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        if (product.getName().isEmpty() || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Invalid product data");
        }

        jdbcTemplate.update("UPDATE products\nSET name = ?, price = ?\nWHERE id = ?;",
                product.getName(), product.getPrice(), product.getId());
    }

    @Override
    public void save(Product product) {
        jdbcTemplate.update("INSERT INTO products (id, name, price) VALUES (?, ?, ?)",
                product.getId(), product.getName(), product.getPrice());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM products\nWHERE id = ?;", id);
    }
}
