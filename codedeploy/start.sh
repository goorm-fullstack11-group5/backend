#!/bin/bash

current_date_time() {
    date +%Y-%m-%dT%H:%M:%S
}
echo "$(current_date_time) Starting new application..." >> /home/ubuntu/log/deploy.log
nohup java -jar /home/ubuntu/deployment/*.jar > /home/ubuntu/log/app.log 2>&1 &
echo "$(current_date_time) Application started successfully." >> /home/ubuntu/log/deploy.log
