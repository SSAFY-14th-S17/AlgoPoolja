

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 미세먼지 안녕 (시뮬레이션)
 * 2차원 배열 관리
 * 
 */
public class gold4_17144_미세먼지_TimeOut {
	
	static class Dust {
		int count;
		ArrayDeque<Integer> queue;
		
		public Dust(int count, ArrayDeque<Integer> queue) {
			this.count = count;
			this.queue = queue;
		}
	}
	
	static int R, C, T;
	static int[][] matrix;
	static List<String> airCleaner;
	
	static Map<String, Dust> map;
	
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	static int total;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		matrix = new int[R][C];
		airCleaner = new ArrayList<>();
		map = new HashMap<>();
		total = 0;
		
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				int num = Integer.parseInt(st.nextToken());
				if (num == -1) {
					airCleaner.add(i + "," + j);
				} else if (num != 0) {
					map.put(i + "," + j, new Dust(num, new ArrayDeque<>()));
				}
			}
		}
		
		for (int i = 0; i < T; i++) {
			spread();			
			cleanerOn();
		}
		
		// 남아있는 먼지 양 구하기
		for (Dust dust : map.values()) {
			total += dust.count;
		}
		
		System.out.println(total);
	} // main
	
	// 미세먼지 확산
	public static void spread() {
		// map의 크기만큼 순회
		Map<String, Dust> tmpMap = new HashMap<>();
		
		// 복제
		for(String key : map.keySet()) {
			Dust dust = map.get(key);
	        tmpMap.put(key, dust);
	    }
		
		for (String key : map.keySet()) {
			// 키값으로 value 값을 가져옴
			Dust dust = tmpMap.get(key);
			int tmp = 0;
			
			// 5미만이면 확산될 먼지가 없음
			if (dust.count < 5) {
				continue;
			}
			
			// 키값을 , 기준으로 쪼개 r, c 좌표 가져오기
			StringTokenizer st = new StringTokenizer(key, ",");
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			// 4방향 탐색
			for (int dir = 0; dir < 4; dir++) {
				int nr = r + dr[dir];
				int nc = c + dc[dir];
				
				// 범위 안이거나 공기청정기가 아닌 지역이어야 함
				if (nr >= 0 && nr < R && nc >= 0 && nc < C && !airCleaner.contains(nr + "," + nc)) {
					// 임시 맵에 dust 생성
					if (tmpMap.get(nr + "," + nc) == null) {
						tmpMap.put(nr + "," + nc, new Dust(0, new ArrayDeque<>()));						
					}
					
					// 해당 지역의 dust를 가져오기
					Dust newDust = tmpMap.get(nr + "," + nc);
					// queue에 dust의 count / 5 만큼 추가 하고 tmp값 1 증가
					newDust.queue.offer(dust.count / 5);
					tmp++;
				}
			}
			
			// 탐색 이후 현재 지역의 먼지값 계산
			int newCnt = dust.count;
			newCnt -= dust.count/5 * tmp;
			dust.count = newCnt;
		}
		
		map.clear();
		// map으로 다시 옮겨담기
		for(String key : tmpMap.keySet()) {
			Dust dust = tmpMap.get(key);
	        map.put(key, dust);
	    }
		
		// 다시 순회하면서 dust의 queue안의 dust 값 더해주기
		for (Dust dust : map.values()) {
			int newCnt = dust.count;
			while(!dust.queue.isEmpty()) { 
				newCnt += dust.queue.poll();
			}
			
			dust.count = newCnt;
		}
	}
	
	// 공기청정기 작동
	public static void cleanerOn() {
		StringTokenizer st;
		// 공기청정기 위 아래 분리
		String ac1 = airCleaner.get(0);
		st = new StringTokenizer(ac1, ",");
		int r1 = Integer.parseInt(st.nextToken());
		int c1 = Integer.parseInt(st.nextToken());
		
		String ac2 = airCleaner.get(1);
		st = new StringTokenizer(ac2, ",");
		int r2 = Integer.parseInt(st.nextToken());
		int c2 = Integer.parseInt(st.nextToken());
		
		// 역으로 한칸씩 당김
		// 위는 반시계방향으로 먼지를 밀어냄
		// 위에서 아래로
		for (int r = r1 - 1; r > 0; r--) {
			moveDust(r - 1, 0, r, 0);
		}
		
		// 오른쪽에서 왼쪽
		for (int c = 0; c < C - 1; c++) {
			moveDust(0, c + 1, 0, c);
		}
		
		// 아래에서 위로
		for (int r = 0; r < r1; r++) {
			moveDust(r + 1, C - 1, r, C - 1);
		}
		
		// 왼쪽에서 오른쪽으로
		for (int c = C - 1; c > 1; c--) {
	        moveDust(r1, c - 1, r1, c);
	    }
		
	    map.remove(r1 + "," + c1);
		
		
	    // 아래쪽
	    for (int r = r2 + 1; r < R - 1; r++) {
	        moveDust(r + 1, 0, r, 0);
	    }

	    for (int c = 0; c < C - 1; c++) {
	        moveDust(R - 1, c + 1, R - 1, c);
	    }

	    for (int r = R - 1; r > r2; r--) {
	        moveDust(r - 1, C - 1, r, C - 1);
	    }

	    for (int c = C - 1; c > 1; c--) {
	        moveDust(r2, c - 1, r2, c);
	    }
	    
	    map.remove(r2 + "," + 1);
	}
	
	public static void moveDust(int fromR, int fromC, int toR, int toC) {
		String fromKey = fromR + "," + fromC;
		String toKey = toR + "," + toC;
		
		if (map.containsKey(fromKey)) {
			Dust dustToMove = map.get(fromKey);
			map.put(toKey, dustToMove);
			map.remove(fromKey);
		} else {
			map.remove(toKey);
		}
	}
}
