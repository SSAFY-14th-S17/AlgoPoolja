

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class gold4_16929_twoDots {
	static int N, M;
	static char[][] matrix;
	static boolean[][] visited;
	static boolean isValid;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		matrix = new char[N][M];
		visited = new boolean[N][M];
		isValid = false;
		
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				matrix[i][j] = str.charAt(j);
			}
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (!visited[i][j]) {
					dfs(i, j, 0, 0);
					if (isValid) {
						System.out.println("Yes");
						return;
					}
				}
			}
		}
		
		System.out.println("No");
	}
	
	public static void dfs(int row, int col, int prevR, int prevC) {
		visited[row][col] = true;
		for (int dir = 0; dir < 4; dir++) {
			int nr = row + dr[dir];
			int nc = col + dc[dir];
			
			if (nr == prevR && nc == prevC) {
				continue;
			}
			
			if (check(nr, nc) && matrix[nr][nc] == matrix[row][col]) {
				if (!visited[nr][nc]) {
					dfs(nr, nc, row, col);
				} else {
					isValid = true;
					return;
				}
			}
		}
	}
	
	public static boolean check(int nr, int nc) {
		return nr >= 0 && nr < N && nc >= 0 && nc < M;
	}
}
