package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
 *  N×N인 도시
 *  r & c: 1부터 시작
 *  치킨 거리: 집과 가장 가까운 치킨집 사이의 거리 (집 기준)
 *  0: 빈 칸, 1: 집, 2: 치킨집
 *  M개만 운영
 *  Q: M개를 선택했을 때, 도시의 치킨 거리의 최솟값?
 *  
 *  [문제 풀이]
 *  1. 치킨집 M개 선택: 백트래킹
 *  2. 집 & 치킨집 좌표 저장 : List
 *  2. 치킨집 최소 거리 구하기: 맨해튼 + Math.min
 *  

5 3
0 0 1 0 0
0 0 2 0 1
0 1 2 0 0
0 0 1 0 0
0 0 0 0 2

5

 */
public class bj15686_치킨배달 {
	static int N ;
	static int M;
	static int[][]map;
	static List<int[]> chickenShops = new ArrayList<>();
	static List<int[]> houses  = new ArrayList<>();
	static List<int[]> selectedChickenShops = new ArrayList<>();
	static int minChickenDistance=Integer.MAX_VALUE;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// [input]
		String[] tmp = br.readLine().split(" ");
		N = Integer.parseInt(tmp[0]);
		M = Integer.parseInt(tmp[1]);
		map = new int[N][N];
		
		for(int i=0; i<N; i++) {
			String[] line = br.readLine().split(" ");
			for(int j=0; j<N; j++) {
				map[i][j]=Integer.parseInt(line[j]);
			}
		}
		
		//System.out.println(Arrays.deepToString(map));
		
		// [logic]
		getMap(map); // 각 리스트에 집 & 치킨집 좌표 담기
		chooseMStores(0, 0); // 치킨집 중 M개 조합
		
		// [output]
		System.out.println(minChickenDistance);
		
	}//main

	/**
	 * 치킨집 & 집의 좌표를 저장하는 함수
	 * @param map
	 */
	private static void getMap(int[][] map) {
		for(int i=0; i< N*N; i++) {
			int x = i / N;
			int y = i % N;
			if(map[x][y]==1) {
				houses.add(new int[] {x,y});
				}
			else if(map[x][y]==2) {
				chickenShops.add(new int[] {x,y});
			}
		}
	}

	/**
	 * M개 선택
	 * @param start: 탐색 시작 좌표
	 * @param depth: 몇 개 뽑았는지
	 */
	private static void chooseMStores(int start, int depth) {
		// base-case
		if(depth==M) {
			int chickenDistance = getChickenDistance(map); // 최소 거리 구하기 
			minChickenDistance = Math.min(minChickenDistance,chickenDistance);//조합 중 가장 작은 치킨거리 찾기
			return;
		}
		
		// back-tracking
		 for(int i=start; i<chickenShops.size(); i++) {
			 selectedChickenShops.add(chickenShops.get(i));
			 chooseMStores(i+1,depth+1);
			 selectedChickenShops.remove(selectedChickenShops.size()-1);
		 }
	}// f: chooseMStores

	/**
	 * 최소 치킨 거리를 구하는 함수
	 * @param map
	 */
	private static int getChickenDistance(int[][] map) {
		int chickenDistance=0;
		for(int i=0; i<houses.size(); i++) {
			int[] house = houses.get(i); // 각 집마다
			int min = Integer.MAX_VALUE;
			for(int j=0; j<M; j++) {// 가장 가까운 치킨집까지의 거리
				int [] chickenShop=selectedChickenShops.get(j);
				int distance = Math.abs(house[0]-chickenShop[0])+Math.abs(house[1]-chickenShop[1]);
				min = Math.min(min, distance);
			}
			
			chickenDistance+= min;
		}
		return chickenDistance;
	}// f: getChickenDistance
	
}

