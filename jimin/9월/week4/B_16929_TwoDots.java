package Baekjoon;

import java.util.Scanner;

public class B_16929_TwoDots {
	static int n, m;
	static char[][] arr;
	static boolean[][] visited;
 	static int[] dx = {-1,1,0,0};
 	static int[] dy = {0,0,-1,1};
 	static boolean foundCycle;
 	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//입력
		n = sc.nextInt();
		m = sc.nextInt();
		arr = new char[n][m];
		visited = new boolean[n][m];
		
		for (int i = 0; i < n; i++) {
			String line = sc.next();
			for (int j = 0; j < m; j++) {
				arr[i][j] = line.charAt(j);
			}
		}
		
		//로직 
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if(!visited[i][j]) dfs(i, j, -1, -1, 1);	
				if (foundCycle) break;
			}
		}
		
		//출력 
		System.out.println(foundCycle ? "Yes" : "No");
	}
	
	//fromX , fromY 부모 좌표  /  depth 현재까지 이동한 칸 수 
	private static void dfs(int x, int y, int fromX, int fromY, int depth) {
		visited[x][y] = true; //방문처리 
		
		for (int h = 0; h < 4; h++) {
			int nx = x + dx[h];
			int ny = y + dy[h];
			
			if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue; //격자 범위 벗어나면 continue
			if (arr[x][y] != arr[nx][ny]) continue; //같은 알파벳이 아니면 continue
			if (nx == fromX && ny == fromY) continue; //바로 이전 칸으로 돌아가려는 경우 continue
			
			//방문처리 
			if (!visited[nx][ny]) {
				//같은 알파벳이고, 아직 방문하지 않았으면
				dfs(nx, ny, x, y, depth+1);
			} else {
				//이미 방문했고(visited==true), depth >= 4라면 사이클 발견
				// depth가 4 이상 일 때 사이클 인 이유는 사이클이 만들어지려면 최소 4개의 점이 필요하기 때문 
	            if (depth >= 4) {
	                foundCycle = true;
	                return;
	            }
			}
			
			if (foundCycle) break;
		}
		
	}

}
