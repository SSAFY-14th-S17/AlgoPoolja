

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 플로이드 워셜 모든 도시 쌍에 대해 도시 A에서 B로 가는데 필요한 비용의 최솟값 구하기
 */
public class gold4_11404_플로이드 {
	static int V, E;
	static int[][] matrix;
	static int[][] dp;
	static final int INF = 987654321;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		V = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());

		matrix = new int[V + 1][V + 1];

		for (int i = 0; i < E; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());

			if (matrix[u][v] == 0) {
				matrix[u][v] = w;
			} else {
				matrix[u][v] = Math.min(matrix[u][v], w);
			}

		}

		dp = new int[V + 1][V + 1];
		floyd();
		StringBuilder sb = new StringBuilder();

		for (int i = 1; i <= V; i++) {
			for (int j = 1; j <= V; j++) {
				if (i != j && dp[i][j] == INF) {
					sb.append(0).append(" ");
				} else {
					sb.append(dp[i][j]).append(" ");
				}
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());

	}

	public static void floyd() {
		for (int i = 1; i <= V; i++) {
			for (int j = 1; j <= V; j++) {
				if (i == j) {
					matrix[i][j] = 0;
				} else if (matrix[i][j] != 0) {
					dp[i][j] = matrix[i][j];
				} else {
					dp[i][j] = INF;						
				}
			}
		}

		for(int k = 1;k<=V;k++) {
			for (int i = 1; i <= V; i++) {
				for (int j = 1; j <= V; j++) {
					dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
				}
			}
		}

	}

}
