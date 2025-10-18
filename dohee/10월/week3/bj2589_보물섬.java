package week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;


public class bj2589_보물섬 {
	static int N,M;
	static char[][] map;
	static int maxDist = 0;
	static int[] dr= {-1,1,0,0};
	static int[] dc= {0,0,-1,1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// [input]
		String[] tmp = br.readLine().split(" ");
		
		N = Integer.parseInt(tmp[0]); // 세로 크기 = 행의 개수
		M = Integer.parseInt(tmp[1]); // 열
		
		map = new char[N][M];
		
		for(int i=0; i<N; i++) {
				String temp = br.readLine();
			for(int j=0; j<M; j++) {
				map[i][j]=temp.charAt(j);
			}
		}
		
		//System.out.println(Arrays.deepToString(map));
		
		
		 // [logic]
		// 모든 육지 쌍 중 가장 긴 최단거리
		for(int i=0; i<N; i++) {
			for(int j=0; j<M;j++) {
				if (map[i][j]=='L') { // !! 모든 L에 대해
					findMaxDist(i,j);
				}
			}
		}
		
		System.out.println(maxDist);
		
	
	}// main

	// 하나의 클러스터당 돌릴 bfs
	private static void findMaxDist(int cr, int cc) {
		boolean[][] visited = new boolean[N][M];
		visited[cr][cc]=true;
		
		Deque<int[]> q = new ArrayDeque<>();
		q.add(new int[] {cr, cc,0}); // 현재 행, 열, 거리 누적값
		
		while(!q.isEmpty()) {
			int[] currRCD = q.poll();
			int currR = currRCD[0];
			int currC = currRCD[1];
			int currD = currRCD[2];
			
			maxDist = Math.max(currD, maxDist);
			
			for(int d=0; d<4; d++) {
				int newR = currR + dr[d];
				int newC = currC + dc[d];
				
				if(newR<0|| newR >=N || newC<0|| newC>=M) continue;
				
				if(!visited[newR][newC]&& map[newR][newC]=='L') {
					visited[newR][newC] = true;
					q.add(new int[] {newR,newC,currD+1}); //!! 한 레벨씩 거리 증가시키기 
				}
			}
			
	
		}
		
	}// f: findMaxDist

}
