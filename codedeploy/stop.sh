#!/bin/bash
current_date_time="`date [+%Y-%m-%d %H:%M:%S]`";
echo "$current_date_time Stopping existing application running on port 8080..." >> /home/ubuntu/log/deploy.log
pid=$(lsof -t -i:8080)

if [ -n "$pid" ]; then
  kill -9 $pid
  echo "$current_date_time Stopped process with PID $pid." >> /home/ubuntu/log/deploy.log
else
  echo "$current_date_time No application running on port 8080." >> /home/ubuntu/log/deploy.log
fi
