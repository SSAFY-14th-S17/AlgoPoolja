package youyeon.week4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class 활주로건설 {

    static int N, X;

    public static void main(String[] args) throws IOException {
        // 로컬에서 파일로 테스트하고 싶으면 아래 주석을 해제하세요.
        System.setIn(new FileInputStream("C:\\Users\\yurry\\Desktop\\sample_input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            String[] nx = br.readLine().split(" ");
            N = Integer.parseInt(nx[0]);
            X = Integer.parseInt(nx[1]);

            int[][] plane = new int[N][N];
            for (int i = 0; i < N; i++) {
                String[] line = br.readLine().split(" ");
                for (int j = 0; j < N; j++) {
                    plane[i][j] = Integer.parseInt(line[j]);
                }
            }
            int answer = 0;

            // 가로를 기준으로 검증
            for (int i = 0; i < N; i++) {
                if (isPossible(plane[i])) {
                    answer++;
                }
            }

            // 세로를 기준으로 검증
            for (int i = 0 ; i<N; i++) {
                int[] col = new int[N];
                for (int j = 0; j < N; j++) {
                    col[j] = plane[j][i]; // 세로만
                }

                if (isPossible(col)) {
                    answer++;
                }
            }
            System.out.println("#" + tc + " " + answer);
        }
    }

    // 한 줄 마다 실행
    public static boolean isPossible(int[] arr) {

        // 사용될 연속된 길이
        int cnt = 1;

        for (int i = 1; i < N; i++) {

            // 차이를 기준으로 경우 나누기
            int diff = arr[i] - arr[i-1];

            if (Math.abs(diff) > 1) {
                return false;
            }

            // 1. 오르막길을 마주함
            if (diff == 1) {
                if (cnt >= X) {
                    cnt = 1; // 초기화
                } else {
                    return false;
                }
            }

            // 2. 평지를 마주함
            else if (diff == 0) {
                cnt++; // 계속 연속된 길이 세면 됨
            }

            // 3. 내리막길을 마주함
            else { // diff == -1
                if (cnt >= 0) {
                    cnt = -X + 1; // -X만큼의 제약을 가지고 for문 반복
                } else {
                    // 계속 음수
                    return false;
                }
            }
        }
        return cnt >= 0;
    }
}
