#!/bin/bash
set -euxo pipefail

apt-get update 
DEBIAN_FRONTEND=noninteractive apt-get install -y -q \
    python3 \
    python3-pip \
    python-is-python3 \
    git \
    curl \
    unzip \
    groff

mkdir bin 
cd bin 

git clone https://github.com/aws/aws-ec2-instance-connect-cli.git 
cd aws-ec2-instance-connect-cli 
pip3 install -r requirements.txt 
pip3 install -e . 
cd - 

curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" 
unzip awscliv2.zip 
rm awscliv2.zip 
./aws/install

echo "alias mssh=/home/bin/aws-ec2-instance-connect-cli/bin/mssh" >> /root/.bashrc
echo "export AWS_DEFAULT_REGION=us-east-1" >> /root/.bashrc
echo "complete -C /usr/local/bin/aws_completer aws" >> /root/.bashrc
