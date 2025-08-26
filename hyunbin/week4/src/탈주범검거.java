import java.io.*;
import java.util.*;


public class 탈주범검거 {
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = { 0, 0,-1, 1};
    static final int[] opp = {1, 0, 3, 2}; // 반대방향 방향의 인덱스값 하 상 우 좌

    // 각 터널 타입(0~7)별로 열려 있는 방향 목록
    // 0은 터널 없음, 1~7은 문제 표 그대로 매핑
    static final int[][] typeDirs = {
        {},               // 0: 없음
        {0,1,2,3},        // 1: 상하좌우
        {0,1},            // 2: 상하
        {2,3},            // 3: 좌우
        {0,3},            // 4: 상우
        {1,3},            // 5: 하우
        {1,2},            // 6: 하좌
        {0,2}             // 7: 상좌
    };

    // 빠른 체크용: open[t][d] = 터널 타입 t가 방향 d로 열려 있으면 true
    static final boolean[][] open = new boolean[8][4];
    static {
        for (int t = 1; t <= 7; t++) {
        	for (int d : typeDirs[t]) {
        		open[t][d] = true;
        	}
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());

        for (int t = 1; t <= tc; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());

            int[][] map = new int[N][M];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int ans = bfsCount(N, M, R, C, L, map);
            System.out.println("#" + tc + " " + ans);
        }

    }

    static int bfsCount(int N, int M, int sr, int sc, int L, int[][] map) {
        if (L <= 0 || map[sr][sc] == 0) return 0; // 예외처리

        boolean[][] visited = new boolean[N][M];
        ArrayDeque<int[]> q = new ArrayDeque<>();

        visited[sr][sc] = true;
        q.offer(new int[]{sr, sc, 1}); // 시작 시간 = 1
        int count = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1], t = cur[2];
            count++; // 도달완료

            if (t == L) continue; //시간 넘어가는거 방지

            int typeHere = map[r][c];
            for (int d : typeDirs[typeHere]) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;

                int nextType = map[nr][nc];
                if (nextType == 0 || visited[nr][nc]) continue;

                // 다음 칸이 반대 방향으로도 열려 있어야 연결 가능
                if (!open[nextType][opp[d]]) continue;

                visited[nr][nc] = true;
                q.offer(new int[]{nr, nc, t + 1});
            }
        }
        return count;
    }
}
