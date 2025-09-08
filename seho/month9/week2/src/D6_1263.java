

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 사람 네트워크 (플로이드-워셜)
 * 시작 노드 i에서 각 모든 정점에 대한 이동 횟수 저장
 * -> 이동횟수가 최솟값인 정점의 사람이 가장 영향력 있는 사람
 * 
 * 모든 노드들의 최솟값을 2차원 배열 형태로 저장 
 * 모든 값을 더한 후 가장 작은 값을 갱신
 * N <= 10
 */
public class D6_1263 {
	static int N;
	static int[][] adjList;
	static final int INF = 10000;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			
			adjList = new int[N + 1][N + 1];
			
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					adjList[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			adjList = floyd();
			
			int min = Integer.MAX_VALUE;
			for (int i = 1; i <= N; i++) {
				int sum = 0;
				for (int j = 1; j <= N; j++) {
					if (i != j) sum += adjList[i][j];
				}
				
				min = Math.min(min, sum);
			}
			
			System.out.println("#" + tc + " " + min);
		} // tc
	} // main
	
	public static int[][] floyd() {
		int[][] dp = adjList.clone();
		
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (i != j && dp[i][j] == 0) {
					dp[i][j] = INF;
				}
			}
		}
		
		// floyd 공식
		for (int k = 1; k <= N; k++) {
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
				}
			}
		}
		
		return dp;
	}
	
}
