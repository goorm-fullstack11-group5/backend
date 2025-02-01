#!/bin/bash

echo "Granting execute permissions for new application..." > /home/ubuntu/log/deploy.log 2>&1 &
chmod +x /home/ubuntu/deployment/*.jar
chmod +x /home/ubuntu/deployment/*.sh
echo "Deployment preparation complete." > /home/ubuntu/log/deploy.log 2>&1 &
