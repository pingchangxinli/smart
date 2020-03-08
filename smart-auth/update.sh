#!/bin/bash

mvn clean  package

if [ $? -eq 0 ]
then
  echo "mvn 执行成功"
  cd ./target
  if [ $? -eq 0 ]
  then
    echo "进入target成功，开始上传"
    scp -P22 ./smart-auth-0.0.1-SNAPSHOT.war root@122.112.245.67:/home/smart/smart-auth/webapps/ROOT
    if [ $? -eq 0 ]
    then
      echo "上传成功"
    else
      echo "上传失败"
    fi
  else
    echo "target目录不存在"
  fi
else
echo "执行失败"
fi