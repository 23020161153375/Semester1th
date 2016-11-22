package summerVacation;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import summerVacation.HuffmanCode.Tree;

public class Experiment1 {
	// ��ž������������Ŀ�/��
	public static int sbWH = 18;
	
	//�������������
	private int[][] sbCode = new int[sbWH][sbWH];
	
	//����������Ŀ���
	public static int petWidth = 46,petHeight = 22;
	
	//����������
	private int[][] petCode = new int[petHeight][petWidth];
	
	//�Ƿ��Ѿ��������ٻ�������
	//ֻ�н������ٻ������ˣ�����
	//����mirrorTrans��coverHit
	private boolean petWaked = false;
	
	//��ǰ����������ˮƽ����������֮��
	//1��������-1������
	private int petHorizontalDirection = 1;
	
	//�ֱ����س������С��Ϳո��
	//��������ASCII��ֵ
	private static int cr = 13,nl = 10 ,sp = 32;
	
	//��ħ�������Ŀ���
	public static int devilWidth = 55,devilHeight = 16;
	
	//��Ŷ�ħ����ľ���
	public int[][] devilCode = new int[devilHeight][devilWidth];
	
	//��ӡ��������ͣ�������Ƕ�ħ��
	public  static  enum Type{
		pet,devil
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//�ĸ��ļ�λ�÷ֱ��ǣ��������������ħ���·�ӡ��
		String sbFilename = "E:/Eclipse/xmuTasks/supplement/sb.txt";
		String petFilename = "E:/Eclipse/xmuTasks/supplement/pet.txt";
		String devilFilename = "E:/Eclipse/xmuTasks/supplement/devil.txt"; 
		String newSealFilename = "E:/Eclipse/xmuTasks/supplement/newSeal.dat"; 
		Experiment1 exp1 = new Experiment1();
		
		//����1��������
		//��Ҫ��DOS�������в��ܵõ�Ԥ�ڽ��
		//exp1.setSbCode(sbFilename);
		//exp1.printSb();	
		
		//����2��3���ٻ����ޣ���������
		exp1.showHexFile(petFilename,Type.pet);
		//for(int i = 0;i < 3;i ++)
			//exp1.mirrorTrans();
		
		//����4����ħ��Ϯ����������
		exp1.showHexFile(devilFilename, Type.devil);
		exp1.coverHit();

		//����5���úշ�������ӡ��ħ��
		//exp1.sealDevil(devilFilename, newSealFilename);
		//exp1.unsealDevil(newSealFilename);	
	}
	 
	public void unsealDevil(String filename) throws IOException{
		//�Լ�д��λ��ȡ��
		BitInputStream input = new BitInputStream(new File(filename));
		
		//��ȡ�ļ���һ���֣���ħͼ���е��ַ�����
		int charsKind = Integer.parseInt(input.read(),2);
		
		int[] counts = new int[256];
		for(int i = 0;i < charsKind;i ++ ){
			//����charsKindȡ���ļ��ڶ���������
			int charASCII = Integer.parseInt(input.read(),2);
			int charOccurrence = 
					Integer.parseInt(input.read() + input.read(),2);
			counts[charASCII] = charOccurrence;
		}
		
		//���ݵڶ����ֵ���Ƶ�õ��շ�����
		HuffmanCode.Tree tree = HuffmanCode.getHuffmanTree(counts);	
		
		//��Ҫ���˼���շ�����������
		 HuffmanCode.getCode(tree.root);

		 //��ȡ���루�ļ������������ݣ�
		StringBuilder sBuffer = new StringBuilder();
		while(!input.isEnd())
			sBuffer.append(input.read());
		
		//�ر���
		input.close();
		
		char[] bitCode = sBuffer.toString().toCharArray();
		
		//����
		char[] devilCodes = HuffmanCode.searchHuffmanTree(bitCode, tree);
		
		//��ʾ��ħ
		for(int i = 0;i < devilCodes.length;i ++)
			System.out.print(devilCodes[i]);	
			
	}
	
	//����ħ��ӡ���µ�λ�ã�newFilename��,�������
	//�շ������롣���ַ�������ŵ�һ���������ļ��С�
	//����������ļ���������������Ϣ��ɣ�
	//һ����һ���ֽڴ洢��ħͼ���е��ַ����ࡣ
	//�����洢�ַ�ASCII�루1B���Լ�����ִ�����2B�������ǹ���һ�ԡ�
	//	    ��һ�����Ѿ���¼�˶�����
	//�����շ������롣
	public void sealDevil(String oldFilename,String newFilename) throws IOException{
		//��ȡ��ħͼƬ�������ַ�
		String devilString = getDevilString(oldFilename);
		
		//��¼devilString�и��ַ����ֵĴ���
		int[] counts = HuffmanCode.getCharacterFrequency(devilString);

		//����counts�õ��շ���������������ַ�����
		HuffmanCode.Tree tree = HuffmanCode.getHuffmanTree(counts);
		String[] codes = HuffmanCode.getCode(tree.root);
		
		//�Լ�д��λ�����������Ϣд��������ļ�
		BitOutputStream output = new BitOutputStream(new File(newFilename));
		
		//��ħͼ���е��ַ�����
		byte charsKind = 0;
		for(int i = 0;i < counts.length;i ++)
			if(counts[i] > 0)
				charsKind ++;
		
		//���صĶ����ƴ�λ�����̶����������С���
		String charsKindBits = Integer.toBinaryString(charsKind);
		
		//��ʽ����д���ļ�����һ�������ݣ�
		output.writeBit(formatBitStream(charsKindBits,8));
		
		//д�롰�ַ�ASCII�롪�����ִ������ԣ��ڶ��������ݣ�
		for(int i = 0;i < counts.length;i ++)
			if(counts[i] > 0){
				String charBits = Integer.toBinaryString(i);
				output.writeBit(formatBitStream(charBits,8));
				//�ַ���Ŀ
				String charCountBits = Integer.toBinaryString(counts[i]);
				output.writeBit(formatBitStream(charCountBits,16));
			}
		
		//д��շ������루�����������ݣ�
		for(int i = 0;i < devilString.length();i ++){
				int charASCII = (int)devilString.charAt(i);
				output.writeBit(codes[charASCII]);
			} 
		
		//�ر���
		output.close();		
	}
	
	//ֱ�Ӵ��ı��ļ���devil.txt���ж�ȡ�ַ���
	//����ַ��������ظô���
	public static  String getDevilString(String filename) throws IOException{		
		Experiment1 exp1 = new Experiment1();
		
		//�ַ�����ŵ�devilCode����
		exp1.setHexCode(filename, Experiment1.Type.devil);
		
		StringBuilder sBuff = new StringBuilder();
		for(int i = 0;i < exp1.devilCode.length;i ++)
			for(int j = 0;j < exp1.devilCode[0].length;j ++){
					sBuff.append((char)exp1.devilCode[i][j] );		
			}
		
		return  sBuff.toString();
	}
	
	//�Զ����Ʊ���(bitStream)��ʽ���󣬷����µ��ַ���
	//standardDigitsΪbitStream�����λ���������λ��
	//С��standardDigits�������λ��0.
	private static String formatBitStream(String bitStream,int standardDigits){
		int addedDigits = standardDigits - bitStream.length();
		String formattedString = new String(bitStream);
		for(int i = 0; i < addedDigits;i ++)
			//�ַ������ɱ�
			formattedString = '0' + formattedString;
		return formattedString;
	}
	
	//������������ռ��ڲ��������ĵ㣨���װ棩
	public void coverHit(){
		if(!petWaked)
			System.out.println("������δ���ѣ�");
		else{
			//ֻ�����ޱ������ˣ�petCode��������
			
			//�����ӡ��������ռ�����������������Ǵ�1��ʼ��
			int numberOfRows,numberOfColumns;
			numberOfColumns = nextLineLoc(0) - 2;
			int sum = petWidth * petHeight;
			numberOfRows = sum / (numberOfColumns + 2) + 1;
					
			for(int count = 0; count < sum;count ++)
				if(getCodeValue(Type.pet,count) == cr || getCodeValue(Type.pet,count) == nl)
					//������ʽ��ֱ�����
					System.out.print((char)getCodeValue(Type.pet,count));
				else if(isEdgeRegion(count,numberOfRows,numberOfColumns))
					//�����ռ��Ե�����*��
					System.out.print('*');
				else if(getCodeValue(Type.pet,count) == sp)
					//������ͼ����Ŀո񻻳ɡ�*�������״���
					System.out.print('*');
				else
					//����ͼ������������Ű�ԭ�����
					System.out.print((char)getCodeValue(Type.pet,count));				
		}		
	}
	
	//�ж�count��devilCode����ָ����ַ��Ƿ���
	//��ħͼ���ı��루��ʽ���������۷�Χ�ڣ�
	private boolean isEdgeRegion(int count,int nOfR,int nOfC){
		//r,c��ʾcount��ָ��ķ����ڳ���ͼ����
		//��ռ���С��С�����1��ʼ��
		int r,c;
		
		//ÿ��ʵ����ҪnOfC + 2 ���ַ�
		r = count  / ( nOfC + 2) + 1;
		c = count % (nOfC + 2) + 1;
		if(r == 1 || r == nOfR || c == 1 || c == nOfC)
			return true;
		
		return false;
	}
	
	//�ı���ﵱǰˮƽ����
	public void mirrorTrans(){
		if(!petWaked)
			System.out.println("������δ���ѣ�");
		else if(petHorizontalDirection == -1){
			//������������Ƿ��򣬸�Ϊ����
			//ֱ�Ӱѳ�����ʾ��������
			
			//��ʾ������Ҫ�����ַ���
			int sum = petWidth * petHeight;
			
			//�ٻ�����
			for(int c = 0;c < sum;c ++)
				System.out.print((char)getCodeValue(Type.pet,c));
			
			//����һ�У�������Ӱ���������
			System.out.println();
			
			//��ʱ�����ֱ������
			petHorizontalDirection *= -1;
		}else{	
			//��������ʱ�������Ϊ����
			int lengthOfLine = nextLineLoc(0) - 2 ;
			int origin = 0;
			
			//���ȼ�������=��  >��=��,����Ҫ������
			//��ʱ���ȼ�����ջ��Ĵ���˳�򣩣���=��> ��(��=��)��>��!=��
			while((origin =nextLineLoc(origin)) != -1 ){
				for(int i = origin - 3 ; i >= origin - 2 - lengthOfLine;i -- )
					//ÿһ�����ݶ��������
					System.out.print((char)getCodeValue(Type.pet,i));
				//�Լ�����
				System.out.println();		
			}
			//���һ�еļ����ո�ɴ಻����ֱ�ӻ���
			System.out.println();
			//�ı���ﵱǰ����
			petHorizontalDirection *= -1;
		}	
	}
	
	//��originΪ���,����Ѱ����һ�еĵ�һ���ַ���λ��
	//�����ǰ�������һ�У�����-1
	private int nextLineLoc(int origin){
		int sum = petWidth * petHeight;
		for(int c = origin;c < sum;c ++){
			if(getCodeValue(Type.pet,c) == cr 
					&& getCodeValue(Type.pet,c + 1) == nl)
				return c + 2;				
		}
		return -1;
	}
	
	
	//�ٻ����ޣ��������ʾ��ħ�ĺ�����
	//ȡ����type��������Ϊ�����ķ�ӡ��ʽ��ͬ
	//����filename�Ǵ�ų�������ļ���·��
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

	/*��ʾ����/��ħ*/
		for(int i = 0; i < height;i ++)
			for(int j = 0;j < width;j ++){
				if(type == Type.pet)
					System.out.print((char)petCode[i][j]);
				else
					System.out.print((char)devilCode[i][j]);
			}
		System.out.println();		
	}
	
	//������/��ħ������ļ���ȡ�������У�ȡ����type
	//����filename�Ǵ����Ӧ�����ļ���·��
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
		
		//sumΪ��ʾ����/��ħ����Ҫ���ַ�����
		int count = 0,sum = height * width;
		
		while(input.hasNextLine()){
			String line = input.nextLine();
			String[] codes = line.split(" ");
			
			for(int i = 0;i < codes.length &&count < sum;i ++){
				//��Ҫ�������ܵ�����Խ��
				
				//��ʮ�������ַ���תΪ10��������
				int value = Integer.parseInt(codes[i], 16);
				setCodeValue(type,count,value);
				
				count ++;
			}			
		}
		//�ر���
		input.close();
	}
	
	//���ó���/��ħ���������countλ�ã������򣩵�ֵ
	//ȡ����type
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
	
	//��ȡ����/��ħ���������countλ�ã������򣩵�ֵ
	//ȡ����type
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
	
	//���ļ���sb.txt����ȡ��������Ϣ��sbCode����
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

	//��ӡ������ͼ���������漰GBKת�룬��Ҫ��DOS����̨���
	public void printSb() throws UnsupportedEncodingException{
		int sum = sbWH * sbWH;
			for(int c = 0; c < sum;c ++){
				if(getSbCodeValue(c) < 0){
					//�����GBK��
					byte[] bytesGBK = new byte[2];
					bytesGBK[0] = (byte)getSbCodeValue(c); 
					
					//˳�������ϸ��ոն�ȡ�ı��룬�������ڱ���forѭ�����
					//�������ֻ��������������Ҫ��ȡ�ı��롣
					bytesGBK[1] = (byte)getSbCodeValue(++c);
					
					//תΪGBK�ַ�������ַ�(����)
					//������temp.getBytes(charsetName);	����				
					String temp = new String(bytesGBK,"GBK");

					System.out.print(temp.charAt(0));
				}else{	
					//�����ASCII��ֱ�����
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
