import java.util.*;
import java.io.*;

public class 면접 {
	/*
	 * 	N = 총 문제 개수
	 * 	M = 맞춘 문제
	 * 	K = 카운트(2배)
	 * 
	 * 	문제를 연속으로 맞출 때 마다 콤보 , K콤보 달성 시 문제 점수 2배	
	 * 	어디어디 맞춘지 까먹은 바보
	 * 	총 N 문제 중에 M 문제를 맞췄을 때 K 콤보를 고려하여 가장 최소값을 배출
	 * 	
	 * 	그리디?
	 * 	K 콤보 달성 할 것 같으면 틀려버리게 하는 로직
	 *  N은 언제나 M 보다 크다.
	 *  
	 *  유의 사항은 N에 도달하기 전에 M을 다 써야하고 K를 고려하여 가장 최소값을 내야한다.
	 *  만약 K 콤보 달성 시 현재 점수에서 2배 즉 콤보를 빨리 달성하면 좋음.
	 * 
	 *  설계
	 *  나는 N 에 도달하기 전에 M 을 전부 다 써야하고 N에 도달하면 멈춘다.
	 *  N - M > K 인경우 노콤보로 달성이 가능 하다. 반대로
	 *  N - M < K 인 경우 무조건 콤보를 달성해야한다. 이 떄 알아야할 점이 N - M / K 가 달성해야할 콤보의 개수다.
	 *  만약 몫이 0 이라면 그냥 점수 출력가능 
	 * 	
	 */
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tc = Integer.parseInt(br.readLine()); //테스트케이스 있다고 가정 
		for (int t = 1; t <= tc; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine()); //입력은 1줄만 주니, 토크나이저사용
			
			int N = Integer.parseInt(st.nextToken()); // 총문제
			int M = Integer.parseInt(st.nextToken()); // 맞춘 문제
			int K = Integer.parseInt(st.nextToken()); // 콤보
			
			int W = N - M; // 틀린 문제 개수
			
			if (combo == 0) {
				System.out.println("#" + t + " " + M); 
				break;
			}
			int cnt = 0;
			while (cnt < N) {
				
			}
			
		}
	}
}
