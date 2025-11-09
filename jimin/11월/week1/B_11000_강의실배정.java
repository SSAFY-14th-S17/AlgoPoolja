package Baekjoon;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class B_11000_강의실배정 {
	static int n, classCount;
	
	//강의 객체
	static class Lecture {
		int start, end; //시작시간, 종료시간 
		
		Lecture(int s, int e) {
			start = s;
			end = e;
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		Lecture[] lectures = new Lecture[n];
		
		//강의정보입력
		for (int i = 0; i < n; i++) {
			int s = sc.nextInt();
			int e = sc.nextInt();
			lectures[i] = new Lecture(s, e);			
		}
		
        // 1️. 시작 시간 기준으로 정렬
		Arrays.sort(lectures, (a, b) -> a.start - b.start);

        // 2️. 종료 시간 관리용 우선순위 큐 (가장 작은 end가 먼저)
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // 3️. 첫 강의의 종료시간을 큐에 넣음
        pq.add(lectures[0].end);

        // 4️. 나머지 강의들 순회
        for (int i = 1; i < n; i++) {
            Lecture lec = lectures[i];

            // 현재 가장 빨리 z끝나는 강의와 비교
            if (pq.peek() <= lec.start) {
                pq.poll(); // 강의실 재활용 가능 → 제거
            }

            pq.add(lec.end); // 새 종료시간 추가
        }

        // 5️. 큐에 남은 강의실 수 = 필요한 강의실 수
        System.out.println(pq.size());
	}
}
