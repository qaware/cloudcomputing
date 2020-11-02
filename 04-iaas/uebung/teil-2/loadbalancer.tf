# TODO: Create a Security Group for the Load Balancer
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/security_group

# TODO: Allow incoming traffic on the Load Balancer on port 80 from everywhere 
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/security_group_rule 

# TODO: Allow outgoing traffic from the LB to everywhere
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/security_group_rule 


# TODO: Create the Load Balancer
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lb

# resource "aws_lb" "app" {
#   name               =
#   load_balancer_type = "application"
#   security_groups    = 
#   subnets            = 
#   tags               = local.standard_tags
# }

# TODO: Create a target group
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lb_target_group

# resource "aws_lb_target_group" "app" {
#   name                 = 
#   port                 = 
#   protocol             = 
#   vpc_id               = 
#   deregistration_delay = 60

#   health_check {
#     interval = 6
#     path     = 
#     port     = 
#     protocol = 
#   }
# }

# TODO: Create a listener
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lb_listener

# resource "aws_lb_listener" "lb_forward_to_app" {
#   ...
#   default_action {
#   }
# }

