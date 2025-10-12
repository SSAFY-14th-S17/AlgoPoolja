package week1_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
 * R×C인 격자판
 * 공기청정기는 항상 1번 열에 설치되어 있고, 크기는 두 행을 차지
 * 1. 미세먼지의 확산
 * (r, c)에 있는 미세먼지는 인접한 네 방향으로 확산
 * 인접한 방향에 공기청정기가 있거나, 칸이 없으면 그 방향으로는 확산이 일어나지 않음
 * 확산되는 양: ⌊Ar,c/5⌋
 * 남은 미세먼지의 양: Ar,c - ⌊Ar,c/5⌋×(확산된 방향의 개수) 
 * 
 * 2 공기청정기 작동
 * 위쪽 공기청정기의 바람은 반시계방향으로 순환하고, 아래쪽 공기청정기의 바람은 시계방향으로 순환
 * 바람이 불면 미세먼지가 바람의 방향대로 모두 한 칸씩 이동
 * 공기청정기로 들어가면 모두 정화됨
 * 
 * R, C, T (6 ≤ R, C ≤ 50, 1 ≤ T ≤ 1,000) 
 * Q:  T초가 지난 후 구사과 방에 남아있는 미세먼지의 양?
 * 
 * 
 * ------------
 * [풀이]
 * 1. 입력 받을 때 미리 공기청정기 위치 저장
 * upperPurifier & lowerPurifier(upperPurifier+1)
 * 
 * 2. T초 만큼의 반복문을 돌면서
 * 		3. 미세먼지 확산시키기 (모든 좌표 순회 -> 미세먼지가 있으면 4방향 델타 탐색으로 확산시키기)
 * 		4. 공기청정기 작동 (바람 방향으로 1만큼의 좌표 이동시키기)
 */
public class bj17144_미세먼지 {
	static int R,C,T;
	static int[][] grid;
	// 공기청정기는 모두 1열에 위치 -> 공기청정기의 행의 좌표
	static int upperPurifier= -1; // !!
	static int lowerPurifier; // 없어도 가능 (upperPurifier +1)
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] temp = br.readLine().split(" ");

		R = Integer.parseInt(temp[0]);
		C = Integer.parseInt(temp[1]);
		T = Integer.parseInt(temp[2]);
		grid = new int[R][C]; 

		for(int i=0; i<R; i++) {
			String[] line = br.readLine().split(" ");
			for(int j=0; j<C; j++) {
				grid[i][j] = Integer.parseInt(line[j]);
				if(grid[i][j]==-1 && upperPurifier==-1) { // 공기청정기 행의 좌표 미리 저장
					upperPurifier = i;
					lowerPurifier = i+1;
				}
			}
		}


		for(int i=0; i<T; i++) {
			spreadFineDust();
			runAirPurifier();
		}
		
		int total = 0;
		for (int i = 0; i < R; i++) {
		    for (int j = 0; j < C; j++) {
		        if (grid[i][j] > 0) total += grid[i][j];
		    }
		}
		System.out.println(total);


	}// main

	// 모든 칸을 돌면서 미세먼지를 델타 탐색으로 확산

	private static void runAirPurifier() {
		// 위쪽 공기청정기: 반시계 방향 순환
		// 아래 → 위
		for (int i = upperPurifier - 1; i > 0; i--) {
			grid[i][0] = grid[i - 1][0];
		}
		// 왼쪽 → 오른쪽
		for (int i = 0; i < C - 1; i++) {
			grid[0][i] = grid[0][i + 1];
		}
		// 위 → 아래
		for (int i = 0; i < upperPurifier; i++) {
			grid[i][C - 1] = grid[i + 1][C - 1];
		}
		// 오른쪽 → 왼쪽
		for (int i = C - 1; i > 1; i--) {
			grid[upperPurifier][i] = grid[upperPurifier][i - 1];
		}
		// 공기청정기 바로 오른쪽은 항상 0 (정화)
		grid[upperPurifier][1] = 0;

		// 아래쪽 공기청정기: 시계 방향 순환
		// 위 → 아래
		for (int i = lowerPurifier + 1; i < R - 1; i++) {
			grid[i][0] = grid[i + 1][0];
		}
		// 왼쪽 → 오른쪽
		for (int i = 0; i < C - 1; i++) {
			grid[R - 1][i] = grid[R - 1][i + 1];
		}
		// 아래 → 위
		for (int i = R - 1; i > lowerPurifier; i--) {
			grid[i][C - 1] = grid[i - 1][C - 1];
		}
		// 오른쪽 → 왼쪽
		for (int i = C - 1; i > 1; i--) {
			grid[lowerPurifier][i] = grid[lowerPurifier][i - 1];
		}
		// 공기청정기 바로 오른쪽은 항상 0 (정화)
		grid[lowerPurifier][1] = 0;
	}



	// 공기청정기 바람에 따라 미세먼지를 한 칸씩 이동
	private static void spreadFineDust() {
		int[][] temp = new int[R][C];
		// 공기청정기 위치 표시
		temp[upperPurifier][0] = -1;
		temp[lowerPurifier][0] = -1;

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (grid[i][j] > 0) {
					int spreadAmount = grid[i][j] / 5;
					int spreadCount = 0;
					for (int d = 0; d < 4; d++) {
						int nr = i + dr[d];
						int nc = j + dc[d];
						if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
						if (grid[nr][nc] == -1) continue;

						temp[nr][nc] += spreadAmount;
						spreadCount++;
					}
					temp[i][j] += grid[i][j] - (spreadAmount * spreadCount);
				}
			}
		}
		grid = temp;
	}


}
