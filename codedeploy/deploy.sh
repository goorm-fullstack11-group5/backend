#!/bin/bash

current_date_time="`date [+%Y-%m-%d %H:%M:%S]`";
echo "$current_date_time Granting execute permissions for new application..." >> /home/ubuntu/log/deploy.log
chmod +x /home/ubuntu/deployment/*.jar
chmod +x /home/ubuntu/deployment/*.sh
echo "$current_date_time Deployment preparation complete." >> /home/ubuntu/log/deploy.log
