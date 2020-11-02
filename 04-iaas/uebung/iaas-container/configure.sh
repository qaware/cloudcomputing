#!/bin/bash
set -euo pipefail

echo -n "insert your aws_access_key_id:"
read -s accessKey
echo -n "insert your aws_secret_access_key:"
read -s secretKey
echo -n "insert your aws_session_token:"
read -s sessionToken

mkdir -p ~/.aws/

cat > ~/.aws/config <<-EOF
[default]
region = us-east-1
EOF

cat > ~/.aws/credentials <<-EOF
[default]
aws_access_key_id=${accessKey}
aws_secret_access_key=${secretKey}
aws_session_token=${sessionToken}
EOF

echo "Your identitiy:"
aws sts get-caller-identity