package week8;

import java.io.*;
import java.util.*;

/*
 * 
 * 
 */
public class 퇴사 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[][] therapy = new int [N+1][2];
		therapy[0][0] = 0;
		therapy[0][1] = 0;
		
		for (int i = 1;  i <= N ; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			therapy[i][0] = Integer.parseInt(st.nextToken());
			therapy[i][1] = Integer.parseInt(st.nextToken());
		}
		int[] dp = new int[N+1];
		
			
		int maxcost = Integer.MIN_VALUE;
		for (int i = 1; i <= N; i++) {
			
			int curday = i;
			while(curday <= N) {
				if (therapy[i][0] + i <= N) {
					curday += therapy[curday][0];
					if (curday > N) break;
					dp[i] += therapy[curday][1];
				}
			}
			maxcost = Math.max(maxcost, dp[i]);

		}
		System.out.println(maxcost);
	}
}
