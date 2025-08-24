package Swea;

import java.util.Scanner;
//DP - 동적계획법 
public class Swea_1952_수영장 {
	//dp배열 생성
	static int[] dp = new int[13];
	static int[] plan = new int[13];
	static int oneDay;
	static int oneMon;
	static int threeMon;
	static int oneYear;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		for(int T = 1; T <= t; T++) {
			//입력
			//1일 이용권의 요금, 1달 이용권의 요금, 3달 이용권의 요금, 1년 이용권의 요금
			oneDay = sc.nextInt();
			oneMon = sc.nextInt();
			threeMon = sc.nextInt();
			oneYear = sc.nextInt();
			//1월부터 12월까지의 이용 계획
			plan[0] = 0;
			for(int i = 1; i < 13; i++) {
				plan[i] = sc.nextInt();
			}

			//dp배열
			dp[0] = 0;
			
			//로직 
			for(int i = 1; i < 13; i++) {
				dp(i);
			}
			
			//마지막 1년 권과 비교해서 더 작은 값을 출력 
            //계속 전달과 비교하여 최저 값을 찾아왔기 때문에 dp[12]가 최저값
			System.out.println("#" + T + " " + Math.min(dp[12], oneYear));
		}
	}
	
	static void dp(int mon) {
		//계속 dp[mon - 1]을 하는 이유는 이전 달까지의 최소 비용(dp[mon-1])과 이번 달에 사용하는 이용권 비용을 합쳐야 하기 때문
		//1일권
		dp[mon] = plan[mon] * oneDay + dp[mon - 1]; 
		//1달권 
		dp[mon] = Math.min(dp[mon], oneMon + dp[mon - 1]); //1일권과 1달권 중 최소를 찾아 지정 
		//3달권		
		dp[mon] = Math.min(dp[mon], threeMon + dp[Math.max(0, mon-3)]);
	}

}
