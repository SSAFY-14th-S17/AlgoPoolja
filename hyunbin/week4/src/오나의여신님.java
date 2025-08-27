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
			
			Deque<Pos> devil = new ArrayDeque<>();//악마
			Deque<Pos> soo = new ArrayDeque<>(); //수연
			
			
			
			for (int i = 0; i < N; i++) {
				String line = br.readLine().trim(); //혹시모를 공백제거
				for (int j = 0; j < M; j++) {
					char c = line.charAt(j);
					map[i][j] = c;
					if (c == '*') devil.add(new Pos(i,j));
					else if (c == 'S') {
						soo.add(new Pos(i,j,0));
						visited[i][j]= true;
					}
				}
			}
			int ans = bfs(devil , soo);
			
			if (ans == -444) System.out.println("#" + t+ " " + "GAME OVER");
			else System.out.println("#" + t+ " " + ans);
		}
		
		
	}
	
	public static int bfs(Deque<Pos> devil , Deque<Pos> soo) {
		while(!soo.isEmpty()) {			
			//악마 칸 확장
			int devilSize = devil.size();
			for (int i = 0; i < devilSize; i++) {
				
				Pos cur = devil.poll(); //악마칸 위치 빼내기
				
				for (int d = 0; d < 4; d++) { //델타 탐색 
					int nx = cur.x + dx[d];
					int ny = cur.y + dy[d];
					
					if (!isDir(nx, ny)) continue;
					
					char cell = map[nx][ny]; //악마칸이 퍼져나갈 지도의 위치 
					
					if (cell == 'X' || cell == 'D' || cell == '*') continue; //X는 돌 D는 여신 *는 악마칸이므로 패스
					map[nx][ny] = '*';
					devil.add(new Pos(nx,ny));
				}
			}
			
			//수연이 움직임
			int sooSize = soo.size();
			for (int i = 0; i < sooSize; i++) {
				Pos cur = soo.poll();
				
				for (int d = 0; d < 4; d++) {
					int nx = cur.x + dx[d];
					int ny = cur.y + dy[d];
					if (!isDir(nx, ny) || visited[nx][ny]) continue; //범위 밖이거나 이미 방문한 곳이면 패스
					
					char cell = map[nx][ny]; //움직일 지도의 위치
					
					if (cell == 'D') {
						//도착할 때 끝남
						return cur.t + 1;
					}
					
					if (cell == 'X' || cell == '*') continue; //돌 칸이거나 악마칸이면 못가
					
					visited[nx][ny] = true;
					soo.add(new Pos(nx,ny, cur.t + 1)); //다시 수연의 위치 넣어줌 시간더해서
				}
			}
		}
		return -444;
	}
	
	public static boolean isDir(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M ;
	}
}

/*

D*S
.X.
.X.
.X.
...

*/