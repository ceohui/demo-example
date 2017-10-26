package cn.zzh.demo.concurrent.executor;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorMain {

	private static final Logger LOG = LoggerFactory.getLogger(ExecutorMain.class);

	/**
	 * 自定义执行器线程池
	 *
	 * @return
	 */
	public static ExecutorService customExecutor(int poolSize, int queueSize) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize, poolSize, 100,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize),
				new ThreadFactory() {

					private AtomicInteger ids = new AtomicInteger(0);

					@Override
					public Thread newThread(Runnable r) {
						Thread thread = new Thread(r);
						thread.setDaemon(true);
						thread.setName("job-processor-" + ids.getAndIncrement());
						return thread;
					}
				},
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						String msg = "Task " + r.toString() +
								" rejected from " +
								executor.toString();
						LOG.error(msg);
					}
				});
		return executor;
	}

	/**
	 * 自定义定时执行器线程池
	 *
	 * @param poolSize
	 * @return
	 */
	public static ScheduledExecutorService customScheduledExecutor(int poolSize) {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(poolSize,
				new ThreadFactory() {

					private AtomicInteger ids = new AtomicInteger(0);

					@Override
					public Thread newThread(Runnable r) {
						Thread thread = new Thread(r);
						thread.setName("scheduled-thread-" + ids.getAndIncrement());
						return thread;
					}
				},
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						String msg = "Task " + r.toString() +
								" rejected from " +
								executor.toString();
						LOG.error(msg);
					}
				}
		);

		return executor;
	}


	public static ExecutorService newFixExecutor(int poolSize) {
		ExecutorService executor = Executors.newFixedThreadPool(poolSize);
		return executor;
	}

	public static ExecutorService newCacheExecutor() {
		ExecutorService executor = Executors.newCachedThreadPool();
		return executor;
	}

	public static ExecutorService newScheduledExecutor(int poolSize) {
		return Executors.newScheduledThreadPool(poolSize);
	}

	public static ScheduledExecutorService newSingleScheduledExecutor() {
		return Executors.newSingleThreadScheduledExecutor();
	}


	public static void main(String[] args) throws InterruptedException {

		ExecutorService executor = customExecutor(2, 10);

		for (int i = 0; i < 10; i++) {
			final int flag = i;
			System.out.println("start run-" + flag);
			try {
				executor.submit(new Runnable() {
					@Override
					public void run() {
						System.out.println("ok run-" + flag);
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
						}
					}
				});


				Future<String> future = executor.submit(new Callable<String>() {
					@Override
					public String call() throws Exception {
						return "futuretask name: victorzheng-" + flag;
					}
				});

				Object ret = future.get();
				System.out.println(JSON.toJSONString(ret, true));


			} catch (Exception e) {
				System.out.println("===>e:" + e.getMessage());
				e.printStackTrace();
			}
			TimeUnit.SECONDS.sleep(1);
		}


		// 关闭线程池
		// 1.拒绝再提交任务
		executor.shutdown();
		// 2.等待线程池中的任务执行结束或者中断
		if (executor.awaitTermination(100, TimeUnit.SECONDS)) {
			// 3.试图停止正在执行的任务，并返回尚未执行的task的list
			executor.shutdown();
		}

	}

	/**
	 * 线程工厂
	 */
	static class MyThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			return null;
		}
	}

	/**
	 * 拒绝处理
	 */
	class rejectedHandler implements RejectedExecutionHandler {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			throw new RejectedExecutionException("Task " + r.toString() +
					" rejected from " +
					executor.toString());
		}
	}

}
