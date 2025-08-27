

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 오 나의 여신님
 * 
 * 2초, 256MB
 * 
 * 악마의 손아귀를 피하면서 안전지역에 도달하는 최소 시간 구하기
 * 
 * N, M의 전체 지도 정보
 * 수연이 위치 S 여신 공간 D 돌의 위치 X 악마 *
 * .는 평범한 지역 - 이동 가능 / 악마의 손아귀 스킬 확장 가능
 * S, D는 한번만 나타남 
 * 
 * 악마의 손아귀를 벗어나면서 안전지역 가는 최소 시간 출력
 * 불가능 시 GAME OVER 출력
 * 
 * 악마의 손아귀는 매초 상하좌우 인접지역 확장
 * 
 * 
 */
public class D5_7793 {
	static int N, M;
	static char[][] map;
	static Queue<int[]> human;
	static Queue<int[]> devil;
	static int[] goddess;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			map = new char[N][M];
			
			human = new LinkedList<>();
			devil = new LinkedList<>();
			
			// 기록하면서 수연, 악마, 여신 위치 저장
			for (int i = 0; i < N; i++) {
				String str = br.readLine();
				for (int j = 0; j < M; j++) {
					char c = str.charAt(j);
					if (c == 'S') {
						human.add(new int[] {i, j});
					} else if (c == '*') {
						devil.add(new int[] {i, j});
					}
					map[i][j] = str.charAt(j);
				}
			}
			int result = bfs();
			
			System.out.println("#" + tc + " " + (result != -1 ? result : "GAME OVER"));
		}
	} // main
	
	public static int bfs() {
		int t = 0;
		
		while(!human.isEmpty()) {
			t++;
			
			int devilSize = devil.size();
			int humanSize = human.size();
			
			// 악마의 손아귀 확장
			for (int i = 0; i < devilSize; i++) {
				int[] cur = devil.poll();
				int row = cur[0];
				int col = cur[1];
				
				for (int dir = 0; dir < 4; dir++) {
					int nr = row + dr[dir];
					int nc = col + dc[dir];
					
					if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] == '.') {
						// 악마 손아귀 확장
						map[nr][nc] = '*';
						devil.add(new int[] {nr, nc});
					}
				}
			}
			
			// 수연 이동
			for (int i = 0; i < humanSize; i++) {
				int[] cur = human.poll();
				int row = cur[0];
				int col = cur[1];
				
				for (int dir = 0; dir < 4; dir++) {
					int nr = row + dr[dir];
					int nc = col + dc[dir];
					
					if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
						// 도착하면 걸린 시간 반환
						if (map[nr][nc] == 'D') {
							return t;
						} else if (map[nr][nc] == '.') {
							// 이동 가능 지역이면 이동
							map[nr][nc] = 'S';
							human.add(new int[] {nr, nc});							
						}
						
					}
				}
			}
		}
		
		return -1;
	}
}
