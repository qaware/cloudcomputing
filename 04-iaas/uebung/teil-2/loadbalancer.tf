# TODO: Create a Security Group for the Load Balancer
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/security_group
resource "aws_security_group" "lb" {
  name        = "${local.env}-lb"
  description = "Allow TLS inbound traffic"
  vpc_id      = module.vpc.vpc_id
  tags        = local.standard_tags
}

# TODO: Allow incoming traffic on the Load Balancer on port 80 from everywhere 
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/security_group_rule 
resource "aws_security_group_rule" "lb_http" {
  security_group_id = aws_security_group.lb.id
  description       = "Allows HTTP from everywhere"
  type              = "ingress"
  from_port         = 80
  to_port           = 80
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
}

# TODO: Allow outgoing traffic from the LB to everywhere
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/security_group_rule 
resource "aws_security_group_rule" "lb_all_outgoing" {
  security_group_id = aws_security_group.lb.id
  description       = "Allows HTTP to App SG"
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
}


# TODO: Create the Load Balancer
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lb
resource "aws_lb" "app" {
  name               = local.env
  load_balancer_type = "application"
  security_groups    = [aws_security_group.lb.id]
  subnets            = module.vpc.public_subnets
  tags               = local.standard_tags
}

# TODO: Create a target group
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lb_target_group
resource "aws_lb_target_group" "app" {
  name                 = local.env
  port                 = 8080
  protocol             = "HTTP"
  vpc_id               = module.vpc.vpc_id
  deregistration_delay = 60

  health_check {
    interval = 6
    path     = "/"
    port     = "8080"
    protocol = "HTTP"
  }
}

# TODO: Create a listener
# See: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lb_listener
resource "aws_lb_listener" "lb_forward_to_app" {
  load_balancer_arn = aws_lb.app.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.app.arn
  }
}

