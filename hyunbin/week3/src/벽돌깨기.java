import java.io.*;
import java.util.*;


public class 벽돌깨기 {
	/*
	 * N 떨어뜨릴 공의 개수
	 * W 배열의 가로
	 * H 배열의 세로
	 * 
	 * 1의 벽돌은 본인만 깨진다.
	 * 2부터 상하좌우의 +1 의 범위만큼 깨진다.
	 * 
	 */
	
	
	static int N , W , H;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= tc; t++) {
			StringTokenizer st;
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()) ;
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			int[][] arr = new int[H][W];
			int bricks = 0;
			for (int i = 0; i < H; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < W; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken());
					if (arr[i][j] > 0) bricks ++; 
				}
			}
			leftover = bricks;
			dfs(0,arr);
			System.out.println("#" + t + " " + leftover);
			
		}
	}
	
	static int leftover;

	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	
	static class Point {
		int r , c , range;
		public Point(int r , int c, int range) {
			this.r = r;
			this.c = c;
			this.range = range;
		}
	}
	static void dfs (int count, int[][] arr) {
		
		// 종료 조건 count 가 구슬 갯수와 똑같아질 때
		if (count == N) {
			
			int remain = 0; //남아있는 공 수 계산 변수
			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					if (arr[i][j] > 0) remain++; 
				}
			}
			leftover = Math.min(leftover, remain);
			return;
		}
		for (int c = 0; c < W; c++) {
			int[][] newarr = new int[H][W];
			deepcopy(arr , newarr);
			
			int r = -1;
			for (int j = 0; j < H; j++) {
				if (newarr[j][c] > 0) {
					r = j;
					break;
				}
			}
			
			if (r != -1) {
				Queue<Point> q = new LinkedList<>();
				
				q.add(new Point(r, c , newarr[r][c]));
				newarr[r][c] = 0;
				
				while(!q.isEmpty()) {
					Point p = q.poll();
					
					for (int d = 0; d < 4; d++) {
						for (int k = 1; k < p.range; k++) {
							int nr = p.r + dx[d] * k;
							int nc = p.c + dy[d] * k;
							
							if (nr >= 0 && nr < H && nc >= 0 && nc < W && newarr[nr][nc] > 0) {
								q.add(new Point(nr, nc  , newarr[nr][nc]));
								newarr[nr][nc] = 0;
							}
						}
					}
				}
				rerange(newarr);
			}
			dfs(count+1 , newarr);
		}
	}
	
	static void deepcopy(int[][] tocopy, int[][] tar) {
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				tar[i][j] = tocopy[i][j];
			}
		}
	}
	
	static void rerange(int[][] arr) {
		for (int j = 0; j < W; j++) {
			Deque<Integer> bricks = new ArrayDeque<>();
			
			for (int i = 0; i < H; i++) {
				if (arr[i][j] > 0) bricks.push(arr[i][j]);
			}
			for (int i = 0; i < H; i++) {
				arr[i][j] = 0;
			}
			
			int r = H -1 ;
			while (!bricks.isEmpty()) {
				arr[r--][j] = bricks.pop();
			}
		}
		
	}
}
