import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class problem2 {
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt(); 

        for (int test_case = 1; test_case <= T; test_case++) {
            int N = sc.nextInt(); // 재료의수
            int L = sc.nextInt(); // 제한칼로리

            int[] scores = new int[N];
            int[] calories = new int[N];

            for (int i = 0; i < N; i++) {
                scores[i] = sc.nextInt();   // 맛점수
                calories[i] = sc.nextInt(); // 칼로리
            }

            int maxScore = 0; // 최고점수

            // 비트마스킹을 이용한 완전탐색 시작
            // 1 << N 은 2의 N제곱을 의미. 0부터 (2^N - 1)까지 모든 조합을 확인..
            for (int i = 1; i < (1 << N); i++) {
                
                int currentScore = 0;
                int currentCal = 0;

                // 현재 조합(i)에 어떤 재료들이 포함되는지 확인
                for (int j = 0; j < N; j++) {
                    // i의 j번째 비트가 1인지 확인 (1을 j번 왼쪽으로 shift하고 AND 연산)
                    // 1이면 j번째 재료를 포함하는 조합
                    if ((i & (1 << j)) != 0) {
                        currentScore += scores[j];
                        currentCal += calories[j];
                    }
                }

                // 현재 조합의 칼로리가 제한 칼로리 이하인 경우;
                if (currentCal <= L) {
                    // 최고 점수 갱신
                    maxScore = Math.max(maxScore, currentScore);
                }
            }

            System.out.println("#" + test_case + " " + maxScore);
        }
        
        sc.close();
    }
}
