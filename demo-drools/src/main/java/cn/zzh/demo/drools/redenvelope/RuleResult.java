package cn.zzh.demo.drools.redenvelope;

import java.util.ArrayList;
import java.util.List;

public class RuleResult {

	private int result;

	private int score;

	private List<String> hitRuls = new ArrayList<>();

	private StringBuilder msgs = new StringBuilder();

	public void saveResult(int newScore, int newResult, String hitRule, String msg) {
		if (newScore > score) {
			score = newScore;
		}

		if (newResult > newResult) {
			newResult = newResult;
		}

		if (hitRule != null && !hitRule.isEmpty()) {
			addHitRule(hitRule);
		}

		if (msg != null && !msg.isEmpty()) {
			this.msgs.append(msg).append(";");
		}
	}


	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<String> getHitRuls() {
		return hitRuls;
	}

	public void setHitRuls(List<String> hitRuls) {
		this.hitRuls = hitRuls;
	}

	public void addHitRule(String ruleName) {
		this.hitRuls.add(ruleName);
	}

	public String getMsgs() {
		return msgs.toString();
	}

	public void setMsgs(StringBuilder msgs) {
		this.msgs = msgs;
	}
}
