# Solution

1. Point Docker against minikube: `eval $(minikube -p minikube docker-env)`
1. Build image: `docker build -t service:1 .`
1. Apply [deployment](deployment.yaml): `kubectl apply -f deployment.yaml`

