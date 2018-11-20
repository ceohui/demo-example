package cn.zzh.demo.rocketmq.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class TransactionConsumer {

	private static String NameSrvAddr = "192.168.6.33:9876";
	private static String topic = "test_topic";

	public static void main(String[] args) throws Exception{
		// Instantiate with specified consumer group name.
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test_group");

		// Specify name server addresses.
		consumer.setNamesrvAddr(NameSrvAddr);

		// Subscribe one more more topics to consume.
		consumer.subscribe(topic, "*");
		// Register callback to execute on arrival of messages fetched from brokers.
		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
															ConsumeConcurrentlyContext context) {
				System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);

				if (msgs != null && !msgs.isEmpty()) {
					for (MessageExt msg : msgs) {
						System.out.println(new String(msg.getBody()));
					}
				}

				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		//Launch the consumer instance.
		consumer.start();

		System.out.printf("Consumer Started.%n");
	}
}
