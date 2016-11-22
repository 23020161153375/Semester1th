package week6th;

import java.io.BufferedInputStream;
import java.util.Scanner;


/**�����Ƕ����ĸ����ӵĺ�ŵ���ĵݹ��㷨�����ļ��㣬
 * <p>����û�ж�Ϊʲô���㷨���ԭ���⻨�Ѹ��ٵĲ�۸���н��͡�</p>*/
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
			//ǰ�᣺Ĭ�ϸ��㷨��ԭ�㷨�Ը��ٵĲ���������⣬
			//�����������ʼ��С�ڡ�Integer.MAX_VALUE��
			int min = Integer.MAX_VALUE;
			for(int x = 1;x < n;x ++){//x����Ϊn����Ϊ���Ǻ��������
				if(2 * minSteps[x ] + Math.pow(2, n - x) - 1 < min)
					min = 2 * minSteps[x ] + (int)Math.pow(2, n - x) - 1;
			}
			minSteps[n] = min;
		}		
	}
	
}
