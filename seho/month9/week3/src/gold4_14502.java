

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 연구소
 * 인체에 치명적인 바이러스를 연구하던 연구소에서 바이러스 유출
 * 바이러스는 퍼지지 않았고, 바이러스 확산을 막기 위해 연구소에 벽을 세우려고 함
 * N x M인 직사각형으로 나타낼 수 있음
 * 연구소는 빈칸과 벽으로 이루어져 있으며 벽은 칸 하나를 가득 차지함
 * 일부 칸은 바이러스가 존재하며 상하좌우 인접한 빈칸으로 모두 퍼져나갈 수 있음
 * 세울 수 있는 벽 개수는 3개 꼭 3개를 세워야 함
 * 
 * 0은 빈칸 1은 벽 2는 바이러스가 있는 곳
 * 벽을 3개 세운 후 바이러스가 퍼질 수 없는 곳 -> 안전 영역
 * 안전영역 크기 구하기
 * 완탐으로 벽 세군데 치고 바이러스 퍼트린 후 0 갯수 세면 끝
 */
public class gold4_14502 {
	static int N, M;
	static int[][] matrix;
	
	static List<int[]> virus;
	static List<int[]> safty;
	static List<Integer> selected;
	
	static int result;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		matrix = new int[N][M];
		virus = new ArrayList<>();
		safty = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				int num = Integer.parseInt(st.nextToken());
				matrix[i][j] = num;
				if (num == 2) {
					virus.add(new int[] {i, j});
				} else if (num == 0) {
					safty.add(new int[] {i, j});
				}
			}
		}
		
		result = 0;
		dfs(0, 0);
		
		System.out.println(result);
		
	}
	
	public static void dfs(int depth, int idx) {
		if (depth == 3) {
			result = Math.max(result, bfs());
			return;
		}
		
		if (idx == safty.size()) {
			return;
		}
		
		int[] cur = safty.get(idx);
		
		matrix[cur[0]][cur[1]] = 1;
		dfs(depth + 1, idx + 1);
		
		matrix[cur[0]][cur[1]] = 0;
		dfs(depth, idx + 1);
	}
	
	public static int bfs() {
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		int[][] newMatrix = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				newMatrix[i][j] = matrix[i][j];
			}
		}
		
		for (int i = 0; i < virus.size(); i++) {
			queue.offer(virus.get(i));
		}
		
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			int r = cur[0];
			int c = cur[1];
			
			for (int dir = 0; dir < 4; dir++) {
				int nr = r + dr[dir];
				int nc = c + dc[dir];
				
				if (nr >= 0 && nr < N && nc >= 0 && nc < M && newMatrix[nr][nc] == 0) {
					newMatrix[nr][nc] = 2;
					queue.offer(new int[] {nr, nc});
				}
			}
			
		}
		
		return check(newMatrix);
	}
	
	public static int check(int[][] newMatrix) {
		int count = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (newMatrix[i][j] == 0) {
					count++;
				}
			}
		}
		return count;
	}
}
