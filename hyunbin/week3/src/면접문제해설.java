import java.io.*;
import java.util.*;

public class 면접문제해설 {
    static final long MOD = 1_000_000_009L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        for (int t = 1; t <= tc; t++) {			
        	StringTokenizer st;
        	st = new StringTokenizer(br.readLine());
        	long n = Long.parseLong(st.nextToken());
        	long m = Long.parseLong(st.nextToken());
        	long k = Long.parseLong(st.nextToken());
        	
        	long safeMax = (n / k) * (k - 1) + (n % k);
        	
        	if (m <= safeMax) {
        		// 막을 수 있음 → 그냥 맞은 문제 수가 점수
        		System.out.println(m % MOD);
        	} else {
        		// 막을 수 없음 → 연속 k개 이벤트가 최소 X번 발생
        		long excess = m - safeMax; // 초과 정답 수
        		long X = excess;           // 최소 이벤트 횟수
        		
        		long score = 0;
        		
        		// 1) 연속 k개 이벤트 X번 앞에서 발생시킴
        		for (int i = 0; i < X; i++) {
        			score = (score + k) % MOD;  // k개 맞음
        			score = (score * 2) % MOD;  // 두 배
        		}
        		
        		// 2) 남은 정답들은 그냥 더하기
        		long remaining = (m - X * k) % MOD;
        		score = (score + remaining) % MOD;
        		
        		System.out.println(score);
        	}
		}
    }
}
