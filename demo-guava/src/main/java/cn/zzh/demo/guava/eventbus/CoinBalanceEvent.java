package cn.zzh.demo.guava.eventbus;

public class CoinBalanceEvent extends AccountBalanceEvent {

	private int currency = 1;

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}
}
