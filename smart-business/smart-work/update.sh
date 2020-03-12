#!/bin/bash

# start to backup war file in smart-work server
ssh smart "/home/shell/work-backup.sh"
if [ $? -eq 0 ]; then
  echo "INFO ： backup war file successful,next to start mvn package."
  ## package smart work war
  mvn clean package && cd ./target

  if [ $? -eq 0 ]; then
    echo "INFO : mvn package successful ,start to upload war file."
    scp -P22 ./smart-work-0.0.1-SNAPSHOT.war root@smart:/home/smart/smart-work/webapps/ROOT
    if [ $? -eq 0 ]; then
      echo " "
      echo "INFO : upload war file successful,please to call server work-update.sh shell !!!!!"
      if [ $? -eq 0 ]; then
        echo "INFO : udpate work successful."
      else
        echo "INFO : udpate work failed."
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
