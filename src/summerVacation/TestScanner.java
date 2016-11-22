package summerVacation;

import java.util.Scanner;

public class TestScanner {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("输入两个整数，以一个空格隔开：");
		int i1 = input.nextInt();
		
		//String leftString = input.nextLine();
		
		//结论，在键盘中，空格可以作为分隔符
		//System.out.println("第一个整数 " + i1 + " 剩余字符串长度 " + leftString.length());
		
		int i2 = input.nextInt();
		//结论，在键盘中，回车可以作为分隔符
		System.out.println("第一个整数 " + i1 + " 第二个整数 " + i2);
		
		input.close();
		
	}

}
