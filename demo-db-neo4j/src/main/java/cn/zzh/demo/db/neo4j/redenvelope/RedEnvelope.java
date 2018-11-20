package cn.zzh.demo.db.neo4j.redenvelope;

import java.util.ArrayList;
import java.util.List;

public class RedEnvelope {

	private long redId;

	private int money;

	private int count;

	private User sendUser;

	private List<User> reviceUser = new ArrayList<>();

	public long getRedId() {
		return redId;
	}

	public void setRedId(long redId) {
		this.redId = redId;
	}

	public User getSendUser() {
		return sendUser;
	}

	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}

	public List<User> getReviceUser() {
		return reviceUser;
	}

	public void setReviceUser(List<User> reviceUser) {
		this.reviceUser = reviceUser;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
