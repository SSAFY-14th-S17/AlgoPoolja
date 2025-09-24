package week8;

import java.io.*;
import java.util.*;

public class Twodots_BFS {
    static int N, M;
    static char[][] board;
    static boolean[][] visited;
    static int[][] parentR, parentC;
    static final int[] dr = {1, -1, 0, 0};
    static final int[] dc = {0, 0, 1, -1};

    static boolean inRange(int r, int c) {
        return 0 <= r && r < N && 0 <= c && c < M;
    }

    static boolean bfsCycle(int sr, int sc) {
        char color = board[sr][sc];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        visited[sr][sc] = true;
        parentR[sr][sc] = -1;
        parentC[sr][sc] = -1;
        q.offer(new int[]{sr, sc});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];

            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k], nc = c + dc[k];
                if (!inRange(nr, nc)) continue;
                if (board[nr][nc] != color) continue;

                if (visited[nr][nc]) {
                    if (parentR[r][c] == nr && parentC[r][c] == nc) continue;
                    return true; // 부모가 아닌 방문 이웃
                } else {
                    visited[nr][nc] = true;
                    parentR[nr][nc] = r;
                    parentC[nr][nc] = c;
                    q.offer(new int[]{nr, nc});
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
        for (int r = 0; r < N; r++) {
            String s = br.readLine();
            board[r] = s.toCharArray();
        }

        visited = new boolean[N][M];
        parentR = new int[N][M];
        parentC = new int[N][M];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (!visited[r][c]) {
                    if (bfsCycle(r, c)) {
                        System.out.println("Yes");
                        return;
                    }
                }
            }
        }
        System.out.println("No");
    }
}
