import java.util.Arrays;
import java.util.Scanner;

public class boj2659 {
	/*	로직
	 * 1. 조합 할 수 있는 가장 작은 수 N 을 찾고
	 * 2. 조합 할 수 있는 시계수를 제외한 1111부터 ~ N까지의 거리
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int[] arr = new int[4];
		int len = arr.length-1;
		for (int i = 0 ; i < len+1 ; i++) {
			arr[i] = sc.nextInt();
		}
		int[][] nums = new int[4][1];
		
//		arr{1,2,3,4}
//		arr{2,3,4,1}
//		arr{3,4,1,2}
//		arr{4,1,2,3}
		for (int k = 0; k < nums.length; k++) {
			int first = arr[0];
			for (int i = 0; i < len; i++) {
				arr[i] = arr[i+1];
			}
			arr[len] = first;
			System.out.println(Arrays.toString(arr));
			nums[k][1] = arr[k];
		}
		
	}
 }

