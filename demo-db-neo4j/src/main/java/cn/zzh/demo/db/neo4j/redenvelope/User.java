package cn.zzh.demo.db.neo4j.redenvelope;

import java.util.HashSet;

public class User {

	private long userId;

	private HashSet<String> relationIp = new HashSet<>();

	private HashSet<String> relationDevice = new HashSet<>();

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public HashSet<String> getRelationIp() {
		return relationIp;
	}

	public void setRelationIp(HashSet<String> relationIp) {
		this.relationIp = relationIp;
	}

	public HashSet<String> getRelationDevice() {
		return relationDevice;
	}

	public void setRelationDevice(HashSet<String> relationDevice) {
		this.relationDevice = relationDevice;
	}
}
