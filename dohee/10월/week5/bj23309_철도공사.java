package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class bj23309_철도공사 {
	static int N, M;
	static final int Max = 1000001;
	// 인덱스: 역의 고유 번호 (1~)
	static int[] next = new int[Max]; // 각 인덱스 역번호의 다음 역번호 저장 
	static int[] prev = new int [Max]; // 이전 역 번호 저장 
	static boolean[] exists = new boolean [Max]; // 이미 지어진 역인지 체크 

	static int tmp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		// [input]
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());


		// next, prev 배열 채우기 (N개의 숫자)
		Arrays.fill(next,-1);
		Arrays.fill(prev, -1);

		st = new StringTokenizer(br.readLine());

		int first = Integer.parseInt(st.nextToken());
		exists[first]=true;
		int current = first;

		for(int i=1; i<N; i++) {
			int nextStop = Integer.parseInt(st.nextToken());
			exists[nextStop] = true;
			next[current]=nextStop;
			prev[nextStop] = current;
			current = nextStop;
		}

		// !! 원형 구조 -> 마지막 역과 첫번째 역 연결
		next[current]= first;
		prev[first]=current;



		// [logic]
		// 작업의 수 (M개)
		for(int m=0; m<M; m++) {
			st = new StringTokenizer(br.readLine());
			String cmd = st.nextToken();
			int i = Integer.parseInt(st.nextToken());
			int j=0;
			if(st.hasMoreTokens()) {j = Integer.parseInt(st.nextToken());} // !! hasMoreTokens 


			switch(cmd) {
			case "BN":{ // 다음 역의 번호를 출력 & i번역과 그 다음 역 사이에 j번 역 설립
				// !! 모든 역은 원형으로 연결되어 있기 때문에 next[i]는 항상 유효함
				int nextSt = next[i];
				sb.append(nextSt).append('\n');

				if(exists[j]) break; // 이미 설립되어 있다면 
				//설립해야 한다면)  i - j -  tmp
				exists[j]=true;

				tmp = next[i];
				next[i]= j; // i역 다음 = j
				prev [j] = i; // j역 이전 = i
				next[j] = tmp; // j의 다음 = tmp 
				prev[tmp]=j;
				break;
			}

			case "BP":{ // 이전 역의 번호를 출력 & j번 역 설립
				sb.append(prev[i]).append('\n');

				if(exists[j]) break; 
				exists[j] = true;

				// tmp - j - i
				tmp = prev[i]; // -1일 수도 있음 (head) 
				next[tmp]=j;
				prev[j] = tmp;
				next[j]=i;
				prev[i] = j;
				break;
			}


			case "CN":{ // i역의 다음 역의 번호를 폐쇠 & 그 역 번호 출력
				int nextStn = next[i];

				// 역이 2개 이상 남아있을 때만 폐쇄 작업 가능
				// nextStn이 자기 자신을 가리키는 경우 예외 처리
				if(nextStn == i) break;
				if(!exists[nextStn]) break;

				// 다음 역 있으면 찍고, 폐쇄 
				sb.append(nextStn).append('\n');
				exists[nextStn]=false;

				int newNext = next[nextStn]; // 다음역의 다음 
				// i - nextStn(x) - newNext
				next[i]= newNext;
				prev[newNext]=i;

				break;
			}


			case "CP":{ // i역의 이전 역의 번호를 폐쇠 & 그 역 번호 출력
				int prevS = prev[i];

				// 자기 자신을 가리키는 경우 (역이 1개밖에 없을 떄)
				// 이전역이 없을 경
				if(prevS == i) break;
				if(!exists[prevS]) break;

				sb.append(prevS).append('\n');
				exists[prevS]=false;

				// newPrev - prevS - i
				int newPrev = prev[prevS];
				prev[i] = newPrev;
				next[newPrev]=i;
				break;
			}

			}// switch

		}// for: m

		System.out.println(sb);
	}
}
