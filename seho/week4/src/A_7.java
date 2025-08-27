

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 요리사
 * 
 * 두 명의 손님 식성 비슷
 * 최대한 비슷한 맛의 음식
 * N개의 식재료
 * N/2개로 나누어 두개의 요리 N은 짝수
 * A음식 B음식
 * 맛의 차이가 최소가 되도록 재료 분배
 * 맛은 식재료 조합에 따라 다름
 * 식재료 i는 j와 요리하게 되면 궁합이 잘맞아 시너지가 발생
 * 음식의 맛은 시너지들의 합
 * 
 * 시너지 정보를 통히 식재료를 사용해 A음식과 B음식간의 맛의 차이가 최소가 되는 경우를 찾고 최솟값을 찾기
 */

public class A_7 {
	static int N;
	static int[][] ingredient;
	static List<Integer> foodA;
	static List<Integer> foodB;
	static boolean[] visited;
	static int min;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int T = sc.nextInt();
		
		for (int tc = 1; tc <= T; tc++) {
			N = sc.nextInt();
			
			ingredient = new int[N][N];
			
			visited = new boolean[N];
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					ingredient[i][j] = sc.nextInt();
				}
			}
			
			foodA = new ArrayList<>();
			foodB = new ArrayList<>();
			
			min = Integer.MAX_VALUE;
			dfs(0);
			
			System.out.println("#" + tc + " " + min);
		}
		
	}
	
	public static void dfs(int depth) {
		if (depth == N) {
			int sumA = cook(foodA);
			int sumB = cook(foodB);
			
			min = Math.min(min, Math.abs(sumA - sumB));
			return;
		}
		
		for (int i = 0; i < N; i++) {
			
		}
	}
	
	public static int cook(List<Integer> list) {
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				sum += ingredient[list.get(i)][list.get(j)];
			}
		}
		
		return sum;
	}

}
