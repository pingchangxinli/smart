#!/bin/bash

ssh root@122.112.245.67
mvn clean  package

if [ $? -eq 0 ]
then
  echo "mvn 执行成功"
  cd ./target
  if [ $? -eq 0 ]
  then
    echo "生成WAR成功，进入服务器，备份原始WAR文件"
    currentTime=$(date "+%Y-%m-%d %H:%M:%S")

    cd /home/smart/smart-auth/webapps/ROOT
    cp smart-user-biz-0.0.1-SNAPSHOT.war ../smart-user-biz-0.0.1-SNAPSHOT_${currentTime}.war
    if [ $? -eq 0 ]
    then
      echo "备份成功，开始上传"
      scp -P22 ./smart-user-biz-0.0.1-SNAPSHOT.war root@122.112.245.67:/home/smart/smart-user/webapps/ROOT
      if [ $? -eq 0 ]
      then
        echo "上传成功"
      else
        echo "上传失败"
      fi
    else
      echo "备份原始WAR文件失败"
    fi
  else
    echo "target目录不存在"
  fi
else
echo "执行失败"
fi