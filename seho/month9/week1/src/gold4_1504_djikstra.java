

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 특정한 최단경로
 * 
 * 무방향 그래프 1번 -> N번 정점 최단 거리 이동 임의로 주어진 두 정점 통과
 * 
 * 이동했던 정점 물론 이동 했던 간선도 다시 이동 가능 1 ~ N번 정점 이동할 때 주어진 두 정점을 반드시 거치면서 최단 경로로 이동하는
 * 프로그램 작성
 * 
 * 1줄 N, E 2 ~ E -> a b c (a - b c = 가중치) 거쳐야하는 v1, v2 u, v 사이에는 간선이 최대 1개 존재
 */
public class gold4_1504_djikstra {
	private static final boolean Node = false;
	static int N, E;
	static List<Node>[] graph;
	static int[] dist;
	static int v1;
	static int v2;

	static class Node implements Comparable<Node> {
		int to;
		int cost;

		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		graph = new ArrayList[N + 1];

		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			graph[a].add(new Node(b, c));
			graph[b].add(new Node(a, c));
		}

		st = new StringTokenizer(br.readLine());
		v1 = Integer.parseInt(st.nextToken());
		v2 = Integer.parseInt(st.nextToken());

		int[] start1 = djikstra(1);
		int[] start2 = djikstra(v1);
		int[] start3 = djikstra(v2);

		int result1 = start1[v1] + start2[v2] + start3[N];
		int result2 = start1[v2] + start3[v1] + start2[N];

		int result = Math.min(result1, result2);
		if (result >= Integer.MAX_VALUE) {
			System.out.println(-1);
		} else {
			System.out.println(result);
		}
	}

	public static int[] djikstra(int from) {
	// 0번 인덱스 사용 x
		dist = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			dist[i] = Integer.MAX_VALUE;
		}
		PriorityQueue<Node> queue = new PriorityQueue<>();
		dist[from] = 0;
		queue.offer(new Node(from, 0));
		 
		while(!queue.isEmpty()) {
			Node cur = queue.poll();
			int to = cur.to;
			int cost = cur.cost;
			
			if (dist[to] < cost) {
				 continue;
			}
			 
			for (Node node : graph[to]) {
				int newCost = cost + node.cost;
				if (dist[node.to] > newCost) {
					dist[node.to] = newCost;
					queue.offer(new Node(node.to, newCost));
				}
			}
		}
		return dist;
	 }

}
