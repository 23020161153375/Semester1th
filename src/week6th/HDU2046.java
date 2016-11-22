package week6th;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**类斐波那契问题：p * 1+ q * 2 = n，求"1"，"2"的排列方式的数目*/
public class HDU2046 {
	private static Scanner input = new Scanner(new BufferedInputStream(System.in));
	private static final  int MAX_N= 50;
	
	//先计算好存储起来，再查表
	private static long[] ways = new long[MAX_N + 1];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		similarFibSolve();
		while(input.hasNextInt()){
			int m = input.nextInt();
			
			System.out.println(ways[m]);;
		}
		
		input.close();
	}

	public static void similarFibSolve(){	
		ways[0] = 1;
		ways[1] = 1;
		ways[2] = 2;
		
		for(int i = 3 ;i <= MAX_N;i ++){
			ways[i] = ways[i - 2] + ways[i - 1];
		}		
	}
}
