#!/bin/bash

echo "Starting new application..." > /home/ubuntu/log/deploy.log 2>&1 &
nohup java -jar /home/ubuntu/deployment/*.jar > /home/ubuntu/log/app.log 2>&1 &
echo "Application started successfully."
