#!/bin/bash
echo "--------start IM server------->>"
export JAVA_HOME=/opt/jdk1.8.0_121
export CLASSPATH=$CLASSPATH:$JAVA_HOME/lib/*.jar
export PATH=.:$JAVA_HOME/bin:$PATH

java -version
# args[] 0:topicName  1:indexName  2:typeName  3:ignoreField
java -cp kafka-es-0.0.1-SNAPSHOT.jar com.bitnei.kafka.consumer.GenerateIndex testTopic test doc 2003,2103,2308,7003,7103