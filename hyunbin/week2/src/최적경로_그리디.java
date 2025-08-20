


import java.io.*;
import java.util.*;

public class 최적경로_그리디 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int tc = Integer.parseInt(br.readLine());

        for (int t = 1; t <= tc; t++) {
            int N = Integer.parseInt(br.readLine()); // 고객 수
            ArrayList<int[]> points = new ArrayList<>(); // [x, y] 저장

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N + 2; i++) { // 회사, 집, 고객 N명
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                points.add(new int[]{x, y});
            }

            int[] company = points.get(0); // 시작점 (회사)
            int[] home = points.get(1);    // 도착점 (집)
            List<int[]> customers = new ArrayList<>(points.subList(2, points.size()));

            int[] start = company; // 현재 위치
            int totalDistance = 0;

            // 고객이 남아 있는 동안 반복
            while (!customers.isEmpty()) {
                int minDist = Integer.MAX_VALUE;
                int minIndex = -1;

                // 가장 가까운 고객 찾기
                for (int i = 0; i < customers.size(); i++) {
                    int dist = Math.abs(start[0] - customers.get(i)[0])
                             + Math.abs(start[1] - customers.get(i)[1]);
                    if (dist < minDist) {
                        minDist = dist;
                        minIndex = i;
                    }
                }

                // 해당 고객 방문
                totalDistance += minDist;
                start = customers.get(minIndex);
                customers.remove(minIndex);
            }

            // 마지막 집까지 거리 추가
            totalDistance += Math.abs(start[0] - home[0])
                           + Math.abs(start[1] - home[1]);

            System.out.println("#" + t + " " + totalDistance);
        }
    }
}
