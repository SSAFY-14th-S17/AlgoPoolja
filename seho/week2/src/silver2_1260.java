import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * DFS와 BFS
 */
public class silver2_1260 {
	static Map<Integer, List<Integer>> map;
	static Queue<Integer> queue;
	static int V;
	static int E;
	static boolean[] dfsVisited;
	static boolean[] bfsVisited;
	static StringBuilder dfsResult;
	static StringBuilder bfsResult;
	
	public static void dfs(int cur, int depth) {
		if (depth == map.size()) {
			dfsResult.append(cur).append(" ");
			dfsVisited[cur] = true;
			return;
		}
		
		dfsVisited[cur] = true;
		dfsResult.append(cur).append(" ");
		List<Integer> curList = map.get(cur);
		if (curList != null) {
			Collections.sort(curList);
			for (int i = 0; i < curList.size(); i++) {
				int next = curList.get(i);
				if (!dfsVisited[next]) {
					dfs(next, depth + 1);
					
				} else {
					continue;
				}
			}
		}
	}
	
	public static void bfs(Queue<Integer> queue) {
		
		while(!queue.isEmpty()) {
			int cur = queue.poll();
			if (!bfsVisited[cur]) {
				bfsVisited[cur] = true;
				bfsResult.append(cur).append(" ");
				
				List<Integer> curList = map.get(cur);
				if (curList != null) {
					Collections.sort(curList);
					for (int i = 0; i < curList.size(); i++) {
						int next = curList.get(i);
						if (!bfsVisited[next]) {
							queue.add(next);
						}
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		int start = Integer.parseInt(st.nextToken());
		map = new HashMap<>();
		queue = new LinkedList<>();
		
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int ver1 = Integer.parseInt(st.nextToken()); 
			int ver2 = Integer.parseInt(st.nextToken());
			if (map.get(ver1) == null) {
				map.put(ver1, new ArrayList<>());
			}
			map.get(ver1).add(ver2);
			
			if (map.get(ver2) == null) {
				map.put(ver2, new ArrayList<>());
			}
			map.get(ver2).add(ver1);
		}
		
		queue.add(start);
		
		dfsVisited = new boolean[V + 1];
		bfsVisited = new boolean[V + 1];
		
		dfsResult = new StringBuilder();
		bfsResult = new StringBuilder();
		
		dfs(start, 0);
		bfs(queue);
		
		System.out.println(dfsResult.toString());
		System.out.println(bfsResult.toString());
	}
}
