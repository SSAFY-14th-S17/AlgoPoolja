import java.io.*;
import java.util.*;

/**
 * SWEA 6808 유사: 규영이와 인영이의 카드게임
 * - 규영의 9장 순서는 입력 그대로 고정
 * - 인영의 9장 순서를 전부(9!) 순열로 시도
 * - 각 라운드 승자는 두 카드의 합을 득점
 * - 총합 171 불변 성질로 가지치기 (rem = 171 - awardedSum)
 * 출력: #tc  (규영 승 수) (규영 패 수)
 */
public class 규인카 {

    static int T;
    static int[] kyu = new int[9];
    static int[] inn = new int[9];
    static boolean[] used = new boolean[9];

    static long winCnt, loseCnt;        // 규영 승/패 경우의 수
    static int[] fact = new int[10];    // 남은 자리수에 대한 팩토리얼(가지치기 시 한 번에 더하기)

    static final int TOTAL_SUM = 171;   // 1..18 합

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine().trim());

        // factorial 준비
        fact[0] = 1;
        for (int i = 1; i <= 9; i++) fact[i] = fact[i - 1] * i;

        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {
            // ----- 입력: 규영 카드 9장 -----
            StringTokenizer st = new StringTokenizer(br.readLine());
            boolean[] taken = new boolean[19]; // 1~18 사용표시
            for (int i = 0; i < 9; i++) {
                kyu[i] = Integer.parseInt(st.nextToken());
                taken[kyu[i]] = true;
            }

            // ----- 인영 카드 구성: 나머지 9장 -----
            int idx = 0;
            for (int v = 1; v <= 18; v++) {
                if (!taken[v]) inn[idx++] = v;
            }

            // 초기화
            Arrays.fill(used, false);
            winCnt = 0;
            loseCnt = 0;

            // 백트래킹 시작
            dfs(0, 0, 0, 0);

            sb.append('#').append(tc).append(' ')
              .append(winCnt).append(' ')
              .append(loseCnt).append('\n');
        }

        System.out.print(sb);
    }

    /**
     * @param round     0..8
     * @param kScore    현재 규영 점수
     * @param iScore    현재 인영 점수
     * @param awarded   현재까지 분배된 점수의 총합 (kScore + iScore)
     */
    static void dfs(int round, int kScore, int iScore, int awarded) {
        if (round == 9) {
            if (kScore > iScore) winCnt++;
            else if (kScore < iScore) loseCnt++;
            // 무승부는 문제 출력 요구사항에 없으므로 카운트하지 않음
            return;
        }

        // ---- 가지치기: 남은 점수(rem)로도 역전/추월 불가능하면 확정 ----
        int rem = TOTAL_SUM - awarded;
        int left = 9 - round; // 남은 라운드 수

        if (kScore > iScore + rem) {   // 규영 확정 승
            winCnt += fact[left];
            return;
        }
        if (iScore > kScore + rem) {   // 규영 확정 패
            loseCnt += fact[left];
            return;
        }

        // 현재 라운드에서 규영은 kyu[round]를 반드시 낸다 (순서 고정)
        int kc = kyu[round];

        // 인영의 남은 9 - round 장 중 하나를 선택
        for (int i = 0; i < 9; i++) {
            if (used[i]) continue;
            used[i] = true;

            int ic = inn[i];
            int gain = kc + ic;

            if (kc > ic) {
                dfs(round + 1, kScore + gain, iScore, awarded + gain);
            } else {
                dfs(round + 1, kScore, iScore + gain, awarded + gain);
            }

            used[i] = false;
        }
    }
}
