package cn.zzh.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		LOG.info("hi,{}~", "boyi");

		testDynamic();
	}

	public static void testDynamic() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (true) {
					i++;
					LOG.info("info log i={}", i);
					LOG.debug("debug log i={}", i);

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
} 


