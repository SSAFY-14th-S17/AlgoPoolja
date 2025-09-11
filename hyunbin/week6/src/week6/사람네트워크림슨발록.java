package week6;

import java.io.*;
import java.util.*;

public class 사람네트워크림슨발록 {
	static int INF = 1_000_000_000;
	static int[][] dist;
	static int N;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= tc; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			dist = new int[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int connectionInfo = Integer.parseInt(st.nextToken());
					if (i == j) {
						dist[i][j] = 0;
					} 
					else if (connectionInfo == 1) {dist[i][j]	= 1;}
					else {dist[i][j] = INF;}
				}
			}
			
			for (int k = 0; k < N; k++) { //k 경유 노드
				for (int i = 0; i < N; i++) { // i 출발 노드
					for (int j = 0; j < N; j++) { // j 도착 노드 
						if(dist[i][j] > dist[i][k] + dist[k][j]) {
							dist[i][j] = dist[i][k] + dist[k][j];
						}
					}
				}
			}
			
			int minCC = Integer.MAX_VALUE;
			for (int i = 0; i < N; i++) { 
				int curCC = 0; //현재 노드 i 의 cc 값
				for (int j = 0; j < N; j++) {
					curCC += dist[i][j];					
				}
				if (curCC < minCC) {minCC = curCC;}
			}
			System.out.println("#" + t + " " + minCC);
		}
		
	}
}

