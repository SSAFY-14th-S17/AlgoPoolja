package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/*
 * 바이러스의 확산 막기 위해 연구소에 벽을 세우려 함
 * 연구소: N×M인 직사각형
 * 직사각형은 1×1 크기의 정사각형으로 나누어져 있음
 * 연구소는 빈 칸, 벽으로 이루어져 있으며, 벽은 칸 하나를 가득 차지함
 * 0: 빈 칸 / 1: 벽 / 2: 바이러스가 있는 곳
 * 바이러스는 상하좌우 인접한 빈칸으로 모두 퍼져나감
 * 새로 세울 수 있는 벽의 개수는 3개
 * 안전 영역: 벽을 3개 세운 뒤, 바이러스가 퍼질 수 없는 곳 (0의 개수)
 * Q. 연구소의 지도가 주어졌을 때 얻을 수 있는 안전 영역 크기의 최댓값을 구하여라
 * 
 * [문제풀이]
 * 1.백트래킹?
 * 현재 좌표에 1을 두냐 안 두냐 
 * 
 */

public class bj14502_연구소 {
	static int N, M;
	static int [][] map ;
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static int max = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// [input]
		String[] tmp = br.readLine().split(" ");


		N = Integer.parseInt(tmp[0]); // 세로, 행
		M = Integer.parseInt(tmp[1]); // 가로, 열
		map = new int[N][M];

		for(int i=0; i<N;i++) {
			String[] numbers= br.readLine().split(" ");

			for(int j=0; j<M;j ++) {
				map[i][j] = Integer.parseInt(numbers[j]);
			}
		}

		//System.out.println(Arrays.deepToString(map));

		// [logic]
		// 현재 좌표에 1을 두냐 안 두냐
		// 1이 아닌 곳에 두냐 / 안 두냐 -> 바이러스 퍼지기 -> 남아있는 0의 개수 
		// -> 최댓값 갱신
		dfs(0, 0);

		System.out.println(max);

	}

	/**
	 * 빈 공간에 벽을 세우는 함수
	 * @param start: 2차원 배열 -> 1차원 배열로 평탄화한 인덱스
	 * @param depth: 현재 벽을 몇 개 세웠는가
	 * @return 
	 */
	static void dfs(int start, int depth){

		// base-case
		if(depth==3) {
			// 바이러스 퍼지기
			// map 복사해서 전달! (원본 수정 X)
			int[][] mapCopy = new int [N][M];
			for(int i=0; i<N; i++) {
				System.arraycopy(map[i], 0, mapCopy[i], 0, M);
			}

			spreadVirus(mapCopy);

			// 안전 지대 최댓값
			checkSafeArea(mapCopy);

			return;// !!
		}

		// 2차원 배열 -> 1차원 배열 평탄
		for(int i=start; i<N*M; i++ ) {
			int x = i /M;
			int y = i % M;
			
			if(map[x][y] ==0) {
				map[x][y]=1;
				dfs(i+1,depth+1); // start+1 X
				map[x][y]=0;
			}
			
		}
	}




	/**
	 * 벽을 3개 세웠을 때 바이러스 퍼지기 å
	 * @param map: 3개의 벽을 세운 후의 맵
	 */
	private static void spreadVirus(int[][] mapCopy) {
		Queue<int[]> q = new ArrayDeque<>();

		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(mapCopy[i][j] == 2) {
					q.add(new int[] {i,j}); 
				}
			}
		}


		while(!q.isEmpty()) {
			int[] curr = q.poll();

			int x = curr[0];
			int y = curr[1];

			for(int d=0; d<4; d++) {
				int nx= x + dx[d];
				int ny = y + dy[d];

				if(nx>=0 && nx <N && ny>=0 && ny<M  && mapCopy[nx][ny]==0) {
					mapCopy[nx][ny]=2;
					q.add(new int[] {nx,ny});
					
				}

			}
		}

	}


	/** 
	 * 바이러스가 퍼진 후 안전 지대 개수의 최댓값 구하는 함
	 * @param map: 벽을 세운 후 바이러스가 퍼진 후의 맵
	 */
	private static void checkSafeArea(int[][] mapCopy) {
		int count =0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(mapCopy[i][j] ==0) {
					count++;
				}
			}
		}
		max = Math.max(count, max);
	}
}
