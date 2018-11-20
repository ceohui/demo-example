package cn.zzh.demo.kuaishou.bean;

import java.util.Date;

public class KsHistFans {

	private String userId;

	private String kwaiId;

	private int numFans;

	private int numFollow;

	private int numWorks;

	private Date createTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKwaiId() {
		return kwaiId;
	}

	public void setKwaiId(String kwaiId) {
		this.kwaiId = kwaiId;
	}

	public int getNumFans() {
		return numFans;
	}

	public void setNumFans(int numFans) {
		this.numFans = numFans;
	}

	public int getNumFollow() {
		return numFollow;
	}

	public void setNumFollow(int numFollow) {
		this.numFollow = numFollow;
	}

	public int getNumWorks() {
		return numWorks;
	}

	public void setNumWorks(int numWorks) {
		this.numWorks = numWorks;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
