package week3;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bj19238_스타트택시  {
	static int N;
	static int M;
	static int fuel;
	static int tr;
	static int tc;

	static int[][] map;

	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};

	static class Passenger{
		int sr, sc, er, ec;
		Passenger(int sr,int sc,int er,int ec){
			this.sr= sr;
			this.sc= sc;
			this.er=er;
			this.ec=ec;
		}
	}

	static Map<String, Passenger> passengersMap = new HashMap<>(); // 키 값: 시작 좌표(String:r,c) - Passenger(sr,sc,er,ec)
	static Passenger closestPassenger;




	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] tmp = br.readLine().split(" ");

		// [input]
		N = Integer.parseInt(tmp[0]); // map의 크기
		M = Integer.parseInt(tmp[1]); // 승객 수
		fuel = Integer.parseInt(tmp[2]); // 연료의 양

		map = new int[N][N]; 
		for(int i=0; i<N;i++) {
			String[] temp = br.readLine().split(" ");
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(temp[j]);
			}
		}

		// 문제: 1-based -> 풀이: 0-based

		// 택시 운행 시작 좌표
		String[] start = br.readLine().split(" ");
		tr = Integer.parseInt(start[0])-1;
		tc= Integer.parseInt(start[1])-1;

		// 승객의 출발(String) & 도착 좌표(int[]): Map	
		for(int i=0; i<M; i++) {
			String[] line = br.readLine().split(" ");
			// 키: 시작 좌표 (String)
			String key = line[0]+","+line[1];

			int sr = Integer.parseInt(line[0])-1;
			int sc = Integer.parseInt(line[1])-1;
			int er = Integer.parseInt(line[2])-1;
			int ec = Integer.parseInt(line[3])-1;

			passengersMap.put(sr+","+sc, new Passenger(sr,sc,er,ec));
		}


		// [logic]

		// 현재 택시의 위치에서 가장 가까운 손님까지의 거리
		for(int i=0; i<M; i++) {
			int distanceToPassenger = findClosestPassenger(); // 가장 가까운 손님까지의 거리 
			if (distanceToPassenger == -1) { fuel = -1; break; }

			int distanceToDestination = findDestination(); // 손님 탑승 위치 ~ 도착 위치까지의 거리
			if (distanceToDestination == -1) { fuel = -1; break; }

			checkFuel(distanceToPassenger, distanceToDestination);
			if (fuel == -1) break;
		}

		System.out.println(fuel);




	}// main


	// 현재 택시의 위치에서 가장 가까운 손님 찾기
	private static int findClosestPassenger() {
		Deque<int[]> q = new ArrayDeque<>();
		boolean[][] visited = new boolean[N][N];

		// 시작 위치
		q.add(new int[] {tr,tc}); // 택시의 위치
		visited[tr][tc]=true;
		int distance = 0;

		// 동일 거리(레벨)에 있는 승객들의 위치 좌표를 저장해야 함
		List<Passenger> candidates = new ArrayList<>();

		while(!q.isEmpty()) {
			int qSize = q.size();

			for(int i=0; i<qSize; i++) {
				int[] currRC = q.poll();
				int currR= currRC[0];
				int currC= currRC[1];

				// 현재 레벨에 승객이 있다면 list에 넣기
				String key = currR+","+currC;
				if(passengersMap.containsKey(key)) { // 현재 좌표값이 승객의 시작 좌표라면
					candidates.add(passengersMap.get(key));
				}

				// 4d
				for(int d=0; d<4; d++) {
					int newR = currR + dr[d];
					int newC = currC + dc[d];

					// isValid
					if(newR <0 || newR >= N || newC <0 || newC >= N) continue;

					if(map[newR][newC]!=1 && !visited[newR][newC]) {
						q.add(new int[] {newR,newC});
						visited[newR][newC]=true;
					}

				}// for:4d

			}// for:qSize

			// 한 레벨의 끝 1)동일 거리의 승객 처리 2)거리 ++

			// 1) 동일 거리의 승객 처리
			if(!candidates.isEmpty()) { // 동일 거리에  승객들이 있다면 우선순위로 태울 승객 선택
				candidates.sort((a,b)-> {
					if(a.sr!=b.sr) return a.sr - b.sr; // 행이 같다면
					return a.sc- b.sc; // 열이 같다면 ->낮은 순서가 우선순위
				});
				closestPassenger = candidates.get(0);

				// !! 승객을 태운 후 택시 위치 갱신  
				tr = closestPassenger.sr;
				tc = closestPassenger.sc;
				return distance; // 가장 가까운 승객을 찾았으면 거리 반환하기
			}

			distance ++;

		}// while
		//!! while 끝까지 승객을 못 찾은 경우
		return -1;



	}//f: findClosest


	// ClosestPassenger - sx, sy: 현재 택시 & 승객의 위치
	// ex, ey: 승객의 목적지의 위치
	private static int findDestination() {
		Deque<int[]> q = new ArrayDeque<>();
		boolean[][] visited = new boolean[N][N];

		int distance=0;

		int sr = closestPassenger.sr;
		int sc = closestPassenger.sc;
		int er = closestPassenger.er;
		int ec = closestPassenger.ec;

		q.add(new int[] {sr,sc});
		visited[sr][sc] = true;

		while(!q.isEmpty()) {

			int qSize = q.size();

			for(int i=0; i<qSize; i++) {

				int[] currRC = q.poll();
				int currR = currRC[0];
				int currC = currRC[1];

				// 현재 좌표가 승객의 도착좌표인가?
				if(currR == er && currC == ec) {
					return distance;
				}

				for(int d=0; d<4; d++) {
					int newR = currR + dr[d];
					int newC = currC + dc[d];

					//isValid
					if(newR<0 || newR >= N || newC <0 || newC >=N) continue;

					if(!visited[newR][newC] && map[newR][newC]!=1) {
						q.add(new int[] {newR, newC});
						visited[newR][newC]=true;
					}

				}// for:4d

			}// for:qSize

			distance ++;


		}// while

		// 못 찾았을 경우
		return -1;
	}// f: findDest


	// fuel: 현재 가지고 있는 연료의 양
	private static void checkFuel(int distanceToPassenger, int distanceToDestination) {
		int requiredFuel = distanceToPassenger + distanceToDestination; // 승객까지의 거리 + 승객의 목적지까지의 거리
		// 1) 연료 부족
		if(fuel < requiredFuel) {
			fuel =-1;
			return;
		}
		
		// 2) 이동 가능
		fuel -= requiredFuel;
		tr = closestPassenger.er;
		tc = closestPassenger.ec;
		fuel += distanceToDestination *2; // 보상 
		
		// 3) 승객 완료 처리
		passengersMap.remove(closestPassenger.sr+","+closestPassenger.sc);
		

	}// f: checkFuel


}





