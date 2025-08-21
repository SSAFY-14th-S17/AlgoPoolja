import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 빙고
 * 이차원 배열 탐색 3줄이 되는 순간 빙고
 */
public class silver4_2578 {
	static int[][] matrix;
	static int bingo;
	static int row;
	static int col;
	static int bingoCount = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		matrix = new int[5][5];
		
		for (int i = 0; i < 5; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 5; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 사회자가 부르는 수를 찾아 0으로 바꾸고 완전탐색으로 빙고가 완성되어있는지 확인
		// 빙고가 완성되면 빙고 카운트를 1 증가
		// 빙고가 3이되면 현재 사회자가 부른 수를 출력
		int result = 0;
		bingo = 0;
		label:
		for (int i = 0; i < 5; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 5; j++) {
				int target = Integer.parseInt(st.nextToken());
				result++;
				
				if (checkTarget(target)) {
					bingoSearch();
				}
				
				if (bingoCount >= 3) {
					System.out.println(result);
					break label;
				}
				
			}
		}
	}
	
	public static boolean checkTarget(int target) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (matrix[i][j] == target) {
					matrix[i][j] = 0;
					return true;
				}
			}
		}
		
		return false;
	}
	
	// 전체 검사
	public static void bingoSearch() {
		bingoCount = 0; // 검사 전 항상 0으로 초기화

        // 가로 검사
        for (int i = 0; i < 5; i++) {
            int zeroCount = 0;
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == 0) {
                    zeroCount++;
                }
            }
            if (zeroCount == 5) {
                bingoCount++;
            }
        }

        // 세로 검사
        for (int i = 0; i < 5; i++) {
            int zeroCount = 0;
            for (int j = 0; j < 5; j++) {
                if (matrix[j][i] == 0) {
                    zeroCount++;
                }
            }
            if (zeroCount == 5) {
                bingoCount++;
            }
        }

        // 대각선 검사 (왼쪽 위 -> 오른쪽 아래)
        int zeroCount = 0;
        for (int i = 0; i < 5; i++) {
            if (matrix[i][i] == 0) {
                zeroCount++;
            }
        }
        if (zeroCount == 5) {
            bingoCount++;
        }

        // 대각선 검사 (오른쪽 위 -> 왼쪽 아래)
        zeroCount = 0;
        for (int i = 0; i < 5; i++) {
            if (matrix[i][4 - i] == 0) {
                zeroCount++;
            }
        }
        if (zeroCount == 5) {
            bingoCount++;
        }
	}
}