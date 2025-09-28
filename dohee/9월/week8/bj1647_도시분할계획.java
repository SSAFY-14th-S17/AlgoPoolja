package week8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 마을) N개의 집과 그 집들을 연결하는 M개의 길
 * 각 길마다 유지비 있음
 * 임의의 두 집 사이에 경로가 항상 존재함
 * 
 * 마을을 두개로 분할 계획
 * - 각 분리된 마을 안에 집들이 서로 연결되도록 분할해야 함 ( 두 집 사이에 경로가 항상 존재햐야 함)
 * - 마을에는 하나 이상의 집이 있어야 함
 * - 분리된 두 마을 사이에 있는 길들은 필요 없으므로 없앨 수 있음
 * - 분리된 마을 안에서도 두 집 사이의 경로가 항상 존재하게 하면서 길을 더 없앨 수 있음
 * - 위 조건들을 만족하도록 길들을 없애고 나머지 길의 유지비의 합의 최솟값?
 * 
 * [문제 분석]
 * 0. 문제유형
 * 각 정점을 이을 수 있는 최소한의 간선 & 최소 가중치 => MST?
 *
 * 1.크루스칼 or 프림
 * 정점(집): 2이상 100,000이하인 정수
 * 간선(길): 1이상 1,000,000이하인 정수
 * - 크루스칼: O(ElogE)
 * - 프림:O(ElogV) 
 * => 프림?
 * 
 * [문제 풀이]
 * 1.프림을 이용하여 MST만들기
 * - Edge 클래스 & 인접 리스트 만들기
 * - PQ 사용
 * 2. 가중치가 가장 큰 간선 제거
 * 3. 총비용 = MST 가중치 총 합 - 제거한 가중치
 */

public class bj1647_도시분할계획 {
	// Edge 클래스
	static class Edge implements Comparable<Edge>{
		int to, cost;
		
		public Edge(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			return this.cost - o.cost;
		}
		
	}
	
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		
		// [input]
		String[] tmp = br.readLine().split(" ");
		int N = Integer.parseInt(tmp[0]); // 집의 수
		int M = Integer.parseInt(tmp[1]); // 길의 수
		int totalCost = 0;
		int maxEdge =0;
		
		// 인접 리스트 초기화
		List<Edge>[] adj = new ArrayList[N+1]; // 인접 리스트
		
		for(int i=0; i<N+1; i++) {
			adj[i] = new ArrayList<>();
		}
		
		for(int i=0; i<M; i++) {// 간선 & 가중치 입력받기
			String[] temp = br.readLine().split(" ");
			int from = Integer.parseInt(temp[0]);
			int to = Integer.parseInt(temp[1]);
			int cost = Integer.parseInt(temp[2]);
			
			// 무방향 그래프
			adj[from].add(new Edge(to,cost));
			adj[to].add(new Edge(from,cost));
		}
		
		// [logic]
		// prim - MST 구하기
		boolean[] visited = new boolean[N+1]; // 정점이 MST에 포함되었는가
		PriorityQueue<Edge> pq = new PriorityQueue<>(); // 가까운 정점을 고르기 위함
		
		int picked = 0; // N-1개 정점 뽑기
		
		pq.add(new Edge(1,0)); // 시작 정점
		
		while(picked < N) { // N개의 정점을 선택할 때까지
			Edge e = pq.poll();
			if(visited[e.to]) continue; // 이미 포함된 정점이면 스킵
			
			// 아니면
			visited[e.to]=true;
			picked++;
			totalCost += e.cost;
			maxEdge = Math.max(maxEdge, e.cost);// MST에 포함되고 && 가중치가 제일 높은 간선
			
			pq.addAll(adj[e.to]);
			
		}
		
		// 가중치가 가장 높은 간선 빼기
		int answer = totalCost - maxEdge;
	
		// [output]
		System.out.println(answer);
		
	}
}
