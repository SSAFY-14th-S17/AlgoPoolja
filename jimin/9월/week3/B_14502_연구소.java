package Baekjoon;
//
//import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.Deque;
//import java.util.List;
//import java.util.Scanner;
//
//public class B_14502_연구소 {
//	static int n, m, result;
//	static int[][] arr;
//	static int[] dx = {-1,1,0,0};
//	static int[] dy = {0,0,-1,1};
//	static List<int[]> selected;  
//	static List<int[]> empty;
//	static List<int[]> virus;
//	
//	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		
//		virus = new ArrayList<>();
//		selected = new ArrayList<>();
//		empty = new ArrayList<>();
//
//		n = sc.nextInt();
//		m = sc.nextInt();
//		arr = new int[n][m]; 
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < m; j++) {
//				arr[i][j] = sc.nextInt();
//				if (arr[i][j] == 0) { //벽 세개를 뽑기 위해 빈칸을 수집
//					empty.add(new int[] {i,j});
//				} if (arr[i][j] == 2) { //바이러스 좌표 수집
//					virus.add(new int[] {i,j});
//				}
//			}
//		}
//		
//		//로직
//		//0은 빈 칸, 1은 벽, 2는 바이러스가 있는 곳
//		//벽의 개수는 3개이며, 꼭 3개를 세워야 한다.
//		//벽 세개를 뽑기 위해 빈칸을 수집 후 조합 
//		pickWallArea(0, 0);
//		
//		System.out.println(result);
//	}
//	
//	static void pickWallArea(int start, int depth) {
//	    if (depth == 3) { 	    	
//	        // 3개 선택 완료
//	    	buildWall(); //벽세우기 
//	        return;
//	    }
//
//	    for (int i = start; i < empty.size(); i++) {
//	        selected.add(empty.get(i));       // i번째 치킨집 선택
//	        pickWallArea(i + 1, depth + 1);  // 다음 조합 뽑기
//	        selected.remove(selected.size() - 1);  // 백트래킹
//	    }
//		
//	}
//	
//	static void buildWall() {
//		int[][] copy = new int[n][m];
//	    for (int i = 0; i < n; i++) {
//	        copy[i] = arr[i].clone(); // 맵 복사
//	    }
//	    
//	    //조합으로 뽑은 3곳에 벽 세우기 
//		for(int[] target : selected) {
//			copy[target[0]][target[1]] = 1;
//		}
//		
//		//바이러스 퍼뜨리기 
//		startVirus(copy);
//	}
//
//	static void startVirus(int[][] copy) {
//		Deque<int[]> q = new ArrayDeque<>();
//	    boolean[][] visited = new boolean[n][m];
//
//	    for (int[] v : virus) {
//	        q.add(v);
//	        visited[v[0]][v[1]] = true;
//	    }
//
//	    while (!q.isEmpty()) {
//	        int[] cur = q.poll();
//	        int x = cur[0];
//	        int y = cur[1];
//
//	        for (int d = 0; d < 4; d++) {
//	            int nx = x + dx[d];
//	            int ny = y + dy[d];
//	            if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
//	            if (copy[nx][ny] != 0 || visited[nx][ny]) continue;
//
//	            copy[nx][ny] = 2;
//	            visited[nx][ny] = true;
//	            q.add(new int[]{nx, ny});
//	        }
//	    }
//
//	  //안전영역(0개수 세기)
//	    calcSafeArea(copy);
//	}
//	
//	//안전영역(0개수 세기)
//	static void calcSafeArea(int[][] map) {
//	    int safe = 0;
//	    for (int i = 0; i < n; i++) {
//	        for (int j = 0; j < m; j++) {
//	            if (map[i][j] == 0) safe++;
//	        }
//	    }
//	    result = Math.max(result, safe);
//	}
//
//}

import java.io.*;
import java.util.*;

public class B_14502_연구소 {
    static int n, m, result;
    static int[][] arr;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static List<int[]> selected;
    static List<int[]> empty;
    static List<int[]> virus;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        arr = new int[n][m];
        virus = new ArrayList<>();
        selected = new ArrayList<>();
        empty = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                if (arr[i][j] == 0) empty.add(new int[]{i, j});
                else if (arr[i][j] == 2) virus.add(new int[]{i, j});
            }
        }

        pickWallArea(0, 0);
        System.out.println(result);
    }

    static void pickWallArea(int start, int depth) {
        if (depth == 3) {
            buildWall();
            return;
        }
        for (int i = start; i < empty.size(); i++) {
            selected.add(empty.get(i));
            pickWallArea(i + 1, depth + 1);
            selected.remove(selected.size() - 1);
        }
    }

    static void buildWall() {
        int[][] copy = new int[n][m];
        for (int i = 0; i < n; i++) copy[i] = arr[i].clone();

        for (int[] target : selected) copy[target[0]][target[1]] = 1;
        startVirus(copy);
    }

    static void startVirus(int[][] copy) {
        Deque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][m];

        for (int[] v : virus) {
            q.add(v);
            visited[v[0]][v[1]] = true;
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d], ny = y + dy[d];
                if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
                if (copy[nx][ny] != 0 || visited[nx][ny]) continue;

                copy[nx][ny] = 2;
                visited[nx][ny] = true;
                q.add(new int[]{nx, ny});
            }
        }

        calcSafeArea(copy);
    }

    static void calcSafeArea(int[][] map) {
        int safe = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 0) safe++;
            }
        }
        result = Math.max(result, safe);
    }
}

