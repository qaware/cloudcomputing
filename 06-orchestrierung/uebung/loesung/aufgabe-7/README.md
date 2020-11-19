# Solution

1. Apply [persistent volume](pv.yaml): `kubectl apply -f pv.yaml`
1. Apply [deployment](deployment.yaml): `kubectl apply -f deployment.yaml`
1. Open a shell in the running pod with k9s
1. `cd /data`
1. `touch foo`
1. Exit shell, kill the pod with k9s
1. Open a shell in the running pod with k9s
1. `cd /data`
1. `ls -la` - The file is still there
