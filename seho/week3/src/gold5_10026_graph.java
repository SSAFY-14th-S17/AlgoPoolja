

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 적록색약
 * N * N 그리드에 R G B가 담김
 * 같은 색상이 상하좌우로 인접해 있는 경우 두 글자는 같은 구역에 속함
 * 
 * 구역 구하기
 * 
 * N * N 은 이차원 배열로 관리
 * 
 * 구역은 List로 관리 인접구역에 같은 색상이 있을경우 추가하기
 * 
 * 코드 리팩토링 필요
 */
public class gold5_10026_graph {
	static int N;
	static char[][] graph;
	static int person1;
	static int person2;
	static Queue<int[]> queue;
	static boolean[][] visited;
	
	// 인접지역 탐색(상, 하, 좌, 우)
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		graph = new char[N][N];
		
		for (int i = 0; i < graph.length; i++) {
			graph[i] = br.readLine().toCharArray();
		}
		
		person1 = 0;
		person2 = 0;
		
		bfs();
		bfs2();
		
		System.out.println(person1 + " " + person2);
		
	}
	
	public static int[] check(boolean[][] visited) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!visited[i][j]) {
					visited[i][j] = true;
					return new int[] {i, j};
				}
			}
		}
		
		return null;
	}
	
	// 적록 색약x
	public static void bfs() {
		visited = new boolean[N][N];
		queue = new LinkedList<>();
		
		while (true) {
			int[] cur = check(visited);			
			if (cur == null) {
				break;
			} else {
				person1++;				
				char target = graph[cur[0]][cur[1]];
				queue.add(new int[] {cur[0], cur[1]});
				
				// 구역 탐색
				while(!queue.isEmpty()) {
					int[] curRC = queue.poll();
					int row = curRC[0];
					int col = curRC[1];
					
					for (int dir = 0; dir < 4; dir++) {
						int nr = row + dr[dir];
						int nc = col + dc[dir];
						
						if (nr >= N || nr < 0 || nc >= N || nc < 0) {
							continue;
						}
						
						if (graph[nr][nc] == target) {
							if (!visited[nr][nc]) {
								visited[nr][nc] = true;
								queue.add(new int[] {nr, nc});
							}
						}
					}
				}
			}
		}
	}
	
	// 적록 색약
	public static void bfs2() {
		visited = new boolean[N][N];
		queue = new LinkedList<>();
		
		while (true) {
			int[] cur = check(visited);			
			if (cur == null) {
				break;
			} else {
				person2++;				
				char target = graph[cur[0]][cur[1]];
				queue.add(new int[] {cur[0], cur[1]});
				
				// 구역 탐색
				while(!queue.isEmpty()) {
					int[] curRC = queue.poll();
					int row = curRC[0];
					int col = curRC[1];
					
					for (int dir = 0; dir < 4; dir++) {
						int nr = row + dr[dir];
						int nc = col + dc[dir];
						
						if (nr >= N || nr < 0 || nc >= N || nc < 0) {
							continue;
						}
						
						if (target == 'R' || target == 'G') {
							if (graph[nr][nc] == 'R' || graph[nr][nc] == 'G') {
								if (!visited[nr][nc]) {
									visited[nr][nc] = true;
									queue.add(new int[] {nr, nc});
								}
							}
						} else {
							if (graph[nr][nc] == target) {
								if (!visited[nr][nc]) {
									visited[nr][nc] = true;
									queue.add(new int[] {nr, nc});
								}
							}
						}
					}
				}
			}
		}
	}
}
