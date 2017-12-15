package cn.zzh.demo.guava.eventbus;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Executors;

public class AccountManager {

	private static final Logger LOG = LoggerFactory.getLogger(AccountManager.class);


	//	static EventBus eventBus = new EventBus("account");
	static AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(10));


	public void add(long userId, int amount) {
		LOG.info("account add|userId:{}|amount:{}", userId, amount);

		CoinBalanceEvent event = new CoinBalanceEvent();
		event.setUserId(userId);
		event.setAmount(amount);
		event.setTime(new Date());

		eventBus.post(event);
	}

	public void sub(long userId, int amount) {
		LOG.info("account sub|userId:{}|amount:{}", userId, amount);

		AccountBalanceEvent event = new AccountBalanceEvent();
		event.setUserId(userId);
		event.setAmount(amount);
		event.setTime(new Date());

		eventBus.post(event);

	}


	@Subscribe
	public void listen(AccountBalanceEvent event) {
		LOG.info("listen|{}", JSON.toJSONString(event));
	}

	@Subscribe
	@AllowConcurrentEvents
	public  void listenCoin(CoinBalanceEvent event){
		LOG.info("listenCoin|{}", JSON.toJSONString(event));
	}


	public static void main(String[] args) {

		AccountManager accountManager = new AccountManager();

		eventBus.register(accountManager);

		long userId = 10086;
		int amount = 10;
		accountManager.add(userId, amount);
		accountManager.sub(userId, amount);


		int VERSION = 1;
		long timeFlag = System.currentTimeMillis();
		int serverFlag = 1;
		int counter = 1;
		int type = 1;
		long x = (((VERSION << 30 | timeFlag) << 10 | serverFlag) << 12 | counter) << 9 | type;

		System.out.println(VERSION << 3);
		System.out.println(VERSION << 30);
		System.out.println(VERSION << 30 | timeFlag);
		System.out.println(x);


	}

}
