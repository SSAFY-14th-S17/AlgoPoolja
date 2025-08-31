package Baekjoon;
import java.util.Scanner;

//https://www.acmicpc.net/problem/2503
public class B_2503 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int num = sc.nextInt();
		int[][] info = new int[num][3];
		
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < 3; j++) {				
				info[i][j] = sc.nextInt();
			}
		}		
		
		int count = 0;
		for (int i = 123; i <= 987; i++) {
			int n1 = i / 100;
			int n2 = (i % 100 ) / 10;
			int n3 = (i % 100 ) % 10;
			String s1 = Integer.toString(n1);
			String s2 = Integer.toString(n2);
			String s3 = Integer.toString(n3);
			
			//만약 0이 있거나, 같은 숫자가 있다면 넘기기 
			if ((n1 == 0 || n2 == 0 || n3 == 0) || (n1 == n2 || n2 == n3 || n1 == n3)) {
				continue;
			} else {
				boolean isPossible = true;
				for (int h = 0; h < num; h++) {
					int strikeCount = 0;
					int ballCount = 0;			
					
					if (s1.charAt(0) == Integer.toString(info[h][0]).charAt(0)) strikeCount++;
					else if (Integer.toString(info[h][0]).contains(s1)) ballCount++;

					if (s2.charAt(0) == Integer.toString(info[h][0]).charAt(1)) strikeCount++;
					else if (Integer.toString(info[h][0]).contains(s2)) ballCount++;

					if (s3.charAt(0) == Integer.toString(info[h][0]).charAt(2)) strikeCount++;
					else if (Integer.toString(info[h][0]).contains(s3)) ballCount++;
					
	                if (strikeCount != info[h][1] || ballCount != info[h][2]) {
	                	isPossible = false;
	                    break;
	                }
				}	
				if (isPossible) count++;
			}
		}		
		System.out.println(count);
	}
}
