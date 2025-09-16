import java.io.*;
import java.util.*;


final class Cell {
    final int r, c;
    Cell(int r, int c) { this.r = r; this.c = c; }
}


final class Border {
    final Cell a, b;
    Border(Cell x, Cell y) {

        if (x.r < y.r || (x.r == y.r && x.c <= y.c)) {
            a = x; b = y;
        }

        else {
            a = y;
            b = x;
        }
    }
}

public class 인구이동_P16234 {

    static int N, L, R;
    static int[][] arr;
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상하좌우

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        String[] NLR = br.readLine().trim().split(" ");
        N = Integer.parseInt(NLR[0]);
        L = Integer.parseInt(NLR[1]);
        R = Integer.parseInt(NLR[2]);

        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) arr[i][j] = Integer.parseInt(st.nextToken());
        }

        int days = simulate();
        System.out.println(days);
    }


    static int simulate() {
        int days = 0;
        while (true) {
            boolean moved = false;
            boolean[][] visited = new boolean[N][N];

            for (int sr = 0; sr < N; sr++) {
                for (int sc = 0; sc < N; sc++) {
                    if (visited[sr][sc]) continue;

                    // BFS로 연합 찾기
                    ArrayDeque<int[]> q = new ArrayDeque<>();
                    List<int[]> union = new ArrayList<>();
                    q.add(new int[]{sr, sc});
                    visited[sr][sc] = true;
                    int sum = 0;

                    while (!q.isEmpty()) {
                        int[] cur = q.poll();
                        int r = cur[0], c = cur[1];
                        union.add(cur);
                        sum += arr[r][c];

                        for (int[] d : dirs) {
                            int nr = r + d[0], nc = c + d[1];
                            if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                            if (visited[nr][nc]) continue;

                            int diff = Math.abs(arr[r][c] - arr[nr][nc]);
                            if (diff >= L && diff <= R) {
                                visited[nr][nc] = true;
                                q.add(new int[]{nr, nc});
                            }
                        }
                    }

                    if (union.size() > 1) {
                        int avg = sum / union.size();
                        for (int[] cell : union) {
                            arr[cell[0]][cell[1]] = avg;
                        }
                        moved = true;
                    }
                }
            }
            if (!moved) break;
            days++;
        }
        return days;
    }
}
