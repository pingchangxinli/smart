#!/bin/bash

# start to backup war file in smart-user server
ssh smart "/home/shell/user-backup.sh"
if [ $? -eq 0 ]; then
  echo "INFO ： backup war file successful,next to start mvn package."
  ## package smart user war
  mvn clean package && cd ./target

  if [ $? -eq 0 ]; then
    echo "INFO : mvn package successful ,start to upload war file."
    scp -P22 ./smart-user-biz-0.0.1-SNAPSHOT.war root@smart:/home/smart/smart-user/webapps/ROOT
    if [ $? -eq 0 ]; then
      echo " "
      echo "INFO : upload war file successful,start to call server user-update.sh shell"
      ssh smart "/home/shell/user-update.sh"
      if [ $? -eq 0 ]; then
        echo "INFO : udpate user successful."
      else
        echo "INFO : udpate user failed."
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
