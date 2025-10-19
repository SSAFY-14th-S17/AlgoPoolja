

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * DP 풀이
 */
public class gold5_17070_파이프옮기기 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		int[][] matrix = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//dp[dir][r][c]
		long[][][] dp = new long[3][N][N];
		
		// 초기값
		dp[0][0][1] = 1;
		
		// dp 테이블
		for (int i = 0; i < N; i++) {
			for (int j = 2; j < N; j++) { // (0,0), (0,1)은 초기 위치
				// (i, j) 칸이 벽이면 놓을 수 없음
				if (matrix[i][j] == 1) {
					continue;
				}
				
				// 가로에 놓는 경우
				dp[0][i][j] = dp[0][i][j - 1] + dp[2][i][j - 1];
				
				// 세로를 놓는 경우 (i가 0일땐 불가능
				if (i > 0) {
					dp[1][i][j] = dp[1][i - 1][j] + dp[2][i - 1][j];
				}
				
				// (i, j)에 대각선 파이프를 놓는 경우 (i가 0일땐 불가능)
				// (i-1, j)와 (i, j-1)도 벽이 아니어야 함
				if (i > 0 && matrix[i - 1][j] == 0 && matrix[i][j - 1] == 0) {
					dp[2][i][j] = dp[0][i - 1][j - 1] + dp[1][i - 1][j - 1] + dp[2][i - 1][j - 1];
				}
				
			}
		}
		
		// 결과 반환
		System.out.println(dp[0][N - 1][N - 1] + dp[1][N - 1][N - 1] + dp[2][N - 1][N - 1]);
		
	}
}
