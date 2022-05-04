# Solution

1. Enable [minikube ingress addon](https://kubernetes.io/docs/tasks/access-application-cluster/ingress-minikube/#enable-the-ingress-controller): `minikube addons enable ingress`
1. Apply [service](service.yaml): `kubectl apply -f service.yaml`
1. Apply [ingress](ingress.yaml): `kubectl apply -f ingress.yaml`
1. Find minikube ip: `minikube ip`
1. Open http://$MINIKUBE_IP/ in your browser
