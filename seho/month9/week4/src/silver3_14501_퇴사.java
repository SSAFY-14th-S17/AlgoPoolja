

import java.util.Scanner;

/**
 * 퇴사
 * N일동안 상담을 진행하여
 * 최대 금액 받기
 */
public class silver3_14501_퇴사 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		
		int[] dp = new int[N + 2];
		
		int[] day = new int[N + 1];
		int[] money = new int[N + 1];
		
		for (int i = 1; i <= N; i++) {
			day[i] = sc.nextInt();
			money[i] = sc.nextInt();
		}
		
		for (int i = N; i >= 1; i--) {
			int next = i + day[i];
			
			if (next > N + 1) {
				dp[i] = dp[i + 1];
			} else {
				dp[i] = Math.max(dp[i + 1], money[i] + dp[next]);
			}
		}
		
		System.out.println(dp[1]);
	}
}
