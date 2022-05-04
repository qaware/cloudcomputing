# Solution

I added a piece of code to read the greeting from the environment variable `GREETING` to the [IndexController](IndexController.java). 

1. Build with Maven: `./mvnw clean package`
1. Point Docker against minikube: `eval $(minikube -p minikube docker-env)`
1. Build image: `docker build -t service:3 .`
1. Apply [config map](config.yaml): `kubectl apply -f config.yaml`
1. Apply [deployment](deployment.yaml): `kubectl apply -f deployment.yaml`
1. Find minikube ip: `minikube ip`
1. Open http://$MINIKUBE_IP/ in your browser
