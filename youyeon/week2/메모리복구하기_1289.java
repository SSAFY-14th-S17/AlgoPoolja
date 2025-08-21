import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.io.IOException;

public class 메모리복구하기_1289 {

    public static void main(String[] args) throws IOException {

         BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
         int T = Integer.parseInt(br.readLine());

         for (int tc = 1; tc <= T; tc++) {
             String input = br.readLine();

             StringBuilder output = new StringBuilder(); // 결과물

             for (int i = 0; i < input.length(); i++) {
                 output.append("0");
             }

             int cnt = 0;

             for (int i = 0; i < input.length(); i++) {

                 if (input.charAt(i) != output.charAt(i)) {
                     char ch = input.charAt(i);
                     StringBuilder sb = new StringBuilder();
                     for (int j = i; j < input.length(); j++) {
                         sb.append(ch);
                     }
                     output.replace(i, input.length(), sb.toString());
                     cnt++;
                 }

                 if (output.toString().equals(input)) {
                     break;
                 }
             }
             System.out.println("#" + tc +" " + cnt);
         }
    }
}