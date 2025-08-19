package Baekjoon;

import java.util.Scanner;

public class Baekjoon_2578_빙고 {
    // 빙고 줄 개수 세기
	public static int countBingo(int[][] bingo) {
		int count = 0;
        int cross1 = 0, cross2 = 0;

        for (int i = 0; i < 5; i++) {
            int row = 0, col = 0;
            for (int j = 0; j < 5; j++) {
                if (bingo[i][j] == 0) row++;
                if (bingo[j][i] == 0) col++;
            }
            if (row == 5) count++;
            if (col == 5) count++;
        }

        for (int i = 0; i < 5; i++) {
            if (bingo[i][i] == 0) cross1++;
            if (bingo[i][4 - i] == 0) cross2++;
        }
        if (cross1 == 5) count++;
        if (cross2 == 5) count++;

        return count;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[][] bingo = new int[5][5];
		for(int i = 0; i<5; i++) {
			for(int j = 0; j<5; j++) {
				bingo[i][j] = sc.nextInt();
			}
		}
		
		//빙고 숫자 부르기 시작
		int gameCount = 0; //게임 진행 수 
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				int target = sc.nextInt();
				gameCount++; //게임 진행 수 업
				
				for (int p = 0; p < 5; p++) {
					for (int q = 0; q < 5; q++) {
						//부른 숫자 빙고판에서 찾아서 지우기
						if (bingo[p][q] == target) {
							bingo[p][q] = 0; //불린 숫자를 0으로 표시
							break;
						}	
					}
				}	
				
				// 5개 이상 불린 후부터 빙고 검사
	            if (gameCount >= 5) {
	                int bingoCount = countBingo(bingo);
	                if (bingoCount >= 3) {
	                    System.out.println(gameCount);
	                    return;
	                }
	            }
			}
		}	
	}
}
