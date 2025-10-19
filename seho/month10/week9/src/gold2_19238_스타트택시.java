import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 스타트 택시
 * 
 * 손님 출발지점 -> 도착지점 map으로 관리 도착하면 삭제
 * map.size가 0이면 종료
 * 
 * 주변 손님 탐색 or 도착지점 탐색 -> BFS 구현
 * 도착지점까지 최단거리 이동 -> 다익스트라 구현
 */

public class gold2_19238_스타트택시 {
	static int N, M, F;
	static Map<Integer, int[]> map;
	static int[][] matrix;
	static int[] taxi;
	
	static boolean isValid;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	static final int INF = 987654321;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		F = Integer.parseInt(st.nextToken());
		
		matrix = new int[N][N];
		
		map = new HashMap<>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(br.readLine());
		taxi = new int[] {Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1};
		
		for (int i = 2; i < M + 2; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			
			matrix[a - 1][b - 1] = i;
			map.put(i, new int[] {c - 1, d - 1});
		}
		
		isValid = true;
		
		for (int i = 0; i < M; i++) {
			int[] tmp = findPassanger();
			// 가져온 좌표값이 그대로 -1, -1면 승객을 태울 수 없는 상황
			if (tmp[0] == -1 && tmp[1] == -1) {
				isValid = false;
				break;
			}
			
			F -= tmp[2];
			// 연료를 소모하여 목적지까지 이동하지 못하는 경우
			if (F < 0) {
				isValid = false;
				break;
			}
			
			
			// 택시 위치 승객위치로 이동
			taxi[0] = tmp[0];
			taxi[1] = tmp[1];
			// 목적지
			int[] goal = map.get(matrix[tmp[0]][tmp[1]]);
			// 승객을 태우고 목적지 호출시 승객 위치는 0으로 변경
			matrix[tmp[0]][tmp[1]] = 0;

			int moveCnt = move(goal[0], goal[1]);
			
			// 이동할 수 없는 경우
			if (moveCnt == -1) {
				isValid = false;
				break;
			}
			
			if (F - moveCnt < 0) {
				isValid = false;
				break;
			} else {
				F = (F - moveCnt) + (moveCnt * 2);
				taxi[0] = goal[0];
				taxi[1] = goal[1];
			}
			
			
		}
		
		System.out.println(isValid ? F : -1);
		
	} // main
	
	// 승객 위치 탐색 BFS 기반	
	public static int[] findPassanger() {
		// i, j, move -> move 가 같은 경우 i -> j순으로 낮은 값이 먼저 오도록 설계
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		boolean[][] visited = new boolean[N][N];
		visited[taxi[0]][taxi[1]] = true;
		queue.offer(new int[] {taxi[0], taxi[1], 0});
		
		while(!queue.isEmpty()) {
			// 단계별로 진행
			int size = queue.size();
			// 탐색할 승객 위치
			int targetR = -1;
			int targetC = -1;
			int targetMove = 0;
			
			for(int i = 0; i < size; i++) {
				int[] cur = queue.poll();
				int row = cur[0];
				int col = cur[1];
				int move = cur[2];
				
				// 현재 위치에 승객이 있는 경우
				if (matrix[row][col] >= 2) {
					if (targetR == -1 && targetC == -1) {
						targetR = row;
						targetC = col;
						targetMove = move;
					} else if (targetR > row) { // 이미 target이 있는 경우 C 기준
						targetR = row;
						targetC = col;
						targetMove = move;
					} else if (targetR == row) { // C도 같으면 R 기준
						if (targetC > col) {
							targetR = row;
							targetC = col;
							targetMove = move;								
						}
					}
					break;
				}
				
				for (int dir = 0; dir < 4; dir++) {
					int nr = row + dr[dir];
					int nc = col + dc[dir];
					
					// 범위 밖 or 방문상태 or 벽인 경우 다음 탐색 진행
					if (nr < 0 || nr >= N || nc < 0 || nc >= N ||
							visited[nr][nc] || matrix[nr][nc] == 1) {
						continue;
					}
					
					visited[nr][nc] = true;
					int next = move + 1;
					
					// 승객 위치인 경우
					if (matrix[nr][nc] >= 2) {
						if (targetR == -1 && targetC == -1) {
							targetR = nr;
							targetC = nc;
							targetMove = next;
						} else if (targetR > nr) { // 이미 target이 있는 경우 C 기준
							targetR = nr;
							targetC = nc;
							targetMove = next;
						} else if (targetR == nr) { // C도 같으면 R 기준
							if (targetC > nc) {
								targetR = nr;
								targetC = nc;
								targetMove = next;								
							}
						}
					}
					
					queue.offer(new int[] {nr, nc, next});
				}				
			}
			
			// 승객 위치를 찾은 경우 반환
			if (targetR != -1 && targetC != -1) {
				return new int[] {targetR, targetC, targetMove};
			}
		}
		
		return new int[] {-1, -1, -1};
	}
	
	// 목적지 이동 다익스트라 기반(목적지값을 인자값으로 받음)
	public static int move(int r, int c) {
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> (o1[2] - o2[2]));
		int[][] dist = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			Arrays.fill(dist[i], INF);
		}
		pq.offer(new int[] {taxi[0], taxi[1], 0});
		dist[taxi[0]][taxi[1]] = 0;
		
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int row = cur[0];
			int col = cur[1];
			int move = cur[2];
			
			if (dist[row][col] < move) {
				continue;
			}
			
			if (row == r && col == c) {
				return move;
			}
			
			for (int dir = 0; dir < 4; dir++) {
				int nr = row + dr[dir];
				int nc = col + dc[dir];
				
				if (nr < 0 || nr >= N || nc < 0 || nc >= N ||
						matrix[nr][nc] == 1) {
					continue;
				}
				
				int next = move + 1;
				if (dist[nr][nc] > next) {
					dist[nr][nc] = next;
					pq.offer(new int[] {nr, nc, next});
				}
			}
		}
		
		return -1;
	}
	
	public static void check() {
		
	}

}
