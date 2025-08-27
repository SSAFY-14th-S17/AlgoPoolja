
public class test {
	static int N;
	static int M;
	public static void main(String[] args) {
		N = 10;
		M = 10;
		int x = -1;
		int y = 5;
		System.out.println(isDir(x,y));
	}
	
	public static boolean isDir(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M ;
	}
}
