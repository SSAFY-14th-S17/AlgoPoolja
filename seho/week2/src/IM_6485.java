import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/** 
삼성시의 버스 노선
1~5000 번호 붙어 있음
버스 노선은 N개 i번째 버스 노선은 번호가 Ai 이상
Bi 이하인 모든 정류장만 다니는 노선
P개의 버스 정류장에 대해 각 정류장에 몇 개의 버스 노선이 다니는가
첫 줄에 tc
첫 줄에는 하나의 정수 N이 주어짐
다음 N개의 줄의 i번째 줄에는 두 정수 Ai, Bi가 공백 하나로 구분되어 주어짐
다음 줄에는 하나의 정수 p
다음 p개의 줄의 j번째 줄에는 하나의 정수 Cj가 주어짐
 */

public class IM_6485 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			
			// 노선 관리
			int[][] route = new int[N][2];
			
			// 노선 저장
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				route[i] = new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
			}
			
			// 정류장 갯수와 배열 생성
			int P = Integer.parseInt(br.readLine());
			int[] PArr = new int[P];
			
			int[] PCount = new int[5001];
			
			// 각 정류장 입력
			for (int i = 0; i < P; i++) {
				PArr[i] = Integer.parseInt(br.readLine());
			}
			
			// 몇대 지나다니는지 확인하기 위한 배열 생성
			int[] result = new int[P];
			
			// 순회하면서 해당 버스 노선이 지나다니는 정류장 카운트
			for (int i = 0; i < N; i++) {
				int[] curRoute = route[i];
				for (int j = curRoute[0]; j <= curRoute[1]; j++) {
					PCount[j]++;
				}
			}
			
			// 출력
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(tc).append(" ");
			for (int i = 0; i < P; i++) {
				sb.append(PCount[PArr[i]]).append(" ");
			}
			
			System.out.println(sb.toString());
		}
		
	}
}
