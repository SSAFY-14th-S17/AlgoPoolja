import java.io.*;
import java.util.*;


public class 인구이동 {
	/*
	 *  AB 
	 *  CD
	 *  예시로 이렇게 nxn 배열 속에 국가가 있는데
	 *  인접한 국가들이 L명 이상 R명 이하라면 두 나라가 공유하는 국경선을 만든다 
	 *  인구이동 공식 (인접한 국가들의 인구의 합)/인접한 국가들의 수 -> 소수점 버림 퉤
	 *  이게 1 루프 하루가 지남
	 *  더이상 연합이 안될때까지 반복
	 */
	
	static int N, L ,R;
	static int[][] A;
	static boolean[][] visited;
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	
	static class Pos{
		int r, c;
		Pos(int r , int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		A = new int[N][N];
		
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
				//System.out.print(A[i][j] + " ");
			}
			//System.out.println();
		}
		
		int days = 0;
		
		while(true) {
			visited = new boolean[N][N];
			boolean moved = false;
			
			//킬킬킬 기괴한 포문
			for (int r = 0; r < N; r++) for (int c = 0; c < N; c++) {
				if(!visited[r][c]) {
					List<Pos> unionstate = new ArrayList<>();
					int sum = bfs( r, c, unionstate);
					
					if (unionstate.size() >=2 ) {
						int avg = sum / unionstate.size();
						for (Pos p : unionstate) {
							A[p.r][p.c] = avg;
						}
						moved = true;	
					}
				}
			}
			if (!moved) {
				break;
			}
			days++;
		}
		System.out.println(days);
		
	}
	static int bfs(int sr , int sc, List<Pos> unionstate) {
		Deque<Pos> dq = new ArrayDeque<>();
		dq.offer(new Pos(sr , sc));
		visited[sr][sc] = true;
		
		unionstate.add(new Pos(sr, sc));
		int sum  = A[sr][sc];
		while (!dq.isEmpty()) {
			Pos cur = dq.poll();
			
			for (int d = 0; d < 4; d++) {
				int nr = cur.r + dr[d];
				int nc = cur.c + dc[d];
				
				if (!inRange(nr,nc) || visited[nr][nc]) continue;
				
				int diff = Math.abs(A[cur.r][cur.c] - A[nr][nc]);
				if (L <= diff && diff <= R ) {
					visited[nr][nc] = true;
					dq.offer(new Pos(nr, nc));
					unionstate.add(new Pos(nr , nc));
					sum += A[nr][nc];
					
				}
			}
		}
		return sum;
	}
	static boolean inRange(int r ,int c) {
		return 0 <= r && r < N && 0 <= c && c < N;
	}
}

