package swea;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

//문제를 보고 어떻게 접근해야하는지 설계하는 법을 잘 정리해보자
//여기에서는 Cell 객체를 이용해서 문제를 풀 수 있다는 걸 배웠다. (이전에는 arr기반 bfs로 탐색하려고 했었음)
//이 문제는 큐를 쓰는 것은 맞지만 bfs는 아니다. -> 아닌가 큐를 빌 때까지 돌리는거면 bfs 문제인건가?
public class 줄기세포배양_5653 {
	static int[] di = { -1, 1, 0, 0 };
	static int[] dj = { 0, 0, -1, 1 };

	static int N, M, K;
	static int[][] board;
	static int OFFSET = 300;
	static int SIZE = 700;

	// 객체를 만들어서 해야 하는구만
	static class Cell  {
		int x, y;
		int life; // 생명력 (고정)
		int time; // 남은 시간
		int state; // 0 : 비활성, 1 : 활성

		Cell(int x, int y, int life, int time, int state) {
			this.x = x;
			this.y = y;
			this.life = life;
			this.time = time;
			this.state = state;
		}


	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("src/input.txt"));

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// bufferedReader에서 readline하고 정수형으로 파싱하기
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			// br 입력값 처리하기
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			board = new int[SIZE][SIZE];

			//큐 생성
			Queue<Cell> q = new LinkedList<>();

			// 초기 세포 입력
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					int life = Integer.parseInt(st.nextToken());
					// 값이 있다면(0이 아니라면)
					if (life > 0) {
						// 배열의 중앙에 위치시키기 위해 offset을 더해서 넣어준다.
						int x = i + OFFSET;
						int y = j + OFFSET;
						board[x][y] = life;
						// 값이 있으면 새로운 cell 객체를 만들어 우선순위 큐에 저장
						// 처음엔 비활성으로 저장
						q.add(new Cell(x, y, life, life, 0));

					}
				}
			}

			// for문으로 시뮬레이션 돌리기
			for (int t = 0; t < K; t++) {
				// 하루마다 한번에 업데이트해야하기 때문에 ArrayList에 넣고 한꺼번에 업데이트 하기
				List<Cell> nextTurn = new ArrayList<>();
				Map<String, Cell> candidates = new HashMap<>(); // hashmap으로 저장하는 법

				while (!q.isEmpty()) {
					Cell c = q.poll();

					// 비활성 상태라면
					if (c.state == 0) {
						if (c.time > 1) {
							nextTurn.add(new Cell(c.x, c.y, c.life, c.time - 1, 0));
						} else { // 비활성이 끝났기 때문에 활성으로 전환. time의 값은 초기로 만들기
							nextTurn.add(new Cell(c.x, c.y, c.life, c.life, 1));
						}
					} else if (c.state == 1) { // 활성 상태라면
						if (c.time == c.life) { // 활성된 첫 시간 -> 번식
							for (int d = 0; d < 4; d++) {
								int nx = c.x + di[d];
								int ny = c.y + dj[d];
								if (board[nx][ny] == 0) {
									// 이건 뭐지?
									String key = nx + "," + ny;
									// 이미 후보가 있으면 생명력 큰 세포가 차지
									if (!candidates.containsKey(key) || candidates.get(key).life < c.life) {
										candidates.put(key, new Cell(nx, ny, c.life, c.life, 0));
									}
								}
							}

						}
						if (c.time > 1) {
							nextTurn.add(new Cell(c.x, c.y, c.life, c.time - 1, 1));
						}
						// time이 1이면 죽기 때문에 큐에 넣지 않는다.
					}
				}
				// 경쟁 처리 끝난 후보들을 board에 확정 반영
				for (Cell newCell : candidates.values()) {
					board[newCell.x][newCell.y] = newCell.life;
					nextTurn.add(newCell);
				}
				// 다음 턴으로 교체
				q.addAll(nextTurn);

			}
			// 큐의 사이즈를 재면 된다? - 큐에 활성 / 비활성이 담기기 때문에
			int answer = q.size();

			System.out.println("#" + tc + " " + answer);
		}
	}
}

//우선순위큐가 무엇인지