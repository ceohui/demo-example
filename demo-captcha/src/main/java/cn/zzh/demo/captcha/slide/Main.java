//package cn.zzh.demo.captcha.slide;
//
//public class Main {
//
//	private int[][] getBlockData() {
//		int[][] data = new int[targetLength][targetWidth];
//		double x2 = targetLength-circleR-2;
//		//随机生成圆的位置
//		double h1 = circleR + Math.random() * (targetWidth-3*circleR-r1);
//		double po = circleR*circleR;
//
//		double xbegin = targetLength-circleR-r1;
//		double ybegin = targetWidth-circleR-r1;
//
//		for (int i = 0; i < targetLength; i++) {
//			for (int j = 0; j < targetWidth; j++) {
//				//右边○
//				double d3 = Math.pow(i - x2,2) + Math.pow(j - h1,2);
//
//				if (d1 <= po
//						|| (j >= ybegin && d2 >= po)
//						|| (i >= xbegin && d3 >= po)
//						) {
//					data[i][j] = 0;
//
//				}  else {
//					data[i][j] = 1;
//				}
//			}
//		}
//		return data;
//	}
//}
