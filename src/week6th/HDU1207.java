package week6th;

import java.io.BufferedInputStream;
import java.util.Scanner;


/**该题是对有四根柱子的汉诺塔的递归算法步数的计算，
 * <p>这里没有对为什么该算法会比原问题花费更少的步鄹进行解释。</p>*/
public class HDU1207 {
	private static Scanner input = new Scanner(new BufferedInputStream(System.in));
	private static int[] minSteps = new int[65];
	private static int n;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		fourColumnsHanoi();
		while(input.hasNextInt()){
			n = input.nextInt();
			System.out.println(minSteps[n]);			
		}		
	}

	private static void fourColumnsHanoi(){
		minSteps[1] = 1;
		minSteps[2] = 3;

		for(int n = 3;n <= 64;n ++){
			//前提：默认该算法比原算法以更少的步数解决问题，
			//而且这个步数始终小于“Integer.MAX_VALUE”
			int min = Integer.MAX_VALUE;
			for(int x = 1;x < n;x ++){//x不能为n，因为这是毫无意义的
				if(2 * minSteps[x ] + Math.pow(2, n - x) - 1 < min)
					min = 2 * minSteps[x ] + (int)Math.pow(2, n - x) - 1;
			}
			minSteps[n] = min;
		}		
	}
	
}
