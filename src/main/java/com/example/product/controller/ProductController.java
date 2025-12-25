package com.example.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.dto.ErrorResponse;
import com.example.product.dto.ProductDto;
import com.example.product.dto.ProductResponse;
import com.example.product.model.Product;
import com.example.product.service.ProductService;

@RestController
@RequestMapping("/api/products/")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductDto dto) {
        Product saved = service.createProduct(dto);
        ProductResponse response = new ProductResponse(
                saved.getId(),
                saved.getBrand(),
                saved.getModel(),
                saved.getPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/db")
    public ResponseEntity<List<ProductResponse>> getAllFromDb() {
        List<ProductResponse> list = service.getAllFromDb()
                .stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getBrand(),
                        p.getModel(),
                        p.getPrice()))
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return service.getById(id)
                    .<ResponseEntity<?>>map(product -> ResponseEntity.ok(ProductDto.fromEntity(product)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ErrorResponse("Product not found")));
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Database access error"));
        }
    }
}
