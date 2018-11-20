package cn.zzh.demo.rocketmq.transaction;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class TransactionProducer {

	private static String ProductGroup = "test_group";
	private static String topic = "test_topic";
	private static String NameSrvAddr = "192.168.6.33:9876";

	public static void main(String[] args) throws Exception {

		TransactionListener transactionListener = new TransactionListenerImpl();
		ExecutorService executorService = new ThreadPoolExecutor(
				2,
				5,
				100,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(2000),
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread thread = new Thread(r);
						thread.setName("client-transaction-thread");
						return thread;
					}
				});

		// producer
		TransactionMQProducer producer = new TransactionMQProducer(ProductGroup);
		producer.setNamesrvAddr(NameSrvAddr);
		producer.setExecutorService(executorService);
		producer.setTransactionListener(transactionListener);
		producer.start();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_");
		Date now = new Date();

		// send msg
		for (int i = 0; i < 10; i++) {
			try {
				Message msg = new Message(topic, "tag1", "key1", (sdf.format(now) + "msg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
				SendResult sendResult = producer.sendMessageInTransaction(msg, null);
				System.out.printf("%s%n", sendResult);

				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("send done");

//		for (int i = 0; i < 100000; i++) {
//			Thread.sleep(1000);
//		}
		// producer.shutdown();


	}
}
