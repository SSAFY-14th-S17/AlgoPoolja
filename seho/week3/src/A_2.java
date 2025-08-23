

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 벽돌깨기
 * 
 * 구슬 N번만 쏠 수 있고 벽돌 정보는 W x H 배열로 주어짐 (0은 빈공간 그 외는 벽돌)
 * 
 * 규칙
 * 1. 구슬은 좌, 우로만 움질일 수 있고 맨 위의 벽돌만 깨트릴 수 있음
 * 2. 벽돌은 숫자 1 ~ 9로 표현 구슬이 명중한 벽돌은 상하좌우로 (벽돌에 적힌 숫자 - 1)칸 만큼 같이 제거
 * 3. 제거 범위 내에 있는 벽돌은 동시에 제거됨
 * 4. 빈 공간이 있을 경우 벽돌은 밑으로 떨어지게 됨
 * 
 * N개의 벽돌을 떨어트려 최대한 많은 벽돌을 제거
 * N, W, H 그리고 벽돌 정보가 주어질 때 남은 벽돌 개수를 구하기
 */

/*
1
3 10 10
0 0 0 0 0 0 0 0 0 0
1 0 1 0 1 0 0 0 0 0
1 0 3 0 1 1 0 0 0 1
1 1 1 0 1 2 0 0 0 9
1 1 4 0 1 1 0 0 1 1
1 1 4 1 1 1 2 1 1 1
1 1 5 1 1 1 1 2 1 1
1 1 6 1 1 1 1 1 2 1
1 1 1 1 1 1 1 1 1 5
1 1 7 1 1 1 1 1 1 1
 */

public class A_2 {
	// 상 하 좌 우
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static int N;
	static int W;
	static int H;
	
	static int min;
	
	public static int check(int[][] matrix) {
		int count = 0;
		
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if (matrix[i][j] != 0) {
					count++;	
				}
				 
			}
		}
		
		return count;
	}
	
	// 벽돌 깨기(row 좌표를 받아야함)
	public static void breakBrick(int[][] matrix, int col) {
		Queue<int[]> queue = new LinkedList<>();
		
		// 배열 범위 밖이 아니거나 0이 아닌경우까지 아래로 내리면서 탐색
		int startRow = -1;
		for (int i = 0; i < H; i++) {
			if (matrix[i][col] != 0) {
				startRow = i;
				break;
			}
		}
		
		if (startRow == -1) {
			return;
		}
		
		queue.add(new int[] {startRow, col, matrix[startRow][col]});
		matrix[startRow][col] = 0;
		
		while (!queue.isEmpty()) {
			int[] cur = queue.poll();
			
			int r = cur[0];
			int c = cur[1];
			int power = cur[2];
			
			// matrix[nr][nc]가 0이 아닌 경우
			// 1보다 크면 델타탐색으로 탐색 후 queue에 추가
			for (int i = 0; i < 4; i++) {
				for (int j = 1; j < power; j++) {
					int nr = r + dr[i] * j;
					int nc = c + dc[i] * j;
					
					// 범위 체크
					if (nr >= 0 && nr < H && nc >= 0 && nc < W) {
						if (matrix[nr][nc] > 0) { // 벽돌 존재
							// 큐에 추가 후 벽돌 제거
							// 1이면 제거만 1이상이면 추가
							if (matrix[nr][nc] > 1) {
								queue.add(new int[] {nr, nc, matrix[nr][nc]});
							}
							matrix[nr][nc] = 0;
						}
					}			
				}
			}
		}
	}
	
	// 벽 아래로 정렬
	public static void gravity(int[][] matrix) {
		for (int i = 0; i < W; i++) {
			Queue<Integer> bricks = new LinkedList<>();
			// 아래서부터 벽돌을 큐에 담는다.
			for(int j = H - 1; j >= 0; j--) {
				if(matrix[j][i] != 0) {
					bricks.add(matrix[j][i]);
				}
			}
			
			// 해당 열 모두 초기화
			for (int j = 0; j < H; j++) {
				matrix[j][i] = 0;
			}
			
			// 아래서 부터 다시 담는다.
			int r = H - 1;
			while (!bricks.isEmpty()) {
				matrix[r--][i] = bricks.poll();
			}
		}
	}
	
	// 벽돌 정렬하기	
	public static void dfs(int[][] matrix, int depth) {
		if (depth == N) {
			int count = check(matrix);
			min = Math.min(min, count);
			return;
		}
		
		for (int i = 0; i < W; i++) {
			int[][] newMatrix = new int[H][W];
			for (int j = 0; j < H; j++) {
				System.arraycopy(matrix[j], 0, newMatrix[j], 0, W);
			}
			
			// 벽돌 부수기
			breakBrick(newMatrix, i);
			
			// 벽돌 내리기
			gravity(newMatrix);
			
			// 다음 쏘러 가기
			dfs(newMatrix, depth + 1);
		}
	
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			int[][] matrix = new int[H][W];
			
			for (int i = 0; i < matrix.length; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < matrix[i].length; j++) {
					matrix[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			min = Integer.MAX_VALUE;
		
			dfs(matrix, 0);
			System.out.println("#" + tc + " " + min);
		}
	}
}
