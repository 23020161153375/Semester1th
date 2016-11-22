package begin;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**先找出递推规律，发现和求斐波那契数类似.其表达式为：
 * <p>	f(n) = f(n - 1) + 2 f(n - 2)</p>
 * */
public class HDU2154 {
	private static Scanner input = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n;
		while(input.hasNextInt()){
			n = input.nextInt();
			
			if(n != 0)
            	System.out.println(similarFibSolve(n));
			else//输入零表示结束
				break;				
		}		
		input.close();
	}

	public static int similarFibSolve(int n){
		int f0 = 2;
		int f1 = 2;
		int f2 = 6;
		
		if(n == 2)
			return f0;
		else if(n == 3)
			return f1;
		else if(n == 4)
			return f2;
		else if(n == 1)
			//只跳一次是回不去的
			return 0;
		
		for(int i = 5 ;i <= n;i ++){
			f0 = f1;
			f1 = f2;
			//在这里取模不会影响结果，因为f前面系数是整数
            //另一方面，由于f函数呈现指数级变化，应该提前
            //取余，防止数值超出范围。			
			f2 = (f1 + 2 * f0) % 10000;
		}
		
		return f2;
	}
	
}
