

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 하늘에서 별똥별이 빗발친다.
 * L x L 트램펄린을 배치해 최대한 많은 별똥별을 튕겨내고
 * 지표면에 부딪히는 별똥별 갯수를 최소화하고 출력하기
 * 별똥별이 떨어지는 구역
 * 1 <= N * M <= 500,000
 * 1 <= L <= 100,000
 * 1 <= K <= 100
 * 
 * 격자판 크기가 500,000 이기 때문에 단순하게 2차원 배열로 구현하는 것은 불가능(시간, 공간 둘다 터짐)
 * 
 * 트램펄린 사이즈로 나눈 값으로 격자판 재구성 트램펄린 사이즈가 작으면 결과적으로 위와 똑같은 결과 발생
 * 어떻게 효율적으로 완전탐색을할지?
 * 
 * 별똥별 기준 트램펄린 길이만큼 탐색하여 안에 있는 별똥 별 갯수를 저장후 제일 큰 별동별 갯수를
 * 전체 개수에서 빼준다.
 * 
 * 그럼 시간복잡도가 최대 100 * 100 * 4 => 40000임
 */
public class gold3_14658_하늘에서별똥벌이빗발친다 {
	static class Star {
		int x;
		int y;
		
		public Star (int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	static int N, M, L, K;
	static List<Star> stars;
	static int result;
	
	public static void main(String[] rags) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		stars = new ArrayList<>();
		
		// 별똥별 좌표 기록
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			stars.add(new Star(x, y));
		}
		
		// 지면에 떨어지는 별똥별 갯수 초기화
		result = K;
		
		// 별똥별 마다 브루트포스 탐색 진행
		for (Star s1 : stars) {
			// 기준이 되는 별똥별 좌표 가져오기
			for (Star s2 : stars) {
				int startX = s1.x;
				int startY = s2.y;
				
				int cnt = 0;
				
				// 모든 별똥별 확인
				for (Star target: stars) {
					if (startX <= target.x && target.x <= startX + L &&
							startY <= target.y && target.y <= startY + L) {
						cnt++;
					}
				}
				
				result = Math.min(result, K - cnt);
				
			}
			
		}
		
		System.out.println(result);
	}
}
