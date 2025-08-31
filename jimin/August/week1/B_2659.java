package Baekjoon;

import java.util.Scanner;

public class B_2659 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int[] clockNumArr = new int[4];
		
		for (int i = 0; i < 4; i++) {
			clockNumArr[i] = sc.nextInt();
		}	
		
		//시계수 구하기 
		int minClockNumber = 9999;
		for (int i = 0; i < 4; i++) {
			int num = clockNumArr[i % 4] * 1000 + clockNumArr[(i + 1) % 4] * 100 + clockNumArr[(i + 2) % 4] * 10 + clockNumArr[(i + 3) % 4];
			minClockNumber = Math.min(minClockNumber, num);
		}	
		
		int targetNumber = minClockNumber;
		
		int count = 0;
		for (int clockNum = 1111; clockNum <= targetNumber; clockNum++) {
			String clockNumStr = Integer.toString(clockNum);
			if (clockNumStr.contains("0")) continue; // 0 포함되면 skip
			
		    // 숫자 4개로 나누기
		    int[] digits = new int[4];
		    for (int i = 0; i < 4; i++) {
		    	// '0'을 빼는 이유
		    	// clockNumStr.charAt(i)은 문자이기 때문에 사칙연산을 실행하면 아스키 코드로 저장됨 
		    	// 그래서 문자 '0'의 아스키코드 값은 48이고, 
		    	// '1' - '0' 은 49 - 48 = 1 이 되어, 문자 '1'이 숫자 1로 변환
		    	// 같은 원리로 '9' - '0' 은 57 - 48 = 9가 돼요.
		        digits[i] = clockNumStr.charAt(i) - '0'; 
		    }

		    // 회전하면서 가장 작은 시계수 구하기
		    int min = 9999;
		    for (int i = 0; i < 4; i++) {
		        int rotated =
		            digits[i % 4] * 1000 +
		            digits[(i + 1) % 4] * 100 +
		            digits[(i + 2) % 4] * 10 +
		            digits[(i + 3) % 4];
		        min = Math.min(min, rotated); //시계수 구하기 
		    }

		    if (min == clockNum) count++; // 자기 자신이 시계수일 때만 카운트
		}
		
		System.out.println(count);
	}

}
