package com.example.product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return service.getById(id);
    }
}
