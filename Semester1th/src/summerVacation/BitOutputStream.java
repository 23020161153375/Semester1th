package summerVacation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitOutputStream {
//输出比特到二进制文件
	
	//一级缓存
	private byte bitStream;
	
	//当前bitStream中已存放的比特位数
	private int bitAmount = 0;
	
	//二级缓存
	private BufferedOutputStream output;
	
	/**
	 * 构造函数
	 * @param file 输出的目标文件
	 * @throws FileNotFoundException
	 */
	public BitOutputStream(File file) throws FileNotFoundException{
		output = new BufferedOutputStream(new FileOutputStream(file));		
	}
	
	//主函数，测试
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("text/Exercise19_17.dat");
		BitOutputStream output = new BitOutputStream(file);
		output.writeBit("010000100100001001101");
		output.close();
	}

	/**
	 * 写一个比特
	 * @param bit 字符 ‘0’或字符‘1’
	 * @throws IOException
	 */
	public void writeBit(char bit) throws IOException{
		if(bit != '0' && bit != '1')
			throw new NumberFormatException("参数应是字符0或字符1.");

		//将字符转换成数字
		int mask = bit == '0' ? 0:1;
		
		// 0 ^ mask = mask
		//temp左移后，最低位总是0
		int temp = (bitStream << 1) ^ mask;
		
		//取temp低8位
		bitStream = (byte)temp;	
		bitAmount = (bitAmount + 1) % 8;
		
		if(bitAmount == 0){
		//此时bitStream中的8位已经写满了
			output.write(bitStream);
		
		//Reset
			bitStream = 0;
		}
	}
	
	/**
	 * 写一串比特
	 * @param bit ‘0’或‘1’组成的比特流
	 * @throws IOException
	 */
	public void writeBit(String bit) throws IOException{
		char[] bits = bit.toCharArray();
		for(char c : bits)
			writeBit(c);		
	}
	
	/**
	 * 完成输出后需要关闭输出流
	 * @throws IOException
	 */
	public void close() throws IOException{
		
		//如果bitStream没有写满8位，在后面补零
		while(bitAmount != 0)
			writeBit('0');
		output.close();		
	}
	
}

