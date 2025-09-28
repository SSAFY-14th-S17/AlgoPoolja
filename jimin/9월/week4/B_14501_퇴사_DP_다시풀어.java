package Baekjoon;

import java.util.Scanner;

public class B_14501_퇴사_DP_다시풀어 {
    static int n;
    static int[] dp;
    static int[][] schedule;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 입력
        n = sc.nextInt();
        dp = new int[n+2]; // n+1로 하면 끝 상담 처리 못할 수 있어서 n+2
        schedule = new int[n+1][2]; // [0]=기간, [1]=금액

        for (int i = 1; i <= n; i++) {
            schedule[i][0] = sc.nextInt();
            schedule[i][1] = sc.nextInt();
        }

        // DP 처리
        for (int i = 1; i <= n; i++) {
            // 1. 상담 선택 안 함 → 이전까지 최대 수익 유지
            dp[i] = Math.max(dp[i], dp[i-1]);

            // 2. 상담 선택 → 끝나는 날 확인
            // 끝나는 날 = 시작일 + 상담 기간 - 1
            /* -1을 하는 이유:
			 *	오늘 포함해서 계산해야 하니까
			 *	2일부터 3일 동안이면 2+3-1 = 4
			 */
            int endDay = i + schedule[i][0] - 1;
            if (endDay <= n) {
                dp[endDay] = Math.max(dp[endDay], dp[i-1] + schedule[i][1]);
            }
        }

        // 최대 수익
        int maxProfit = 0;
        for (int i = 1; i <= n; i++) {
            maxProfit = Math.max(maxProfit, dp[i]);
        }

        System.out.println(maxProfit);
    }
}
