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
nohup java -Dfile.encoding=UTF8 -Duser.timezone=GMT+08 -Xms500m -Xmx500m -Xmn250m -Xss256k -server -XX:+HeapDumpOnOutOfMemoryError -jar novel_crawler.jar -verbose:class &
echo '结束启动sell测试项目……'
echo $?
exit