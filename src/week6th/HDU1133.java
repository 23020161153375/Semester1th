package week6th;

import java.io.BufferedInputStream;
import java.math.BigInteger;
import java.util.Scanner;

public class HDU1133 {
	private static Scanner input = 
			new Scanner(new BufferedInputStream(System.in));

	private static int m,n;

	private static BigInteger[] factorials = new BigInteger[201];
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		factorial();
		m = input.nextInt();
		n = input.nextInt();
		int caseth = 0;
		while(m != 0 || n != 0){
			caseth ++;
			BigInteger factor = new BigInteger(String.valueOf(m + 1 - n));
			BigInteger divisor = new BigInteger(String.valueOf(m + 1));
			
			System.out.println("Test #" + caseth +":");
			if(m + 1 - n >= 0)
				System.out.println(factorials[m + n].multiply(factor).divide(divisor));		
			else
				System.out.println(0);
			m = input.nextInt();
			n = input.nextInt();
		}		
	}

	private static void factorial(){
		factorials[0] =  BigInteger.ONE;
		for(int i = 1;i <= 200;i ++){
			factorials[i] = factorials[i - 1].multiply(
					new BigInteger(String.valueOf(i)));			
		}		
	}	
}
