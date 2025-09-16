package SWEA_4012_요리사;
/*
 * [문제]
 * 2명의 손님: 식성 비슷 -> 최대한 비슷한 맛의 음식 
 * 식재료 N/2 -> 2개의 요리: A / B (맛의 차이가 최소가 되도록 재료를 배분)
 * 식재료 i & j => 시너지  Sij  (1 ≤ i ≤ N, 1 ≤ j ≤ N, i ≠ j)
 *  A음식과 B음식을 만들 때, 두 음식 간의 맛의 차이가 최소가 되는 경우를 찾고 그 최솟값을 정답으로 출력
 *  
 * A: Sij + Sji
 * B: Sij + Sji
 * | A - B |
 * 
 * [input]
 * T
 * N: even num  (4 ≤ N ≤ 16)
 * N개의 줄에는 N * N개의 시너지 Sij값
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			
			// [input]
			int N = Integer.parseInt(br.readLine());
			int[][] sTable = new int[N+1][N+1];
			
			for(int r=1; r<=N; r++) {
				String[] temp = br.readLine().split(" "); // 한 줄씩 읽기
				for(int c=1; c<=N; c++) {
					sTable[r][c] = Integer.parseInt(temp[c-1]);
				}
			}
			
			// [logic]
			List<List<Integer>> IngredientsForA = choosenIngredientsForA(N);
			List<List<Integer>> IngredientsForB = choosenIngredientsForB(N, IngredientsForA);
			
			List<Integer> tastesOfFoodA = tastesOfFood(sTable,IngredientsForA);
			List<Integer> tastesOfFoodB = tastesOfFood(sTable,IngredientsForB);
			
			int min = findBestComb(tastesOfFoodA, tastesOfFoodB);
				
			System.out.printf("#"+t+" "+min+"\n");
			}// for: t
			
			
	}// main
		

		
	// A 요리 사용할 수 있는 재료의 조합 : N개의 식재료 중 N/2개 선택
	public static List<List<Integer>> choosenIngredientsForA (int N) {
		List<List<Integer>> ij = new ArrayList<>();
		
		int r = N/2;
		
		dfs(N,r,1, new ArrayList<>(), ij);
		
		return ij;
	} 
	
	/**
	 * 
	 * @param N : 전체 재료 개수
	 * @param r : 뽑아야 하는 재료 개수 (N/2)
	 * @param start : 다음 재료 선택을 시작할 인덱스 번호
	 * @param current: 현재까지 선택한 재료들의 목록
	 * @param ij: 모든 조합을 담는 결과 리스트
	 */
	public static void dfs(int N, int r, int start, List<Integer> current, List<List<Integer>> ij) {
		// base-case
		if(current.size()==r) {
			// XX ij.add(current) XX
			ij.add(new ArrayList<>(current));
		}
		
		// recursion
		for(int i=start; i<=N; i++) {
			current.add(i);
			dfs(N,r,i+1, current,ij);
			current.remove(current.size()-1);
		}
	}
	
	// B요리에 사용할 수 있는 재료의 조합
	public static List<List<Integer>> choosenIngredientsForB (int N, List<List<Integer>> choosenIngredientsForA ){
		List<List<Integer>> choosenIngredientsForB = new ArrayList<>();
		
		for( List<Integer> ingredientsforA : choosenIngredientsForA) {
			List<Integer> ingredientsforB = new ArrayList();
			for(int i=1; i<=N; i++) {
				if(!ingredientsforA.contains(i)) {
					ingredientsforB.add(i);
				}
			}
			choosenIngredientsForB.add(ingredientsforB);
		}
		
		return choosenIngredientsForB;
	}
	
	
	// 음식의 맛
	// 같은 식재료는 같이 사용 x 
	// 문제 조건: (1 ≤ i ≤ N, 1 ≤ j ≤ N, i ≠ j)
	// 각 조합에서 (i=j)를 제외한 순열을 만들어 더하기
	public static List<Integer> tastesOfFood (int[][] sTable, List<List<Integer>> choosenIngredients) {
		
		List<Integer> result = new ArrayList<>();
		
		for(List<Integer> ingredients: choosenIngredients) {
			int sum = 0;
			int n = ingredients.size();
			
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					// i,j 값이 아니라 그 번째의 값!
					int ith = ingredients.get(i);
					int jth = ingredients.get(j);
					if(ith != jth) {
						sum+= sTable[ith][jth];
					}
				}
			}
			result.add(sum);
		}
		
		return result;
	
	}
	
	public static int findBestComb (List<Integer>tastesOfFoodA,List<Integer>tastesOfFoodB ) {
		int min = Integer.MAX_VALUE;
		for(int i=0; i<tastesOfFoodA.size();i++) {
			int abs = Math.abs(tastesOfFoodA.get(i)-tastesOfFoodB.get(i));
			if(abs < min) min = abs;
		}
		return min;
	}
	
	
}
