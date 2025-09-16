package week6;

import java.io.*;
import java.util.*;

public class 작업순서 {

    static int V, E;
    static List<Integer>[] g;
    static int[] indeg;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int tc = 10; // 문제에서 고정 10개
        StringBuilder out = new StringBuilder();

        for (int t = 1; t <= tc; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            g = new ArrayList[V + 1];
            for (int i = 1; i <= V; i++) g[i] = new ArrayList<>();
            indeg = new int[V + 1];
  
            // E개의 (u, v) 쌍을 모두 읽는다: 한 줄에 다 없을 수 있으므로 토큰을 소진하면 다음 줄을 읽는다.
            int readEdges = 0;
            StringTokenizer edgeTok = null;
            while (readEdges < E) {
                if (edgeTok == null || !edgeTok.hasMoreTokens()) {
                    edgeTok = new StringTokenizer(br.readLine());
                }
                int u = Integer.parseInt(edgeTok.nextToken());
                int v = Integer.parseInt(edgeTok.nextToken());
                g[u].add(v);
                indeg[v]++;
                readEdges++;
            }

            Deque<Integer> q = new ArrayDeque<>();
            for (int i = 1; i <= V; i++) if (indeg[i] == 0) q.add(i);

            List<Integer> order = new ArrayList<>(V);
            while (!q.isEmpty()) {
                int cur = q.poll();
                order.add(cur);

                for (int nxt : g[cur]) {
                    if (--indeg[nxt] == 0) q.add(nxt);
                }
            }

            out.append('#').append(t).append(' ');
            for (int i = 0; i < order.size(); i++) {
                if (i > 0) out.append(' ');
                out.append(order.get(i));
            }
            out.append('\n');
        }

        System.out.print(out.toString());
    }
}
