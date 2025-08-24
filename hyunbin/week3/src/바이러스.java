import java.util.*;
import java.io.*;

public class 바이러스 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		
		List<String[]> computers = new ArrayList<>();
		
		for (int i = 0; i < M; i++) {
			computers.add(br.readLine().split(" "));
		}
		
		Set<String> virus = new HashSet<>();
		virus.add("1");
		
		boolean isInfected = true;
		
		while (isInfected) {
			isInfected = false;
			
			for (String[] v : computers) {
				String parent  = v[0];
				String child  = v[1];
				
				boolean parentInfected = virus.contains(parent);
				boolean childInfected = virus.contains(child);
				
				if (parentInfected && !childInfected) {//부모는 감염되었지만 자식은 아직일때 
					virus.add(child);
					isInfected = true;
				}
				if (!parentInfected && childInfected) {//자식 감염되었지만 부모는 아직일때 
					virus.add(parent);
					isInfected = true;
				}
			}
		}
		System.out.println(virus.size()-1); // 1번컴터제외
	}
}