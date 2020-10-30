
# TODO: Define an output for the LoadBalancer URL
output "load_balancer_url" {
    value = "http://${aws_lb.app.dns_name}"
}