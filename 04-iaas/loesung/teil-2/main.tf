provider aws {
  version = "3.12.0"
  region  = "us-east-1"
}

provider random {
  version = "3.0.0"
}

resource random_string id_suffix {
  length  = 4
  special = false
  upper   = false
}

locals {
  # use this variable as prefix for all resource names. This avoids conflicts with globally unique resources (all resources with a hostname)
  env = "${terraform.workspace}-${random_string.id_suffix.result}"

  # use this map to apply env-specific values for certain components.
  env_config = {
    stg = {
    }
    dev = {
      message = "Hello Workspaces!"
    }
    default = {
      message = "Hello World!"
    }
  }
  config = merge(local.env_config["default"], lookup(local.env_config, terraform.workspace, {}))

  # tag all resources at least with these tags
  # allows filtering in AWS and distinction between environments
  standard_tags = {
    "environment" = local.env
  }

  standard_tags_asg = [for key in keys(local.standard_tags) : {
    key                 = key
    value               = lookup(local.standard_tags, key)
    propagate_at_launch = "true"
  }]
}