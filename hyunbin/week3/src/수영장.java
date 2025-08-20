import java.io.*;
import java.util.*;

public class 수영장 {
	/*
	 * 	수영장 계획수립.
	 * 
	 * 	이용권 목록 	
	 * 	10원 : 1일 이용권
	 * 	40원 : 1달 이용권 : 매달 1일 부터 시작
	 * 	100원 : 3달 이용권 : 연속된 3달 이용가능 만약 다음 해를 넘어갈 시 소멸.
	 * 	300원 : 1년 이용권 : 1년동안 사용가능 이용일 시작은 1월 1일
	 * 	
	 * 	위의 이용권의 가격은 예시이고 각 테스트 케이스 마다 이용권 요금이 정해진다
	 * 	
	 * 	1~12월의 계획표가 주어질 경우 가장 적은 값으로 수영장을 이용 할 수 있는 방법을 찾아야한다.
	 * 
	 * 	예시로 1달에 4번 이하로 이용권을 이용할 수 있는경우엔 1일 이용권을 사용하고
	 * 	예로 1~3월달 동안 10일이 넘어가면 3달 이용권을 사용하는 것이 이득이다.
	 * 	만약 모든 합의 이용일이 30일이 넘어가고 1,3,10이렇게 띄엄띄엄있을 경우 1년 이용권을 이용하는 것이 합리적이다.
	 * 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tc = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= tc; t++) {
			StringTokenizer st;
			st= new StringTokenizer(br.readLine());
			
			int day = Integer.parseInt(st.nextToken());
			int month = Integer.parseInt(st.nextToken());
			int threeMonth = Integer.parseInt(st.nextToken());
			int year = Integer.parseInt(st.nextToken());
			
			int[] plan = new int[13];
			st = new StringTokenizer(br.readLine());
			
			for (int i = 1; i < plan.length; i++) {
				plan[i] = Integer.parseInt(st.nextToken());
			}
			
			int[] dp = new int[13];
			
			dp[0] = 0;
			
			for (int i = 1; i < dp.length; i++) {
				//1일권 vs 1달권
				dp[i] = dp[i-1] + Math.min(plan[i] * day , month);
				
				if (i >= 3) {
					dp[i] = Math.min(dp[i], dp[i-3] + threeMonth);
				}
				
			}
			int answer = Math.min(dp[12], year);
			System.out.println("#" + t + " " + answer);
			
		}
	}
}