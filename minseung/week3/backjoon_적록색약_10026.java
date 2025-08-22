package backjoon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.System.Logger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class 적록색약_10026 {
	static int N;

	//
	static int[] di = { -1, 1, 0, 0 };
	static int[] dj = { 0, 0, -1, 1 };

	public static void main(String[] args) throws FileNotFoundException {
		// 적록색약은 빨강, 초록의 차이를 느끼지 못한다.

		System.setIn(new FileInputStream("src/input2.txt"));

		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();

		// 2차원 배열 생성
		char[][] board = new char[N][N];

		for (int i = 0; i < N; i++) {
			String str = sc.next();
			for (int j = 0; j < N; j++) {
				board[i][j] = str.charAt(j);
			}
		}

		// 2차원배열을 깊은 복사를 해서 쓴다.(deepCopy)
		// 방문숫자의 값을 + 1을 한다.

		// 델타탐색을 통해서 //있는지 탐색 -> 있으면 그 자리에 1을 넣기

		// 마지막 방문숫자의 값을 return한다.

		// 적록색약인이 봤을 때 경우의 수
		// 2차원 배열을 돌면서 다시 지정할지 or 한번만 돌면서 하는 방법이 있을 수 있는데,,,
		// 그냥 두번 돌려야겠다.

		// 적록색약인이 봤을 때 경우의 수
		// unVisibleNum = 0;

		// 일반인이 봤을 때 경우의 수

		// visited 2차원 배열 생성

		// 큐 생성
		Queue<int[]> q = new LinkedList<>();

		int visibleNum = 0;

		char[][] visibleArr = deepCopy(board);

		// 2차원 배열을 탐색하면서 그 자리의 값이 숫자가 아니면 (R,G,B 중에 하나면) 그것을 큐의 시작지점으로 넣는다.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {

				if (visibleArr[i][j] == 'R' || visibleArr[i][j] == 'G' || visibleArr[i][j] == 'B') {
					q.offer(new int[] { i, j });

					visibleNum += 1;

					while (!q.isEmpty()) {

						int[] node = q.poll();
						int x = node[0];
						int y = node[1];
						char color = visibleArr[x][y];
						visibleArr[x][y] = 'Z';
						// 델타탐색
						for (int k = 0; k < 4; k++) {
							int dx = x + di[k];
							int dy = y + dj[k];
							// 범위를 벗어나지 않는지 확인. 이미 방문한 곳인지 확인
							if (dx >= 0 && dx < N && dy >= 0 && dy < N && visibleArr[dx][dy] != 'Z'
									&& visibleArr[dx][dy] == color) {

								q.offer(new int[] { dx, dy });
							}
						}

					}
				}
			}
		}

		// 큐 생성
		q = new LinkedList<>();

		int inVisibleNum = 0;
		char[][] inVisibleArr = deepCopy(board);

		// 2차원 배열을 돌면서 R을 G로 바꾸기
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (inVisibleArr[i][j] == 'R') {
					inVisibleArr[i][j] = 'G';
				}
			}
		}

		// 2차원 배열을 탐색하면서 그 자리의 값이 숫자가 아니면 (R,G,B 중에 하나면) 그것을 큐의 시작지점으로 넣는다.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {

				if (inVisibleArr[i][j] == 'R' || inVisibleArr[i][j] == 'G' || inVisibleArr[i][j] == 'B') {
					q.offer(new int[] { i, j });

					inVisibleNum += 1;

					while (!q.isEmpty()) {

						int[] node = q.poll();
						int x = node[0];
						int y = node[1];
						char color = inVisibleArr[x][y];
						inVisibleArr[x][y] = 'Z';
						// 델타탐색
						for (int k = 0; k < 4; k++) {
							int dx = x + di[k];
							int dy = y + dj[k];
							// 범위를 벗어나지 않는지 확인. 이미 방문한 곳인지 확인
							if (dx >= 0 && dx < N && dy >= 0 && dy < N && inVisibleArr[dx][dy] != 'Z'
									&& inVisibleArr[dx][dy] == color) {

								q.offer(new int[] { dx, dy });
							}
						}

					}
				}
			}
		}

		System.out.println(visibleNum + " " + inVisibleNum);

	}

	private static char[][] deepCopy(char[][] board) {
		char[][] result = new char[N][N];
		for (int i = 0; i < N; i++) {
			System.arraycopy(board[i], 0, result[i], 0, N);
		}
		return result;
	}

}
