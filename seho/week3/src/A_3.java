

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 줄기세포 배양
 * 배양 용기에 도포 후 일정 시간 동안 배양을 시킨 후 줄기 세포의 개수가 몇 개가 되는지 계산하는 시뮬레이션
 * 
 * 가로, 세로 크기가 1인 정사각형 형태로 존재 배양 용기는 격자 그리드로 구성
 * 하나의 그리드 셀은 줄기 세포의 크기와 동일한 가로, 세로 크기가 1인 정사각형
 * 
 * 각 줄기 세포는 생명력이라는 수치가 있음
 * 초기 상태 줄기 세포들 -> 비활성 상태 생명력 수치가 x인 줄기 세포의 경우 x 시간 동안 비활성 상태
 * x 시간이 지나는 순간 활성 상태
 * 활성상태 시 x시간동안 살아있을 수 있고 x시간이 지나면 죽음
 * 세포는 죽어도 소멸되는 것은 아니고 죽은 상태로 해당 그리드 셀을 차지하게 됨
 * 활성화된 줄기세포는 첫 1시간 동안 상하좌우 네방향 동시 번식
 * 
 * 번식된 세포는 비활성 상태
 * 그리드 셀에는 하나의 줄기 세포만 존재할 수 있기 때문에 번식하는 방향에 이미 줄기 세포가 존재하는 경우 해당 방향으로 추가 번식
 * 두 개 이상의 줄기 세포가 하나의 그리드 셀에 동시 번식하려는 경우 생명력 수치가 높은 줄기 세포가 해당 그리드 셀을 혼자 차지
 * 
 * 배양용기 크기는 무한하다고 가정
 * 
 * 줄기 세포 초기 상태 정보와 배양 시간 K가 주어질 때, K시간 후 살아있는 줄기 세포(비활성 상태 + 활성 상태)의 총 개수
 * 
 * 초기 주어진 값을 좌표값을 담은 객체로 저장
 * 객체 저장시 현재 상태도 같이 입력
 */

public class A_3 {
	static PriorityQueue<Pointer> queue;
	static List<Pointer> list;
	static Map<String, Pointer> map;
	static int K;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	static class Pointer {
		int x;
		int y;
		int status; // -1 죽음 // 0 비활성 // 1 활성
		int size;
		int time;
		
		Pointer(int x, int y, int status, int size, int time) {
			this.x = x;
			this.y = y;
			this.status = status;
			this.size = size;
			this.time = time;
		}
	}
	
	public static void culture() {
		for (int k = 0; k < K; k++) {
			int t = queue.size();
			List<Pointer> list = new ArrayList<>();
			for (int i = 0; i < t; i++) {
				
				Pointer cur = queue.poll();
				// 비활성
				if (cur.status == 0) {
					// 시간 1만큼 증가
					cur.time++;
					if (cur.time == cur.size) {
						cur.status = 1;
						cur.time = 0;
					}
					list.add(cur);
				} else if (cur.status == 1) {
					if (cur.time == 0) {
						for (int dir = 0; dir < 4; dir++) {
							int nr = cur.x + dr[dir];
							int nc = cur.y + dc[dir];
							
							// 인접지역에 세포가 존재하는지 확인
							// 가능한 경우 비활성 상태로 추가
							if (!map.containsKey(nr + "," + nc)) {
								Pointer p = new Pointer(nr, nc, 0, cur.size, 0);
								list.add(p);
								map.put(nr + "," + nc, p);
							}
						}
					}
					cur.time++;
					if (cur.time < cur.size) {
						list.add(cur);
					} else {
						cur.status = -1;
					}
				}
			}
			for (Pointer p : list) {
				queue.add(p);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			queue = new PriorityQueue<>((o1, o2) -> o2.size - o1.size);
			map = new HashMap<>();
			
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					int tmp = Integer.parseInt(st.nextToken());
					if (tmp != 0) {
						Pointer p1 = new Pointer(i, j, 0, tmp, 0);
						queue.add(p1);
						map.put(i + "," + j, p1);
					}
				}
			}
			
			culture();
			
			System.out.println("#" + tc + " " + queue.size());
		}
	}

}
