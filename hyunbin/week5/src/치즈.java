import java.util.*;
import java.io.*;

/*
 * 	Scratch
 *  둘러쌓인 치즈가 있다.
 * 	치즈 블록한개 중 상 하 좌 우 중 2개 이상의 면이 공기와 닿을 경우 녹는다.
 * 	녹는 로직은 어렵지 않으나 여기서 외부공기와 내부 공기를 판별해야한다.
 * 	완전탐색으로 내부공기와 외부 공기를 판별하여 내부 공기의 치즈는
 * 	내부 공기를 판별하는 법은 치즈로 둘러 쌓인 공기를 내부 공기라고 한다.
 * 	녹으면서 내부 공기는 언젠간 외부 공기가 되므로 time 을 기준으로 사이클을 돌려가며
 * 	치즈를 녹이면 된다.
 *  
 *  Structure
 *  전역변수 : 전체 배열 cheese , row , col
 *  
 *  Keypoint
 *  배열 하나로 모든 속성값을 관리한다. 치즈 = 1 외부공기 = 2 내부공기 = 0  
 * 	
 * 
 */
public class 치즈 {
	
	static int[][] cheese ; //전체 치즈배열
	static int cntC; // 치즈 블록  = 이게 0이되면 종료
	static boolean[][] visited;  //DFS 외부공기 판단용
	static int r ; // 행
	static int c ; // 열
	static int time; // 시간
	
	static String rowSkip;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		cheese = new int[r][c];
		visited = new boolean[r][c];
		for (int row = 0; row < r; row++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int col = 0; col < c; col++) {
				cheese[row][col] =Integer.parseInt(st.nextToken());
				if (cheese[row][col] == 1) cntC++;
			}
			
		}
		//System.out.println("남은 치즈 수 : " + cntC);
		while(cntC > 0) {
			visited = new boolean[r][c]; //외부 공기를 찾기 위해 사이클 마다 방문 배열 초기화
			findOuterAir(0, 0); // 외부공기 찾기
			//debug();
			Queue<int[]> willMelt = new ArrayDeque<>();
			for (int row = 0; row < r; row++) {
				for (int col = 0; col < c; col++) {
					if (cheese[row][col] == 1) {
						int cntA = 0;
						
						for (int d = 0; d < 4; d++) {
							int nx = row + dx[d];
							int ny = col + dy[d];
							
							if (!isRange(nx, ny)) continue;
							if (cheese[nx][ny] == 2) cntA++;
						}	
						if (cntA >= 2) {
							willMelt.add(new int[] {row,col});
						}
					}
				}
			}
			if (willMelt.isEmpty()) break;
			
			while(!willMelt.isEmpty()) {
				int[] melt = willMelt.poll();
				cheese[melt[0]][melt[1]] = 2;
				cntC--;
			}
			//System.out.println("남은 치즈 수 : " + cntC);
			time++;
		}
		System.out.println(time);
	}
	
	
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = { 0, 0,-1, 1};
	
	/**
	 * DFS로 외부 공기인지 아닌지 판별한다.
	 * @param x
	 * @param y
	 */
	public static void findOuterAir(int x , int y) {
		visited[x][y] = true;
		cheese[x][y] = 2; // 외부공기라는 의미로 바꿔줌
		
		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			if (!isRange(nx, ny)) continue; 
			if (visited[nx][ny] || cheese[nx][ny] == 1) continue;
			findOuterAir(nx,ny);
		}
	}
	
	
	/**
	 * 해당 좌표값이 범위값인지 판단
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isRange(int x, int y) {
		return x >= 0 && x < r && y >= 0 && y < c;
	}

	/**
	 * 디버깅 메서드
	 */
	public static void debug() {
		System.out.println();
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				System.out.print(cheese[i][j] + " ");
			}
			System.out.println();
		}
	}
	
}
