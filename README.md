# Product Microservice

This service is the "Source of Truth" for products in our system.

## Key Responsibilities:
1.  **Product Management**: Allows Admins to create and manage products via REST API (`POST /api/products`).
2.  **Dual Write**: Saves product data to its local PostgreSQL database AND publishes a `ProductEvent` to Kafka.
3.  **Kafka Producer**: Publishes events to the `products.changelog` topic, acting as the data source for other services (CQRS).
