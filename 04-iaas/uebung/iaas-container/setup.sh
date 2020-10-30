#!/bin/bash
set -euxo pipefail

apt-get update 
DEBIAN_FRONTEND=noninteractive apt-get install -y -q \
    python3 \
    python3-pip \
    python-is-python3 \
    git \
    unzip \
    curl \
    groff


mkdir bin 
cd bin 


curl "https://releases.hashicorp.com/terraform/0.13.5/terraform_0.13.5_linux_amd64.zip" -o "terraform_0.13.5_linux_amd64.zip" 
unzip terraform_0.13.5_linux_amd64.zip
rm terraform_0.13.5_linux_amd64.zip
./terraform -install-autocomplete

git clone https://github.com/aws/aws-ec2-instance-connect-cli.git 
cd aws-ec2-instance-connect-cli 
pip3 install -r requirements.txt 
pip3 install -e . 
cd - 

curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" 
unzip awscliv2.zip 
rm awscliv2.zip 
./aws/install

cd ~

cat >> /root/.bashrc <<-EOF
    alias mssh=/root/bin/aws-ec2-instance-connect-cli/bin/mssh
    export AWS_DEFAULT_REGION=us-east-1
    complete -C /usr/local/bin/aws_completer aws
    alias terraform=/root/bin/terraform
EOF