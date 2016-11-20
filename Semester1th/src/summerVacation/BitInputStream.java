package summerVacation;

import java.io.*;

public class BitInputStream {
	private  int  bitStream;
	
	private BufferedInputStream input;
	
	public BitInputStream(File file) throws IOException{
		input = new BufferedInputStream(new FileInputStream(file));
		
		//总是预读取1个字节（以int形式存储）
		bitStream = input.read();	
	}
	
	/**读取文件中下一个字节，并转化成字符串形式
	 * 的比特流。
	 * */
	public String read() throws IOException{		
		//不实行文件末尾检查
		
		String bits = DigitalSystem.decimalToBinary(bitStream,8);
	
		//如果bitStream已经是文件最后一个字节，
		//返回这个字节后，读取到文件末尾标志-1；
		//如果bitStream是文件末尾标志，此时isEnd()
		//返回true,再读取会抛出IOException异常。
		bitStream = input.read();		
		return bits;
	}
	/**
	 * 是否已到文件结尾
	 * @return 如果到结尾返回true
	 */
	public boolean isEnd(){
		return bitStream == -1;
	}
	
	/**
	 * 关闭这个流
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
