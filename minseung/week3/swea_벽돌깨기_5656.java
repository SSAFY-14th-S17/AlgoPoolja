package swea;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class 벽돌깨기_5656 {

	static int minBrick;
	static int N;
	static int W;
	static int H;

	static int[] di = { -1, 1, 0, 0 };
	static int[] dj = { 0, 0, -1, 1 };

	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("src/input.txt"));
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			N = sc.nextInt();
			W = sc.nextInt();
			H = sc.nextInt();

			int[][] arr = new int[H][W];
			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					arr[i][j] = sc.nextInt();
				}
			}

			minBrick = Integer.MAX_VALUE;
			dfs(arr, 0);

			System.out.println("#" + tc + " " + minBrick);
		}
	}

	private static void dfs(int[][] arr, int depth) {
		if (depth == N) {
			int rest = countBricks(arr);
			if (rest < minBrick) {
				minBrick = rest;
			}
			return;
		}

		// <<<가장 위에 있는 벽돌 탐색>>>>

		for (int j = 0; j < W; j++) { // 각 열에 대해 구슬을 쏘는 경우 탐색
			int i = -1;
			for (int r = 0; r < H; r++) {
				if (arr[r][j] != 0) {
					i = r;
					break;
				}
			}

			if (i != -1) { // 벽돌이 있는 열이라면
				// 현재 배열 상태를 복사
				// <<<깊은 복사를 해야하는 이유>>> -> 각 경우의 수를 구할 때 영향을 받지 않기 위해서
				int[][] copyArr = deepCopy(arr);

				// BFS를 통해 벽돌 제거
				bfs(copyArr, i, j);

				// 벽돌 떨어뜨리기 --> 이 과정이 bfs와 dfs 중간에 있어야 한다.
				dropBricks(copyArr);

				// 다음 구슬 발사
				dfs(copyArr, depth + 1);
			} else { // 벽돌이 없는 열이라면, 이 경로도 탐색할 필요가 있음 (N번을 다 쏴야 하므로) -> 이 부분은 고려하지 못했다.
				dfs(arr, depth + 1);
			}
		}
	}

	private static void bfs(int[][] arr, int r, int c) {
		Queue<int[]> q = new LinkedList<>();

		// 첫 번째 벽돌 제거 후 큐에 추가
		if (arr[r][c] > 1) { // 숫자가 1보다 클 때만 고려하는 이유는 1일 때는 양옆에 영향을 주지 않기 때문에
			q.offer(new int[] { r, c, arr[r][c] });
		}
		// 큐에 넣은 다음에는 0으로 처리
		arr[r][c] = 0;

		while (!q.isEmpty()) {
			int[] node = q.poll();
			int x = node[0];
			int y = node[1];
			int scope = node[2];

			for (int s = 1; s < scope; s++) {
				for (int k = 0; k < 4; k++) {
					int dx = x + di[k] * s;
					int dy = y + dj[k] * s;

					if (dx >= 0 && dx < H && dy >= 0 && dy < W && arr[dx][dy] != 0) { // 인덱스 범위 검사 + 0이 아닐 때만 검사하도록하기...
						if (arr[dx][dy] > 1) {
							q.offer(new int[] { dx, dy, arr[dx][dy] });
						}
						arr[dx][dy] = 0; // 큐에 넣는 동시에 제거
					}
				}
			}
		}
	}

	// 벽돌을 아래로 내리기
	private static void dropBricks(int[][] arr) {
		for (int j = 0; j < W; j++) {
			Queue<Integer> q = new LinkedList<>();
			// 열에서 0이 아니면 큐에 담기
			for (int i = H - 1; i >= 0; i--) {
				if (arr[i][j] != 0) {
					q.offer(arr[i][j]);
					arr[i][j] = 0;
				}
			}
			// 큐의 값을 빼서 열에 채우기
			int i = H - 1;
			while (!q.isEmpty()) {
				arr[i--][j] = q.poll();
			}
		}
	}

	private static int countBricks(int[][] arr) {
		int count = 0;
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if (arr[i][j] != 0) {
					count++;
				}
			}
		}
		return count;
	}

	// 깊은 복사 진행하기
	private static int[][] deepCopy(int[][] original) {
		if (original == null) {
			return null;
		}
		int[][] result = new int[H][W];
		for (int i = 0; i < H; i++) {
			System.arraycopy(original[i], 0, result[i], 0, W);
		}

//		System.arraycopy는 자바에서 배열을 복사하는 내장 메서드로, 원자 단위로 배열을 복사하기 때문에 for 반복문보다 훨씬 빠르고 효율적입니다.
//
//		original[i]는 원본 배열의 i번째 행을 의미합니다. 이 행의 내용을...
//
//		result[i]로 복사합니다.
//
//		0과 0은 각각 원본 배열의 시작 인덱스와 대상 배열의 시작 인덱스를 나타냅니다.
//
//		W는 복사할 요소의 개수(배열의 열 길이)를 의미합니다.
		return result;
	}
}