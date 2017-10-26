package cn.zzh.demo.concurrent.queue;

import java.util.concurrent.ArrayBlockingQueue;

public class QueueMain {

	public ArrayBlockingQueue newArrayBlockingQueue(){
		ArrayBlockingQueue queue = new ArrayBlockingQueue(3,false);
		queue.add("");
		queue.offer("");

		return queue;
	}

}
