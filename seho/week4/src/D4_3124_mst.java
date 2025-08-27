

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 최소 스패닝 트리(MST)
 * 
 * 크루스칼 알고리즘과 Union-Find에 대한 기본 이해
 * 
 */
public class D4_3124_mst {
	
	static class Node implements Comparable<Node> {
		int u;
		int v;
		int w;
		
		public Node(int u, int v, int w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}
		
		@Override
		public int compareTo(Node o) {
			return this.w - o.w;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int V = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			
			// 부모 초기화
			int[] parent = new int[V + 1];
			
			// 처음엔 자기자신이 부모노드
			for (int i = 1; i <= V; i++) {
				parent[i] = i;
			}
			
			// 가중치 최댓값이 100만 까지 이기에 주어지는 간선 개수 고려
			long result = 0;
			
			// 최소 가중치부터 비교해야 하기 때문에 pq로 낮은 가중치 부터 우선적으로 나올 수 있게 설계
			PriorityQueue<Node> queue = new PriorityQueue<>();
			
			// 간선 정보 입력
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				int w = Integer.parseInt(st.nextToken());
				
				queue.add(new Node(u, v, w));
			}
			
			while (!queue.isEmpty()) {
				Node cur = queue.poll();
				
				// 두 정점의 부모 노드 탐색
				int rootU = find(parent, cur.u);
				int rootV = find(parent, cur.v);
				
				// 부모노드가 같지 않아야 합해도 사이클이 되지 않음
				if (rootU != rootV) {
					// 같지 않으면 합하고 가중치 더하기
					result += cur.w;
					union(parent, rootU, rootV);
				}
				
			}
			
			// 출력
			System.out.println("#" + tc + " " + result);
		}
	} // main
	
	// 부모 노드 탐색하면서 바로 부모노드 접근 가능하도록 parent 압축
	public static int find(int[] parent, int x) {
		if (parent[x] != x) {
			parent[x] = find(parent, parent[x]);
		}
		
		// 부모 노드 return
		return parent[x];
	}
	
	// 부모 노드가 낮은 쪽이 부모 노드가 되도록 두 트리를 합하기
	public static void union(int[] parent, int rootU, int rootV) {
		if (rootU > rootV) {
			parent[rootV] = rootU;
		} else {
			parent[rootU] = rootV;
		}
	}

}
