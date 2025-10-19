

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 우주신과 교감
 * 황선자씨를 통해 모든 우주신들이 교감가능하도록 만들기
 * 연결되는 통로는 짧아야 함
 * 이미 연결된 우주신들에 새로운 우주신들과 교감할 수 있는 통로를 만들 수 있는 경우의수중
 * 가장 짧은 길이를 만들어 빵상을 외치게 도와주기
 * 
 * 우주신 개수와 통로 개수 (1 <= N, M <= 1000)
 * 우주신들의 좌표 (0 <= X, Y <= 1,000,000)
 * 이미 연결된 통로 M개
 * 
 * 만들어야할 최소의 통로 길이를 소수점 둘째 자리까지 반올림하여 출력
 * 
 * MST
 */
public class gold3_1774_우주신 {
	static class Node {
		int x;
		int y;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int u;
		int v;
		double w;
		
		public Edge(int u, int v, double w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}
		
		@Override
		public int compareTo(Edge o) {
			return Double.compare(this.w, o.w);
		}
	}
	
	static int V, E;
	static Map<Integer, Node> nodes;
	static int[] parent;
	static List<Edge> edges;
	static double result;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		nodes = new HashMap<>();
		parent = new int[V + 1];
		edges = new ArrayList<>();
		
		// parent 초기화
		for (int i = 1; i <= V; i++) {
			parent[i] = i;
		}
		
		// 정점 좌표 입력
		for (int i = 1; i <= V; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			nodes.put(i, new Node(x, y));
		}
		
		// 모든 간선 만들어 연결하기
		for (int i = 1; i <= V; i++) {
			for (int j = 1; j <= V; j++) {
				edges.add(new Edge(i, j, calculate(i, j)));
			}
		}
		
		int cnt = 0;
		
		// 초기 간선 연결
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			
			if (find(u) != find(v)) {
				union(u, v);
				cnt++;
			}
		}
		
		// 간선 길이 정렬
		Collections.sort(edges);
		
		result = 0;
		
		int idx = 0;
		// 크루스칼
		while(cnt < V) {
			Edge cur = edges.get(idx++);
			int u = cur.u;
			int v = cur.v;
			double w = cur.w;
			
			if (find(u) != find(v)) {
				union(u, v);
				result += w;
				cnt++;
			}
			
			if (cnt == V - 1) {
				break;
			}
		}
		
		System.out.println(String.format("%.2f", result));
	}
	
	// 거리 계산
	public static double calculate(int a, int b) {
		Node v1 = nodes.get(a);
		Node v2 = nodes.get(b);
		
		return Math.sqrt(Math.pow(Math.abs(v1.x - v2.x), 2) + Math.pow(Math.abs(v1.y - v2.y), 2));
	}
	
	// union-find
	public static int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]);
		}
		return parent[x];
	}
	
	public static void union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		
		if (rootA != rootB) {
			if (rootA < rootB) {
				parent[rootB] = rootA;
			} else {
				parent[rootA] = rootB;
			}
		}
	}
}
