import java.io.*;
import java.util.StringTokenizer;

public class 탈주범검거2 {
	
	//상하좌우 델타
	static final int[] dr = {-1, 1 , 0, 0};
	static final int[] dc = { 0, 0 ,-1, 1};
	static final int[] opp = {1, 0 , 3 , 2}; //반대값 적용 상하좌우 -> 하상우좌
	
	
	static final int[][] typeDirs = {
			{},
			{0,1,2,3},
			{0,1},
			{2,3},
			{0,3},
			{1,3},
			{1,2},
			{0,2},
	};
	
	
	// 빠른체크
	static final boolean[][] open = new boolean[8][4];
	static {
		for (int t = 1; t <= 7; t++) {
			for (int d : typeDirs[t]) {
				open[t][d] = true;
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int tc = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= tc; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int R = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			int L = Integer.parseInt(st.nextToken());
			
			
		}
	}
}