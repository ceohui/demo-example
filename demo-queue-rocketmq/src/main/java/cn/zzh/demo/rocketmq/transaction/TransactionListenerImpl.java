package cn.zzh.demo.rocketmq.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionListenerImpl implements TransactionListener {

	private static final Logger LOG = LoggerFactory.getLogger(TransactionListenerImpl.class);

	private AtomicInteger transactionIdx = new AtomicInteger(0);

	private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();


	/**
	 * 本地事务执行器
	 *
	 * @param message
	 * @param o
	 * @return
	 */
	@Override
	public LocalTransactionState executeLocalTransaction(Message message, Object o) {

		int value = transactionIdx.getAndIncrement();
		int status = value % 3;
		localTrans.put(message.getTransactionId(), status);

		LOG.info("executeLocalTransaction|txId:{}|status:{}", message.getTransactionId(), status);

		return LocalTransactionState.UNKNOW;
	}

	/**
	 * 回查
	 *
	 * @param messageExt
	 * @return
	 */
	@Override
	public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {

		Integer status = localTrans.get(messageExt.getTransactionId());

		LOG.info("checkLocalTransaction|txId:{}|stauts:{}|msg:{}", messageExt.getTransactionId(), status, new String(messageExt.getBody()));

		if (null != status) {
			switch (status) {
				case 0:
					return LocalTransactionState.UNKNOW;
				case 1:
					return LocalTransactionState.COMMIT_MESSAGE;
				case 2:
					return LocalTransactionState.ROLLBACK_MESSAGE;
			}
		}

		return LocalTransactionState.COMMIT_MESSAGE;
	}
}
