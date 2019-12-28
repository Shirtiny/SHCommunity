#!/bin/sh
#更新代码库
git pull
#maven打包
mvn clean compile package
#拷贝dockerfile
cd target/
cp ../Dockerfile ./
#构建镜像
docker build -t registry.cn-hongkong.aliyuncs.com/shirtinycn/shcommunity:v11 ./
#登陆阿里云
sudo docker login --username=shirtiny registry.cn-hongkong.aliyuncs.com
#推送镜像
docker push registry.cn-hongkong.aliyuncs.com/shirtinycn/shcommunity:v11
