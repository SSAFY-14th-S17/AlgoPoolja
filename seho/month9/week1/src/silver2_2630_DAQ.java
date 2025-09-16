

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 색종이 만들기 - 분할정복
 * 
 * 전체 종이 크기 N * N -> (N = 2^k 1 <= k <= 7)
 * 전체 종이가 모두 같은색으로 칠해져있지 않으면 가로 세로 중간 부분을 잘라 같은 크기의 네 개의 N/2 * N/2 색종이로 나눔
 * 종이가 모두 하얀색 or 파란색 칠해져 있거나 하나의 정사각형 칸이되어 더이상 자를 수 없을 때 까지 반복
 * 흰색 종이와 파란색 종이 개수 구하기
 * 
 */

/* 입출력
8
1 1 0 0 0 0 1 1
1 1 0 0 0 0 1 1
0 0 0 0 1 1 0 0
0 0 0 0 1 1 0 0
1 0 0 0 1 1 1 1
0 1 0 0 1 1 1 1
0 0 1 1 1 1 1 1
0 0 1 1 1 1 1 1
 */

public class silver2_2630_DAQ {
	static int N;
	static int[][] matrix;
	
	static int B, W;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		matrix = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		W = 0;
		B = 0;
		
		recursion(0, 0, N);
		
		System.out.println(W);
		System.out.println(B);
		
	}
	
	public static void recursion(int r, int c, int len) {
		if (check(r, c, len)) {
			if (matrix[r][c] == 0) {
				W++;
			} else {
				B++;
			}
			return;
		}
		
		int divLen = len / 2;
		
		// 분할
		recursion(r, c, divLen);
		recursion(r, c + divLen, divLen);
		recursion(r + divLen, c, divLen);
		recursion(r + divLen, c + divLen, divLen);		
	}
	
	// 색상이 모두 같은지 확인
	public static boolean check(int r, int c, int len) {
		int tmp = matrix[r][c];
		for (int i = r; i < r + len; i++) {
			for (int j = c; j < c + len; j++) {
				if (matrix[i][j] != tmp) {
					return false;
				}
			}
		}
		
		return true;
	}
}
