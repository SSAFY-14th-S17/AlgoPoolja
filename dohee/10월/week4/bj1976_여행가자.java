package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * N개의 도시, 임의의 2개의 도시 사이에 길이 있을 수도 없을 수도 있
 * 동혁이의 여행 일정이 주어졌을 때, 해당 여행 경로가 가능한 것인가?
 * 각 도시 사이의 길이 있으면, A-B 형태로 주어짐
 * 
 * [문제 정리]
 * - 무방향 그래프
 * - 간선 목록이 주어짐
 * - 노드 방문 순서가 주어짐 (같은 노드를 여러 번 방문해도 괜찮음)
 * - 주어진 순서대로 이동 가능한 경로가 존재하는가?
 * 
 * [사용 알고리즘]
 * - 무방향 그래프에서 여러 개의 정점이 주어질 때
 * - 각 정점이 다른 정점을 거쳐서라도 이동 가능한가?
 * => 유니온 파인드: 정점들이 같은 연결 요소(그룹)에 속하느냐
 * 
 */

public class bj1976_여행가자 {
	static int N;
	static int M;
	
	// union find
	static int[] parent;
	
	static int find(int x) {
		if(parent[x] != x) parent[x] = find(parent[x]); //경로 압축
		return parent[x];
	}
	
	static void union(int x, int y) {
		x= find(x);
		y= find(y);
		if(x!=y) parent[y]=x;
	}
	
	public static void main (String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// [input]
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		// !! 1-based 인덱스
		// !! 부모 배열 초기화 
		parent = new int [N+1]; 
		for(int i=1; i<=N; i++ ) parent[i] =i;
		
		// 인접 행렬의 연결 관계(1)로 미리 union하기
		for(int i=1; i<=N; i++) {
			String[] tmp = br.readLine().split(" ");
			for(int j=1; j<=N; j++) { 
				int connected = Integer.parseInt(tmp[j-1]); // !! tmp는 0부터 시작
				if(connected==1) union(i,j);
			}
		}// for: connected
		
		// 여행 순서 입력
		String[] temp = br.readLine().split(" ");
		int[] journey = new int[M+1];
		for(int i=1; i<=M; i++) {
			journey[i] = Integer.parseInt(temp[i-1]); // !! tmp는 0부터 시작
		}
		
		// [logic]
		// journey 안의 정점들이 같은 그룹에 있는지 체크
		boolean possible = true;
		for(int i=1; i<M; i++) {
			if(find(journey[i]) != find(journey[i+1])) {
				possible = false;
				break; 
			}
		}
		
		// [output]
		System.out.println(possible?"YES":"NO");
	}
}
