package backjoon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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

		days = 0;

		if (!tomatoCheck(arr)) { // 불가능한 상황
			days = -1;
		} else {
			tomato(arr);
		}

		// 무한재귀 미쳤다

		System.out.println(days);
	}

	private static void tomato(int[][] arr) {
		if (!zeroExist(arr)) {
			return;
		}

		ArrayList<int[]> todayTomato = new ArrayList<>();

		// 하루 더 카운트 -> 여기에 하는 게 맞나?
		days += 1;
		// 1이 있는 경우를 탐색
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {

				if (arr[i][j] == 1) {
					// 토마토가 있다면 사방을 돌아서 1로 만들기
					for (int k = 0; k < 4; k++) {
						// 인덱스 범위를 벗어나지 않는지 확인
						if (isPossible(i + di[k], j + dj[k]) && arr[i + di[k]][j + dj[k]] == 0) {
							// 토마토가 없다면 1으로 바꾸면 안됨

							todayTomato.add(new int[] { i + di[k], j + dj[k] });

						}
					}
				}
			}
		}

		// 탐색이 끝나면 토마토 업데이트
		for (int i = 0; i < todayTomato.size(); i++) {
			int[] idx = todayTomato.get(i);
			arr[idx[0]][idx[1]] = 1;
		}

		tomato(arr);
	}

//양 사방에 -1이 있다면 
	private static boolean tomatoCheck(int[][] arr) {

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				boolean check = false;
				for (int k = 0; k < 4; k++) {
					if (isPossible(i + di[k], j + dj[k])) {
						if (arr[i + di[k]][j + dj[k]] != -1) {
							check = true;
						}
					}
				}
				 if (check == false && arr[i][j] == 0) { //0 사이에 -1이 둘러싸야 있어야 함. 가운데에 1이나 -1이 있으면 해당 안됨
					return false;
				}
			}
		}

		return true;
	}

	private static boolean isPossible(int i, int j) {
		if (i >= N || j >= M || i < 0 || j < 0) {
			return false;
		}
		return true;

	}

	private static boolean zeroExist(int[][] arr) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (arr[i][j] == 0) {
					return true;
				}
			}
		}

		return false;
	}

}
