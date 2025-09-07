package Swea;

import java.util.Scanner;

public class Swea_4014_활주로건설_이해했지만다시풀어보자 {
	static int n, x;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int T = sc.nextInt();
		
		for(int t = 1; t <= T; t++) {
			n = sc.nextInt();
			x = sc.nextInt();
			
			int[][] arr = new int[n][n];
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					arr[i][j] = sc.nextInt();
				}
			}
			
			int count = 0;
			//가로
			int[] col = new int[n];
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					col[j] = arr[i][j];
				}
				if(check(col)) count++;	
			}
			//세로
			col = new int[n];
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					col[j] = arr[j][i];
				}
				if(check(col)) count++;	
			}
			
			System.out.println("#" + t + " " + count);
		}
	}
	
	public static boolean check(int[] line) {
		boolean[] used = new boolean[n];
		
		for (int i = 0; i < n - 1; i++) {
			int diff = line[i+1] - line[i];
			
			if(diff == 0) continue; //높이 같음
			// 오르막 -> 뒤로 x칸 확인 
			else if (diff == 1) {
				for (int j = 0; j < x; j++) {
					if (i-j < 0 || //배열의 인덱스 벗어나면 실패 
						line[i-j] != line[i] || //높이가 다르면 실패 
						used[i-j] ) { //이미 경사로 있으면 실패
						return false;
					}
					used[i-j] = true; //조건을 만족하면 경사로 설치했다고 표시 
				}
			}
			// 내리막 -> 앞으로 x칸 확인 
			else if (diff == -1) {
				for (int j = 0; j < x; j++) {
					if (i+1+j >= n || line[i+1+j] != line[i+1] || used[i+1+j]) {
						return false;
					}
					used[i+1+j] = true;
				}
			}
			else return false; //높이 차이 2이상 -> 불가능
		}
		
		return true; //마지막 까지 문제 없으면 활주로 설치 가능하다는 의미로 true 반환 
		
	}

}
