package Swea;

import java.util.*;

public class Swea_1267_작업순서 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// 테스트케이스 수 10
		int T = 10;
		for (int tc = 1; tc <= T; tc++) {
			// 입력

			// 정점의 갯수 V, 간선의 갯수 E
			String[] temp = sc.nextLine().split(" ");
			int V = Integer.parseInt(temp[0]);
			int E = Integer.parseInt(temp[1]);

			// 간선의 정보 입력...!
			// 인접 리스트
			List<List<Integer>> graph = new ArrayList<>();
			for (int i = 0; i < V + 1; i++) {
				graph.add(new ArrayList<>());
			}

			// 진입 차수 배열
			int[] indegree = new int[V + 1];

			// 간선의 정보를 입력...!
			temp = sc.nextLine().split(" ");
			for (int i = 0; i < E; i++) {
				int u = Integer.parseInt(temp[i * 2]);
				int v = Integer.parseInt(temp[i * 2 + 1]);

				// u -> v 단방향 간선 기록
				graph.get(u).add(v);

				// 진입 차수...!
				indegree[v]++;
			}

			// 로직 : 위상정렬을 위한 kahn 알고리즘 실행...!
			List<Integer> result = topologySort(graph, V, E, indegree);

			// 출력
			System.out.print("#" + tc);
			for(int i = 0; i < V; i++) {
				System.out.print(" " + result.get(i));
			}
			System.out.println();
		}
	}

	private static List<Integer> topologySort(List<List<Integer>> graph, int V, int E, int[] indegree) {
		List<Integer> result = new ArrayList<>();
		Queue<Integer> queue = new LinkedList<>();

		// 1. 진입차수가 0 인 정점을 큐에 추가
		for (int i = 1; i <= V; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}
		
		// 큐가 빌 때까지 반복...!
		while (!queue.isEmpty()) {
			// 2. 큐에서 정점을 꺼내어 결과로 출력하고, 연결된 정점들의 진입차수를 감소
			int current = queue.poll(); 
			result.add(current);
			
			// 인접되어 있는 정점에 대해서 진입차수 감소...!
			for(int nxt : graph.get(current)) {
				// 연결된 정점의 진입차수들을 1씩 감소
				indegree[nxt]--;
				
				
				// 3. 새로 진입차수가 0이 된 정점들을 큐에 추가
				if(indegree[nxt] == 0) {
					queue.offer(nxt);
				}
			}
		}

		return result;
	}
}
