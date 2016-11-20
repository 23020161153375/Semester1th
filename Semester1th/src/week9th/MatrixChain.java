package week9th;


public class MatrixChain {
//���Ż��������˷�
//�ֱ��õݹ顢��̬�滮������¼ʵ��
//���Ƚ�����֮���Ч��
//����ʱ����һ������P= {p0,p1,...,pn}��ʾ��������n�������ά��
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[] p = {30,35,15,5,10,20,25};
		
		//p.len = n + 1
		int[][] s = new int[p.length ][p.length ];
		//���Եݹ鷨
		/*
		int result = recMatrixChain(p,s);
		System.out.println(result);
		print(s,1,p.length - 1);
		*/
		
		//���Զ�̬�滮��		
		int[][] m = new int[p.length ][p.length ];
		/*
		dpMatrixChain(p,m,s);
		int result = m[1][p.length - 1];
		System.out.println(result);
		print(s,1,p.length - 1);		
		*/
		
		//���Ա���¼��
		/*
		memoMatrixChain(p,m,s);
		int result = m[1][p.length - 1];
		System.out.println(result);
		print(s,1,p.length - 1);		
		*/
		
		//���ܷ���
		timeStatistics();		
	}
	
	public static int[][] p ;
	public static int[][][] m;
	public static int[][][] s;
	
	public static void initiatePMS(){
		p = new int[4][];
		m = new int[4][][];
		s = new int[4][][];
		
		/*
		p[0] = new int[7];
		p[1] = new int[17];
		p[2] = new int[27];
		p[3] = new int[37];
		*/
		int[] q3 = {30,35,15,5,10,20,25,
						30,45,5,6,9,15,10,8,22,18,
						15,5,21,18};
		int[] q2 = {30,35,15,5,10,20,25,
						30,45,5,6,9,15,10,8,22,18}; 
		int[] q1 =  {30,35,15,5,10,20,25,
						30,45,5,6,9,15}; 
		int[] q0 =  {30,35,15,5,10,20,25};
		
		p[0] = q0;p[1] = q1;
		p[2] = q2;p[3]= q3;
		for(int i = 0; i < p.length; i ++){
			int n = p[i].length - 1;
			m[i] = new int[n + 1][n + 1];
			s[i] = new int[n + 1][n+ 1];		
		}		
	}
	
	public static void timeStatistics(){
		initiatePMS();
		long startTime,executeTime;
		System.out.println("ʱ��Ч�ܷ���(��λ������)");
		System.out.printf("%-5s%-10s%-10s%-10s\n", "n","��̬�滮","�ݹ�","����¼");
		System.out.println("----------------------------------------------------------------");
		for(int i = 0; i < p.length;i ++){
			int n = p[i].length - 1;
			System.out.printf("%-5d",n);
			startTime = System.currentTimeMillis();
			dpMatrixChain(p[i],m[i],s[i]);
			executeTime = System.currentTimeMillis() - startTime;
			System.out.printf("%-10d",executeTime);

			startTime = System.currentTimeMillis();
			recMatrixChain(p[i],s[i]);
			executeTime = System.currentTimeMillis() - startTime;
			System.out.printf("%-10d",executeTime);
			
			startTime = System.currentTimeMillis();
			memoMatrixChain(p[i],m[i],s[i]);
			executeTime = System.currentTimeMillis() - startTime;
			System.out.printf("%-10d\n",executeTime);			
		}
		System.out.println("�����Ž��");
		for(int i = 0; i < p.length;i++){
			int n = p[i].length - 1;
			
			//�ڼ���
			System.out.printf("%-5d",i+1);
			print(s[i],1,n);
			System.out.println();
		}
		
	}
	
	
	public static int recMatrixChain(int[] p,int[][] s){
		//s = new int[p.length + 1][p.length + 1];
		//ԭ���⣺�ӵ�1���������һ����������Ż���
		return recMatrixChain(p,s,1,p.length-1);		
	}
	
	private static int recMatrixChain(int[] p,int[][] s,int i,int j){
		if(i == j){
			return 0;
		}else{
			int value = Integer.MAX_VALUE,temp;
			for(int k = i ;k <= j - 1;k ++){
				//ѭ��ȡ��Сֵ
				temp = recMatrixChain( p,s,i,k)+recMatrixChain( p,s,k+1,j) 
							+ p[i-1]*p[k]*p[j];				
				if(temp< value){
					value = temp;
					s[i][j] = k;
				}								
			}
			return value;			
		}		
	}
	
	
	public static void dpMatrixChain(int[] p,int[][] m,int[][] s){
		int n = p.length-1;
		for(int i = 1;i <= n;i ++)
			m[i][i] = 0;
		
		//��Ҫ��ע����ִ��˳��
		//c = 2,i[1...n-1],j[2...n];
		//c=3,i[2...n-2],j[3...n];
		//...
		//c=n,i[1],j[n];
		for(int c = 2;c <= n ;c ++)
			for(int i = 1; i <= n - c + 1;i ++){
				int j = i + c - 1;
				m[i][j] = Integer.MAX_VALUE;
				for(int k = i ;k <= j-1;k ++){
					int temp = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j];
					if(temp < m[i][j]){
						m[i][j] = temp;
						s[i][j] = k;
					}					
				}				
			}				
	}
	
	public static void memoMatrixChain(int[] p,int[][] m,int[][] s){
		for(int i = 0; i < m.length;i ++)
			for(int j = 0; j < m[0].length;j ++)
				m[i][j] = -1;//-1Ϊû�и�ֵ�ı�־
				
		memoMatrixChain(p,m,s,1,p.length - 1);
	}

	public static void memoMatrixChain(int[] p,int[][] m,int[][] s,int i,int j){
		if(i == j){
			m[i][j] = 0;
			//return 0;
		}else{
			m[i][j] = Integer.MAX_VALUE;
			int temp,v1,v2;
			for(int k = i ;k <= j - 1;k ++){
				//ѭ��ȡ��Сֵ
				temp = 0;
				if(m[i][k] != -1 )
					v1 = m[i][k];
				else
					v1 = recMatrixChain( p,s,i,k);
				
				if(m[k + 1][j] != -1)
					v2 = m[k+1][j];
				else
					v2 = recMatrixChain( p,s,k+1,j);
				
				
				//temp = recMatrixChain( p,s,i,k)+recMatrixChain( p,s,k+1,j) 
					//		+ p[i-1]*p[k]*p[j];				
				if((temp += v1 + v2+p[i-1]*p[k]*p[j])< m[i][j]){
					m[i][j] = temp;
					//value = temp;
					s[i][j] = k;
				}								
			}
			
			//return value;			
		}	
	}
	
	public static void print(int[][] s,int i ,int j){
		if(i == j)
			System.out.print("A"+i);
		else{
			System.out.print("(");
			print(s,i,s[i][j]);
			print(s,s[i][j] + 1,j);
			System.out.print(")");
		}
	}
	
	
}
