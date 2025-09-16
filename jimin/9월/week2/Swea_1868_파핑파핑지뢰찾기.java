package Swea;

import java.util.Scanner;

public class Swea_1868_파핑파핑지뢰찾기 {
	static char arr[][];
	static boolean visited[][];
	static int n;
	static int[] dx = {-1,1,0,0,-1,-1,1,1}; 
	static int[] dy = {0,0,-1,1,-1,1,-1,1};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int T = sc.nextInt();
		
		for (int tc = 1; tc <= T; tc++) {
			n = sc.nextInt();
			arr = new char[n][n];
			visited = new boolean[n][n];
			
			for (int i = 0; i < n; i++) {
				String line = sc.next();
				for (int j = 0; j < n; j++) {
					arr[i][j] = line.charAt(j);
				}
			}
			
			//로직
            // Step1: 숫자판 만들기
            makeNumberBoard();
            
            int count = 0;

            // Step2: 0칸부터 DFS 돌리기
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (arr[i][j] == '0' && !visited[i][j]) { //arr[i][j] == '0' 주변에 지뢰가 없느 빈 칸이고 아직 열리지 않은 칸이면 
                        dfs(i, j); //연쇄적으로 주변 칸들(0과 그 주변 숫자칸들)까지 다 열어줌
                        count++; //0 칸 하나 클릭 = 한 번 클릭 추가
                    }
                }
            }
            
            // Step3: 아직 방문 안 된 숫자칸 개별 클릭
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (arr[i][j] != '*' && !visited[i][j]) { //지뢰가 아니면서(!= '*'), 아직 열리지 않은 칸 == 이 칸들은 모두 숫자칸(1~8)
                        count++; // 0을 클릭해도 자동으로 안 열리니까 직접 클릭
                    }
                }
            }
			
			System.out.println("#" + tc + " " + count);
		}
	}
	
    // 숫자판 만들기
    public static void makeNumberBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
            	// 지뢰 없는 칸을 눌러야 하기 때문에 (지뢰 있는 칸 누르면 게임 끝나버림)
                if (arr[i][j] == '.') { // 지뢰 없는 칸이면 .. 
                    int mineCount = 0; // 그 주변 8방향 지뢰 개수 세기
                    for (int d = 0; d < 8; d++) {
                        int nx = i + dx[d];
                        int ny = j + dy[d];
                        if (nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                        if (arr[nx][ny] == '*') mineCount++; //지뢰면 카운트 업 
                    }
                    arr[i][j] = (char)(mineCount + '0'); // '0' ~ '8' 지뢰 개수로 값 바꾸기 
                }
            }
        }
    }
	
	public static void dfs(int x, int y) {
		visited[x][y] = true;
				
		if (arr[x][y] == '0') { // 0일 때만 주변 확장
	        for (int h = 0; h < 8; h++) {
	            int nx = x + dx[h];
	            int ny = y + dy[h];

	            if (nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
	            if (visited[nx][ny]) continue;

	            dfs(nx, ny);
	        }
	    }
	}
}
