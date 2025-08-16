import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/** 
 * 원재의 메모리 복구하기
 * 
 * 메모리 bit중 하나를 골라 0인지 1인지 결정하면 해당 값이 메모리 끝까지 덮어씌우는 것
 * 
 */

public class IM_1289 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			
			char[] prevBit = br.readLine().toCharArray();
			char[] curBit = new char[prevBit.length];
			for (int i = 0; i < curBit.length; i++) {
				curBit[i] = '0';
			}
			
			System.out.println(Arrays.toString(prevBit));
			System.out.println(Arrays.toString(curBit));
			
			int cnt = 0;
			for (int i = 0; i < prevBit.length; i++) {
				if (prevBit[i] != curBit[i]) {
					for (int j = i; j < curBit.length; j++) {
						curBit[j] = prevBit[i];
					}
					cnt++;
				}
			}
	
			System.out.println("#" + tc + " " + cnt);
		}
	}
}