package cn.zzh.demo.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

public class Main {

	private static String ProducterGroup = "demo_test";
	private static String ComsumerGroup = "demo_test_consumer";
	private static String NameSrvAddr = "192.168.6.33:9876";

	/**
	 * 同步发送消息
	 * <p>
	 * 可靠的同步传输用于广泛的场景，如重要的通知消息，短信通知，短信营销系统等。
	 *
	 * @param topic
	 * @param tags
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public SendResult sync(String topic, String tags, String content) throws Exception {
		DefaultMQProducer producer = new DefaultMQProducer(ProducterGroup);
		// Specify name server addresses.
		producer.setNamesrvAddr(NameSrvAddr);
		// Launch the instance.
		producer.start();
		//Create a message instance, specifying topic, tag and message body.
		Message msg = new Message(topic, tags, content.getBytes(RemotingHelper.DEFAULT_CHARSET));
		//Call send message to deliver message to one of brokers.
		SendResult sendResult = producer.send(msg);
		System.out.printf("%s%n", sendResult);
		//Shut down once the producer instance is not longer in use.
		producer.shutdown();

		return sendResult;
	}


	/**
	 * 异步发送消息
	 * <p>
	 * 异步传输通常用于响应时间敏感的业务场景。
	 *
	 * @param topic
	 * @param tags
	 * @param content
	 * @throws Exception
	 */
	public void async(String topic, String tags, String content) throws Exception {
		//Instantiate with a producer group name.
		DefaultMQProducer producer = new DefaultMQProducer(ProducterGroup);
		// Specify name server addresses.
		producer.setNamesrvAddr(NameSrvAddr);
		//Launch the instance.
		producer.start();
		producer.setRetryTimesWhenSendAsyncFailed(0);

		//Create a message instance, specifying topic, tag and message body.
		Message msg = new Message(topic, tags, "OrderID188", content.getBytes(RemotingHelper.DEFAULT_CHARSET));
		producer.send(msg, new SendCallback() {
			@Override
			public void onSuccess(SendResult sendResult) {
				System.out.printf("OK %s %n", sendResult.getMsgId());
			}

			@Override
			public void onException(Throwable e) {
				System.out.printf(" Exception %s %n", e);
				e.printStackTrace();
			}
		});

		//Shut down once the producer instance is not longer in use.
		producer.shutdown();
	}


	/**
	 * 以单向模式发送消息
	 * <p>
	 * 单向传输用于需要中等可靠性的情况，例如日志收集。
	 *
	 * @param topic
	 * @param tags
	 * @param content
	 * @throws Exception
	 */
	public void oneway(String topic, String tags, String content) throws Exception {
		//Instantiate with a producer group name.
		DefaultMQProducer producer = new DefaultMQProducer(ProducterGroup);
		// Specify name server addresses.
		producer.setNamesrvAddr(NameSrvAddr);
		//Launch the instance.
		producer.start();
		//Create a message instance, specifying topic, tag and message body.
		Message msg = new Message(topic, tags, content.getBytes(RemotingHelper.DEFAULT_CHARSET));
		//Call send message to deliver message to one of brokers.
		producer.sendOneway(msg);

		//Shut down once the producer instance is not longer in use.
		producer.shutdown();
	}


	/**
	 * 消费
	 *
	 * @param topic
	 * @throws Exception
	 */
	public void consumer(String topic) throws Exception {
		// Instantiate with specified consumer group name.
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(ComsumerGroup);

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


	public static void main(String[] args) throws Exception {

		String topic = "test_topic";
		String tags = "tag1";

		Main main = new Main();
		main.consumer(topic);

		Thread.sleep(1000);

		System.out.println("发送消息：");
		main.sync(topic, tags, "同步消息:sync msg");
//		main.async(topic, tags, "异步消息:async msg");
		main.oneway(topic, tags, "单向消息:oneway msg");

	}
}
