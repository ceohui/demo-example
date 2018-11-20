package cn.zzh.demo.rocketmq;

public class SubwayMain {

	public static void main(String[] args) {
//		int n2 = 0;
//		int n9 = 0;

		double min = 0;
		double max = 0;
		double total = 0;

		int all = 23 * 2;
		int font = 15;

		double minYuan = 1.9;
		double maxYuan = 6.5;

		for (int i = 0; i <= font; i++) {
			double y2 = i * minYuan;

			int n9 = (font - i);
			double y9 = n9 * maxYuan;

			int n9dis = (all - n9);
			double y9dis = n9dis * maxYuan * 0.6;

			total = y2 + y9 + y9dis;
			System.out.printf("[2元]%s次%s元 \t [9元]%2d次%s元 \t [9元]%s次%s元 \t total:%s \n", i, y2, n9, y9, n9dis, y9dis, total);

			if (min > total || min == 0) {
				min = total;
			}
			if (max < total || max == 0) {
				max = total;
			}
		}

		System.out.println("min:" + min);
		System.out.println("max:" + max);
		System.out.println("gap:" + (max - min));

	}
}
