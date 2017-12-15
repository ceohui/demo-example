package cn.zzh.demo.javase;

public class BitOperate {

	public static void main(String[] args) {
		int a = 7;
		int b = 1;// 金币
		int c = 2;// 荔枝
		int d = 4;// 订单

		System.out.println((a & b) == b);
		System.out.println((a & c) == c);
		System.out.println((a & d) == d);

	}
}
