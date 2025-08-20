

import java.io.*;
import java.util.*;

public class 최적경로_DFS {
	
	static int N;
	static int[][] position; // 0: 회사 1: 집 , 2부터는 고객
	static boolean[] visited; //방문한곳 체크
	static int best; //최적의 값
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		
		int tc = Integer.parseInt(br.readLine());
		StringBuilder out = new StringBuilder();
		
		
		
		for (int t = 1; t <= tc ; t++) {
			N = Integer.parseInt(br.readLine());
			position = new int[N+2][2];
			
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0 ; i < N+2; i++) {
				position[i][0] = Integer.parseInt(st.nextToken());
				position[i][1] = Integer.parseInt(st.nextToken());	
			}
			
			visited = new boolean[N];
			best = Integer.MAX_VALUE;
			
			dfs(0, position[0][0] , position[0][1] , 0);
			
			out.append("#" + t +" ");
			out.append(best);
			out.append('\n');
			
		}
		System.out.println(out.toString());
	
	}
	// count = 방문한 고객 수 , (cx,cy) 현재 위치 , distSum: 누적 이동거리
	static void dfs(int count, int cx, int cy , int distSum) {
		
		//가지치기 로직 : 이미 현재 최적(best) 이상이면 계산을 중단
		if (distSum >= best ) return;
		
		
		// 모든 고객 탐색 로직 시작
		if (count == N) {
			distSum += calculate(cx, cy, position[1][0], position[1][1]); 
			
			if (distSum < best) best = distSum;
			return;
		}
		
		for (int i = 0; i < N; i++) {
			if (visited[i]) continue;
			visited[i] = true;
			
			int nx = position[i + 2][0];
			int ny = position[i + 2][1];
			int nd = distSum + calculate(cx, cy, nx, ny);
			
			dfs(count + 1, nx, ny , nd);
			
			visited[i] = false;
		}
	}
	
	static int calculate(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
}
