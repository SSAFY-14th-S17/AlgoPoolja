import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class 십자카드_2659 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 숫자 입력
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(String.valueOf(sc.nextInt()));
        }

        String num = sb.toString();

        // 시계수 구하기
        int clockNum = getClockNum(num);
        System.out.println(clockNum);

        // 시계수보다 작은 시계수 몇개인지 확인
        Set<Integer> set = new HashSet<>(); // 중복 방지
        for (int i = 1111; i < clockNum; i++) {
            String other = String.valueOf(i);
            if (other.contains("0")) {
                continue; // 다음 스킵
            }
            int otherClockNum = getClockNum(other);
            if (otherClockNum < clockNum) {
                set.add(otherClockNum);
            }
        }

        System.out.println(set.size() + 1); // 자기자신이 몇번째인지 카운트 해야하므로
    }

    private static int getClockNum(String input) {
        int clockNum = Integer.parseInt(input);
        for (int i = 0; i < 4; i++) {
            int temp = 0;
            for (int j = 0; j <4; j++) {
                temp = temp*10 + (input.charAt((i+j)%4) - '0');
            }
            clockNum = Math.min(clockNum, temp);
        }
        return clockNum;
    }
}
