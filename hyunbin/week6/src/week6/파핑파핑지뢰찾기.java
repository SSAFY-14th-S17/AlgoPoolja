package week6;

import java.util.*;
import java.io.*;

public class 파핑파핑지뢰찾기 {
	
	static int N;
	static char[][] g;
	static int[][] adj;
	static boolean[][] vis;

	static int countA (int r, int c) {
		int cnt = 0;
		for (int d = 0; d < 9; d++) {
			
			int dr = r + (d/3) -1;
			int dc = c + (d%3) -1;
			if (dr == r && dc == c) continue;
			if (!isRange(dr,dc)) continue;
			if (g[dr][dc] == '*' ) cnt++;
		}
		return cnt;
		
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		for (int t = 1; t <= tc; t++) {
			N = Integer.parseInt(br.readLine());
			g = new char[N][N];
			for (int i = 0; i < N; i++) {
				String s = br.readLine().trim();
				for (int j = 0; j < N; j++) {
					g[i][j] = s.charAt(j);
				}
			}
			adj = new int[N][N];
			vis = new boolean[N][N];
			
			for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) {
				if (g[i][j] == '.') adj[i][j] = countA(i,j);	
			}
			
			int clicks = 0;
			
			for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) {
				if (g[i][j] == '.' && adj[i][j] == 0 && !vis[i][j]) {
					bfs(i,j);
					clicks++;
				}
			}
			
			for (int i = 0 ; i < N; i++) for (int j = 0; j < N ; j++ ) {
				if (g[i][j] == '.' && !vis[i][j]) clicks++;
			}
			System.out.println("#"+t+" "+clicks);
		}
		
	}
	
	static void bfs(int r, int c) {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		vis[r][c] = true;
		q.add(new int[] {r,c});
		
		
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int cr = cur[0] , cc= cur[1];
			if (adj[cr][cc] != 0) continue; // 양수면 더 이상 확장하지 않는다잉.
			
			for (int k = 0; k <= 8; k++) {
				int kr = cr + (k/3)-1;
				int kc = cc + (k%3)-1;
				if (kr == cr && kc == cc) continue;
				if (!isRange(kr, kc)) continue;
				if (g[kr][kc] != '.' || vis[kr][kc]) continue;
				
				vis[kr][kc] = true;
				
				if (adj[kr][kc] == 0 ) q.add(new int[] {kr , kc});
			}
		}
	}
	
	
	static boolean isRange(int x, int y) {
		return x >= 0 && y >= 0 && x < N && y < N;
	}

}
