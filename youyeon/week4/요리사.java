package youyeon.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class 요리사 {

    static boolean[] selected;
    static int[][] synergy;
    static int N;
    static int minDiff;

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++){

            // 입력받기
            N = Integer.parseInt(br.readLine()); // N개의 식재료의 수
            synergy = new int[N][N];
            selected = new boolean[N];

            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < N ; j++) {
                    synergy[i][j] = Integer.parseInt(line.split(" ")[j]);
                }
            }

            minDiff = Integer.MAX_VALUE; // 초기화

            dfs(0,0);
            System.out.println("#" + tc + " " + minDiff);
        }
    }

    // dfs 정의
    public static void dfs(int idx, int cnt) {

        if (cnt == N/2) {
            calculate();
            return;
        }

        for (int i = idx; i < N; i++) {
            selected[i] = true; // 자기자신 선택했을 때
            dfs(i+1, cnt+1); // 다음
            selected[i] = false; // 선택하지 않았을 때
        }
    }

    public static void calculate() {
        int sumA = 0;
        int sumB = 0;

        for (int i =0; i < N; i++) {
            for (int j= 0; j < N; j++) {

                // 비교할 필요 없음
                if (i == j) continue;

                if (selected[i] && selected[j]) {
                    sumA += synergy[i][j];
                }
                else if (!selected[i] && !selected[j]) {
                    sumB += synergy[i][j];
                }
            }
        }
        int diff = Math.abs(sumA - sumB);
        minDiff = Math.min(diff, minDiff);
    }
}
