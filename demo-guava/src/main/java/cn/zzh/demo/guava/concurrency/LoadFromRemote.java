package cn.zzh.demo.guava.concurrency;

import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoadFromRemote {

	private AtomicBoolean isStarted;

	private SettableFuture<String> future;

	public LoadFromRemote(){
		isStarted = new AtomicBoolean(false);
		future = SettableFuture.<String> create();
	}

	public AtomicBoolean getIsStarted() {
		return isStarted;
	}

	public void setIsStarted(AtomicBoolean isStarted) {
		this.isStarted = isStarted;
	}

	public SettableFuture<String> getFuture() {
		return future;
	}

	public void setFuture(SettableFuture<String> future) {
		this.future = future;
	}
}
