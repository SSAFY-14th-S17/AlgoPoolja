package week8;

import java.io.*;
import java.util.*;

public class 퇴사 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());		
		
		
		// N+1 까지 사용힐 건데 안전용으로 하나 더 만들기
		int[] T = new int[N+2];
		int[] P = new int[N+2];
		
		for (int i = 1; i <= N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			T[i] = Integer.parseInt(st.nextToken());
			P[i] = Integer.parseInt(st.nextToken());
			
		}
		
		int[] dp = new int[N+2]; 
		
		for (int i = N; i >= 1; i--) {
			int next = i + T[i];
			
			if (next <= N+1) {
				dp[i] = Math.max(dp[i+1], P[i] + dp[next]);				
			} else {
				dp[i] = dp[i+1];
			}
		}
		System.out.println(dp[1]);
	}
}
