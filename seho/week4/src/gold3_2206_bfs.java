

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 벽 부수고 이동하기
 * 
 * 0의 위치를 확인하며 1,1에서 N,M 까지 도착하는 경우의 수 탐색
 * 
 * 벽을 부쉈을때랑 벽을 부수지 않은 경우를 나눠 3차원 배열로 최단거리를 관리
 * 
 */

public class gold3_2206_bfs {
	static int N, M;
	
	static int[][] matrix;
	// 벽 부쉈을때랑 안부쉈을 때의 최단 거리 기록
	static int[][][] visited;
	
	// 상 우 하 좌
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		matrix = new int[N + 1][M + 1];
		visited = new int[N + 1][M + 1][2];
		
		for (int i = 1; i <= N; i++) {
			String str = br.readLine();
			for (int j = 1; j <= M; j++) {
				matrix[i][j] = str.charAt(j-1) - '0';
			}
		}
		
		System.out.println(bfs());
		
	}
	
	// 탐색
	public static int bfs() {
		Queue<int[]> queue = new LinkedList<>();
		visited[1][1][0] = 1;
		// 좌표, 이동거리, 부순 횟수 기록
		queue.add(new int[] {1, 1, 1, 0});
		
		while (!queue.isEmpty()) {
			int[] cur = queue.poll();
			
			int row = cur[0];
			int col = cur[1];
			int move = cur[2];
			
			if (row == N && col == M) {
				// 도착하면 반환
				return move;
			}
			
			for (int dir = 0; dir < 4; dir++) {
				int nr = row + dr[dir];
				int nc = col + dc[dir];
				
				// 범위 안
				if (nr > 0 && nr <= N && nc > 0 && nc <= M) {
					// 벽이 있는 경우
					if (matrix[nr][nc] == 1) {
						// 벽 부술 횟수 있는지 체크 && 방문한적 있는지 체크
						if (cur[3] == 0 && visited[nr][nc][1] == 0) {
							// 벽 부수고 해당위치 기록 후 이동
							visited[nr][nc][1] = move + 1;
							queue.add(new int[] {nr, nc, move + 1, 1});
						}
					} else { // 벽이 없는 경우
						// 방문 확인
						if (visited[nr][nc][cur[3]] == 0) {
							// 기록 후 이동
							visited[nr][nc][cur[3]] = move + 1;
							queue.add(new int[] {nr, nc, move + 1, cur[3]});
						}
					}
				}
			}
		}
		
		// 이동 불가
		return -1;
	}
}
