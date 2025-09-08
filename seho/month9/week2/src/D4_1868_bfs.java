

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

/**
 * 파핑파핑 지뢰찾기
 * 
 * 각 칸에 지뢰가 있을 수도 없을 수도 있음 표의 각 칸 클릭했을 때, 그 칸이 지뢰가 있는 칸이라면 '파핑 파핑!'이라는 소리와 함께 게임
 * 끝
 * 
 * 지뢰 없는 칸 -> 변이 맞닿아 있거나 꼭지점이 맞닿아 있는 최대 8칸에 대해 지뢰가 있는지가 0에서 8사이 숫자로 클릭한 칸에 표시됨
 * 
 * 숫자가 0이라면 8방향 근처 지뢰 없는 것 확정 그 8방향의 칸도 자동으로 숫자를 표시해준다.
 * 
 * 지뢰 '*', 없는칸 '.' 지뢰 클릭 'c'
 * 
 * 표의 크기와 표가 주어질 때 지뢰가 있는 칸을 제외한 다른 모든 칸의 숫자들이 표시되려면 최소 몇번 클릭해야 하는지 구하는 프로그램 작성
 * 
 * 1 <= N <= 300
 * 
 * 최소 클릭수 -> 모든 덩어리 조합
 * 
 */
public class D4_1868_bfs {
	static int N;
	static char[][] matrix;
	static int[] dr = { -1, 1, 0, 0, -1, -1, 1, 1 };
	static int[] dc = { 0, 0, -1, 1, -1, 1, -1, 1 };

	static boolean[][] visited;

	static int cnt;
	static int result;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());

			matrix = new char[N][N];
			visited = new boolean[N][N];
			result = 0;
			for (int i = 0; i < N; i++) {
				String tmp = br.readLine();
				for (int j = 0; j < N; j++) {
					char c = tmp.charAt(j);
					matrix[i][j] = c;
				}
			}
			

			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (matrix[i][j] == '.') {
						// 인접 8방향에 마인이 없는 곳부터 탐색 진행
						if (checkMine(i, j)) {
							result++;
							bfs(i, j);
						}
					}
				}
			}
			
			
			// 나머지 . 처리
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (matrix[i][j] == '.') {
						// 인접 8방향에 마인이 없는 곳부터 탐색 진행
						result++;
					}
				}
			}

			System.out.println("#" + tc + " " + result);

		} // tc

	} // main

	public static void bfs(int row, int col) {
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		visited[row][col] = true;
		matrix[row][col] = '0';
		queue.offer(new int[] { row, col });

		while (!queue.isEmpty()) {
			int[] cur = queue.poll();
			int r = cur[0];
			int c = cur[1];
			
			// 인접지역 탐색(상하좌우)
			for (int dir = 0; dir < 8; dir++) {
				int nr = r + dr[dir];
				int nc = c + dc[dir];

				if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
					if (!visited[nr][nc] && matrix[nr][nc] != '*') {
						visited[nr][nc] = true;
						// 지뢰 탐색
						if (checkMine(nr, nc)) {
							matrix[nr][nc] = '0';
							queue.offer(new int[] {nr, nc});
						} else {
							matrix[nr][nc] = '1';
						}
					}

				}
			}
			
			
		}
	} // bfs

	public static boolean checkMine(int row, int col) {
		for (int dir = 0; dir < 8; dir++) {
			int nr = row + dr[dir];
			int nc = col + dc[dir];

			if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
				if (matrix[nr][nc] == '*') {
					return false;
				}

			}
		}
		return true;
	}
}
