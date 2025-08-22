package swea;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

//어떻게 푸는지 모르겠어서 지피티에게 힌트를 얻었더니 DP로 푸는 문제라고 하더라
//한 달 씩을 지나면서 가능한 경우를 구하고 그 중에서 가장 최소비용이 드는 경로를 선택함
public class 수영장_1952 {
	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("src/input2.txt"));

		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();

		for (int tc = 1; tc <= T; tc++) {
			// 이용권 가격을 담을 배열 생성
			int[] price = new int[4];

			for (int i = 0; i < 4; i++) {
				price[i] = sc.nextInt();
			}

			int[] plan = new int[13];

			for (int i = 1; i < 13; i++) {
				plan[i] = sc.nextInt();
			}

			// dp 배열 만들기
			int[] dp = new int[13];

			// 0번째 달은 0으로 초기화
			dp[0] = 0;

			int oneDay = 0;
			int oneMonth = 0;
			int threeMonth = Integer.MAX_VALUE;
			int threeMonth2 = Integer.MAX_VALUE;

			int minPrice = 0;

			int oneDayPrice = price[0];
			int oneMonthPrice = price[1];
			int threeMonthPrice = price[2];

			int oneYearPrice = price[3];

			for (int i = 1; i < 13; i++) {
				// dp 주의할 점 : 누적된 부분이기 때문에 이전까지의 비용인 dp[i - 1] 같은 게 있어야 한다.

				// 1일권을 끊는 경우 //지금까지 누적된 비용

				oneDay = dp[i - 1] + (plan[i] * oneDayPrice);

				// 1달권을 끊는 경우
				oneMonth = dp[i - 1] + oneMonthPrice;

				// 3달권을 끊는 경우 (3월부터 고려하기)
				if (i >= 3) {
					// 누적된 경우이기 때문에 3개월 전 + 3개월 비용으로 계산하기
					threeMonth = dp[i - 3] + threeMonthPrice;
					// 아래와 같이 계산하는 게 아니다
//					threeMonth = threeMonthPrice - dp[i - 1] - dp[i - 2];
				}

				// 11월과 12월에는 뒤로 3개월을 끊는 경우도 고려해야 한다. -> 이거 이해가 안됨
//				if (i >= 11) {
//					threeMonth2 = dp[i - 1] + threeMonthPrice;
//				}
				
				dp[i] = Math.min(oneDay, oneMonth);
				if (i >= 3) {
					dp[i] = Math.min(threeMonth, dp[i]);
				}


			}

			int answer = dp[12];

			// 1년권 끊는거랑 비교
			if (oneYearPrice < dp[12]) {
				answer = oneYearPrice;
			}

			System.out.println("#" + tc + " " + answer);

		}

	}

}
