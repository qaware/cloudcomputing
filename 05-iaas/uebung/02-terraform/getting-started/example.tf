provider "aws" {
  region     = "eu-central-1"
}

resource "aws_instance" "cc2017-example" {
  ami           = "ami-97e953f8"
  instance_type = "t2.micro"
}
