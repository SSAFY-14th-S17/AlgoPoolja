public class 적록색약 {

    static int[][] delta = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static boolean[][] visited;
    static int N;
    static char[][] plane;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        plane = new char[N][N];
        visited = new boolean[N][N];

        // 입력 받기
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j =0; j < N; j++) {
                plane[i][j] = input.charAt(j);
            }
        }

        int noneCount = 0;
        for (int i = 0; i < N; i ++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    bfs(i, j);
                    noneCount += 1;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (plane[i][j] != 'B') {
                    plane[i][j] = '0';
                }
            }
        }
        int count = 0;
        visited = new boolean[N][N]; // 초기화
        for (int i = 0; i < N; i ++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    bfs(i, j);
                    count += 1;
                }
            }
        }
        System.out.println(noneCount +  " " + count);

    }

    public static void bfs(int x, int y) {

        // 좌표 형태 자체를 큐에 집어넣음
        Queue<Integer[]> queue = new ArrayDeque<>();
        Integer[] cord = {x, y};
        queue.add(cord);

        // 탐색 내용
        visited[x][y] = true;
        char target = plane[x][y];

        while (!queue.isEmpty()) {
            // 일단 큐에서 넣은 애들을 뽑음
            Integer[] arr = queue.poll();
            int r = (int) arr[0];
            int c = (int) arr[1];

            // 델타 배열을 통해 동일한 애들 탐색
            for (int[] d: delta) {
                int nr = r + d[0];
                int nc = c + d[1];
                // visited에서 해당 좌표를 들린 적이 있는지를 검증
                if (nr < N && nr >= 0 && nc < N && nc >= 0 && !visited[nr][nc]) {
                    // 만약 동일한 결과이다 -> 넣었던 인자를 동일한 타겟으로 두기
                    if (plane[nr][nc] == target) {
                        Integer[] temp = {nr, nc};
                        visited[nr][nc] = true;
                        queue.add(temp);
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        }
    }
}