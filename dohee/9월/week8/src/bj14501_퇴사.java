
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class bj14501_퇴사 {
	static int Max = 0;
	static int N;
	static int[] Ti;
	static int[] Pi;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


		// [input]
		N = Integer.parseInt(br.readLine());
		Ti = new int[N+1]; // 상담 완료 시간을 저장하는 배열, 1부터 시작
		Pi = new int[N+1]; // 받을 수 있는 금액 저장하는 배열


		for(int i=1; i<=N; i++) {
			String[] temp = br.readLine().split(" ");
			Ti[i] = Integer.parseInt(temp[0]);
			Pi[i] = Integer.parseInt(temp[1]);
		}

		//System.out.println(Arrays.toString(Ti));
		//System.out.rintln(Arrays.toString(Pi));

		// [logic]
		findMax(1,0);
		
		// [output]
		System.out.println(Max);


	}// main

	private static void findMax(int currentDay, int sumTilNow) {
		// base-case
		if(currentDay > N) {
			Max = Math.max(Max, sumTilNow);
			return;
		}
		
		// 현재 날짜에 상담을 함
		if(currentDay + Ti[currentDay] <= N+1) {
			findMax(currentDay+Ti[currentDay], sumTilNow + Pi[currentDay]);
		}

		// 안 함
		findMax(currentDay+1,sumTilNow);

	}
}
