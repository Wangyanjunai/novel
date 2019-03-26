#!/usr/bin/env bash
echo '开始更新和发布小说电子书Web在线阅读项目前端源代码……'
cd /root/potato369-ebook/vue-novel-ebook
echo '正在更新前端源代码……'
git pull
echo '开始更新node_modules依赖的版本库……'
cnpm install
echo '结束更新node_modules依赖的版本库……'
echo '开始构建前端代码……'
cnpm run build
echo '开始发布前端代码到本地Nginx服务器……'
rm -rf /ebookData/upgrade_file/http/Resources/ebook
mv dist /ebookData/upgrade_file/http/Resources/ebook
echo '结束更新和发布小说电子书Web在线阅读项目前端源代码……'
echo '开始更新和发布小说电子书Web在线阅读项目后端源代码……'
cd /root/potato369-ebook/springboot-novel-ebook
echo '正在更新后端源代码……'
git pull
echo '更新后端源代码完毕……'
echo '开始打包后端源代码……'
mvn clean package -Dmaven.test.skip=true
echo '结束打包后端源代码……'
echo '开始删除服务器后端打包的novel.war项目包……'
rm -rf /usr/local/apache-tomcat/webapps/novel.war
rm -rf /usr/local/apache-tomcat/webapps/novel
echo '结束删除服务器后端打包的novel.war项目包……'
echo '开始发布后端打包的novel.war项目包到tomcat服务器……'
mv ./target/novel.war /usr/local/apache-tomcat/webapps/
echo '结束发布后端打包的novel.war项目包到tomcat服务器……'
echo '结束更新和发布小说电子书Web在线阅读项目后端源代码……'
echo $?
echo '开始重启tomcat服务器……'
systemctl restart tomcat.service
echo '结束重启tomcat服务器……'
exit