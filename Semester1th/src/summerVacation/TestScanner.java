package summerVacation;

import java.util.Scanner;

public class TestScanner {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("����������������һ���ո������");
		int i1 = input.nextInt();
		
		//String leftString = input.nextLine();
		
		//���ۣ��ڼ����У��ո������Ϊ�ָ���
		//System.out.println("��һ������ " + i1 + " ʣ���ַ������� " + leftString.length());
		
		int i2 = input.nextInt();
		//���ۣ��ڼ����У��س�������Ϊ�ָ���
		System.out.println("��һ������ " + i1 + " �ڶ������� " + i2);
		
		input.close();
		
	}

}
