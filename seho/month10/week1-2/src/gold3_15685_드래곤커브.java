package Algorithm.Simulation.BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class gold3_15685_드래곤커브 {
	
	static boolean[][] matrix;
	
	static int[] dx = {1, 0, -1, 0};
	static int[] dy = {0, -1, 0, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		matrix = new boolean[101][101];
		
		int N = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			List<Integer> list = new ArrayList<>();
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int g = Integer.parseInt(st.nextToken());

			list.add(d);
			
			for (int j = 0; j < g; j++) {
				for (int k = list.size() - 1; k >= 0; k--) {
					int newD = list.get(k);
					list.add((newD + 1) % 4);
				}
			}
			
			matrix[y][x] = true;
			int curX = x;
			int curY = y;
			
			for (int newD : list) {
				curX += dx[newD];
				curY += dy[newD];
				
				if (curX >= 0 && curX <= 100 && curY >= 0 && curY <= 100) {
					matrix[curY][curX] = true;
				}
			}
		}
		
		// 꼭지점 체크
		int result = 0;
		
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (matrix[i][j] && matrix[i][j + 1] && matrix[i + 1][j] && matrix[i + 1][j + 1]) {
					result++;
				}
			}
		}
		
		System.out.println(result);
	}
}
