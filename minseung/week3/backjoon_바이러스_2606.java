package backjoon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class 바이러스_2606 {
	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("src/input2.txt"));

		Scanner sc = new Scanner(System.in);

		int N = sc.nextInt(); // 컴퓨터 수
		int M = sc.nextInt(); // 간선 수

		List<List<Integer>> graph = new ArrayList<>(); // List의 타입에 List를 넣기

		for (int i = 0; i < N + 1; i++) {
			graph.add(new ArrayList<>());
		}

		int from = 0;
		int to = 0;
		for (int i = 0; i < M; i++) {
			from = sc.nextInt();
			to = sc.nextInt();

			// 양방향 연결
			graph.get(from).add(to);
			graph.get(to).add(from);
		}

//		System.out.println(graph);
		// 1번 컴퓨터와 연결되어 있는 컴퓨터의 수를 bfs로 찾아내보자
		// 방문배열 만들기
		boolean[] visited = new boolean[N + 1];

		Queue<Integer> q = new LinkedList<>();

		q.offer(1);

		while (!q.isEmpty()) {
			int node = q.poll();

			for (int edge : graph.get(node)) {
				if (!visited[edge]) {
					visited[edge] = true;
					q.offer(edge);
				}
			}
		}

		// boolean 배열에서 true 개수 찾기
		int answer = 0;
		for (int i = 2; i < N + 1; i++) {
			if (visited[i]) {
				answer += 1;
			}
		}
		System.out.println(answer);
	}

}
