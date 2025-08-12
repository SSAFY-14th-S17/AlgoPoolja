import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	// 꽃 심는 범위 -> 델타를 활용한 이동
	static int[] di = { 0, 1, -1, 0, 0 };
	static int[] dj = { 0, 0, 0, 1, -1 };

	// 꽃을 심을 수 있는지 검사
	private static boolean canPlant(int x, int y, boolean[][] visited, int N) {
		for (int k = 0; k < 5; k++) {
			int nx = x + di[k];
			int ny = y + dj[k];
			// 범위를 벗어나면 안된다.
			if (nx < 0 || nx >= N || ny < 0 || ny >= N)
				return false;
			// 이미 방문한 곳이면 안된다.
			if (visited[nx][ny])
				return false;
		}
		return true;
	}

	// 꽃의 가격 구하기
	private static int plantCost(int x, int y, int[][] arr) {
		int sum = 0;
		for (int k = 0; k < 5; k++) {
			sum += arr[x + di[k]][y + dj[k]];

		}
		return sum;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);

		int N = sc.nextInt();

		int[][] arr = new int[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				arr[i][j] = sc.nextInt();
			}
		}

		// 이미 심은 자리를 표시하기 위한 visited 배열
		// boolean 배열의 기본값은 false
		boolean[][] visited = new boolean[N][N];

		// 최소가격을 저장하기 위한 변수
		// 초기값은 아주 큰 수로 지정하기
		int minPrice = 100000;

		// 세 개의 씨앗을 심기 위한 2차원 배열 삼중 for
		for (int i = 1; i < N - 1; i++) {
			for (int j = 1; j < N - 1; j++) {
				// 꽃을 심을 수 없다면 continue
				if (!canPlant(i, j, visited, N))
					continue;
				// 꽃을 심은 자리는 visited 배열에 true 해주기
				visited[i][j] = true;
				visited[i + 1][j] = true;
				visited[i - 1][j] = true;
				visited[i][j + 1] = true;
				visited[i][j - 1] = true;

				for (int x = 1; x < N - 1; x++) {
					for (int y = 1; y < N - 1; y++) {

						// 꽃을 심을 수 없다면 continue
						if (!canPlant(x, y, visited, N))
							continue;
						// 꽃을 심은 자리는 visited 배열에 true 해주기
						visited[x][y] = true;
						visited[x + 1][y] = true;
						visited[x - 1][y] = true;
						visited[x][y + 1] = true;
						visited[x][y - 1] = true;

						for (int r = 1; r < N - 1; r++) {
							for (int c = 1; c < N - 1; c++) {
								// 꽃을 심을 수 없다면 continue
								if (!canPlant(r, c, visited, N))
									continue;
								// 꽃을 심은 자리는 visited 배열에 true 해주기
								visited[r][c] = true;
								visited[r + 1][c] = true;
								visited[r - 1][c] = true;
								visited[r][c + 1] = true;
								visited[r][c - 1] = true;

								// 3개의 씨앗 값 구하기
								int cost = plantCost(i, j, arr) + plantCost(x, y, arr) + plantCost(r, c, arr);
								
								//현재 가격 vs minPrice 중에 최솟값 구하기 업데이트하기
								minPrice = Math.min(minPrice, cost);
								
								//세번째 꽃 복구
								visited[r][c] = false;
								visited[r + 1][c] = false;
								visited[r - 1][c] = false;
								visited[r][c + 1] = false;
								visited[r][c - 1] = false;
							}

						}
						//두번째 꽃 복구 
						visited[x][y] = false;
						visited[x + 1][y] = false;
						visited[x - 1][y] = false;
						visited[x][y + 1] = false;
						visited[x][y - 1] = false;

					}
				}
				visited[i][j] = false;
				visited[i + 1][j] = false;
				visited[i - 1][j] = false;
				visited[i][j + 1] = false;
				visited[i][j - 1] = false;
			}

		}

		System.out.println(minPrice);

	}

}