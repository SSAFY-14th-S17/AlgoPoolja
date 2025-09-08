

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 다리만들기
 * 
 * N * N 크기 이차원 평면에 여러 섬 존재
 * 바다에 가장 짧은 다리를 놓아 두 대륙을 연결
 * 격자에서 차지하는 칸의 수가 가장 작은 다리
 * 
 * 가장 짧은 다리 하나를 놓아 두 대륙을 연결하는 방법 찾기
 * 
 * 3개의 좌표로 이루어진 섬의 집합을 만들고
 * 각 집합간 맨해튼 거리를 비교하여 가장 최솟값을 출력 -> 완탐(시간이 괜찮을까??)
 * 
 */
public class gold3_2146 {
	static int N;
	static int[][] matrix;
	static boolean[][] visited;
	static int min;
	static List<List<int[]>> island;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		matrix = new int[N][N];
		visited = new boolean[N][N];
		
		island = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int idx = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (matrix[i][j] == 1 && !visited[i][j]) {
					bfs(i, j, idx);
					idx++;
				}
			}
		}
		
		min = Integer.MAX_VALUE;
		
		for (int i = 0; i < island.size() - 1; i++) {
			for (int j = i + 1; j < island.size(); j++) {
				checkBridge(island.get(i), island.get(j));
			}
		}
		
		System.out.println(min - 1);
		
	} // main
	
	public static void bfs(int row, int col, int idx) {
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		visited[row][col] = true;
		island.add(new ArrayList<>());
		island.get(idx).add(new int[] {row, col});
		queue.offer(new int[] {row, col});
		
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			int r = cur[0];
			int c = cur[1];
			
			for (int dir = 0; dir < 4; dir++) {
				int nr = r + dr[dir];
				int nc = c + dc[dir];
				
				if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
					if (!visited[nr][nc] && matrix[nr][nc] == 1) {
						visited[nr][nc] = true;
						island.get(idx).add(new int[] {nr, nc});
						queue.offer(new int[] {nr, nc});
					}
				}
			}
		}
	}
	
	public static void checkBridge(List<int[]> list1, List<int[]> list2) {
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				int[] arr1 = list1.get(i);
				int[] arr2 = list2.get(j);
				
				min = Math.min(min, Math.abs(arr1[0] - arr2[0]) + Math.abs(arr1[1] - arr2[1]));
			}
		}
	}
}
