import java.io.*;
import java.util.*;

/**
 * BOJ 1504 - 특정한 최단 경로
 * 다익스트라 3회: from 1, from v1, from v2
 * 답 = min( dist1[v1] + distV1[v2] + distV2[N],
 *           dist1[v2] + distV2[v1] + distV1[N] )
 */
public class 특정한최단경로 {
    static class Edge {
        int to, w;
        Edge(int to, int w) { this.to = to; this.w = w; }
    }

    static final long INF = Long.MAX_VALUE / 4;
    static int N, E;
    static List<Edge>[] g;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        g = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) g[i] = new ArrayList<>();

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            g[a].add(new Edge(b, c));
            g[b].add(new Edge(a, c));
        }

        st = new StringTokenizer(br.readLine());
        int v1 = Integer.parseInt(st.nextToken());
        int v2 = Integer.parseInt(st.nextToken());

        long[] dist1  = dijkstra(1);
        long[] distV1 = dijkstra(v1);
        long[] distV2 = dijkstra(v2);

        long case1 = addSafe(dist1[v1], distV1[v2], distV2[N]);
        long case2 = addSafe(dist1[v2], distV2[v1], distV1[N]);

        long ans = Math.min(case1, case2);
        System.out.println(ans >= INF ? -1 : ans);
    }

    // 다익스트라: start에서 각 정점까지 최단거리
    static long[] dijkstra(int start) {
        long[] dist = new long[N + 1];
        Arrays.fill(dist, INF);
        dist[start] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
        pq.offer(new long[]{start, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int u = (int) cur[0];
            long d = cur[1];
            if (d != dist[u]) continue;

            for (Edge e : g[u]) {
                int v = e.to;
                long nd = d + e.w;
                if (nd < dist[v]) {
                    dist[v] = nd;
                    pq.offer(new long[]{v, nd});
                }
            }
        }
        return dist;
    }

    // INF 체크를 겸한 안전 덧셈
    static long addSafe(long... xs) {
        long s = 0;
        for (long x : xs) {
            if (x >= INF) return INF;
            s += x;
            if (s >= INF) return INF;
        }
        return s;
    }
}
