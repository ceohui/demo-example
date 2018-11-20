package cn.zzh.demo.kuaishou.bean;

public class KsUser {

	private String userId;

	private String kwaiId;

	private String name;

	private String headUrl;

	private String sign;

	private int sex;

	private int numFans;

	private int numFollow;

	private int numWorks;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
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

	public String getKwaiId() {
		return kwaiId;
	}

	public void setKwaiId(String kwaiId) {
		this.kwaiId = kwaiId;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
