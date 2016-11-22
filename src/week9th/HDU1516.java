/**   
* @Title: HDU1516.java 
* @Package week9th 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author FlyingFish
* @date 2016-11-09
* @version V1.0   
*/
package week9th;

import java.io.BufferedInputStream;
import java.util.Scanner;

enum Operation{
	insert,delete,replace,loop
}

/**
* <p> HDU1516</p>
* <p>Description: </p>
* @author FlyingFish
* @date 2016-11-09
*/
public class HDU1516 {

	private static Scanner input = new Scanner(new BufferedInputStream(System.in));
	
	public final static int MAX_LEN = 80;
	
	private static int[][] dp = new int[MAX_LEN + 1][MAX_LEN + 1];
	
	private static Operation[][] opTag = new Operation[MAX_LEN + 1][MAX_LEN + 1];
	
	
	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param args 
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//����һ������༭�����ֵ
		/*
		String[] testStrings = {"abcac","bcd","aaa","aabaaaa"}; 
		calcEditDistance(testStrings[0],testStrings[1]);
		System.out.println(dp[testStrings[0].length()][testStrings[1].length()]);
		calcEditDistance(testStrings[2],testStrings[3]);
		System.out.println(dp[testStrings[2].length()][testStrings[3].length()]);
*/
		//���Զ��������·
		/*
		String[] testStrings = {"abcac","bcd","aaa","aabaaaa"}; 
		calcEditDistance(testStrings[0],testStrings[1]);
		printPath(testStrings[0],testStrings[1]);
		calcEditDistance(testStrings[2],testStrings[3]);
		printPath(testStrings[2],testStrings[3]);
		 */
		String s1="" ,s2="";
		while(input.hasNext()){
			s1 = input.next();
			s2 = input.next();
			calcEditDistance(s1,s2);
			printPath(s1,s2);
		}			
	}
	
	private static void calcEditDistance(String s1,String s2){
		
		int len1 = s1.length(),len2 = s2.length();
		for(int i = 0;i <= len1;i ++){
			dp[i][0] = i;
			opTag[i][0] = Operation.delete;
		}	
		for(int j = 0;j <= len2;j ++){
			dp[0][j] = j;
			opTag[0][j] = Operation.insert;
		}
		opTag[0][0] = Operation.loop;

		for(int i = 1; i <= len1;i ++)
			for(int j = 1; j <= len2;j ++){//�Ⱥ�
				int indicator = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;//ע��ʵ���±��0��ʼ
				
				switch(orderOfMin(dp[i][j-1]+1,dp[i-1][j]+1,dp[i-1][j-1] + indicator)){
					case 1: dp[i][j] = dp[i][j-1]+1;
									opTag[i][j] = Operation.insert;
									break;
					case 2: dp[i][j] = dp[i-1][j]+1;
									opTag[i][j] = Operation.delete;									
									break;
					//case 3:				
					default: dp[i][j] = dp[i-1][j-1]+indicator;
									opTag[i][j] =  indicator == 1 ? Operation.replace :Operation.loop;
									break;	
				}		
			}					
	}
	
	private static void printPath(String s1,String s2){

		int i =  s1.length(), j  = s2.length();
		System.out.println(dp[i][j]);
		int k = 1;
		while(i != 0 || j != 0){
			switch(opTag[i][j]){
				case insert: //��s2�ĵ�j��Ԫ�ز��뵽s1��i+1λ��
					System.out.println(k++ + " Insert " +(i+1)+","+s2.charAt(j-1));
					j--;
					break;
				case delete://��s1�ĵ�i��Ԫ��ɾ��
					System.out.println(k++ +" Delete " + i);
					i--;
					break;
				case replace://��s1�ĵ�i��Ԫ�ػ���s2�ĵ�j��Ԫ��
					System.out.println(k++ +" Replace "+i+","+s2.charAt(j-1));
					i--;j--;
					break;
				case loop:
					i--;j--;
					break;		
			}		
		}
	}
	
	
	
	/** 
	* <p>�����������е���Сֵ����� </p> 
	* <p>Description: </p> 
	* @param a
	* @param b
	* @param c
	* @return ��ſ���Ϊ1��2��3
	*/
	private static int orderOfMin(int a,int b ,int c){
		int temp = a,order = 1;
		if(b < temp){
			temp = b;
			order = 2;
		}
		if(c < temp){
			temp = c;
			order = 3;
		}	
		return order;
	}	
}
