import java.io.*;
import java.util.*;

public class 치킨배달_향상 {
	static int M; // 선택할 치킨집의 수
	static List<int[]> chicken = new ArrayList<>();
	static List<int[]> house = new ArrayList<>();
	
	
	static int H ,C ;
	static int [][] dist; //dist[h][c]
	static int best = Integer.MAX_VALUE; 
	static int INF = 1_000_000;
	
	/*	
	 *  logic : 
	 *	choose amount of K at the left bucket until M .
	 * 	And choose amout of K-M at the right bucket;
	 *  
	 *  At the each buckets there are vectors of the minimum distance of chicken to house.
	 *  
	 *  
	 */
	// 이 배열이 무슨 말이냐면 ... List<int[]> 배열 값을 지닌 배열 선언 int[] 는 뭐야? int 값을 가진 배열이잖아!! 
	// 유레카! 그러니까 List<int[]>[] 은 List<int[]> 배열을 가진 배열..! 
	static List<int[]>[] leftBucket;  
	static List<int[]>[] rightBucket;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		//치킨집 저장
		for (int i = 0; i < N ; i ++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N ; j++) {
				int num = Integer.parseInt(st.nextToken());
				if (num ==  1) house.add(new int[] {i,j});
				if (num ==  2) chicken.add(new int[] {i,j});
			}
		}
		
		H = house.size();
		C = chicken.size();
		
		dist = new int[H][C];
		
		// House to Chicken distance calculate;
		for(int h = 0; h < H ; h++) {
			// Get the location  of each house . "h" means house number and 0,1 means int x , y . Get it?
			int hr = house.get(h)[0] , hc = house.get(h)[1]; 
			
			for (int c  = 0; c < C; c++) {
				// Same explaination of  
				int cr = chicken.get(c)[0] , cc = chicken.get(c)[1];
				dist[h][c] = Manhattan(hr, cr, hc, cc);
			}
		}
		
		// Chicken Half split
		int mid = C / 2; // half of the chicken
		int[] leftIdx = new int[mid]; 
		int[] rightIdx = new int[C - mid]; //the leftover.
		
		for (int i = 0; i < mid ; i++) leftIdx[i] = i; // 절반의 치킨집을 왼쪽 치킨집 번호 넣기
		for (int i = mid; i < C; i++) rightIdx[i - mid] = i; // 절반의 나머지를 오른쪽 치킨집 번호 넣기
		
		// M 개 만큼 만들어 놓기..~
		leftBucket =  new ArrayList[M+1];
		rightBucket = new ArrayList[M+1];
		
		
		// 이제 0~M개 까지 배열 생성.
		for (int k = 0 ; k <=M; k++) {
			leftBucket[k] = new ArrayList<>();
			rightBucket[k] = new ArrayList<>();
		}
	
		// 각 절반에 대해 "집 별로 최소 거리 벡터" 생성
		getSubsets(leftIdx , 0, new int[H] , 0, true);
		getSubsets(rightIdx , 0, new int[H] , 0, false);
		
		 for (int i = 0; i <= M; i++  ) {
			 List<int[]> Ls = leftBucket[i];
			 List<int[]> Rs = rightBucket[M - i];
			 if (Ls.isEmpty() || Rs.isEmpty()) continue;
			 for (int[] L : Ls) {
				 for (int[] R : Rs) {
					 
					 int sum = 0;
					 
					 for (int h = 0; h < H; h++) {
						 int m = L[h] < R[h] ? L[h] : R[h];
						 sum += m;
						 
						 if (sum >= best) break;
					 }
					 if (sum < best) best = sum; 
				 }
			 }
		 }
		 System.out.println(best);
	}
	/**
	 * 
	 * 부분집합을 재귀로 만들어, 현재까지의 집 별 최소거리를 유지/갱신.
	 * @param leftIdx
	 * @param pos
	 * @param curMins
	 * @param picked
	 * @param isLeft
	 */
	private static void getSubsets(int[] idxs, int pos, int[] curMins, int picked, boolean isLeft) {
		
		// 배열 초기 값 : 첫 진입 때만 무한대로 세팅 ..~ 
		if (pos == 0) {
			Arrays.fill(curMins , INF);
		}
		
		// 끝에 도달 할 시 필요한 개수범위만 버킷에 저장한다.
		if (pos == idxs.length) {
			if (picked <= M) {
				
				int[] snapshot = curMins.clone();
				
				if (isLeft) leftBucket[picked].add(snapshot);
				
				else rightBucket[picked].add(snapshot);
			}
			return;
		}
		// 1. 현재 치킨 선택 안하는경우
		getSubsets(idxs, pos + 1 , curMins, picked, isLeft);
		
		// 2. 현재 치킨 선택 하는경우
		if (picked + 1 <= M) {
			int cIdx = idxs[pos];
			
			// backups
			int[] backup = curMins.clone();
			for (int h = 0 ; h < curMins.length; h++) {
				int d = dist[h][cIdx];
				if (d < curMins[h]) curMins[h] = d;
			}
			getSubsets(idxs, pos + 1, curMins, picked + 1, isLeft);
			
			//복구
			System.arraycopy(backup, 0, curMins, 0, curMins.length);
		}
	}
	

	
	// 거리 구하는 메서드
	public static int Manhattan(int x1 , int x2 , int y1 , int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
}
