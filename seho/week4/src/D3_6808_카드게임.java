

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 규영이와 인영이는 1에서 18까지의 수가 적힌 18장 카드로 게임
 * 카드를 섞어 9장씩 카드를 나누고 9라운드에 걸쳐 게임 진행
 * 한 라운드에 한 장씩 카드를 낸 다음 두 사람이 낸 카드에 적힌 수를 비교해 점수를 계산
 * 
 * 높은 수가 적힌 카드를 낸 사람은 두 카드에 적힌 수의 합만큼 점수 +
 * 낮은수는 0점
 * 
 * 9라 이후 총점 계산 -> 총점 높은사람이 게임 승자
 * 같으면 무승부
 * 규영이가 받은 9장 카드에 적힌 수가 주어짐
 * 규영이가 내는 카드의 순서를 고정하면, 인영이가 어떤 카드를 내는지에 따른 9!가지 순서에 따라
 * 승패가 정해짐
 * 규영이가 이기는 경우와 지는 경우가 총 몇가지인지 구하는 프로그램 작성
 * 1~9 까지의 순열을 만들고 순열이 완성될때마다 비교 후 승패 계산
 */
public class D3_6808_카드게임 {
	
	// 이긴 횟수랑 진 횟수를 카운트할 변수를 전역으로 관리
	static int win;
	static int lose;
	static int[] gyu;
	static int[] yeong;
	
	static int[] cardSet;
	
	// 방문처리 visite 배열 생성
	static boolean[] useCard;
	static boolean[] visited;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			win = 0;
			lose = 0;
			gyu = new int[9];
			yeong = new int[9];
			visited = new boolean[9];
			// 1-based
			useCard = new boolean[19];
			
			for (int i = 0; i < 9; i++) {
				int num = Integer.parseInt(st.nextToken());
				gyu[i] = num;
				
				useCard[num] = true;
			}
			
			
			cardSet = new int[9];
			
			int idx = 0;
			for (int i = 1; i <= 18; i++) {
				if (useCard[i] == false) cardSet[idx++] = i;
			}
			
			
			// 규영이 카드 조합
			backtracking(0);
			
			System.out.println("#" + tc + " " + win + " " + lose);
		} // tc
	} // main
	
	// 
	public static void backtracking(int depth) {
		if (depth == 9) {
			int result = simulation();
			if (result == 1) win++;
			else if (result == -1) lose++;
			return;
		}
		
		for (int i = 0; i < 9; i++) {
			if (!visited[i]) {
				visited[i] = true;
				
				yeong[depth] = cardSet[i];
				backtracking(depth + 1);
				
				// backtrack
				yeong[depth] = 0;
				visited[i] = false;
			}
		}
	}
	
	// 카드 비교
	public static int simulation() {
		int sumA = 0;
		int sumB = 0;
		for (int i = 0; i < 9; i++) {
			int result = check(i);
			if (result == 1) {
				sumA += gyu[i] + yeong[i];
			} else if (result == -1) {
				sumB += gyu[i] + yeong[i];
			}
		}
		
		return Integer.compare(sumA, sumB);
	}
	
	public static int check(int idx) {
		return Integer.compare(gyu[idx], yeong[idx]);
	}
}
