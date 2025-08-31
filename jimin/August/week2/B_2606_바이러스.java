package Baekjoon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class B_2606_바이러스 {
	static int n;
	static int t;
	static List<Integer>[] graph;
	/*
	 * ArrayList는 동적 배열 
	 * 일반 배열과 달리 크기가 자동으로 늘어남 
	 * 순서대로 데이터를 넣고 꺼낼 수 있음 
	 * */
	static boolean[] visited;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		t = sc.nextInt();
		graph = new ArrayList[n + 1];
		for(int i = 1; i <= n; i++) {
			graph[i] = new ArrayList<>();
		}
		
        // 연결 정보 입력
        for (int i = 0; i < t; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph[u].add(v);
            graph[v].add(u); // 무방향
        }
        
        visited = new boolean[n+1];
        dfs(1);
        
        //감염될 수 있는 컴퓨터 수 세기 -> visited 배열에서 1제외 true인 컴퓨터 세기
        int count = 0;
        for(boolean isVirus : visited) {
        	if(isVirus) count++;
        }
        
        //1제외 이기 때문에 count에서 1 빼서 출력 
        System.out.print(count - 1);
	}
	
	static void dfs(int node) {
		visited[node] = true;
		 
		for(int next: graph[node]) {
			if(!visited[next]) {
				dfs(next);
			}
		}
	}
}
