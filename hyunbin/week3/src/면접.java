import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
 
public class 면접 {
    static final long MOD = 1_000_000_009L;
 
    static long modPow(long base, long exp) {
        long result = 1;
        base %= MOD;
        while (exp > 0) {
            if ((exp & 1) != 0) result = (result * base) % MOD;
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return result;
    }
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long m = Long.parseLong(st.nextToken());
        long k = Long.parseLong(st.nextToken());
 
        long totalBlocks = n / k;
        long rem = n % k;
 
        long maxSafeCorrect = totalBlocks * (k - 1) + rem;
        if (m <= maxSafeCorrect) {
            System.out.println(m % MOD);
            return;
        }
 
        long excess = m - maxSafeCorrect; 
        long X = excess; 
        long powTerm = (modPow(2, X + 1) - 2 + MOD) % MOD;
        long score = (powTerm * k) % MOD;
        long remaining = (m - X * k) % MOD;
        long answer = (score + remaining) % MOD;
 
        System.out.println(answer);
    }
}
