nohup java -Dfile.encoding=UTF8 -Duser.timezone=GMT+08 -Xms125m -Xmx125m -Xmn250m -Xss256k -server -XX:+HeapDumpOnOutOfMemoryError -jar crawler.jar -verbose:class &
