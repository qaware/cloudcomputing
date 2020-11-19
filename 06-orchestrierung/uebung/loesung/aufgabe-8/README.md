# Solution

1. Add bitnami repo: `helm repo add bitnami https://charts.bitnami.com/bitnami`
1. Install redis with 3 slaves: `helm install redis bitnami/redis --set cluster.slaveCount=3`
1. Store redis password in environment variable: `export REDIS_PASSWORD=$(kubectl get secret --namespace default redis -o jsonpath="{.data.redis-password}" | base64 --decode)`
1. Spawn pod into cluster for redis-cli: `kubectl run --namespace default redis-client --rm --tty -i --restart='Never' --env REDIS_PASSWORD=$REDIS_PASSWORD --image docker.io/bitnami/redis:6.0.9-debian-10-r13 -- bash`
1. Connect to redis master: `redis-cli -h redis-master -a $REDIS_PASSWORD`
1. Create key-value pair: `SET foo bar`
1. List all keys: `KEYS *`
1. Exit shell
1. Uninstall redis: `helm uninstall redis`
