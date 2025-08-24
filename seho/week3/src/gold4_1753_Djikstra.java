import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * 데이크스트라
 * 단방향 그래프
 * 시작 노드에서 각 정점으로 이동하는 최소거리 구하기
 * 자기 자신은 0
 * 이동 못하면 INF
 * 
 * 이동하려는 정점과 정점까지의 거리를 같이 관리해야한다.
 * 우선순위 큐에서 최솟값을 뽑아내기 위한 기준을 설정하는 것이 포인트
 */

public class gold4_1753_Djikstra {
	// 2차원 배열을 만들어 관리하게 될 경우 30만x30만은 많은 공간을 잡아먹게 됨
	static Map<Integer, List<int[]>> graph;
	static PriorityQueue<int[]> queue;
	static boolean[] visited;
	static int[] result;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		
		// 시작 정점
		int startVer = Integer.parseInt(br.readLine());
		
		graph = new HashMap<>();
		
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			
			if (graph.get(start) == null) {
				graph.put(start, new ArrayList<>());
			}
			
			graph.get(start).add(new int[] {end, d});
		}
		
		// 각 정점의 방문처리를 도울 배열 생성 (0번 인덱스 사용x)
		visited = new boolean[V+1];
		
		// 결과 출력용 (0번 인덱스 사용 x)
		result = new int[V+1];
		
		djikstra(startVer);
		
		StringBuilder sb = new StringBuilder();
		
		// StringBuilder 에 각 정점 방문 최소횟수 한번에 입력
		for (int i = 1; i < result.length; i++) {
			// 0인 경우 처리
			if (result[i] == 0) {
				// 시작 노드인 경우 0
				if (i == startVer) {
					sb.append(0).append("\n");
				} else { // 그 외에는 갈 수 없는 지역
					sb.append("INF").append("\n");
				}
			} else { // 0이 아니면 추가
				sb.append(result[i]).append("\n");
			}
		}
		
		System.out.println(sb.toString());
	}
	
	public static void djikstra(int startVer) {
		// 거리 기준 최솟값을 먼저 뽑아내기
		queue = new PriorityQueue<>((o1, o2) -> (o1[1] - o2[1]));
		
		List<int[]> list = graph.get(startVer);
		
		// 시작 노드 방문 처리
		visited[startVer] = true;
		
		for (int[] arr: list) {
			// 현재 정점에서 다음 정점까지의 거리 입력
			queue.add(arr);
		}
		
		while (!queue.isEmpty()) {
			// 큐에서 간선의 거리가 짧은 list 꺼내기
			int[] cur = queue.poll();
			
			// 현재 방문 정점과 누적 거리
			int nowVer = cur[0];
			int total = cur[1];
			
			// 현재 정점 방문 확인
			if (!visited[nowVer]) { // 방문하지 않은 경우
				// 방문 처리
				visited[nowVer] = true;
				result[nowVer] = total;
			} else { // 방문한 경우
				// 방문된경우 최솟값인지 확인
				if (result[nowVer] > total) {
					// 더 짧을 경우 갱신
					result[nowVer] = total;
				} else {
					// 아니면 다음 진행
					continue;
				}
			}
			
			// 현재 방문 정점에서 방문 가능한 정점 리스트 가져오기
			List<int[]> nextList = graph.get(nowVer);
			
			// 순회 (nextList가 null이 아닌 경우)
			// 방문할 정점이 없으면 다음으로
			if (nextList == null) {
				continue;
			}
			
			// 있으면 순회
			for (int i = 0; i < nextList.size(); i++) {
				// 다음 방문할 정점과 거리
				int[] next = nextList.get(i);
				
				int nextVer = next[0];
				int nextD = next[1];
				
				// 현재 누적 거리 합에 nextD를 더한 뒤 queue에 추가
				queue.add(new int[] {nextVer, total + nextD});
			}
		}
	}
}
