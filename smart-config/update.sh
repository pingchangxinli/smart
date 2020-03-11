#!/bin/bash

# start to backup war file in smart-config server
ssh smart "/home/shell/config-backup.sh"
if [ $? -eq 0 ]; then
  echo "INFO ： backup war file successful,next to start mvn package."
  ## package smart config war
  mvn clean package && cd ./target

  if [ $? -eq 0 ]; then
    echo "INFO : mvn package successful ,start to upload war file."
    scp -P22 ./smart-config-0.0.1-SNAPSHOT.war root@smart:/home/smart/smart-config/webapps/ROOT
    if [ $? -eq 0 ]; then
      echo " "
      echo "INFO : upload war file successful,please to call server config-update.sh shell !!!!!"
      if [ $? -eq 0 ]; then
        echo "INFO : udpate config successful."
      else
        echo "INFO : udpate config failed."
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
