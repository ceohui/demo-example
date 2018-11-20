package cn.zzh.demo.drools.redenvelope;

/**
 * 设备画像
 */
public class FigureDevice {

	/**
	 * 安装风险app数量
	 */
	private int riskAppCount;

	/**
	 * 虚拟机数量
	 */
	private int vmCount;

	/**
	 * 设备篡改数量
	 */
	private int tamperCount;

	public int getRiskAppCount() {
		return riskAppCount;
	}

	public void setRiskAppCount(int riskAppCount) {
		this.riskAppCount = riskAppCount;
	}

	public int getVmCount() {
		return vmCount;
	}

	public void setVmCount(int vmCount) {
		this.vmCount = vmCount;
	}

	public int getTamperCount() {
		return tamperCount;
	}

	public void setTamperCount(int tamperCount) {
		this.tamperCount = tamperCount;
	}
}
