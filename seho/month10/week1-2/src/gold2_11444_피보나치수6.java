

import java.math.BigInteger;
import java.util.Scanner;

/**
 * 
 */
public class gold2_11444_피보나치수6 {
	
	static final long MOD = 1000000007;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long N = sc.nextLong();
		
		// 0, 과 1일때 바로 출력
		if (N <= 1) {
			System.out.println(N);
			return;
		}
		
		// 점화식 기본 행렬
		long[][] base = {{1, 1}, {1, 0}};
		
		// N - 1번 거듭 제곱
		long[][] resultMatrix = matrixPow(base, N - 1);
		
		// N - 1 제곱한 행렬에 f(1), f(0) 곱한 결과는 result 첫번째 열
		System.out.println(resultMatrix[0][0]);
	}
	
	// 거듭제곱
	public static long[][] matrixPow(long[][] matrix, long exp) {
		// 1이면 자기자신
		if (exp == 1) {
			return matrix;
		}
		
		// 분할 정복
		long[][] half = matrixPow(matrix, exp / 2);
		
		// 분할된 행렬들을 곱함
		long[][] result = matrixMulti(half, half);
		
		// exp가 홀수인 경우 원래 행렬을 한번더 곱해줌
		if (exp % 2 == 1) {
			result = matrixMulti(result, matrix);
		}
		
		return result;
	}
	
	// 2 x 2 행렬을 곱하기
	
	public static long[][] matrixMulti(long[][] m1, long[][] m2) {
		long[][] result = new long[2][2];
		
		// 계산 과정에 MOD 연산 적용
		result[0][0] = ((m1[0][0] * m2[0][0]) + (m1[0][1] * m2[1][0])) % MOD;
		result[0][1] = ((m1[0][0] * m2[0][1]) + (m1[0][1] * m2[1][1])) % MOD;
		result[1][0] = ((m1[1][0] * m2[0][0]) + (m1[1][1] * m2[1][0])) % MOD;
		result[1][1] = ((m1[1][0] * m2[0][1]) + (m1[1][1] * m2[1][1])) % MOD;
		
		return result;
	}
}

/*
1000000000000000000
*/