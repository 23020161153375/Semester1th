package summerVacation;

import java.io.*;

public class BitInputStream {
	private  int  bitStream;
	
	private BufferedInputStream input;
	
	public BitInputStream(File file) throws IOException{
		input = new BufferedInputStream(new FileInputStream(file));
		
		//����Ԥ��ȡ1���ֽڣ���int��ʽ�洢��
		bitStream = input.read();	
	}
	
	/**��ȡ�ļ�����һ���ֽڣ���ת�����ַ�����ʽ
	 * �ı�������
	 * */
	public String read() throws IOException{		
		//��ʵ���ļ�ĩβ���
		
		String bits = DigitalSystem.decimalToBinary(bitStream,8);
	
		//���bitStream�Ѿ����ļ����һ���ֽڣ�
		//��������ֽں󣬶�ȡ���ļ�ĩβ��־-1��
		//���bitStream���ļ�ĩβ��־����ʱisEnd()
		//����true,�ٶ�ȡ���׳�IOException�쳣��
		bitStream = input.read();		
		return bits;
	}
	/**
	 * �Ƿ��ѵ��ļ���β
	 * @return �������β����true
	 */
	public boolean isEnd(){
		return bitStream == -1;
	}
	
	/**
	 * �ر������
	 * @throws IOException
	 */
	public void close() throws IOException{
		input.close();
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file  = new File("text/Exercise19_2.dat");
		BitInputStream input = new BitInputStream(file);
		while(!input.isEnd())
			System.out.print(input.read()+"");
		input.close();
		
	}

}
