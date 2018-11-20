package cn.zzh.demo.drools.redenvelope;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户画像
 */
public class FigureUser {

	/**
	 * 距离上一次登录间隔（天数）
	 */
	private int loginGapDay;

	/**
	 * 近30天每日活跃（天数）
	 */
	private int recentActiveDay;

	private List<String> loginDevices = new ArrayList<>();


	public int getLoginGapDay() {
		return loginGapDay;
	}

	public void setLoginGapDay(int loginGapDay) {
		this.loginGapDay = loginGapDay;
	}

	public int getRecentActiveDay() {
		return recentActiveDay;
	}

	public void setRecentActiveDay(int recentActiveDay) {
		this.recentActiveDay = recentActiveDay;
	}

	public List<String> getLoginDevices() {
		return loginDevices;
	}

	public void setLoginDevices(List<String> loginDevices) {
		this.loginDevices = loginDevices;
	}
}
