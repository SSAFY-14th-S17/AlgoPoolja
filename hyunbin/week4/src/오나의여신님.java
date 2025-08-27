import java.util.*;
import java.io.*;
/*
 * 	N 행 M 열
 * 	동서남북 델타탐색
 * 	S = 수연이의 위치
 *  D = 여신의 위치
 *  X = 돌의 위치
 *  * = 악마!! 매초마다 상하좌우 부식
 *  
 *  수연은 돌과 악마칸을 피해서 가야하는데 악마칸은 매 초마다 상하좌우 영역들을 부식시킨다.
 */
public class 오나의여신님 {
	
	static int N , M;
	
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static char[][] map;	
	static boolean[][] visited;
	
	
	static class Pos{
		int x, y, t; //t는 경과
		Pos(int x, int y) {
			this.x = x;
			this.y = y;
		} 
		Pos(int x ,int y, int t) {
			this.x = x;
			this.y = y;
			this.t = t;
		} 
		
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		for (int t = 1; t <= tc; t++) {
			StringTokenizer st;
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			
			map = new char[N][M];
			visited = new boolean[N][M];
			
			Deque<Pos> devil = new ArrayDeque<>();//
			Deque<Pos> soo = new ArrayDeque<>(); //
			
			
			
			for (int i = 0; i < N; i++) {
				String line = br.readLine().trim();
				for (int j = 0; j < M; j++) {
					char c = line.charAt(j);
					
				}
			}
		}
		
		
	}
	
	//public int bfs()
	
	public boolean isDir(int x, int y) {
		return (x >= 0 && x < N && y >= 0 && y < M );
	}
}

/*

D*S
.X.
.X.
.X.
...

*/