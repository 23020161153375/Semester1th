package summerVacation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitOutputStream {
//������ص��������ļ�
	
	//һ������
	private byte bitStream;
	
	//��ǰbitStream���Ѵ�ŵı���λ��
	private int bitAmount = 0;
	
	//��������
	private BufferedOutputStream output;
	
	/**
	 * ���캯��
	 * @param file �����Ŀ���ļ�
	 * @throws FileNotFoundException
	 */
	public BitOutputStream(File file) throws FileNotFoundException{
		output = new BufferedOutputStream(new FileOutputStream(file));		
	}
	
	//������������
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("text/Exercise19_17.dat");
		BitOutputStream output = new BitOutputStream(file);
		output.writeBit("010000100100001001101");
		output.close();
	}

	/**
	 * дһ������
	 * @param bit �ַ� ��0�����ַ���1��
	 * @throws IOException
	 */
	public void writeBit(char bit) throws IOException{
		if(bit != '0' && bit != '1')
			throw new NumberFormatException("����Ӧ���ַ�0���ַ�1.");

		//���ַ�ת��������
		int mask = bit == '0' ? 0:1;
		
		// 0 ^ mask = mask
		//temp���ƺ����λ����0
		int temp = (bitStream << 1) ^ mask;
		
		//ȡtemp��8λ
		bitStream = (byte)temp;	
		bitAmount = (bitAmount + 1) % 8;
		
		if(bitAmount == 0){
		//��ʱbitStream�е�8λ�Ѿ�д����
			output.write(bitStream);
		
		//Reset
			bitStream = 0;
		}
	}
	
	/**
	 * дһ������
	 * @param bit ��0����1����ɵı�����
	 * @throws IOException
	 */
	public void writeBit(String bit) throws IOException{
		char[] bits = bit.toCharArray();
		for(char c : bits)
			writeBit(c);		
	}
	
	/**
	 * ����������Ҫ�ر������
	 * @throws IOException
	 */
	public void close() throws IOException{
		
		//���bitStreamû��д��8λ���ں��油��
		while(bitAmount != 0)
			writeBit('0');
		output.close();		
	}
	
}

