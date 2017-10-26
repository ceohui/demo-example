package cn.zzh.demo.concurrent.lock;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionMain {

	private static Lock lock = new ReentrantLock();

	// 阻塞队列-未满条件(容器满则等待，消费完通知生产)
	private static Condition notFull = lock.newCondition();
	// 阻塞队列-非空条件
	private static Condition notEmpty = lock.newCondition();

	private static int increment = 0;

	private static List<Integer> lists = new LinkedList<>();


	/**
	 * 生产
	 */
	public static void add() {
		while (true) {
			lock.lock();
			try {
				if (lists.size() >= 5) {
					// 等待
					notFull.await();
				}

				increment++;
				lists.add(increment);
				System.out.printf("+ add name:%s, size:%d, num:%d \n", Thread.currentThread().getName(), lists.size(), increment);
				Thread.sleep(500);
				//notEmpty.signal();
				notEmpty.signalAll();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * 消费
	 */
	public static void sub() {
		while (true) {
			lock.lock();
			try {

				if (lists.size() == 0) {
					// 等待
					notEmpty.await();
				}

				Integer x = lists.remove(0);
				System.out.printf("- sub name:%s, size:%d, num:%d \n", Thread.currentThread().getName(), lists.size(), x);
				Thread.sleep(50);
				//notFull.signal();
				notFull.signalAll();


			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}


	public static void main(String[] args) {

		int num = 4;
		for (int i = 1; i <= num; i++) {
			Thread add = new Thread(new Runnable() {
				@Override
				public void run() {
					add();
				}
			});
			add.setName("生产-" + i);
			add.start();

			Thread sub = new Thread(new Runnable() {
				@Override
				public void run() {
					sub();
				}
			});
			sub.setName("消费-" + i);
			sub.start();
		}

	}
}
