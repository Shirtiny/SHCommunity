#!/bin/sh
#$1为第一个参数 版本号 比如v12
#更新代码库
git pull
#maven打包
mvn clean compile package
#拷贝dockerfile
cd target/
cp ../Dockerfile ./
#构建镜像
docker build -t registry.cn-hongkong.aliyuncs.com/shirtinycn/shcommunity:$1 ./
#登陆阿里云
sudo docker login --username=shirtiny registry.cn-hongkong.aliyuncs.com
#推送镜像
docker push registry.cn-hongkong.aliyuncs.com/shirtinycn/shcommunity:$1
#展示镜像列表
docker images
#停止上一个版本的容器
docker stop shcommunity
#删除上一个版本的容器
docker rm shcommunity
#运行镜像
docker run -d --restart=always --name shcommunity -p 8888:8888 registry.cn-hongkong.aliyuncs.com/shirtinycn/shcommunity:$1
#展示当前运行的容器
docker ps
