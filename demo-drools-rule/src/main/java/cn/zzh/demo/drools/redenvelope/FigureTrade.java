package cn.zzh.demo.drools.redenvelope;

/**
 * 交易画像
 */
public class FigureTrade {

	/**
	 * 历史充值次数
	 */
	private int histPayCount;

	/**
	 * 历史充值金额
	 */
	private double histPayMoney;

	/**
	 * 平均充值金额
	 */
	private double avgPayMoney;

	/**
	 * 充值间隔（平均多少天充值一次）
	 */
	private int intervalPayDay;

	/**
	 * 红包收入比例
	 */
	private double redEnvelopeIncomeScale;

	public int getHistPayCount() {
		return histPayCount;
	}

	public void setHistPayCount(int histPayCount) {
		this.histPayCount = histPayCount;
	}

	public double getHistPayMoney() {
		return histPayMoney;
	}

	public void setHistPayMoney(double histPayMoney) {
		this.histPayMoney = histPayMoney;
	}

	public double getAvgPayMoney() {
		return avgPayMoney;
	}

	public void setAvgPayMoney(double avgPayMoney) {
		this.avgPayMoney = avgPayMoney;
	}

	public int getIntervalPayDay() {
		return intervalPayDay;
	}

	public void setIntervalPayDay(int intervalPayDay) {
		this.intervalPayDay = intervalPayDay;
	}

	public double getRedEnvelopeIncomeScale() {
		return redEnvelopeIncomeScale;
	}

	public void setRedEnvelopeIncomeScale(double redEnvelopeIncomeScale) {
		this.redEnvelopeIncomeScale = redEnvelopeIncomeScale;
	}
}
