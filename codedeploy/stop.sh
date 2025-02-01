#!/bin/bash

echo "Stopping existing application running on port 8080..." > /home/ubuntu/log/deploy.log 2>&1 &
pid=$(lsof -t -i:8080)

if [ -n "$pid" ]; then
  kill -9 $pid
  echo "Stopped process with PID $pid." > /home/ubuntu/log/deploy.log 2>&1 &
else
  echo "No application running on port 8080." > /home/ubuntu/log/deploy.log 2>&1 &
fi
