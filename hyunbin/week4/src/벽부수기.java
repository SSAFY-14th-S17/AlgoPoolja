import java.util.*;
import java.io.*;

public class 벽부수기 {
	
	static int N , M;
	static class Node {
		int r ,c , broken , dist;
		Node(int r, int c ,int broken,int dist){ // row column broken? dist
			this.r = r;
			this.c = c;
			this.broken = broken;
			this.dist = dist;
		}
	}
	static int[][] map;
	static boolean[][][] visited;
	
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	
	//static final int breakwall = 1;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		
		map = new int[N][M];
		visited = new boolean[N][M][2];
		for (int i = 0; i < N; i++) {
			String line = br.readLine().trim(); 
			for (int j = 0; j < M; j++) {
				map[i][j] = line.charAt(j) - '0';
			
			}
		}
		System.out.println(logic());
		
		
	}
	static int logic() {
		Deque<Node> dq = new ArrayDeque<>();
		dq.add(new Node(0 , 0, 0, 1));  
		visited[0][0][0] = true;
		
		while(!dq.isEmpty()) {
			Node cur = dq.poll();
			
			if (cur.r == N-1 && cur.c == M-1 ) return cur.dist; //return if it's arrived
			
			for (int d = 0; d < 4; d++) {
				int nr = cur.r + dr[d];
				int nc = cur.c + dc[d];
				
				if (!isDir(nr, nc)) continue; 
				
				if (map[nr][nc] == 0) {
					if (!visited[nr][nc][cur.broken]) {
						visited[nr][nc][cur.broken] = true;
						dq.add(new Node(nr , nc, cur.broken, cur.dist+1));
					}
				} 
				
				//it's time for wall
				else {
					if (cur.broken == 0  && !visited[nr][nc][1]) {
						visited[nr][nc][1] = true;
						dq.add(new Node(nr,nc,1, cur.dist+1));
					}
				}
			}
		}
		return -1;
	}
	
	
	public static boolean isDir(int x , int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
	}
}
