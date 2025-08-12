import java.util.Scanner;

public class 햄버거다이어트_5215 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int tc = 1; tc <= T; tc++) {

            int N = sc.nextInt(), L= sc.nextInt(); // 재료, 칼로리 수
            int[] score = new int[N];
            int[] cal = new int[N];

            // 입력
            for (int i = 0; i < N; i++) {
                score[i] = sc.nextInt();
                cal[i] = sc.nextInt();
            }

            int maxScore = 0;

            // 완전탐색으로 가능한 조합 배열을 통한 합 구하기
            for (int n = 1; n <= N; n++) { // n: 인덱스 조합의 개수
                int[] indices = new int[n]; // 인덱스 배열
                for (int i = 0; i < n; i++) {
                    indices[i] = i; // 조합별로 인덱스 배열 생성
                }

                while (indices[0] <= N-n) {
                    int totalScore = 0, totalCal = 0; // 매번 초기화

                    // 인덱스 배열에 따라 더하기
                    for (int i = 0; i < n; i++) {
                        totalScore += score[indices[i]];
                        totalCal += cal[indices[i]];
                    }

                    if (totalCal <= L) {
                        maxScore = Math.max(maxScore, totalScore);
                    }

                    int i = n -1; // 사전식으로 다음 배열 만들기 위한 절차
                    while (i >= 0 && indices[i] == N - n + i) {
                        i--;
                    }

                    if (i < 0) { // 전체 while 종료 조건
                        break;
                    }
                    indices[i]++;
                    for (int j = i +1; j < n; j++) {
                        indices[j] = indices[j-1] +1;
                    }
                }
            }
            System.out.println("#" + tc + " " + maxScore);
        }
    }
}