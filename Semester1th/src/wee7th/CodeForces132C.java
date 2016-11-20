package wee7th;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class CodeForces132C {
	private static Scanner input = 
			new Scanner(new BufferedInputStream(System.in));

	private static String instructions;
	private static int n = 0;
	private static int[][][] dp ;
	private final static int INFINITE =  -500;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		instructions = input.next();
		n = input.nextInt();
		initiate();

		for(int i = 1;i <= instructions.length();i ++)
			for(int j = 0;j <= n;j ++)
				for(int m = 0;m <= j;m ++)
					if(instructions.charAt(i - 1) == 'T' ){
						if(m % 2 == 0){
							dp[i][j][0] = Math.max(dp[i][j][0], dp[i-1][j-m][1]);
							dp[i][j][1] = Math.max(dp[i][j][1], dp[i-1][j-m][0]);							
						}else{
							dp[i][j][0] = Math.max(dp[i][j][0], dp[i-1][j-m][0] + 1);
							//现在朝着负方向前进一个位置
							//正、负的说法只是相对于起始时乌龟朝向而言的
							//起始时海龟有两种可能朝向
							dp[i][j][1] = Math.max(dp[i][j][1], dp[i-1][j-m][1] - 1);							
						}		
					}else{//原指令为"F"
						if(m%2==0){
							dp[i][j][0] = Math.max(dp[i][j][0],dp[i-1][j-m][0] + 1);
							dp[i][j][1] = Math.max(dp[i][j][1],dp[i-1][j-m][1] - 1);
						}else{
							dp[i][j][0] = Math.max(dp[i][j][0],dp[i - 1][j-m][1]);
							dp[i][j][1] = Math.max(dp[i][j][1],dp[i-1][j-m][0]);
						}				
					}
		
		System.out.println(Math.max(dp[instructions.length()][n][0], dp[instructions.length()][n][1]));
	}

	private static void initiate(){
		dp = new int[instructions.length() + 1][n + 1][2];
		
		for(int i = 0;i < dp.length;i ++)
			for(int j = 0; j < dp[0].length;j ++)
				for(int k = 0;k < dp[0][0].length;k ++)
					dp[i][j][k] =  INFINITE;
		
		dp[0][0][0] = dp[0][0][1] = 0;		
	}
}
