

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 다익스트라
 * 1 <= n <= 1000개 도시
 * 한 도시에서 출발하여 다른 도시에 도착하는 m(1<= m <= 100000)개의 버스가 있다
 * A -> B까지 가는데 드는 버스 비용을 최소화
 * 
 * A -> B까지 가는데 드는 최소 비용과 경로 출력
 */
public class gold3_11779_최소비용2 {
	static class Node implements Comparable<Node> {
		int v;
		long w;
		int city;
		List<Integer> visited;
		
		public Node(int v, long w, int city, List<Integer> visited) {
			this.v = v;
			this.w = w;
			this.city = city;
			this.visited = visited;
		}
		
		@Override
		public int compareTo(Node o) {
			return Long.compare(this.w, o.w);
		}
		
	}
	
	static int V, E;
	static List<List<Node>> graph;
	static long[] dist;
	static int start;
	static int end;
	static HashMap<Integer, Node> map;
	static StringBuilder sb;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		V = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());
		
		graph = new ArrayList<>();
		map = new HashMap<>();
		dist = new long[V + 1];
		
		for (int i = 0; i <= V; i++) {
			graph.add(new ArrayList<>());
		}
		
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			graph.get(u).add(new Node(v, w, 1, new ArrayList<>()));
		}
		
		st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());
		
		sb = new StringBuilder();
		
		dijkstra();
		
		Node result = map.get(end);
		
		sb.append(result.w).append("\n").append(result.city).append("\n");
		
		sb.append(start).append(" ");
		
		for (int i = 0; i < result.visited.size(); i++) {
			sb.append(result.visited.get(i)).append(" ");
		}
		
		System.out.println(sb.toString());
	}
	
	public static void dijkstra() {
		Node startNode = new Node(start, 0, 1, new ArrayList<>());
		map.put(start, startNode);
		Arrays.fill(dist, Long.MAX_VALUE);
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(startNode);
		
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			int u = cur.v;
			long d = cur.w;
			int visitCnt = cur.city;
			List<Integer> visited = cur.visited;
			
			if (dist[u] < d) {
				continue;
			}
			
			for (Node node: graph.get(u)) {
				long nextD = d + node.w;
				if (dist[node.v] > nextD) {
					dist[node.v] = nextD;
					List<Integer> newVisited = new ArrayList<>(visited);
					newVisited.add(node.v);
					Node next = new Node(node.v, nextD, visitCnt + 1, newVisited);
					pq.offer(next);
					map.put(node.v, next);
				}
			}
		}
	}

}
