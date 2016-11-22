package week4th;

import java.io.BufferedInputStream;
import java.util.Scanner;


/**水题，祝自己国庆节后开心。
 * <p>这里的关键点是，排列都要从“1”开始。之后的数没有这个限制。
 * 于是将所有可能的排列划分为两种情况。记M级楼梯的排列数为Pm。</p>
 * <p>	情况1，排列第二个数是1，这类排列的数目与P(m-1)相同；</p>
 *<p> 情况2，排列的第二个数是2,这类排列的数目与P(m-2)相同,即把P(m-2)排列的第一个数去掉再接到已有的“12”中去。</p>*/
public class HDU2041 {
	private static Scanner input = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int cases = input.nextInt();
		while(cases -- != 0){
			int m = input.nextInt();
			
			System.out.println(similarFibSolve(m));;
		}
		
		input.close();
	}

	public static int similarFibSolve(int m){
		int f0 = 1;
		int f1 = 1;
		int f2 = 2;
		
		if(m == 1)
			return f0;
		else if(m == 2)
			return f1;
		else if(m == 3)
			return f2;

		
		for(int i = 4 ;i <= m;i ++){
			f0 = f1;
			f1 = f2;
			f2 = f0 + f1;
		}
		
		return f2;
	}
}
