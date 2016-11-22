package begin;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**���ҳ����ƹ��ɣ����ֺ���쳲�����������.����ʽΪ��
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
			else//�������ʾ����
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
			//ֻ��һ���ǻز�ȥ��
			return 0;
		
		for(int i = 5 ;i <= n;i ++){
			f0 = f1;
			f1 = f2;
			//������ȡģ����Ӱ��������Ϊfǰ��ϵ��������
            //��һ���棬����f��������ָ�����仯��Ӧ����ǰ
            //ȡ�࣬��ֹ��ֵ������Χ��			
			f2 = (f1 + 2 * f0) % 10000;
		}
		
		return f2;
	}
	
}
