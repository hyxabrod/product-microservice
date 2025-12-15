package com.example.product;

public record ProductDto(
        Double price,
        String model,
        String name,
        String brand
        ) {

    public static ProductDto fromEntity(Product order) {
        return new ProductDto(
                order.getPrice(),
                order.getModel(),
                order.getName(),
                order.getBrand()
        );
    }
}
