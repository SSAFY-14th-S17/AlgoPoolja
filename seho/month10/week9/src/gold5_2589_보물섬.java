import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/*
5 7
WLLWWWL
LLLWLLL
LWLWLWW
LWLWLLL
WLLWLWW
 */

public class gold5_2589_보물섬 {
	static int N, M;
	static char[][] matrix;
	static int result;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		matrix = new char[N][M];
		
		for (int i = 0; i < N; i++) {
			matrix[i] = br.readLine().toCharArray();
		}
		
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < M; j++) {
//				System.out.print(matrix[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		result = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (matrix[i][j] == 'L') {
					bfs(i, j);
				}
			}
		}
		
		System.out.println(result - 1);
		
	} // main
	
	public static void bfs(int r, int c) {
		boolean[][] visited = new boolean[N][M];
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		queue.offer(new int[] {r, c});
		visited[r][c] = true;
		
		int t = 0;
		
		while(!queue.isEmpty()) {
			t++;
			int size = queue.size();
			
			for (int i = 0; i < size; i++) {
				int[] cur = queue.poll();
				int row = cur[0];
				int col = cur[1];
				
				for (int dir = 0; dir < 4; dir++) {
					int nr = row + dr[dir];
					int nc = col + dc[dir];
					
					if (nr < 0 || nr >= N || nc < 0 || nc >= M 
							|| visited[nr][nc] || matrix[nr][nc] == 'W') {
						continue;
					}
					
					visited[nr][nc] = true;
					queue.offer(new int[] {nr, nc});
				}
			}
		}
		
		result = Math.max(result, t);
	}
}
