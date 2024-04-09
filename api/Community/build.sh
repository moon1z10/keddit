#!/bin/bash

# Define the path to the aws_add_keys.sh script
AWS_ADD_KEYS_SCRIPT=~/git/aws_add_keys.sh

# Check if the aws_add_keys.sh script exists and is executable
if [ -x "$AWS_ADD_KEYS_SCRIPT" ]; then
    # Call the aws_add_keys.sh script
    $AWS_ADD_KEYS_SCRIPT
else
    echo "Error: aws_add_keys.sh does not exist or is not executable."
    exit 1
fi

