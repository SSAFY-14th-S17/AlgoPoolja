import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

// 오목판정 완전탐색
// 육목 가능

public class IM_11315 {
					// 우, 하, 좌, 상, 우하, 좌하, 우상, 좌상
	static int[] dr = {0, 1, 0, -1, 1, 1, -1, -1};
	static int[] dc = {1, 0, -1, 0, 1, -1, 1, -1};
	
	private static boolean search(char[][] matrix, int row, int col) {
		for (int i = 0; i < 8; i++) {
			int cnt = 1;
			int nr = row;
			int nc = col;
			while (cnt <= 5) {
				nr += dr[i];
				nc += dc[i];
				
				if (nr >= matrix.length || nr < 0 || nc >= matrix.length || nc < 0 || matrix[nr][nc] != 'o') {
					break;
				}
				
				cnt++;
			}
			if (cnt >= 5) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		System.setIn(new FileInputStream("sample_input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			
			char[][] matrix = new char[N][N];
			
			boolean isValid = false;
			
			for (int i = 0; i < matrix.length; i++) {
				matrix[i] = br.readLine().toCharArray();
			}
			
			loop:
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix.length; j++) {
					if (matrix[i][j] == 'o') {
						isValid = search(matrix, i, j);
						if (isValid) {break loop;}
					}
				}
			}
			
			
			// 출력
			System.out.println("#" + tc + " " + (isValid ? "YES" : "NO"));
		}
		
	}

}
