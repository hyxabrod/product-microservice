# product-microservice

Spring Boot service that exposes products through a REST API.

## Development
```
./gradlew bootRun
```

## Docker
```
docker build -t product-service:latest .
```

## Kubernetes
Apply `k8s-product.yaml` once you update the image reference.
