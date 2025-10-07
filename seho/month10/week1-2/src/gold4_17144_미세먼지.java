

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 미세먼지 안녕 (시뮬레이션)
 * R x C 격자판 1 x 1 크기의 칸으로 나눔
 * 각 칸 (r, c)에 있는 미세먼지의 양을 실시간으로 모니터링하는 시스템 개발
 * (r, c)는 r행 c열을 의미
 * 
 * 공기청정기는 항상 1번 열에 설치 크기는 두 행 차지
 * 그 외 칸에는 미세먼지, (r, c)에 있는 미세먼지의 양은 Ar,c이다.
 * 
 * 1초동안 아래 적힌 일이 순서대로 일어남
 * 1. 미세먼지가 확산됨
 * 확산은 미세먼지가 있는 모든 칸에서 동시에 일어남
 * r, c에 있는 미세먼지는 인접 네 방향으로 확산됨
 * 
 * 인접한 방향에 공기청정기가 있거나 칸이 없으면 확산이 일어나지 않음
 * 확산되는 양은 Ar,c/5이고 소수점은 버림
 * (r,c)에 남은 미세 먼지 양은 Arc - Arc/5 * 확산된 방향의 개수
 * 2. 공기청정기 작동
 * 위쪽 공기청정기의 바람은 반시계반향으로 순환 아래쪽은 시계방향으로 순환
 * 바람이 불면 미세먼지가 바람의 방향대로 모두 한 칸씩 이동
 * 공기청정기에서 부는 바람은 미세먼지가 없는 바람, 공기청정기로 들어간 미세먼지는 모두 정화됨
 * 
 * T초 후에 방에 남아있는 미세먼지 양 구하기
 * 
 * 6 <= R, C <= 50
 * 1 <= T <= 1,000
 * -1 인접한 두칸이 공기청정기 위치
 * 
 * 설계
 * 50x50 격자판 생성
 * 
 * 1초당 미세먼지 확장,
 * 공기청정기 작동
 * 
 * 미세먼지 확장은 병렬처리가 되어야함 (bfs x)
 * HashMap으로 관리? 좌표값을 키값으로 가지는 dust class를 구현
 * 
 * 먼지 확산시 인접지역에 확산 가능 구역이면 dust 객체 생성후 list에 확산되는 먼지값을 입력한 후
 * 순회가 끝나면 다시 순회하면서 dust 객체의 list의 size가 0이 아니면 더해주기
 * 
 */
public class gold4_17144_미세먼지 {
	
	static int R, C, T;
	static int[][] matrix;
	static int airCleanerTop, airCleanerBottom;	
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		matrix = new int[R][C];
		boolean isCleanerFound = false;
		
		int idx = 0;
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
				if (matrix[i][j] == -1 && !isCleanerFound) {
					airCleanerTop = i;
					airCleanerBottom = i + 1;
					isCleanerFound = true;
				}
			}
		}
		
		for (int i = 0; i < T; i++) {
			spread();			
			cleanerOn();
		}
		
		// 미세먼지 총량 계산
		int total = 0;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (matrix[i][j] > 0) {
					total += matrix[i][j];
				}
			}
		}
		
		System.out.println(total);
	} // main
	
	// 미세먼지 확산
	public static void spread() {
		// 확산 결과를 저장할 임시 2차원 배열 생성
		int[][] tmp = new int[R][C];
		
		for (int r = 0; r < R; r++) {
			for (int c = 0; c < C; c++) {
				if (matrix[r][c] == -1) {
					tmp[r][c] = -1;
					continue;
				}
				
				// 현재 위치에 남는 먼지 양을 임시 배열에 더함
				int remainingDust = matrix[r][c];
				int spreadAmount = matrix[r][c] / 5;
				
				// 4방향 확산
				for (int dir = 0; dir < 4; dir++) {
					int nr = r + dr[dir];
					int nc = c + dc[dir];
					
					// 범위를 벗어나거나 공기청정기가 있는 곳으로는 확산되지 않음
                    if (nr < 0 || nr >= R || nc < 0 || nc >= C || matrix[nr][nc] == -1) {
                        continue;
                    }
                    
                    tmp[nr][nc] += spreadAmount;
                    remainingDust -= spreadAmount;
				}
				tmp[r][c] += remainingDust;
			}
		}
		
		matrix = tmp;
	}
	
	// 공기청정기 작동
	public static void cleanerOn() {
        int top = airCleanerTop;

        // 1. 아래 -> 위 (왼쪽 열)
        for (int r = top - 1; r > 0; r--) matrix[r][0] = matrix[r - 1][0];
        // 2. 오른쪽 -> 왼쪽 (맨 위 행)
        for (int c = 0; c < C - 1; c++) matrix[0][c] = matrix[0][c + 1];
        // 3. 위 -> 아래 (오른쪽 열)
        for (int r = 0; r < top; r++) matrix[r][C - 1] = matrix[r + 1][C - 1];
        // 4. 왼쪽 -> 오른쪽 (공기청정기 행)
        for (int c = C - 1; c > 1; c--) matrix[top][c] = matrix[top][c - 1];
        
        // 공기청정기에서 나온 바람은 먼지가 없음
        matrix[top][1] = 0;

        int bottom = airCleanerBottom;

        // 1. 위 -> 아래 (왼쪽 열)
        for (int r = bottom + 1; r < R - 1; r++) matrix[r][0] = matrix[r + 1][0];
        // 2. 오른쪽 -> 왼쪽 (맨 아래 행)
        for (int c = 0; c < C - 1; c++) matrix[R - 1][c] = matrix[R - 1][c + 1];
        // 3. 아래 -> 위 (오른쪽 열)
        for (int r = R - 1; r > bottom; r--) matrix[r][C - 1] = matrix[r - 1][C - 1];
        // 4. 왼쪽 -> 오른쪽 (공기청정기 행)
        for (int c = C - 1; c > 1; c--) matrix[bottom][c] = matrix[bottom][c - 1];
        
        // 공기청정기에서 나온 바람은 먼지가 없음
        matrix[bottom][1] = 0;
    }
}
