## demo-queue-kafka:kafka消息队列
官网：http://kafka.apache.org/documentation/#gettingStarted

命令行操作：
```

/usr/local/kafka/bin


# 启动kafka
sh bin/kafka-server-start.sh -daemon ../config/server.properties 


# 创建topic
sh bin/kafka-topics.sh --create --zookeeper 172.17.11.161:2182 --replication-factor 1 --partitions 1 --topic zpt

# 查看所有topic
sh bin/kafka-topics.sh --list --zookeeper 172.17.11.161:2182

# 查看topic详情
sh bin/kafka-topics.sh -zookeeper 127.0.0.1:2181 -describe -topic zpt

# 发送消息
sh bin/kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic zpt

# 接收
sh bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic zpt --from-beginning

sh bin/kafka-console-consumer.sh --bootstrap-server 	kafka0.lizhif.fm:9092 --topic lz_topic_security_account_risk


# 查看consumer组内消费的offset
sh bin/kafka-run-class.sh kafka.tools.ConsumerOffsetChecker --zookeeper zookeeper0.lizhi.fm:2182 --group test --topic testKJ1
sh bin/kafka-consumer-offset-checker.sh --zookeeper kafka_zk1.lzfm.com:2182 --group group1 --topic group1

# 为topic增加partition
sh bin/kafka-topics.sh –zookeeper 127.0.0.1:2181 –alter –partitions 20 –topic testKJ1

# 增加副本 
sh bin/kafka-reassign-partitions.sh -zookeeper 127.0.0.1:2181 -reassignment-json-file json/partitions-to-move.json -execute


# 查询consumer列表
sh bin/kafka-consumer-groups.sh --zookeeper kafka_zk1.lzfm.com:2182 --list


```