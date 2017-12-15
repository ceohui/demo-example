package cn.zzh.demo.guava.eventbus;

import java.util.Date;

/**
 * 账户余额事件
 */
public class AccountBalanceEvent {

	private long userId;
	private int amount;
	private Date time;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
