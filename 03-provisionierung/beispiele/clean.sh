#!/bin/bash
# this script deletes the created image if it is tagged with 'purpose: demo'
set -e
set -u
set -o pipefail

aws ec2 describe-images --owners self --filters "Name=tag:purpose,Values=demo" --query 'Images[].ImageId' --output text | xargs aws ec2 deregister-image --image-id
