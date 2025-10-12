package week8;

/*
 * N+1일째 되는 날 퇴사하기 위해
 * 남은 N일 동안 최대한 많은 상담을 하기
 * Ti:i번째 날 상담을 했을 때 걸리는 일수
 * Pi:i번째 날 상담을 했을 때 받을 수 있는 금액
 * 백준이가 얻을 수 있는 최대 수익?
 * 
 * [문제 풀이]
 * DP - Bottom up
 * - i번째 날 상담을 안 한다 / 한다의 경우로 나누어 최댓갑 구함
 * - 점화식: max(dp[i+1], Pi[i]+dp[i+Ti[i]])
 * - i번째 날의 최대 수익을 계산하려면 i이후의 날의 최대 수익이 필요
 * => dp 테이블이 뒤에서부터 채워져야 함
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class bj14501_퇴사_DP_BottomUp {
	static int Max = 0;
	static int N; // 앞으로 근무가 가능한 일 수
	static int[] Ti; // i번째날 상담을 했을 때 걸리는 일 수
	static int[] Pi; //i번째날 상담을 했을 때 받을 수 있는 금액
	static int[] dp;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


		// [input]
		N = Integer.parseInt(br.readLine());
		Ti = new int[N+1]; // 상담 완료 시간을 저장하는 배열, 1부터 시작
		Pi = new int[N+1]; // 받을 수 있는 금액 저장하는 배열
		dp = new int[N+2]; // 1일 ~ N+1일까지 상담 가능

	
		for(int i=1; i<=N; i++) {
			String[] temp = br.readLine().split(" ");
			Ti[i] = Integer.parseInt(temp[0]);
			Pi[i] = Integer.parseInt(temp[1]);
		}


		// [logic]
		// 점화식 채우기
		// dp[i]:i일부터 퇴사일까지 벌 수 있는 최대 수익
		for(int i=N; i>=1; i--) {
			// 상담 가능
			if(i+Ti[i]<=N+1) {
				dp[i] = Math.max(Pi[i]+dp[i+Ti[i]],dp[i+1]);
			}
			// 상담 불가능
			else {
				dp[i] = dp[i+1];
			}
		}
		Max = dp[1];
		
		// [output]
		System.out.println(Max);


	}// main

}
