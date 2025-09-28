package Baekjoon;

import java.util.Scanner;

public class B_2579_계단오르기_DP {
	static int[] stairs;
	static int[] dp;
	static int n;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		stairs = new int[n+1];
		dp = new int[n+1];
		
		for (int i = 1; i <= n; i++) {
			stairs[i] = sc.nextInt();
		}
		
        if (n >= 1) dp[1] = stairs[1];        // 계단 1칸일 때
        if (n >= 2) dp[2] = stairs[1] + stairs[2]; // 계단 2칸일 때

		for (int i = 3; i <= n; i++) {			
			dp(i);
		}
		
		System.out.println(dp[n]);
	}

	private static void dp(int i) {
        int case1 = dp[i-2] + stairs[i];             // 2칸 점프
        int case2 = dp[i-3] + stairs[i-1] + stairs[i]; // 1칸+1칸

        if (case1 > case2) {
            dp[i] = case1;
        } else {
            dp[i] = case2;
        }
	}

	

}
