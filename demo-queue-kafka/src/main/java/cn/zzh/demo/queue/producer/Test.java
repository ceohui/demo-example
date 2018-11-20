package cn.zzh.demo.queue.producer;

public class Test {

	public static void main(String[] args) {
		zimu4();
	}

	public static void shuzi() {
		int x = 0;
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
//				for (int k = 0; k <= 9; k++) {
//					for (int l = 0; l <= 9; l++) {
				System.out.printf("%d%d\n", i, j);
				x++;
//					}
//				}
			}
		}
		System.out.println("total:" + x);
	}

	public static void zimu() {
		int x = 0;
		for (int i = 'a'; i <= 'z'; i++) {
			for (int j = 'a'; j <= 'z'; j++) {
				for (int k = 'a'; k <= 'z'; k++) {
//					for (int l = 0; l <= 9; l++) {
					System.out.printf("%c%c%c\n", i, j, k);
					x++;
//					}
				}
			}
		}
		System.out.println("total:" + x);
	}

	public static void zimu4() {
		int x = 0;
		for (int i = 'a'; i <= 'z'; i++) {
			for (int j = 'a'; j <= 'z'; j++) {
				for (int k = 'a'; k <= 'z'; k++) {
					for (int l = 'a'; l <= 'z'; l++) {
						System.out.printf("%c%c%c%c\n", i, j, k, l);
						x++;
					}
				}
			}
		}
		System.out.println("total:" + x);
	}
}
