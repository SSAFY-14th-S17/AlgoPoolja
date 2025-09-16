import java.util.*;

public class 요리사 {
	static int N;
	static int[][] S;
	static int[][] W;
	static int best;
	
	static int taste(int mask) {
		int sum = 0;
		for (int i = 0; i < S.length; i++) {
			if ((mask & (1<<i))== 0 ) continue;
			for (int j = 0; j < S.length; j++) {
				if ((mask & (1<<j)) == 0 ) continue;
				sum += W[i][j];
			}
		}
		return sum;
	}
	
}