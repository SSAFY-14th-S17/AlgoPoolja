import java.io.*;
import java.util.*;

class 벽돌깨기 {

    static int T, N, W, H;
    static int answer;
    static final int[][] dirs = {{-1,0},{1,0},{0,1},{0,-1}};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            int[][] board = new int[H][W];
            for (int r = 0; r < H; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < W; c++) {
                    board[r][c] = Integer.parseInt(st.nextToken());
                }
            }

            answer = Integer.MAX_VALUE;
            dfs(0, board);
            System.out.println("#" + tc + " " + answer);
        }
    }

    static void dfs(int cnt, int[][] map) {
        int remaining = count(map);
        if (remaining == 0) {
            answer = 0;
            return;
        }
        if (cnt == N) {
            answer = Math.min(answer, remaining);
            return;
        }

        for (int c = 0; c < W; c++) {
            int r = 0;
            while (r < H && map[r][c] == 0) r++;
            if (r == H) continue;

            int[][] copy = new int[H][W];
            for (int i = 0; i < H; i++) {
                System.arraycopy(map[i], 0, copy[i], 0, W);
            }

            explode(r, c, copy);
            down(copy);
            dfs(cnt + 1, copy);

            if (answer == 0) return;
        }
    }

    static void explode(int r, int c, int[][] map) {
        int power = map[r][c];
        if (power == 0) return;
        map[r][c] = 0;

        if (power == 1) return;

        for (int[] d : dirs) {
            for (int step = 1; step < power; step++) {
                int nr = r + d[0] * step;
                int nc = c + d[1] * step;
                if (nr < 0 || nr >= H || nc < 0 || nc >= W) break;
                if (map[nr][nc] > 0) {
                    explode(nr, nc, map);
                }
            }
        }
    }

    static void down(int[][] map) {
        for (int c = 0; c < W; c++) {
            int write = H - 1;
            for (int r = H - 1; r >= 0; r--) {
                if (map[r][c] > 0) {
                    int val = map[r][c];
                    map[r][c] = 0;
                    map[write][c] = val;
                    write--;
                }
            }
        }
    }

    static int count(int[][] map) {
        int cnt = 0;
        for (int r = 0; r < H; r++) {
            for (int c = 0; c < W; c++) {
                if (map[r][c] > 0) cnt++;
            }
        }
        return cnt;
    }
}