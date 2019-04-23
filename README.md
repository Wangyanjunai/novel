nohup java -Dfile.encoding=UTF8 -Duser.timezone=GMT+08 -Xms500m -Xmx500m -Xmn250m -Xss256k -server -XX:+HeapDumpOnOutOfMemoryError -jar novel_crawler.jar -verbose:class &
