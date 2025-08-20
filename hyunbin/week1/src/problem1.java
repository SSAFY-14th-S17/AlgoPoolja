import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class problem1 {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            int n = sc.nextInt();
            
            // 일주일 스케줄을 저장할 배열
            int[] schedule = new int[7];
            // 수업이 있는 날의 인덱스(0:일 ~ 6:토)를 저장할 리스트
            List<Integer> classDaysIndices = new ArrayList<>();
            
            // 스케줄을 입력받고, 수업이 있는 날의 정보를 저장
            for (int i = 0; i < 7; i++) {
                schedule[i] = sc.nextInt();
                if (schedule[i] == 1) {
                    classDaysIndices.add(i);
                }
            }
            
            // 일주일간 총 수업일 수
            int classesPerWeek = classDaysIndices.size();
            
            // 최소 체류일수를 저장할 변수를 매우 큰 값으로 초기화
            long minDays = Long.MAX_VALUE;

            // 수업이 열리는 모든 요일을 시작일로 가정하여 반복
            for (int startDayIndex : classDaysIndices) {
                // (n-1)개의 수업을 듣기 위해 필요한 꽉 찬 주의 수
                long fullWeeks = (long)(n - 1) / classesPerWeek;
                
                // 꽉찬 주를 보내는 데 걸리는 일수
                long daysFromWeeks = fullWeeks * 7;
                
                // 남은 수업의 수
                long remainingClasses = n - (fullWeeks * classesPerWeek);
                
                // 남은 수업을 듣기 위해 필요한 추가 일수 계산
                long additionalDays = 0;
                long tempClassCount = 0;
                int currentDayIndex = startDayIndex;
                
                while (tempClassCount < remainingClasses) {
                    if (schedule[currentDayIndex % 7] == 1) {
                        tempClassCount++;
                    }
                    additionalDays++;
                    currentDayIndex++;
                }
                
                // 현재 시작일로부터 계산된 총 체류일수
                long totalDays = daysFromWeeks + additionalDays;
                
                // 기존 최소값보다 작으면 갱신
                minDays = Math.min(minDays, totalDays);
            }
            
            System.out.println("#" + test_case + " " + minDays);
        }
        
        sc.close();
    }
}
