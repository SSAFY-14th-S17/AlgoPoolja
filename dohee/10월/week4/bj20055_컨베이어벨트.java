package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

/*
 * [문제]
 * 2N 길이의 컨베이어 벨트 (N *2)
 * - 로봇을 올리는 위치: 1번칸(인덱스: 0)
 * - 로봇을 내리는 위치: N번칸(인덱스: N-1)
 * 	로봇을 내린다? 벨트에서 완전히 제거 (밑 라인으로 이동 X)
 * - 각 칸은 내구도를 가짐
 * - !! 몇 번째 단계에서 진행이 멈추는가? => 몇 회전에서 멈췄는가?
 * 
 * [조건]
 * - 벨트는 각 칸 위에 있는 로봇과 함께 회전함
 * - 가장 먼저 올라간 로봇부터, 회전가능하면 이동 아니면 이동 X
 * - 로봇 이동 조건: 이동하려는 칸에 로봇이 없고, 내구도가 1이상이어야 함
 * - 로봇 올리기 조건: 칸의 내구도가 0이 아니면 가능
 * - 내구도가 0인 칸의 개수 >= K -> 종료!
 * - 내구도 감소 조건:로봇을 올리는 위치에 올리거나 / 로봇이 어떤 칸으로 이동할 경우 그 칸의 내구도 --1
 *
 * [문제 풀이]
 * - 올릴 수 있으면 계속 로봇 올리기
 * - 회전시키면서 모든 로봇 이동시키기 & 내구도 변화
 * 
 */
public class bj20055_컨베이어벨트 {
	static int N,K;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		
		// [input]
		String[] tmp = br.readLine().split(" ");
		N = Integer.parseInt(tmp[0]);
		K = Integer.parseInt(tmp[1]);
		
		// 0-based
		String[] temp = br.readLine().split(" ");
		int[] belt = new int[2*N];

		for(int i=0; i<N*2; i++) {
			belt[i] = Integer.parseInt(temp[i]);
		}
		
		// [logic]
		// 해당 인덱스에 로봇이 있는지
		boolean[] robots = new boolean[N];

		int step = 0;
		while(true){
		    step++;
		    
		    // 1. 벨트 회전
		    int last = belt[2*N-1];
		    System.arraycopy(belt, 0, belt, 1, 2*N-1);
		    belt[0] = last;

		    boolean[] newRobots = new boolean[N];
		    for(int i=0; i<N-1; i++) newRobots[i+1] = robots[i];
		    robots = newRobots;
		    robots[N-1] = false;

		    // 2. 로봇 이동
		    for(int i=N-2; i>=0; i--){
		        if(robots[i] && !robots[i+1] && belt[i+1]>0){
		            robots[i]=false;
		            robots[i+1]=true;
		            belt[i+1]--;
		        }
		    }
		    robots[N-1] = false;

		    // 3. 로봇 올리기
		    if(belt[0]>0 && !robots[0]){
		        robots[0]=true;
		        belt[0]--;
		    }

		    // 4. 종료 조건
		    int zeroCount = 0;
		    for(int val : belt) if(val==0) zeroCount++;
		    if(zeroCount >= K) break;
		}

		// [output]
		System.out.println(step);


		
	}
}
