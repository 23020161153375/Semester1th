package week4th;

import java.io.BufferedInputStream;
import java.util.Scanner;


/**ˮ�⣬ף�Լ�����ں��ġ�
 * <p>����Ĺؼ����ǣ����ж�Ҫ�ӡ�1����ʼ��֮�����û��������ơ�
 * ���ǽ����п��ܵ����л���Ϊ�����������M��¥�ݵ�������ΪPm��</p>
 * <p>	���1�����еڶ�������1���������е���Ŀ��P(m-1)��ͬ��</p>
 *<p> ���2�����еĵڶ�������2,�������е���Ŀ��P(m-2)��ͬ,����P(m-2)���еĵ�һ����ȥ���ٽӵ����еġ�12����ȥ��</p>*/
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
