import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 토메이러
 * 
 * 토마토를 M * N 배열의 각 칸마다 하나씩 넣어 창고에 보관
 * 잘익은것과 안익은거 있음
 * 하루 지나면 익은 토마토 인접지역의 익지않은 토마토들이 익게됨(상하좌우)
 * 대각선x 저절로 익지않음
 * 창고에 보관된 토마토들이 며칠이 지나면 다 익는지 최소일 수를 알고싶음
 * 토마토 안들어 있는 칸도 있음(-1로 표시)
 * 
 * 토마토가 모두 못익으면 -1 출력
 */
public class gold5_7576 {
	static int[][] matrix;
	static int result;
	// 상 하 좌 우
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};	
	
	public static boolean check() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == 0) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static void bfs(Queue<int[]> queue) {
		while(!queue.isEmpty()) {
			int t = queue.size();
			int cnt = 0;
			for (int i = 0; i < t; i++) {
				int[] cur = queue.poll();
				for (int dir = 0; dir < 4; dir++) {
					int nr = cur[0] + dr[dir];
					int nc = cur[1] + dc[dir];
					
					if (nr < matrix.length && nc < matrix[0].length && nr >= 0 && nc >= 0) {
						if (matrix[nr][nc] == 0) {
							matrix[nr][nc] = 1;
							cnt++;
							queue.add(new int[] {nr, nc});
						}
					}
				}
			}
			if (cnt > 0) {
				result++;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		matrix = new int[M][N];
		// Queue 생성 후 현재 토마토 위치 전달
		Queue<int[]> queue = new LinkedList<>();
		
		// tomato 정보 저장
		for (int i = 0; i < matrix.length; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < matrix[i].length; j++) {
				 int tomato = Integer.parseInt(st.nextToken());
				 matrix[i][j] = tomato;
				 if (tomato == 1) {
					 queue.add(new int[]{i, j});
				 }
			}
		}
		
		// 시행횟수 저장할 변수 result
		result = 0;
		
		// 익은 틈메이러 증식
		bfs(queue);
		
		// 모두 익었는지 확인
		if (!check()) {
			// 모두 안익었으면 -1
			System.out.println(-1);
		} else {
			// 모두 익었으면 1
			System.out.println(result);
		}
	
	}
}
