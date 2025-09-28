package week8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
 * N×M의 게임판
 * 같은 색으로 이루어진 공의 사이클이 있는지 판단
 * 
 * [사이클?]
 * 모든 k개의 점은 서로 다름 (k >= 4)
 * 모든 점의 색은 가음
 * 인접하다? 각각의 점이 들어있는 칸이 변을 공유함을 의미
 * 
 */

public class bj16929_TwoDots {
	static int N;
	static int M;
	static char[][] board;
	static boolean[][] visited;
	static int [] dx = {-1,1,0,0}; // 상하좌우
	static int [] dy = {0,0,-1,1};

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// [input]
		String[] tmp = br.readLine().split(" ");
		N = Integer.parseInt(tmp[0]); // x
		M = Integer.parseInt(tmp[1]); // y

		board = new char[N][M];
		visited = new boolean[N][M];


		for(int i=0; i<N; i++) {
			String temp = br.readLine();
			for(int j=0; j<M;j++) {
				board[i][j] = temp.charAt(j);
			}
		}

		//System.out.println(Arrays.deepToString(board));

		// [logic] & [output]
		// !! 모든 좌표에 대해 DFS 탐색!!
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(!visited[i][j]) {
					if(checkCycle(i,j,-1,-1,1)) {
						System.out.println("Yes");
						return;
					}
				}
			}
		}

		System.out.println("No");



	}

	/** 
	 * 사이클이 있는지 판별하는 함수 (dfs)
	 * @param x : 현재 보고 있는 x값 (행)
	 * @param y :  현재 보고 있는 값 (열)
	 * @param px : 현재 x의 부모 x좌표
	 * @param py :현재 x의 부모 y좌표
	 * @param depth: 같은 색의 공들 중 몇 번째 공인가? (사이클 판별 용도: k>=4)
	 */
	private static boolean checkCycle(int x, int y, int px, int py, int depth) {
		visited[x][y]=true;


		for(int d=0; d<4; d++) { // 0. 4방향 탐색
			int nx  = x + dx[d];
			int ny  = y + dy[d];

			if(nx>=0 && nx <N && ny >=0 && ny< M) { // 1. 인접한 칸이 보드 내의 범위인지
				if(board[nx][ny]==(board[x][y])) { // 2. 같은 색의 공인
					if(!visited[nx][ny]) { // 3-1. 아직 방문 X
						if(checkCycle(nx,ny,x,y,depth+1)) return true;
					}
					else { // 3-2. 이미 방문한 경우
						if (!(nx==px && ny==py) && depth>=4) {
							return true;
						}

					}
				}

			}


		}// for: 4d

		return false; //4d 탐색 완료 후 사이클 x

	}// f: checkCycle

}
