package cn.zzh.demo.drools.redenvelope;

public class RedUtil {

	public static void setResult(RuleResult rr, int newScore, int newResult, String hitRule){
		if(newScore > rr.getScore()){
			rr.setScore(newScore);
		}

		if(newResult > rr.getResult()){
			rr.setResult(newResult);
		}

		rr.addHitRule(hitRule);
	}
}
