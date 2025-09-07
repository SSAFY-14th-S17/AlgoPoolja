package Swea;

import java.util.Scanner;

public class Swea_1953_탈주범검거 {
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int[][] arr;
	static boolean[][] visited;
	static boolean[][] blue;
	static int n;
	static int m;
	static int r;
	static int c;
	static int l;
	static int sum;
	
	public static void main(String[] args) {
		/*
		 * 탈주범은 시간당 1의 거리를 움직일 수 있다.
		 * 내가 생각했을 땐 파이프 별로 상하좌우에 뭐가 오면 연결이 되고 안되는지를 정의를 해놔야 할 것 같음
		 * 
		 * */
		//          상          하          좌          우
		// { 1 : { {2,5,6}  , {2,4,7}  , {3,4,5}  , {3,6,7}   } }
		// { 2 : { {1,5,6}  , {1,4,7}  ,  -       ,  -        } }
		// { 3 : {  -       ,   -      , {1,4,5}  , {1,6,7}   } }
		// { 4 : { {1,2,5,6},   -      ,  -       , {1,3,6,7} } }
		// { 5 : {  -       , {1,2,4,7},  -       , {1,3,6,7} } }
		// { 6 : {  -       , {1,2,4,7}, {1,3,4,5},  -        } }
		// { 7 : { {1,2,5,6},   -      , {1,3,4,5},  -        } }

		
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		
		for (int tc = 1; tc <= T; tc++) {
			//입력
			n = sc.nextInt(); //지도 세로 크기 
			m = sc.nextInt(); //지도 가로 크기
			r = sc.nextInt(); //멘홀 뚜껑 세로 위치
			c = sc.nextInt(); //멘홀 뚜껑 가로 위치
			l = sc.nextInt(); //탈출 후 걸리는 시간 
			
			arr = new int[n][m];
			
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					arr[i][j] = sc.nextInt();
				}
			}
			
			visited = new boolean[n][m];
			blue = new boolean[n][m];
			
			sum = 1; //1시간일 때는 그냥 start 지점 더하면 돼
			visited[r][c] = true;
			blue[r][c] = true;
			
			for(int h = 1; h < l; h++) {
				boolean[][] nextBlue = new boolean[n][m]; // 다음 턴 후보
				for(int i = 0; i < n; i++) {
					for(int j = 0; j < m; j++) {
						if(blue[i][j]) {
							find(i, j, nextBlue);
						}
					}
				}
				blue = nextBlue;
			}
			
			System.out.println("#" + tc + " " + sum);

		}
	}
	
	public static void find(int x, int y, boolean[][] nextBlue) {
		int cur = arr[x][y]; // 현재 파이프 타입
		
		for(int h = 0; h < 4; h++) {
			int nx = x + dx[h];
			int ny = y + dy[h];
			
			if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;			
			if (arr[nx][ny] == 0 || visited[nx][ny] == true) continue;
			
			boolean isConnected = false;

	        switch (h) {
	            case 0: // 위로 이동
	                // 현재 파이프가 위로 열려있고, 다음 파이프가 아래로 열려있는가?
	                if ((cur == 1 || cur == 2 || cur == 4 || cur == 7) && 
	                    (arr[nx][ny] == 1 || arr[nx][ny] == 2 || arr[nx][ny] == 5 || arr[nx][ny] == 6)) {
	                    isConnected = true;
	                }
	                break;
	            case 1: // 아래로 이동
	                // 현재 파이프가 아래로 열려있고, 다음 파이프가 위로 열려있는가?
	                if ((cur == 1 || cur == 2 || cur == 5 || cur == 6) && 
	                    (arr[nx][ny] == 1 || arr[nx][ny] == 2 || arr[nx][ny] == 4 || arr[nx][ny] == 7)) {
	                    isConnected = true;
	                }
	                break;
	            case 2: // 좌측으로 이동
	                // 현재 파이프가 왼쪽으로 열려있고, 다음 파이프가 오른쪽으로 열려있는가?
	                if ((cur == 1 || cur == 3 || cur == 6 || cur == 7) && 
	                    (arr[nx][ny] == 1 || arr[nx][ny] == 3 || arr[nx][ny] == 4 || arr[nx][ny] == 5)) {
	                    isConnected = true;
	                }
	                break;
	            case 3: // 우측으로 이동
	                // 현재 파이프가 오른쪽으로 열려있고, 다음 파이프가 왼쪽으로 열려있는가?
	                if ((cur == 1 || cur == 3 || cur == 4 || cur == 5) && 
	                    (arr[nx][ny] == 1 || arr[nx][ny] == 3 || arr[nx][ny] == 6 || arr[nx][ny] == 7)) {
	                    isConnected = true;
	                }
	                break;
	        }

	        // 연결이 확인되면 방문 처리 및 합계 증가 (중복 코드 제거)
	        if (isConnected) {
	            visited[nx][ny] = true;
	            nextBlue[nx][ny] = true;
	            sum++;
	        }
		}
		
	}

}
