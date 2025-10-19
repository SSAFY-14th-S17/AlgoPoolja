

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 알파벳
 */
public class gold4_1987_알파벳 {
	static int R, C;
	static char[][] matrix;
	static int result;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static Set<Character> move;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		matrix = new char[R][C];
		for (int i = 0; i < R; i++) {
			matrix[i] = br.readLine().toCharArray();
		}
		
		move = new HashSet<>();
		result = 0;
		
		move.add(matrix[0][0]);
		dfs(0, 0);
		
		System.out.println(result);
	}
	
	public static void dfs(int r, int c) {
		result = Math.max(result, move.size());
		
		for (int dir = 0; dir < 4; dir++) {
			int nr = r + dr[dir];
			int nc = c + dc[dir];
			
			if (nr < 0 || nr >= R || nc < 0 || nc >= C) {
				continue;
			}
			
			if (!move.contains(matrix[nr][nc])) {
				move.add(matrix[nr][nc]);
				dfs(nr, nc);
				move.remove(matrix[nr][nc]);
			}
		}
	}
}
