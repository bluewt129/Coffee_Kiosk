package coffee.pj.comm;

import java.util.Scanner;

public class Scsupport {
	private Scanner sc = new Scanner(System.in);

	public int scInt() {
		int num = 0;
		boolean sccan = false;
		while (!sccan) {
			try {
				num = sc.nextInt();
				sccan = true;
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요");
				e.printStackTrace();
			}
		}
		return num;
	}

	public String scString() {
		String str = "";
		boolean sccan = false;
		while (!sccan) {
			try {
				str = sc.next();
				sccan = true;
			} catch (Exception e) {
				System.out.println("문자만 입력해주세요");
				e.printStackTrace();
			}
		}
		return str;
	}

	void scOff() {
		if (sc != null) {
			sc.close();
			sc = null;
		}
	}
}