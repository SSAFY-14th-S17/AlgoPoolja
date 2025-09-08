

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 작업순서 (위상정렬)
 * V 개 작업 어떤 작업은 특정 작업이 끝나여 시작 가능 -> 선행 관계
 * 
 * 작업과 선행관계 그래프
 * 선행 관계는 방향성을 가진 간선으로 표현
 * 사이클 존재 x
 * 
 * 일을 끝낼 수 있는 작업 순서 찾기
 * 
 * 모든 정점의 진입차수 기록
 * 0부터 단계별 bfs 탐색
 */
public class D6_1267 {
	static int V, E;
	static List<List<Integer>> graph;
	static int[] indegree;
	static int[] result;
	static StringBuilder sb;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		for (int tc = 1; tc <= 10; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			
			indegree = new int[V + 1];
			graph = new ArrayList<>();
			
			// 그래프 초기화
			for (int i = 0; i <= V; i++) {
				graph.add(new ArrayList<>());
			}
			
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				
				// 그래프 연결 및 진입차수 기록
				graph.get(s).add(e);
				indegree[e]++;
			}
			
			sb = new StringBuilder();
			sb.append("#").append(tc).append(" ");
			
			// 단계별 bfs
			bfs();
			
			
			System.out.println(sb.toString());
			
		}
	}
	
	public static void bfs() {
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		int t = 1;
		// 진입차수가 0개인 노드들을 입력
		for (int i = 1; i <= V; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
				sb.append(i).append(" ");
			}
		}
		
		while(!queue.isEmpty()) {
			t++;
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				int cur = queue.poll();
				
				for (int e: graph.get(cur)) {
					indegree[e]--;
					if (indegree[e] == 0) {
						sb.append(e).append(" ");
						queue.offer(e);
					}
				}
			}
		}
		
	}
}
