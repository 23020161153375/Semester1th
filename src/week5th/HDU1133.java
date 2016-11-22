package week5th;

import java.io.BufferedInputStream;
import java.math.BigInteger;
import java.util.Scanner;

public class HDU1133 {
	private static Scanner input = new Scanner(new BufferedInputStream(System.in));
	private static int m ,n;
	private static int[] queue = new int[201];
	private static int[] swapTimes = new int[201]; 
	//private static int[] factorials = new int[100];
	private static BigInteger[] factorials = new BigInteger[101];
	
	private static int totalChanges = 0;
	//private static int count = 0;
	private static BigInteger count = new BigInteger("0");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		factorial(100);
		m = input.nextInt();
		n = input.nextInt();
		int caseth = 0;
		while(m != 0){
			caseth++;
			reset();
			perml(1);
			System.out.println("Test #" + caseth + ":");
			//System.out.println(count * factorials[m] * factorials[n]);
			System.out.println(count.multiply(factorials[n])
																.multiply(factorials[m]));
			m = input.nextInt();
			n  = input.nextInt();
		}
				
	}

	private static void reset(){
		for(int i = 1;i <= m;i ++)
			queue[i] = 50;
		for(int j = m + 1;j <= m + n;j ++)	
			queue[j] = -50;
		totalChanges = 0;
		count = BigInteger.ZERO;
		//count = 0;
	}
	
	private static void perml(int i){
		if(i == m + n)
			count = count.add(BigInteger.ONE);
			//count++;
		else{
			for(int j = i;j <= m+ n;j++){
				if(swapTimes[i] == 0 || (swapTimes[i] == 1 && queue[i] != queue[j])){
					swapTimes[i] ++;
					if(totalChanges + queue[j] >=0){
						totalChanges += queue[j];
						swap(i,j);
						perml(i + 1);
						swap(i,j);
						totalChanges -= queue[j];
					}					
				}					
			}
		swapTimes[i] = 0;
		}		
	}
	
	
	private static void swap(int index1,int index2){
		int temp = queue[index1];
		queue[index1] = queue[index2];  
		queue[index2] = temp;
	}
	
	/**计算n以内（包括n）的所有正整数的阶乘，存储到factorials数组内*/
	private static  void factorial(int n){
		factorials[0] = BigInteger.ONE;
		for(int i = 1;i <= n;i ++)
			factorials[i] = factorials[i-1].multiply(new BigInteger(i +""));
		
		/*factorials[0] = 1;
		for(int i = 1;i <= n;i ++)
			factorials[i] = i * factorials[i - 1];
		 */	
	} 
	
}
