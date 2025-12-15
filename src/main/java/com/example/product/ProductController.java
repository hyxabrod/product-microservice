package com.example.product;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/")
public class ProductController {
    @Autowired
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@NonNull @PathVariable Long id) {
        try {
            return repository.findById(id)
                    .<ResponseEntity<?>>map(product -> ResponseEntity.ok(ProductDto.fromEntity(product)))
                    .orElseGet(()
                            -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ErrorResponse("Product not found"))
                    );
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Database access error"));
        }
    }
}
