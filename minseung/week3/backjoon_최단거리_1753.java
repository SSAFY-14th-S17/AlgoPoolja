package backjoon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class 최단경로_1753 {
	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("src/input.txt"));
		
		Scanner sc = new Scanner(System.in);
		
		int V = sc.nextInt(); //정점의 개수
		int E = sc.nextInt(); //간선의 개수
		int K = sc.nextInt(); //시작 정점의 번호
		
		for (int i = 0; i <E; i++) {
			int u = sc.nextInt(); //시작 저점
			int v = sc.nextInt(); //도착정점
			int w = sc.nextInt(); //가중치
		}
		
		//최단경로값 출력하기
		
	}

}
