#!/bin/bash
echo "--------start IM server------->>"
export JAVA_HOME=/opt/jdk1.8.0_121
export CLASSPATH=$CLASSPATH:$JAVA_HOME/lib/*.jar
export PATH=.:$JAVA_HOME/bin:$PATH

java -version
java -cp kafka-es-0.0.1-SNAPSHOT.jar com.bitnei.kafka.producer.Producer