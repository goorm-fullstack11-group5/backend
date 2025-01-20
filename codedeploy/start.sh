#!/bin/bash

echo "Starting new application..."
nohup java -jar /home/ubuntu/deployment/*.jar > /home/ubuntu/log/app.log 2>&1 &
echo "Application started successfully."
