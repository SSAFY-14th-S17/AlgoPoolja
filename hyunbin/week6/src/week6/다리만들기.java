package week6;

import java.io.*;
import java.util.*;

public class 다리만들기 {
	static int N ; // 맵 배열 크기
	static int[][] map; // 0:1 ? 바다 : 땅
	static boolean[][] visited; // 방분 배열
	
	static int[][] id; //섬 번호 기록
	static int islandCnt; 
	
	static List<int[]>[] borders; // 섬 별 테두리 좌표  모음
	
	static int[] dx = {1,-1,0,0};
	static int[] dy = {0,0,-1,1};
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new  StringTokenizer(br.readLine().trim());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 1) 섬 라벨링 + 테두리 수집
		id = new int[N][N]; // 섬 라벨링
		visited = new boolean[N][N];  // 방문 배열 초기화
		islandCnt = 0;
		
		// 테두리 인덱스 
		borders = new ArrayList[N*N+1]; 
		for (int i = 0; i < borders.length; i++) {borders[i] = new ArrayList<>();}
		
		for (int r = 0; r < N; r++) {for (int c = 0; c < N; c++) {
			if (map[r][c] == 1 && !visited[r][c]) {
				islandCnt++;
				dfs(r, c , islandCnt);
			}
		}}
		if (islandCnt < 2) {
			System.out.println(-1);
			return;
		}
		int ans = bfs();
		System.out.println(ans);
	}
	
	
	// dfs로 섬 라벨링 + 테두리(바다와 맞닿은 땅  수집)
	private static void dfs(int x, int y, int label) {
		Deque<int[]> dq = new ArrayDeque<>();
		dq.push(new int[] {x, y});
		visited[x][y] = true;
		id[x][y] = label;
		
		while(!dq.isEmpty()) {
			int[] cur =  dq.pop();
			int r = cur[0]; int c = cur[1];
			boolean isBoundary = false;
			
			for (int d = 0; d < 4; d++) {
				int nr = r + dx[d];
				int nc = c + dy[d];
				
				if(!inRange(nr,nc)) continue;
				
				if(map[nr][nc] == 0) {
					isBoundary = true;
					
				} else if (!visited[nr][nc]) {
					visited[nr][nc] = true;
					id[nr][nc] = label;
					dq.push(new int[] {nr, nc});
				}
			}
			if (isBoundary) borders[label].add(new int[] {r,c}); //  최종 섬 테두리 넣기
		}
		
	}
	
	
	/* 
	 * 	모든 섬의 테두리에서 바다칸으로 동시에 확장
	 * 	owner[r][c] : 이 바다칸을 최초 점유한 섬 id
	 * 	dist[r][c] : 그 섬 테두리에서 이 바다캄까지의 다리 길이 (칸 수)
	 */
	private static int bfs() {
		int[][] owner = new int[N][N];
		int[][] dist = new int[N][N];
		for (int i = 0; i < N; i++) {Arrays.fill(dist[i], -1);}  // 모든 배열을 -1 로 채움
		
		Deque<int[]> dq = new ArrayDeque<>();
		
		for (int k = 1; k <= islandCnt ; k++) {
			for(int[] land : borders[k]) {
				int r = land[0] ; int c = land[1];
				
				for (int d = 0; d < 4; d++) {
					int nr = r + dx[d]; int nc = c + dy[d];
					
					if (!inRange(nr, nc) || map[nr][nc] != 0 ) continue; 
					if (dist[nr][nc] == -1) {
						dist[nr][nc] = 1;
						owner[nr][nc] = k;
						dq.add(new int[] {nr , nc});
					}
				}
			}
		}
		int best = Integer.MAX_VALUE;
		while(!dq.isEmpty()) {
			int[] cur = dq.poll();
			int r = cur[0] , c = cur[1];
			
			for (int d = 0; d < 4; d++) {
				int nr = r + dx[d] , nc = c + dy[d];
				if (!inRange(nr, nc)) continue;
				
				if (map[nr][nc] == 1) {
	                int other = id[nr][nc];
	                if (other != 0 && other != owner[r][c]) {
	                    best = Math.min(best, dist[r][c]);
	                }
	                continue;
	            }
				if (dist[nr][nc] == -1) {
					dist[nr][nc] = dist[r][c] + 1;
					owner[nr][nc] = owner[r][c];
					dq.add(new int[] {nr,nc});
					
				} else if (owner[nr][nc] != owner[r][c])  {
					best = Math.min(best, dist[r][c] + dist[nr][nc]);
				}
			}
		}
		return best == Integer.MAX_VALUE ? -1 : best;
	}
	
	private static boolean inRange(int r, int c) {
		return r >= 0 && c >= 0 && r < N && c < N;
	}
}
