package youyeon.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class 벽부수고이동하기 {

    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static int N;
    static int M;
    static int[][] plane;

    static int answer;

    static boolean vistied[][][];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] NM = br.readLine().split(" ");
        N = Integer.parseInt(NM[0]);
        M = Integer.parseInt(NM[1]);
        plane = new int[N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                plane[i][j] = Integer.parseInt(String.valueOf(input.charAt(j)));
            }
        }

        vistied = new boolean[N][M][2];
        int answer = bfs();
        System.out.println(answer);

    }

    static class Node {
        int r, c, broke, dist;
        Node(int r, int c, int broke, int dist) {
            this.r = r;
            this.c = c;
            this.broke = broke;
            this.dist = dist;
        }
    }

    public static int bfs() {

        ArrayDeque<Node> queue = new ArrayDeque<>();
        vistied[0][0][0] = true;
        queue.add(new Node(0, 0, 0, 1));

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            // 종료 조건
            if (cur.r == N -1 && cur.c == M -1) {
                return cur.dist; // 다음으로 이동하지 않음 (+ 이미 1로 시작)
            }

            // 실행 코드
            for (int[] d : dirs) {
                int nr = cur.r + d[0];
                int nc = cur.c + d[1];

                // 인덱스 체크
                if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;

                if (plane[nr][nc] == 0) {
                    if (!vistied[nr][nc][cur.broke]) {
                        vistied[nr][nc][cur.broke] =true;
                        queue.add(new Node(nr, nc, cur.broke, cur.dist +1));
                    }
                }
                else if (cur.broke == 0 && plane[nr][nc] == 1) {
                    if (!vistied[nr][nc][1]) {
                        vistied[nr][nc][1] = true;
                        queue.add(new Node(nr, nc, 1, cur.dist +1));
                    }
                }
            }
        }


        return -1; // 실패 시 -1로 종료
    }
}
