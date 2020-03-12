#!/bin/bash

# start to backup war file in smart-eureka server
ssh smart "/home/shell/eureka-backup.sh"
if [ $? -eq 0 ]; then
  echo "INFO ： backup war file successful,next to start mvn package."
  ## package smart eureka war
  mvn clean package && cd ./target

  if [ $? -eq 0 ]; then
    echo "INFO : mvn package successful ,start to upload war file."
    scp -P22 ./smart-eureka-0.0.1-SNAPSHOT.war root@smart:/home/smart/smart-eureka/webapps/ROOT
    if [ $? -eq 0 ]; then
      echo " "
      echo "INFO : upload war file successful,please to call server eureka-update.sh shell !!!!!"
      if [ $? -eq 0 ]; then
        echo "INFO : udpate eureka successful."
      else
        echo "INFO : udpate eureka failed."
      fi
    else
      echo "ERROR: upload war file failed."
    fi
  else
    ehco "ERROR: mvn package failed."
  fi
else
  echo " ERROR: backup origin war file failed."
fi
