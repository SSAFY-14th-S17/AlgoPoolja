

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 연구소3
 * 바이러스를 퍼트리는 최소시간 구하기
 */
public class gold3_17142 {
	static int N, M;
	static int[][] matrix;
	static int result;
	static List<int[]> virus;
	static List<int[]> walls;
	static List<Integer> selected;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		matrix = new int[N][N];
		virus = new ArrayList<>();
		walls = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			st= new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int num = Integer.parseInt(st.nextToken());
				matrix[i][j] = num;
				if (num == 2) {
					virus.add(new int[] {i, j});
				} else if (num == 1) {
					walls.add(new int[] {i, j});
				}
			}
		}
		
		selected = new ArrayList<>();
		result = -1;
		
		dfs(0, 0);
		
		System.out.println(result);
	}
	
	public static void dfs(int depth, int idx) {
		if (depth == M) {
			int t = bfs();
			
			if (t != -1 && result == -1) {
				result = t;
			} else if (t != -1 && result != -1) {
				result = Math.min(result, t);
			}
			
			return;
		}
		
		if (idx == virus.size()) {
			return;
		}
		
		selected.add(idx);
		dfs(depth + 1, idx + 1);
		
		selected.remove(selected.size() - 1);
		dfs(depth, idx + 1);
	}
	
	public static int bfs() {
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		int[][] visited = new int[N][N];
		for (int[] wall : walls) {
			visited[wall[0]][wall[1]] = 1;
		}
		
		for (int[] v : virus) {
			visited[v[0]][v[1]] = 3;
		}
		
		for (int num : selected) {
			int[] v = virus.get(num);
			visited[v[0]][v[1]] = 2;
			queue.offer(v);
		}
		
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				System.out.print(visited[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
		
		int t = 0;
		
		if (check(visited)) {
			return t;
		}
		
		
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				int[] cur = queue.poll();
				int r = cur[0];
				int c = cur[1];
				
				for (int dir = 0; dir < 4; dir++) {
					int nr = r + dr[dir];
					int nc = c + dc[dir];
					
					if (nr >= 0 && nr < N && nc >= 0 && nc < N && (visited[nr][nc] == 0 || visited[nr][nc] == 3)) {
						visited[nr][nc] = 2;
						queue.offer(new int[] {nr, nc});
					}
				}
			}
			t++;
			if (check(visited)) {
				return t;
			}
		}
		
		return -1;
	}
	
	public static boolean check(int[][] visited) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (visited[i][j] == 0) {
					return false;
				}
			}
		}
		
		return true;
	}
}
