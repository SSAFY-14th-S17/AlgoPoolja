package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
 * 1 ~18의 수가 적힌 18장의 카드
 * 2명이 9장씩, 9라운드
 * 
 * 한 라운드: 한 장씩 카드를 낸 후 
 * - 높은 수가 나온 사람: 두 카드에 적힌 수의 합만큼 점수
 * - 낮은 수가 나온 사람  : 점수 X
 * 
 * 9라운드를 끝내고 총점을 따졌을 때, 총점이 더 높은 사람이 승자
 * 같으면 무승부
 * 
 * 규영이가 내는 카드의 순서를 고정 -> 인영이가 낼 수 있는 방법: 9!
 * 이것에 따라 규영이의 승패가 정해짐
 * 
 * Q : 규영이가 이기는 경우와 지는 경우가 총 몇 가지인가?
 */
public class swea6808_카드게임 {
	
	static int[] kyuCards= new int[9];
	static int[] inCards= new int[9];
	static int[] inCardCases = new int[9];
	static final int TotalCount = 362880;
	static int kyuWincount;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
	
		
		for(int t=1; t<=T; t++) {
			
			// [input]
			String[] temp = br.readLine().split(" ");
			
			
			
			// 1 ~ 18까지의 카드
			List<Integer> cards = new ArrayList<>() ;
			for(int i=1; i<=18; i++) {
				cards.add(i);
			}
			

			// 규영이 카드
			for(int i=0; i<9; i++) {
				kyuCards[i] = Integer.parseInt(temp[i]);
				if(cards.contains(kyuCards[i])) {
					cards.remove(Integer.valueOf(kyuCards[i]));
				}
			}
			
			// 인영이 카드
			for(int i=0; i<9; i++) {
				inCards[i] = cards.get(i);
			}
			
			
			//System.out.println(Arrays.toString(kyuCards));
			//System.out.println(Arrays.toString(inCards));

			// [logic]
			// 인영이 카드 순서 구하기
			boolean[] visited = new boolean[9];
			kyuWincount = 0; // 규영이가 몇 번 이기는지 카운트

			inCardsOrder(0, visited); // 인영이 카드의 순열을 구해 차례대로 -> countKyuWin 
			
		System.out.println("#"+t+" "+kyuWincount+ " " + (TotalCount-kyuWincount));
			
			
			
		}// for: T
		
	}// main

	/**
	 * 인영이의 카드 순열 생성하는 함
	 * @param depth: 현재 몇 번째 카드인
	 * @param visited: 방문 체크
	 */
	private static void inCardsOrder(int depth,boolean[] visited) {
		// base-case
		if(depth ==9) {
			countKyuWin(inCardCases);
			return;
		}
		
		for(int i=0; i<9;i++) {
			
			if(!visited[i]) {
				// do
				visited[i] = true;
				inCardCases[depth] = inCards[i];
				
				inCardsOrder(depth+1, visited);
				// undo
				visited[i] = false;
				
			}
		}
		
	}//

	/**
	 * 인영이의 카드 순서를 입력받아 규영이가 게임을 이기는 횟수를 카운트하는 함수
	 * @param inCardCase: 인영이의 카드 순서
	 */
	private static void countKyuWin(int[] inCardCase) {
		int kyuScore = 0;
		int inScore = 0;
		for(int i=0; i<9; i++) {
			if(kyuCards[i] > inCardCase[i]) {
				kyuScore += kyuCards[i] + inCardCase[i];
			}
			else if (kyuCards[i] < inCardCase[i]) {
				inScore += kyuCards[i] + inCardCase[i];
			}
			// 동점 -> 점수 x
		}
		if(kyuScore > inScore) kyuWincount ++;
	}//
}


