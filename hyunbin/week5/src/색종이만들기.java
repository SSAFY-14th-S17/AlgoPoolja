import java.io.*;
import java.util.*;

public class 색종이만들기 {
    static int N;
    static int[][] A;
    static int white, blue;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());
        A = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0, N);
        System.out.println(white);
        System.out.println(blue);
    }

    // (x, y)에서 시작하는 size x size 영역 처리
    static void dfs(int x, int y, int size) {
        int sum = 0;
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                sum += A[i][j];
            }
        }

        if (sum == 0) {          // 전부 0(흰색)
            white++;
            return;
        }
        if (sum == size * size) { // 전부 1(파란색)
            blue++;
            return;
        }

        // 섞여 있으면 4등분
        int half = size / 2;
        dfs(x,         y,         half);
        dfs(x,         y + half,  half);
        dfs(x + half,  y,         half);
        dfs(x + half,  y + half,  half);
    }
}
