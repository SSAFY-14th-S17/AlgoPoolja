

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 치킨게임
 * 
 * N * N도시에서 1 * 1 크기의 칸으로 나누어져 있음 도시의 각 칸은 빈 칸, 치킨집, 집 중 하나
 * 도시의 칸은 r, c형태로 나타내고 r과 c는 1부터 시작
 * 
 * 치킨거리는 집과 가장 가까운 치킨집 사이의 거리
 * 집 기준으로 정해지며 각각의 집은 치킨 거리를 가지고 있음
 * 도시의 치킨 거리는 모든 집의 치킨 거리의 합
 * (r1, c1) (r2, c2) 사이의 거리는 |r1-r2| + |c1-c2|
 * 
 * 치킨집 m개를 제외한 나머지를 폐업한다 했을때 도시 치킨거리의 최솟값 반환
 * 
 * 문제 설계
 * 치킨집과 집에 대한 좌표값을 저장
 * M 갯수만큼 치킨집을 선택하여 집과의 치킨 거리를 구하고 최솟값을 갱신 
 */
public class gold5_15686 {
	static int N, M;
	static int[][] matrix;
	static List<int[]> houses;
	static List<int[]> chickens;
	static List<Integer> selected;
	static boolean[] visited;
	static int result;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		matrix = new int[N + 1][N + 1];
		
		houses = new ArrayList<>();
		chickens = new ArrayList<>();
		
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				int num = Integer.parseInt(st.nextToken());
				matrix[i][j] = num;
				if (num == 1) {
					houses.add(new int[] {i, j});
				} else if (num == 2) {
					chickens.add(new int[] {i, j});
				}
			}
		}
		
		visited = new boolean[chickens.size()];
		selected = new ArrayList<>();
		
		result = Integer.MAX_VALUE;
		backtracking(0, 0);
		
		System.out.println(result);
	}
	
	public static void backtracking(int depth, int idx) {
		if (selected.size() == M) {
			result = Math.min(result, checkDist());
			return;
		}
		
		if (idx == chickens.size()) {
			return;
		}
		
		// 포함하는 경우와 포함하지 않는 경우
		visited[idx] = true;
		selected.add(idx);
		backtracking(depth + 1, idx + 1);
		
		
		visited[depth] = false;
		selected.remove(selected.size() - 1);
		backtracking(depth, idx + 1);
	}
	
	public static int checkDist() {
		int sum = 0;
		
		int[] dist = new int[houses.size()];
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		for (int num : selected) {
			int idx = 0;
			int[] chicken = chickens.get(num);
			for (int[] house : houses) {
				dist[idx] = Math.min(dist[idx], calculate(chicken[0], chicken[1], house[0], house[1]));
				idx++;
			}
		}
		
		for (int d : dist) {
			sum += d;
		}
		
		return sum;
	}
	
	
	public static int calculate(int r1, int c1, int r2, int c2) {
		return Math.abs(r1 - r2) + Math.abs(c1 - c2);
	}
}
