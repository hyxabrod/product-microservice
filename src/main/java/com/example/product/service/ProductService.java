package com.example.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.contracts.products.ProductEvent;
import com.example.product.dto.ProductDto;
import com.example.product.kafka.ProductEventsProducer;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;

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

    public Optional<Product> getById(int id) {
        return productRepository.findById(id);
    }
}
