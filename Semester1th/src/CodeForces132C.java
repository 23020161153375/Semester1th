

import java.util.Scanner;

public class CodeForces132C {

		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    //freopen("in.txt", "r", stdin);
		Scanner input = new Scanner(System.in); 
		int maxn = 110;
		 int[][][] dp = new int[maxn][maxn][2];
		 int inf = 0x3f3f3f3f;  
		
		 
		 String cmd ;
		//char[]  cmd = new char [maxn];
	   
		 cmd = input.next();
		 // scanf("%s", cmd + 1);
	    
		 int n = input.nextInt();    
	    //scanf("%d", &n);
	    int len = cmd.length();
		 //int len = strlen(cmd + 1);
	     	
	    //memset(dp, -inf, sizeof(dp));
	    for (int i = 0; i < dp[0].length; i++)
	        for (int j = 0; j < dp[0][0].length; j++) 
	        	dp[i][j][0] = dp[i][j][1] = -inf;
	    dp[0][0][0] = 0;//正方向
	    dp[0][0][1] = 0;//负方向，两边都可以走
	    for (int i = 1; i <= len; i++)
	    {
	        for (int j = 0; j <= n; j++)
	        {
	            for (int k = 0; k <= j; k++)
	            {
	                if (cmd.charAt(i - 1) == 'F')
	                {
	                    if (k %2 == 1)
	                    {
	                        dp[i][j][0] = Math.max(dp[i][j][0], dp[i - 1][j - k][1]);
	                        dp[i][j][1] = Math.max(dp[i][j][1], dp[i - 1][j - k][0]);
	                    }
	                    else
	                    {
	                        dp[i][j][0] = Math.max(dp[i][j][0], dp[i - 1][j - k][0] + 1);
	                        dp[i][j][1] = Math.max(dp[i][j][1], dp[i - 1][j - k][1] - 1);
	                    }
	                }
	                else
	                {

	                    if (k % 2 == 1)
	                    {
	                        dp[i][j][0] = Math.max(dp[i][j][0], dp[i - 1][j - k][0] + 1);
	                        dp[i][j][1] = Math.max(dp[i][j][1], dp[i - 1][j - k][1] - 1);
	                    }
	                    else
	                    {
	                        dp[i][j][0] = Math.max(dp[i][j][0], dp[i - 1][j - k][1]);
	                        dp[i][j][1] = Math.max(dp[i][j][1], dp[i - 1][j - k][0]);
	                    }
	                }
	            }

	        }

	    }
	    
	    for(int i = 0;i <= len;i ++){
	    	for(int j = 0;j <= n;j ++)
	    		for(int k = 0;k < 2;k ++)
	    			System.out.print(dp[i][j][k] + " ");
	    	System.out.println();
	    }
	    
	    System.out.printf("%d\n", Math.max(dp[len][n][0], dp[len][n][1]));
	   // return 0;
	}

	private static int  max(int a ,int b){
		if(Math.abs(a) >= Math.abs(b))
				return Math.abs(a);
		else
			return Math.abs(b);
	}
	
	
}
