package com.example.product;

public class ProductResponse {

    private final Double price;
    private final String model;
    private final String name;
    private final String brand;

    public ProductResponse(Double price, String model, String name, String brand) {
        this.price = price;
        this.model = model;
        this.name = name;
        this.brand = brand;
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
}
