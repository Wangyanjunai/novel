#!/usr/bin/env bash
echo '开始更新和发布慕课网点餐系统项目后端源代码……'
cd /opt/code/sell_fe_seller/sell/
echo '正在更新后端源代码……'
svn update
echo '更新后端源代码完毕……'
echo '开始打包后端源代码……'
mvn clean package -Dmaven.test.skip=true
echo '结束打包后端源代码……'
echo '开始启动natapp内网穿透工具……'
cd ../
kill -9 `pidof natapp`
nohup ./natapp -authtoken=99f7af6e31464c91 -log=stdout -loglevel=ERROR &
echo '结束启动natapp内网穿透工具……'
echo '开始启动sell测试项目……'
cd ./sell/target/
echo '启动sell测试项目中……'
kill -9 `pidof java`
nohup java -Dhttps.protocols=TLSv1,TLSv1.1,TLSv1.2 -Dfile.encoding=UTF8 -Duser.timezone=GMT+08 -Xms128m -Xmx256m -Xmn256m -Xss256k -server -XX:+HeapDumpOnOutOfMemoryError -XX:PermSize=64m -XX:MaxPermSize=256m -jar novel_crawler.jar --spring.profiles.active=prod2 -verbose:class &
echo '结束启动sell测试项目……'
echo $?
exit