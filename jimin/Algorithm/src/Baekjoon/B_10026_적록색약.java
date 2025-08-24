package Baekjoon;

import java.util.Scanner;

public class B_10026_적록색약 {
	static int n;
	static char[][] grid;
	static boolean[][] visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//입력
		n = sc.nextInt();		
		grid = new char[n][n];
		
		for(int i = 0; i < n; i++) {
			String text = sc.next();
			for(int j = 0; j < n; j++) {
				grid[i][j] = text.charAt(j);
			}
		}
		
		//로직
		//1. 적록색약 아닐 때 , R G B 를 찾고 visited 처리 해준다 
		visited = new boolean[n][n];
		int normalCount = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(!visited[i][j]) {
					dfs(i,j,grid[i][j], false); //정상인
					normalCount++;
				}
			}
		}
		
		//2. 적록색약 
		visited = new boolean[n][n];
		int cbCount = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(!visited[i][j]) {
					dfs(i, j, grid[i][j], true); //적록색약
					cbCount++;
				}
			}
		}

		//출력
		 System.out.println(normalCount + " " + cbCount);
	}
	
	public static void dfs(int x, int y, char color, boolean isColorBlind) {
		visited[x][y] = true;
		for(int h = 0; h < 4; h++) {
			int nx = x + dx[h];
			int ny = y + dy[h];
			
			// 1. 범위 벗어났으면 무시
			if (nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
			
			// 2. 이미 방문 했으면 무시
			if (visited[nx][ny]) continue;
			
            // 색 비교 로직
            if (isColorBlind) {
                // 적록색약: R과 G를 같은 색으로 취급
                if ((color == 'R' || color == 'G') && (grid[nx][ny] == 'R' || grid[nx][ny] == 'G')) {
                    // 같은 영역이므로 dfs 계속
                	dfs(nx, ny, grid[nx][ny], true);
                } else if (color == grid[nx][ny]) {
                	// 같은 색이므로 dfs 계속
                	dfs(nx, ny, grid[nx][ny], true);
                }
            } else {
                // 정상인: 같은 색만 인접 가능
                if (grid[nx][ny] == color) {
                    dfs(nx, ny, grid[nx][ny], false);
                }
            }

			
		}
	}

}
