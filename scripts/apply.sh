./gradlew clean build
eval $(minikube docker-env)
docker build -t product-service:1.0 .
kubectl delete pod -l app=product-service
kubectl apply -f k8s-product.yaml
kubectl get pods -l app=product-service