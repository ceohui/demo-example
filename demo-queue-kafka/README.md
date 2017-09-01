## demo-queue-kafka:kafka消息队列
官网：http://kafka.apache.org/documentation/#gettingStarted

命令行操作：
```
# 创建topic
sh bin/kafka-topics.sh --create --zookeeper 172.17.11.161:2182 --replication-factor 1 --partitions 1 --topic zpt

# 查看topic
sh bin/kafka-topics.sh --list --zookeeper 172.17.11.161:2182

# 发送消息
sh bin/kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic zpt

# 接收
sh bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic zpt --from-beginning

# desc
sh bin/kafka-topics.sh --describe --zookeeper 172.17.11.161:2182 --topic zpt
```