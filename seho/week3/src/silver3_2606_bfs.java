

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * 바이러스
 */

public class silver3_2606_bfs {
	static List<List<Integer>> network;
	static boolean[] visited;
	static int cnt;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int com = sc.nextInt();
		int connect = sc.nextInt();
		
		network = new ArrayList<>();
		// 방문 배열 생성
		visited = new boolean[com + 1];
		
		// com + 1 만큼 ArrayList 생성
		for (int i = 0; i < com + 1; i++) {
			network.add(new ArrayList<>());
		}
		
		// 0번 인덱스는 사용 x
		for (int i = 0; i < connect; i++) {
			int fromCom = sc.nextInt();
			int toCom = sc.nextInt();
			
			// 연결 정보 저장(양방향)
			network.get(fromCom).add(toCom);
			network.get(toCom).add(fromCom);
		}
		cnt = 0;
		
		// 1번 컴퓨터부터 바이러스 오염
		bfs(1);
		
		System.out.println(cnt);
	}
	
	public static void bfs(int start) {
		Queue<Integer> queue = new LinkedList<>();		
		
		// 1번 컴 방문처리
		visited[start] = true;
		
		// 1번 컴 queue에 add
		queue.add(start);
		
		// bfs
		while (!queue.isEmpty()) {
			int cur = queue.poll();
			
			// 현재 컴과 연결된 컴 정보 가져오기
			List<Integer> comList = network.get(cur);
			
			// 연결된 컴이 있는지 확인
			if (comList == null) {
				continue;
			}
			
			// 각각 연결된 컴이 오염됐는지 확인
			for (int computer : comList) {
				// 오염되지 않았으면 오염시킨 후 queue에 add
				if (!visited[computer]) {
					visited[computer] = true;
					cnt++;
					queue.add(computer);
				}
			}
		}
	}
}
