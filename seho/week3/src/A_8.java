

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 수영장
 * 내년 1년 동안 각 달의 이용 계획 수립 -> 가장 적은 비용으로 수영장을 이용할 수 있는 방법
 * 
 * 수영장에서 판매하는 이용권
 * 1일 이용권 : 1일 이용 가능
 * 1달 이용권 : 1달 이용 가능 1달 이용권은 매달 1일 시작
 * 3달 이용권 : 연속된 3달 동안 이용 가능 3달 이용권은 매달 1일 시작
 * (11월 12월에도 3달 이용권 사용 가능 해를 넘어가도록 구매할 순 없음)
 * 1년 이용권 : 1년 이용 가능 -> 매년 1월 1일 시작
 * 
 * 각 달의 이용 계획이 테이블 형태로 수립됨 (해당 달에 수영장을 이용할 날의 수)
 * 
 * 각 이용권의 요금과 각 달의 이용 계획이 입력으로 주어질 때,
 * 가장 적은 비용으로 수영장을 이용할 수 있는 방법을 찾고 비용을 정답으로 출력하는 프로그램 작성
 * 
 * 1일 - 10, 1달 - 40, 3달 100, 1년 300
 * 
 */

public class A_8 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			
			// 각 이용권 요금 저장
			int[] ticket = new int[4];
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < 4; i++) {
				ticket[i] = Integer.parseInt(st.nextToken());
			}
			
			// 월별 이용 횟수 저장
			// 0번인덱스 사용 x
			int[] month = new int[13];
			st = new StringTokenizer(br.readLine());

			for (int i = 1; i <= 12; i++) {
				month[i] = Integer.parseInt(st.nextToken());
			}
			
			// 1년치를 min 값으로 잡음
			int min = ticket[3];
			
			int[] dp = new int[13];
			
			// 1월부터 12월 까지 최소비용 저장
			for (int i = 1; i <= 12; i++) {
				dp[i] = dp[i-1] + (month[i] * ticket[0]);
				
				// 1달치
				int mon1 = dp[i-1] + ticket[1];
				
				// 일일과 1달치 비교 후 갱신
				dp[i] = Math.min(dp[i], mon1);
				
				// 3달치
				if (i-3 >= 0) {
					int mon3 = dp[i-3] + ticket[2];
					// 비교 후 갱신
					dp[i] = Math.min(dp[i], mon3);
				}
			}
			
			// 1년치 비교
			min = Math.min(dp[12], ticket[3]);
			
			System.out.println("#" + tc + " " + min);
		}
		
	}
}
