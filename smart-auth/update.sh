#!/bin/bash

# start to backup war file in smart-auth server
ssh smart "/home/shell/auth-backup.sh"
if [ $? -eq 0 ]; then
  echo "INFO ： backup war file successful,next to start mvn package."
  ## package smart auth war
  mvn clean package && cd ./target

  if [ $? -eq 0 ]; then
    echo "INFO : mvn package successful ,start to upload war file."
    scp -P22 ./smart-auth-0.0.1-SNAPSHOT.war root@smart:/home/smart/smart-auth/webapps/ROOT
    if [ $? -eq 0 ]; then
      echo " "
      echo "INFO : upload war file successful,please to call server auth-update.sh shell !!!!!"
      if [ $? -eq 0 ]; then
        echo "INFO : udpate auth successful."
      else
        echo "INFO : udpate auth failed."
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
