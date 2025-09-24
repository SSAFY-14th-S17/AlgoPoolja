package week8;

import java.io.*;
import java.util.*;

public class Twodots2_DFS {
    static int N, M;
    static char[][] board;
    static int[][] depth;
    static final int[] dr = {1, -1, 0, 0};
    static final int[] dc = {0, 0, 1, -1};

    static boolean dfs(int r, int c, int pr, int pc, int step, char color) {
        depth[r][c] = step;
        for (int k = 0; k < 4; k++) {
            int nr = r + dr[k], nc = c + dc[k];
            if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
            if (board[nr][nc] != color) continue;
            if (depth[nr][nc] == 0) {
                if (dfs(nr, nc, r, c, step + 1, color)) return true;
            } else {
                if (!(nr == pr && nc == pc)) {
                    int cycleLen = step - depth[nr][nc] + 1;
                    if (cycleLen >= 4) return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new char[N][M];
        for (int i = 0; i < N; i++) board[i] = br.readLine().toCharArray();
        depth = new int[N][M];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (depth[r][c] == 0) {
                    if (dfs(r, c, -1, -1, 1, board[r][c])) {
                        System.out.println("Yes");
                        return;
                    }
                }
            }
        }
        System.out.println("No");
    }
}

