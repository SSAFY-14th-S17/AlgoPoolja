

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 치즈
 * N * M
 * 내에 치즈가 있음
 * 4변 중 2변 이상이 실내온도의 공기와 접촉한 것은 한시간만에 녹아 없어짐
 * 
 * 치즈 내부 공간은 외부공기와 접촉하지 않는 것으로 가정
 * 입력으로 주어진 치즈가 모두 녹아 없어지는데 걸리는 정확한 시간 구하기
 * 
 * N과 M은 최대 100 * 100 격자판까지 가능
 * 공기 - 실내와 외부공기의 상태관리가 필요
 * 치즈 - 외부공기 2면과 닿아야함
 * 치즈가 녹기위해서는 인접 4방향 탐색후 2방향이 공기(외부공기)이여야 함
 * 
 * 문제 설계
 * 치즈의 개수를 파악하기 위한 카운트 변수 필요 (종료 조건 - 0이 되면 치즈가 다 녹은 것)
 * 치즈의 좌표값을 저장할 수 있는 공간 필요 최대 10000개까지 저장필요
 * 매 타임마다 격자판을 업데이트 해주어야함 (녹은 치즈를 0으로 처리해주어야함)
 * 
 * 실내 공기와 외부 공기를 구분할 수 있는 방법이 필요하다 <- 이 문제의 핵심
 * 처음 완탐으로 0,0 부터 인접 0의 공기 상태를 외부 상태로 변경 (0 -> 2)
 * 
 * 치즈가 인접 4방향 탐색 하는과정에서 2를 두개 찾으면 녹는 치즈 0이 있으면 치즈 포함 0과 인접한 모든 0들을 2로 변경
 * 
 */
public class gold3_2638 {
	static int N, M;
	static int[][] matrix;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	static ArrayDeque<int[]> cheese;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 외부 공기를 2로 변경하기 위해 가장자리 공간을 만들어 진행 (0,0은 무조건 외부 공기가 됨)
		matrix = new int[N + 2][M + 2];
		cheese = new ArrayDeque<>();
		
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= M; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
				if (matrix[i][j] == 1) {
					// 치즈 좌표 저장
					cheese.offer(new int[] {i, j, 0});
				}
			}
		}
		
		airSet(0, 0);
		
		System.out.println(melt());
		
	} // main
	
	public static int melt() {
		int t = 0;
		while(!cheese.isEmpty()) {			
			t++;
			int size = cheese.size();
			
			ArrayList<int[]> meltList = new ArrayList<>();
			
			for (int i = 0; i < size; i++) {
				int[] cur = cheese.poll();
				int r = cur[0];
				int c = cur[1];
				
				int cnt = 0;
				
				for (int dir = 0; dir < 4; dir++) {
					int nr = r + dr[dir];
					int nc = c + dc[dir];
					
					if (nr >= 1 && nr <= N && nc >= 1 && nc <= M) {
						if (matrix[nr][nc] == 2) {
							cnt++;
						}
					}
				}
				
				if (cnt >= 2) {
					meltList.add(new int[] {r, c});
				} else {
					cheese.offer(cur);
				}
				
			}
			
			for (int[] cur : meltList) {
				matrix[cur[0]][cur[1]] = 0;
				airSet(cur[0], cur[1]);
			}
		}
		
		return t;
	}
	
	public static void airSet(int row, int col) {
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		queue.offer(new int[] {row, col});
		matrix[row][col] = 2;
		
		while (!queue.isEmpty()) {
			int[] cur = queue.poll();
			int r = cur[0];
			int c = cur[1];
			
			for (int dir = 0; dir < 4; dir++) {
				int nr = r + dr[dir];
				int nc = c + dc[dir];
				
				if (nr >= 0 && nr < N + 2 && nc >= 0 && nc < M + 2) {
					if (matrix[nr][nc] == 0) {
						matrix[nr][nc] = 2;
						queue.offer(new int[] {nr, nc});
					}
				}
			}
		}
	}
}

/*
8 9
0 0 0 0 0 0 0 0 0
0 0 0 1 1 0 0 0 0
0 0 0 1 1 0 1 1 0
0 0 1 1 1 1 1 1 0
0 0 1 1 1 1 1 0 0
0 0 1 1 0 1 1 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
*/


