import java.io.*;
import java.util.*;

public class 연구소 {
    static int N, M;
    static int[][] base;
    static List<int[]> empties = new ArrayList<>();
    static List<int[]> viruses = new ArrayList<>();
    static int emptyCount;
    static final int[] DR = {-1, 1, 0, 0};
    static final int[] DC = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        base = new int[N][M];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < M; c++) {
                base[r][c] = Integer.parseInt(st.nextToken());
                if (base[r][c] == 0) empties.add(new int[]{r, c});
                else if (base[r][c] == 2) viruses.add(new int[]{r, c});
            }
        }

        emptyCount = empties.size();
        int bestSafe = 0;

        // 빈 칸 중 3곳 선택 (i < j < k)
        for (int i = 0; i < emptyCount - 2; i++) {
            for (int j = i + 1; j < emptyCount - 1; j++) {
                for (int k = j + 1; k < emptyCount; k++) {
                    int[][] map = copyMap(base);

                    // 벽 세우기
                    int[] a = empties.get(i);
                    int[] b = empties.get(j);
                    int[] c = empties.get(k);
                    map[a[0]][a[1]] = 1;
                    map[b[0]][b[1]] = 1;
                    map[c[0]][c[1]] = 1;

                    // 바이러스 확산 (BFS)
                    int infected = spreadAndCount(map);

                    // 안전 영역 계산
                    int safe = emptyCount - 3 - infected;
                    if (safe > bestSafe) bestSafe = safe;
                }
            }
        }

        System.out.println(bestSafe);
    }

    static int[][] copyMap(int[][] src) {
        int[][] dst = new int[N][M];
        for (int r = 0; r < N; r++) {
            System.arraycopy(src[r], 0, dst[r], 0, M);
        }
        return dst;
    }

    static int spreadAndCount(int[][] map) {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][M];

        for (int[] v : viruses) {
            q.offer(new int[]{v[0], v[1]});
            visited[v[0]][v[1]] = true;
        }

        int infected = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];

            for (int d = 0; d < 4; d++) {
                int nr = r + DR[d];
                int nc = c + DC[d];
                if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
                if (visited[nr][nc]) continue;
                if (map[nr][nc] != 0) continue; // 벽(1) 또는 바이러스(2)면 X

                visited[nr][nc] = true;
                map[nr][nc] = 2; // 감염
                infected++;
                q.offer(new int[]{nr, nc});
            }
        }
        return infected;
    }
}
