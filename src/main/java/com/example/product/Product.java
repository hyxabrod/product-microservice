package com.example.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private String model;
    private String name;
    private String brand;

    protected Product() {
    }

    public Product(Double price, String model, String name, String brand) {
        this.price = price;
        this.model = model;
        this.name = name;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
