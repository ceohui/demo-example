package cn.zzh.demo.thread.lock;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 需求：查询缓存
 * <p>
 * 1. 如果有缓存，则直接返回结果；<br>
 * 2. 若缓存过期了，则触发异步更新缓存操作；<br>
 * 3. 每次限定一个线程去数据库回源；<br>
 * 4. 第一次加载，只有第一个线程去加载，其他的线程等待<br>
 */
public class Service {

	private static final Logger LOG = LoggerFactory.getLogger(Service.class);

	public String method1() {
		String result = null;
		synchronized (this) {
//			System.
		}
		return "victorzheng";
	}

	ConcurrentHashMap<Key, String> caches = new ConcurrentHashMap<Key, String>();
	ConcurrentHashMap<Key, Boolean> loadMark = new ConcurrentHashMap<Key, Boolean>();
	ConcurrentHashMap<Key, Boolean> refreshMark = new ConcurrentHashMap<Key, Boolean>();

	ExecutorService asyncRefreshService = new ThreadPoolExecutor(5, 10,
			0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());
	int expire = 5000;//5秒


	public String getMsg(final String name) {
		final Key key = new Key(name);
		String cacheValue = caches.get(key);

		// 第一次加载
		if (cacheValue == null) {
			return lockedGetOrLoad(key, name);
		}
		// 判断cache过期
		long lastWriteTime = key.getWriteTime();
		if (System.currentTimeMillis() - lastWriteTime > expire) {
			// 缓存过期了

			boolean ok = refreshMark.putIfAbsent(key, true);
			if (ok) {
				// 异步刷新
				asyncRefreshService.execute(new Runnable() {
					public void run() {
						lockedGetOrLoad(key, name);
					}
				});
			}
		}

		return cacheValue;
	}

	public String lockedGetOrLoad(Key key, String name) {
		Boolean mark = loadMark.putIfAbsent(key, true);
		LOG.info("lockedGetOrLoad-entry|{}|{}|{}", mark,key.getKeyStr());

		String result = null;

		if (mark == null) {
			LOG.info("lockedGetOrLoad-getLock|{}", key.getKeyStr());

			result = getMsgFromMysql(name);
			key.setWriteTime(System.currentTimeMillis());
			caches.put(key, result);

			loadMark.remove(key);
			LOG.info("lockedGetOrLoad|{}", key.getKeyStr());
		}

		synchronized (result) {
			return result;
		}

	}

	public String getMsgFromMysql(String name) {
		String msg = name + ":" + System.currentTimeMillis();
		LOG.info("getMsgFromMysql-start|{}", msg);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			LOG.error("ex", e);
		}
		LOG.info("getMsgFromMysql-end|{}", msg);
		return msg;
	}


	public static void main(String[] args) {
		final Service service = new Service();

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 2; i++) {
			executorService.execute(new Runnable() {
				public void run() {
					service.getMsg("zzh");
				}
			});
		}

	}

}
