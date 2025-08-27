

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 인구 이동
 * N*N 땅 r행 c열에 있는 나라에는 A[r][c]명이 삼
 * 인접 나라 사이에 국경선 존재 - 정사각형 형태
 * 
 * 인접 나라의 인구 차이가 L이상 R이하면 두 나라 이동 가능
 * 모든 인접나라 국경선 체크 후 이동 시작
 * 인접 칸만 이용해 이동 가능
 * 국경선이 열린 두 나라는 연합 연합의 각 칸의 인구수는 (연합의 인구수) / (연합을 이루는 칸의 개수) 소수점 x
 * 연합 해체 후 국경선 x
 * 
 * 인구이동 며칠동안?
 * 
 * 1 -> N, L, R
 * 2 -> N개 줄에 각 나라 인구수 r, c에 주어지는 정수는 A[r][c]값 0 ~ 100
 * 인구이동 일수는 2000번 이하
 * 며칠동안 발생?
 */

public class gold4_16234 {
	static int N, L, R;
	static int[][] nation;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		nation = new int[N][N];
		
		// 국가 인구 정보
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				nation[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		simulation();
		
	} // main
	
	static int unionNum;
	static int day;
	static Map<Integer, List<int[]>> union;
	static boolean[][] visited;
	
	// 사전 준비
	public static void simulation() {
		visited = new boolean[N][N];
		union = new HashMap<>();
		day = 0;
		unionNum = 0;
		
		while (true) {
			unionNum++;
			int[] cur = check();
			
//			System.out.println(day);
//			System.out.println(unionNum);
			
			// 모든 연합이 이뤄진 경우
			if (cur == null) {
				// 연합 크기가 없으면 종료
				if (union.size() == N * N) {
					System.out.println(day);
					return;
				} else {
					// 아닌경우 계산 후 연합 초기화
					day++;
					move();
					union = new HashMap<>();
					visited = new boolean[N][N];
				}
			} else { // 아니면 현재 좌표 기준 연합 탐색
				connect(cur);
			}
		}
	}
	
	// 탐색하지 않은 국가 확인
	public static int[] check() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!visited[i][j]) {
//					System.out.println(i + " " + j);
					return new int[] {i, j};
				}
			}
		}
		
		// 없으면 null 반환
		return null;
	}
	
	// 연합국가 찾기(bfs)
	public static void connect(int[] current) {
		Queue<int[]> queue = new LinkedList<>();
		
		visited[current[0]][current[1]] = true;
		queue.add(new int[] {current[0], current[1]});
		union.put(unionNum, new ArrayList<>());
		union.get(unionNum).add(new int[] {current[0], current[1]});
		
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			int r = cur[0];
			int c = cur[1];
			
			for (int dir = 0; dir < 4; dir++) {
				int nr = r + dr[dir];
				int nc = c + dc[dir];
				
				// 배열 범위 안인지 확인 or 방문하지 않았는지
				if (nr >= 0 && nr < N && nc >= 0 && nc < N && !visited[nr][nc]) {
					// 연합 조건 확인
					int diff = Math.abs(nation[r][c] - nation[nr][nc]);
					// L명 이상 R명 이하 범위면 연합하고 queue에 넣기
					if (diff >= L && diff <= R) {
						visited[nr][nc] = true;
						int[] next = new int[] {nr, nc};
						union.get(unionNum).add(next);
						queue.add(next);
					}
				}
			}
		}
	}
	
	// 연합간 인구 이동
	public static void move() {
		for (List<int[]> arr: union.values()) {
			// 연합 총 인구수
			int sum = 0;
			for (int[] num: arr) {
				sum += nation[num[0]][num[1]];
			}
			
			// 총 인구수 / 연합국가 수
			int avg = sum / arr.size();
			
			// 이동
			for (int[] num: arr) {
				nation[num[0]][num[1]] = avg;
			}
		}
	}	
}
