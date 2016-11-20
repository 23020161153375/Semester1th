package summerVacation;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import summerVacation.HuffmanCode.Tree;

public class Experiment1 {
	// 存放救世主编码矩阵的宽/高
	public static int sbWH = 18;
	
	//救世主编码矩阵
	private int[][] sbCode = new int[sbWH][sbWH];
	
	//宠物编码矩阵的宽、高
	public static int petWidth = 46,petHeight = 22;
	
	//宠物编码矩阵
	private int[][] petCode = new int[petHeight][petWidth];
	
	//是否已经将宠物召唤出来了
	//只有将宠物召唤出来了，才能
	//调用mirrorTrans和coverHit
	private boolean petWaked = false;
	
	//当前宠物所处的水平方向，作调教之用
	//1代表正向，-1代表反向
	private int petHorizontalDirection = 1;
	
	//分别代表回车、换行、和空格符
	//赋的是其ASCII码值
	private static int cr = 13,nl = 10 ,sp = 32;
	
	//恶魔编码矩阵的宽、高
	public static int devilWidth = 55,devilHeight = 16;
	
	//存放恶魔编码的矩阵
	public int[][] devilCode = new int[devilHeight][devilWidth];
	
	//封印对象的类型：宠物或是恶魔。
	public  static  enum Type{
		pet,devil
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//四个文件位置分别是：救世主、宠物、恶魔、新封印。
		String sbFilename = "E:/Eclipse/xmuTasks/supplement/sb.txt";
		String petFilename = "E:/Eclipse/xmuTasks/supplement/pet.txt";
		String devilFilename = "E:/Eclipse/xmuTasks/supplement/devil.txt"; 
		String newSealFilename = "E:/Eclipse/xmuTasks/supplement/newSeal.dat"; 
		Experiment1 exp1 = new Experiment1();
		
		//任务1，救世主
		//需要在DOS环境运行才能得到预期结果
		//exp1.setSbCode(sbFilename);
		//exp1.printSb();	
		
		//任务2、3：召唤神兽，调教神兽
		exp1.showHexFile(petFilename,Type.pet);
		//for(int i = 0;i < 3;i ++)
			//exp1.mirrorTrans();
		
		//任务4：恶魔来袭，保护神兽
		exp1.showHexFile(devilFilename, Type.devil);
		exp1.coverHit();

		//任务5：用赫夫曼树封印恶魔。
		//exp1.sealDevil(devilFilename, newSealFilename);
		//exp1.unsealDevil(newSealFilename);	
	}
	 
	public void unsealDevil(String filename) throws IOException{
		//自己写的位读取流
		BitInputStream input = new BitInputStream(new File(filename));
		
		//读取文件第一部分：恶魔图案中的字符种类
		int charsKind = Integer.parseInt(input.read(),2);
		
		int[] counts = new int[256];
		for(int i = 0;i < charsKind;i ++ ){
			//根据charsKind取出文件第二部分内容
			int charASCII = Integer.parseInt(input.read(),2);
			int charOccurrence = 
					Integer.parseInt(input.read() + input.read(),2);
			counts[charASCII] = charOccurrence;
		}
		
		//根据第二部分的字频得到赫夫曼树
		HuffmanCode.Tree tree = HuffmanCode.getHuffmanTree(counts);	
		
		//不要忘了计算赫夫曼树结点编码
		 HuffmanCode.getCode(tree.root);

		 //读取编码（文件第三部分内容）
		StringBuilder sBuffer = new StringBuilder();
		while(!input.isEnd())
			sBuffer.append(input.read());
		
		//关闭流
		input.close();
		
		char[] bitCode = sBuffer.toString().toCharArray();
		
		//译码
		char[] devilCodes = HuffmanCode.searchHuffmanTree(bitCode, tree);
		
		//显示恶魔
		for(int i = 0;i < devilCodes.length;i ++)
			System.out.print(devilCodes[i]);	
			
	}
	
	//将恶魔封印到新的位置（newFilename）,这里采用
	//赫夫曼编码。将字符编码后存放到一个二进制文件中。
	//这个二进制文件内容由三部分信息组成：
	//一，用一个字节存储恶魔图案中的字符种类。
	//二，存储字符ASCII码（1B）以及其出现次数（2B），它们构成一对。
	//	    第一部分已经记录了对数。
	//三，赫夫曼编码。
	public void sealDevil(String oldFilename,String newFilename) throws IOException{
		//获取恶魔图片的所有字符
		String devilString = getDevilString(oldFilename);
		
		//记录devilString中各字符出现的次数
		int[] counts = HuffmanCode.getCharacterFrequency(devilString);

		//根据counts得到赫夫曼树并计算出各字符编码
		HuffmanCode.Tree tree = HuffmanCode.getHuffmanTree(counts);
		String[] codes = HuffmanCode.getCode(tree.root);
		
		//自己写的位输出流，将信息写入二进制文件
		BitOutputStream output = new BitOutputStream(new File(newFilename));
		
		//恶魔图案中的字符种类
		byte charsKind = 0;
		for(int i = 0;i < counts.length;i ++)
			if(counts[i] > 0)
				charsKind ++;
		
		//返回的二进制串位数不固定，与参数大小相关
		String charsKindBits = Integer.toBinaryString(charsKind);
		
		//格式化后写入文件（第一部分内容）
		output.writeBit(formatBitStream(charsKindBits,8));
		
		//写入“字符ASCII码——出现次数”对（第二部分内容）
		for(int i = 0;i < counts.length;i ++)
			if(counts[i] > 0){
				String charBits = Integer.toBinaryString(i);
				output.writeBit(formatBitStream(charBits,8));
				//字符数目
				String charCountBits = Integer.toBinaryString(counts[i]);
				output.writeBit(formatBitStream(charCountBits,16));
			}
		
		//写入赫夫曼编码（第三部分内容）
		for(int i = 0;i < devilString.length();i ++){
				int charASCII = (int)devilString.charAt(i);
				output.writeBit(codes[charASCII]);
			} 
		
		//关闭流
		output.close();		
	}
	
	//直接从文本文件（devil.txt）中读取字符，
	//组成字符串并返回该串。
	public static  String getDevilString(String filename) throws IOException{		
		Experiment1 exp1 = new Experiment1();
		
		//字符被存放到devilCode里面
		exp1.setHexCode(filename, Experiment1.Type.devil);
		
		StringBuilder sBuff = new StringBuilder();
		for(int i = 0;i < exp1.devilCode.length;i ++)
			for(int j = 0;j < exp1.devilCode[0].length;j ++){
					sBuff.append((char)exp1.devilCode[i][j] );		
			}
		
		return  sBuff.toString();
	}
	
	//对二进制编码(bitStream)格式化后，返回新的字符串
	//standardDigits为bitStream的最低位数，如果其位数
	//小于standardDigits，在其高位补0.
	private static String formatBitStream(String bitStream,int standardDigits){
		int addedDigits = standardDigits - bitStream.length();
		String formattedString = new String(bitStream);
		for(int i = 0; i < addedDigits;i ++)
			//字符串不可变
			formattedString = '0' + formattedString;
		return formattedString;
	}
	
	//检测神兽所处空间内不被攻击的点（简易版）
	public void coverHit(){
		if(!petWaked)
			System.out.println("神兽尚未唤醒！");
		else{
			//只有神兽被唤醒了，petCode才有数据
			
			//宠物打印出来后，所占的行数与列数。都是从1开始。
			int numberOfRows,numberOfColumns;
			numberOfColumns = nextLineLoc(0) - 2;
			int sum = petWidth * petHeight;
			numberOfRows = sum / (numberOfColumns + 2) + 1;
					
			for(int count = 0; count < sum;count ++)
				if(getCodeValue(Type.pet,count) == cr || getCodeValue(Type.pet,count) == nl)
					//遇到格式符直接输出
					System.out.print((char)getCodeValue(Type.pet,count));
				else if(isEdgeRegion(count,numberOfRows,numberOfColumns))
					//遇到空间边缘输出‘*’
					System.out.print('*');
				else if(getCodeValue(Type.pet,count) == sp)
					//将宠物图案里的空格换成‘*’（简易处理）
					System.out.print('*');
				else
					//宠物图案里的其他符号按原样输出
					System.out.print((char)getCodeValue(Type.pet,count));				
		}		
	}
	
	//判断count在devilCode里所指向的字符是否在
	//恶魔图案的编码（格式符不在讨论范围内）
	private boolean isEdgeRegion(int count,int nOfR,int nOfC){
		//r,c表示count所指向的符号在宠物图案里
		//所占的行、列。（从1开始）
		int r,c;
		
		//每行实际需要nOfC + 2 个字符
		r = count  / ( nOfC + 2) + 1;
		c = count % (nOfC + 2) + 1;
		if(r == 1 || r == nOfR || c == 1 || c == nOfC)
			return true;
		
		return false;
	}
	
	//改变宠物当前水平朝向
	public void mirrorTrans(){
		if(!petWaked)
			System.out.println("神兽尚未唤醒！");
		else if(petHorizontalDirection == -1){
			//如果宠物现在是反向，改为正向
			//直接把宠物显示出来即可
			
			//显示宠物需要的总字符数
			int sum = petWidth * petHeight;
			
			//召唤神兽
			for(int c = 0;c < sum;c ++)
				System.out.print((char)getCodeValue(Type.pet,c));
			
			//另起一行，不至于影响后面的输出
			System.out.println();
			
			//此时宠物又变成正向
			petHorizontalDirection *= -1;
		}else{	
			//宠物正向时，将其改为反向
			int lengthOfLine = nextLineLoc(0) - 2 ;
			int origin = 0;
			
			//优先级：“！=”  >“=”,所以要加括号
			//此时优先级（在栈里的处理顺序）：“=”> “(”=“)”>“!=”
			while((origin =nextLineLoc(origin)) != -1 ){
				for(int i = origin - 3 ; i >= origin - 2 - lengthOfLine;i -- )
					//每一行内容都反向输出
					System.out.print((char)getCodeValue(Type.pet,i));
				//自己换行
				System.out.println();		
			}
			//最后一行的几个空格干脆不处理，直接换行
			System.out.println();
			//改变宠物当前方向
			petHorizontalDirection *= -1;
		}	
	}
	
	//以origin为起点,往后寻找下一行的第一个字符的位置
	//如果当前已是最后一行，返回-1
	private int nextLineLoc(int origin){
		int sum = petWidth * petHeight;
		for(int c = origin;c < sum;c ++){
			if(getCodeValue(Type.pet,c) == cr 
					&& getCodeValue(Type.pet,c + 1) == nl)
				return c + 2;				
		}
		return -1;
	}
	
	
	//召唤神兽（宠物）、显示恶魔的函数，
	//取决于type，这是因为它俩的封印方式相同
	//其中filename是存放宠物编码文件的路径
	public void showHexFile(String filename,Type type) throws IOException{
		setHexCode( filename, type);
		int height,width;
		if(type == Type.pet){
			petWaked = true;
			height = petHeight;
			width = petWidth;
		}else{
			height = devilHeight;
			width = devilWidth;
		}

	/*显示宠物/恶魔*/
		for(int i = 0; i < height;i ++)
			for(int j = 0;j < width;j ++){
				if(type == Type.pet)
					System.out.print((char)petCode[i][j]);
				else
					System.out.print((char)devilCode[i][j]);
			}
		System.out.println();		
	}
	
	//将宠物/恶魔编码从文件提取到矩阵中，取决于type
	//其中filename是存放相应编码文件的路径
	public  void setHexCode(String filename,Type type) throws IOException{
		int height,width;
		if(type == Type.pet){
			height = petHeight;
			width = petWidth;
		}else{
			height = devilHeight;
			width = devilWidth;
		}
		
		File file = new File(filename);
		Scanner input = new Scanner(file);
		
		//sum为显示神兽/恶魔所需要的字符总数
		int count = 0,sum = height * width;
		
		while(input.hasNextLine()){
			String line = input.nextLine();
			String[] codes = line.split(" ");
			
			for(int i = 0;i < codes.length &&count < sum;i ++){
				//需要防范可能的数组越界
				
				//将十六进制字符流转为10进制整数
				int value = Integer.parseInt(codes[i], 16);
				setCodeValue(type,count,value);
				
				count ++;
			}			
		}
		//关闭流
		input.close();
	}
	
	//设置宠物/恶魔编码矩阵在count位置（行主序）的值
	//取决于type
	private void setCodeValue(Type type,int count,int value){
		int i,j;
		if(type == Type.pet){
			i = count / petWidth;
			j = count % petWidth;
			petCode[i][j] = value;	
		}else{
			i = count / devilWidth;
			j = count % devilWidth;
			devilCode[i][j] = value;			
		}
	}
	
	//获取宠物/恶魔编码矩阵在count位置（行主序）的值
	//取决于type
	private int getCodeValue(Type type,int count){
		int i,j;
		if(type == Type.pet){
			i = count / petWidth;
			j = count % petWidth;
			return petCode[i][j];
		}else{
			i = count / devilWidth;
			j = count % devilWidth;
			return devilCode[i][j];
		}
	}
	
	//从文件（sb.txt）提取救世主信息到sbCode数组
	public  void setSbCode(String filename) throws IOException{
		File file = new File(filename);
		Scanner input = new Scanner(file);
		for(int i = 0;i < sbWH;i ++){
			String line = input.nextLine();
			String[] codes = line.split(" ");
			
			for(int j = 0;j < sbWH;j ++)
				sbCode[i][j] = Integer.parseInt(codes[j]);				
		}		
		input.close();
	}

	//打印救世主图案，由于涉及GBK转码，需要到DOS控制台输出
	public void printSb() throws UnsupportedEncodingException{
		int sum = sbWH * sbWH;
			for(int c = 0; c < sum;c ++){
				if(getSbCodeValue(c) < 0){
					//如果是GBK码
					byte[] bytesGBK = new byte[2];
					bytesGBK[0] = (byte)getSbCodeValue(c); 
					
					//顺便跳过上个刚刚读取的编码，接下来在本次for循环语句
					//结束后又会跳过下面这个将要读取的编码。
					bytesGBK[1] = (byte)getSbCodeValue(++c);
					
					//转为GBK字符集里的字符(解码)
					//可以用temp.getBytes(charsetName);	编码				
					String temp = new String(bytesGBK,"GBK");

					System.out.print(temp.charAt(0));
				}else{	
					//如果是ASCII码直接输出
					System.out.print((char)getSbCodeValue(c));
				}
			}
		
	}
	
	private int getSbCodeValue(int count){
		int i,j;
		i = count / sbWH;
		j = count % sbWH;
		return sbCode[i][j];
	}
		
}
