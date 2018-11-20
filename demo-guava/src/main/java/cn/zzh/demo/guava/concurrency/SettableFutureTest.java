package cn.zzh.demo.guava.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SettableFutureTest {


	public static void main(String[] args) throws InterruptedException {

		LoadFromRemote mark = new LoadFromRemote();

		ExecutorService executor = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 10; i++) {
			executor.submit(new TestTask(i, mark));
		}

		Thread.sleep(10000);
		System.out.println("end");

	}

	static class TestTask implements Runnable {

		private int i;
		private LoadFromRemote mark;

		public TestTask(int i, LoadFromRemote mark) {
			this.i = i;
			this.mark = mark;
		}

		@Override
		public void run() {
			System.out.println("run i=" + i);
			if (mark.getIsStarted().compareAndSet(false, true)) {
				try {
					String msg = "get lock, load from remote";
					mark.getFuture().set(msg);
					System.out.println(i + "|success load");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					String msg = mark.getFuture().get(5, TimeUnit.SECONDS);
					System.out.println(i + "|wait for result| msg:" + msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
