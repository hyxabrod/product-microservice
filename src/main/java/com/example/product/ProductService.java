package com.example.product;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.contracts.products.ProductEvent;
import com.example.product.kafka.ProductEventsProducer;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductEventsProducer productEventsProducer;

    public ProductService(ProductRepository productRepository,
            ProductEventsProducer productEventsProducer) {
        this.productRepository = productRepository;
        this.productEventsProducer = productEventsProducer;
    }

    public Product createProduct(ProductDto dto) {
        Product product = new Product();
        product.setBrand(dto.brand());
        product.setModel(dto.model());
        product.setPrice(dto.price());

        Product saved = productRepository.save(product);

        ProductEvent event = new ProductEvent(
                saved.getId(),
                saved.getBrand(),
                saved.getModel(),
                saved.getPrice());

        productEventsProducer.publish(event);

        return saved;
    }

    public List<Product> getAllFromDb() {
        return productRepository.findAll();
    }

    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return productRepository.findById(id)
                    .<ResponseEntity<?>>map(product -> ResponseEntity.ok(ProductDto.fromEntity(product)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ErrorResponse("Product not found")));
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Database access error"));
        }
    }
}
