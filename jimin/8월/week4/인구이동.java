package Baekjoon;

import java.util.*;

public class 인구이동 {
    static int n, L, R;
    static int[][] arr;
    static boolean[][] visited;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    // DFS로 찾은 연합 좌표 저장
    static List<int[]> union;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        L = sc.nextInt();
        R = sc.nextInt();

        arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = sc.nextInt();
            }
        }

        int days = 0; // 인구 이동 횟수

        while (true) {
            visited = new boolean[n][n];
            boolean moved = false; // 오늘 인구 이동 있었는지?

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!visited[i][j]) {
                        union = new ArrayList<>();
                        dfs(i, j); // (i,j)에서 DFS 시작

                        if (union.size() > 1) { // 연합이 2개 이상이면 인구 이동
                            moved = true;
                            int sum = 0;
                            for (int[] pos : union) {
                                sum += arr[pos[0]][pos[1]];
                            }
                            int avg = sum / union.size();

                            for (int[] pos : union) {
                                arr[pos[0]][pos[1]] = avg;
                            }
                        }
                    }
                }
            }

            if (!moved) break; // 이동 없으면 끝
            days++;
        }

        System.out.println(days);
    }

    static void dfs(int x, int y) {
        visited[x][y] = true;
        union.add(new int[]{x, y}); // 현재 나라를 연합에 추가

        // 4방향 탐색
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            // 범위 밖이면 패스
            if (nx < 0 || ny < 0 || nx >= n || ny >= n) continue;
            // 이미 방문한 나라이면 패스
            if (visited[nx][ny]) continue;

            // 인구 차이 계산
            int gap = Math.abs(arr[x][y] - arr[nx][ny]);

            // 국경 열 수 있으면 DFS 재귀
            if (gap >= L && gap <= R) {
                dfs(nx, ny);
            }
        }
    }
}