

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 전력난
 * 가로등 중 일부를 소등하기로 함
 * 길의 가로등을 켜 두면 하루에 길 미터 수만큼 돈이 들어가는데,
 * 일부를 소등하여 그만큼의 돈을 절약할 수 있음
 * 
 * 어 떤 두 집을 왕래할 때 불이 켜져 있지 않은 길을 반드시 지나야 한다면 위험해 진다.
 * 도시에 있는 모든 두 집 쌍에 대해 불이 켜진 길만으로 서로를 왕래 가능
 * 위 조건을 지키면서 절약할 수 있는 최대 액수 구하기
 * 
 * 집의 수와 길의 수는 최대 200,000
 * 
 * n개 줄에 각 길에 대한 정보 x, y, z
 * x, y 사이를 잇는 양방향 도로 z미터
 * 도로의 모든 길의 합은 2^31보다 작음
 * 입력의 끝에서는 첫 줄에 0이 2개 주어짐
 * 
 * 절약 가능한 최대 비용 출력하기
 * 
 * 조건을 만족하는 그래프의 형태는 mst형태임
 * 
 * 간선의 모든 길이 더한 후 최소신장 트리의 길이만큼 제거
 * 
 * 크루스칼 알고리즘 사용
 */

public class gold4_6479_전력난 {
	
	static class Node implements Comparable<Node> {
		int u;
		int v;
		int w;
		
		public Node (int u, int v, int w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}

		@Override
		public int compareTo(Node o) {
			return this.w - o.w;
		}
	}
	
	static int V, E;
	static List<Node> edges;
	static int[] parent;
	static int total;
	static int result;
	
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
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		while(true) {
			st = new StringTokenizer(br.readLine());
			
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			
			if (V == 0 && E == 0) {
				break;
			}
			
			edges = new ArrayList<>();
			parent = new int[V];
			total = 0;
			result = 0;
			for (int i = 0; i < V; i++) {
				parent[i] = i;
			}
			
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				int w = Integer.parseInt(st.nextToken());
				
				total += w;
				edges.add(new Node(u, v, w));
			}
			
			Collections.sort(edges);
			
			int idx = 0;
			int cnt = 0;
			
			while(cnt < V - 1 && idx < edges.size()) {
				Node cur = edges.get(idx);
				
				idx++;
				
				int u = cur.u;
				int v = cur.v;
				int w = cur.w;
				
				if (find(u) != find(v)) {
					union(u, v);
					cnt++;
					result += w;
				}
			}
			
			System.out.println(total - result);	
		}
		
	}
}
