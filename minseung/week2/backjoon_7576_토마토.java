package backjoon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

//고친부분 : 토마토를 바로바로 익히면 안됨(같은 날 생긴 토마토가 다음 좌표 전파에 사용되기 때문에)
//임시리스트에 익을 토마토를 저장하고 하루가 끝나면 한번에 1로 변경

public class 토마토_7576 {
	static int days;
	static int[][] arr;
	static int N;
	static int M;

	static int[] di = { -1, 1, 0, 0 };
	static int[] dj = { 0, 0, -1, 1 };

	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("src/input.txt"));

		Scanner sc = new Scanner(System.in);

		M = sc.nextInt(); // 가로 칸 수
		N = sc.nextInt(); // 세로 칸 수

		arr = new int[N][M];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				arr[i][j] = sc.nextInt();
			}
		}

		// 지금 익은 토마토를 큐에 넣기
		Queue<int[]> q = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (arr[i][j] == 1) {
					q.offer(new int[] { i, j, days });
				}
			}
		}
		int days = 0;

		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int ci = cur[0];
			int cj = cur[1];
			int day = cur[2];
			days = Math.max(days, day); // 이 연산은 왜 하는거지?

			for (int k = 0; k < 4; k++) {
				int ni = ci + di[k];
				int nj = cj + dj[k];

				if (ni >= 0 && ni < N && nj >= 0 && nj < M && arr[ni][nj] == 0) {
					arr[ni][nj] = 1;
					q.add(new int[] { ni, nj, day + 1 });
				}
			}

		}
		
		//익지 않은 토마토가 있는지 확인
		for (int i =0; i <N; i++) {
			for (int j = 0; j < M; j++) {
				if (arr[i][j] == 0) {
					System.out.println(-1);
					return;
				}
			}
		}
		System.out.println(days);

	}

}
