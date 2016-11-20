package summerVacation;

public class DigitalSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
         System.out.println("Hex->Decimal\n"+"  "+"F->"+hexToDecimal("F")+
        		 " A09F->"+hexToDecimal("A09F")+" 0C2->"+hexToDecimal("0C2"));
         System.out.println("Decimal->Binary\n"+"  0->"+decimalToBinary(0)+" 1->"+
        		 decimalToBinary(1)+" 2->"+decimalToBinary(2)+" 47->"+decimalToBinary(47));
		System.out.println("Binary->Hex\n"+"  001->"+binaryToHex("001")+" 1->"+binaryToHex("1")+
				                         " 1100->"+binaryToHex("1100")+" 100010011->"+binaryToHex("100010011"));
       //  System.out.println("Is there a eternal Circle?");
	}
	
	public static int hexToDecimal(String hex){
		int decimalValue = 0;
		for(int i = 0 ;i < hex.length();i ++){
			char hexChar = hex.charAt(i);
			decimalValue = hexToDecimal(hexChar) + decimalValue * 16;
		}
		return decimalValue;
	}
	public static int hexToDecimal(char c){
		if(c >= 'A' && c <= 'F')
			return c - 'A' +10;
		else
			return c - '0';
	}

	public static int binaryToDecimal(String binary){
		int binaryValue = 0;
		for(int i = 0;i < binary.length();i ++){
			char binaryChar = binary.charAt(i);
			binaryValue = binaryValue * 2 + binaryChar - '0';
		}
		return binaryValue;
	}
	
	public static String binaryToHex(String binary){
		String hex = "";
		char[] binaryGroup = new char[4];
		int count = binary.length() / 4;
		for(int i = 0;i < count;i ++){
			for(int j = 0; j < 4;j ++){
				binaryGroup[3-j] = binary.charAt(binary.length() - 1 - i * 4 - j);
			}
			hex = hex + groupBinaryToHex(binaryGroup);
		}
		if(binary.length() % 4 != 0){
             for(int m = 0 ; m < binary.length() % 4;m++){
            	 binaryGroup[3-m] = binary.charAt(binary.length() - count * 4 - 1 - m);
             }
             for(int n = 0; n< 4 - binary.length() % 4; n++){
            	 binaryGroup[n] = '0';
             }
             hex = hex +groupBinaryToHex(binaryGroup);
		}
		return hex;
	}
	
	private static char groupBinaryToHex(char[] groupBinary){
		int decimalValue = (groupBinary[0] - '0') * 8 + (groupBinary[1] - '0') * 4 +(groupBinary[2] - '0') * 2
				   + groupBinary[3] - '0';
		char hexChar = decimalValue >9 && decimalValue <16 ? (char)(decimalValue - 10 + 65)
				                                                                                            :(char)(decimalValue + 48);
		return hexChar;
	}
	
	/**将十进制正整数转换成二进制
	 * 
	 * @param value 十进制正整数
	 * @return value对应二进制数
	 */
	public static String decimalToBinary(int value){

		String binary = "";
		int q,r;
         do{
        	 q = value / 2;
        	 r = value % 2;
        	 char bit = (r==0 ? '0':'1');
        	 binary = bit + binary ;
        	 value = q;
         }while(q != 0);
		return binary;		
	}
	
	
	
	/**
	 *  将十进制正整数转换成二进制数，并
	 *给出最小位数，如果二进制数位数不足
	 *最小位数，将在其高位补0
	 * @param value 十进制正整数
	 * @param size	二进制数最小位数
	 * @return 以字符串形式表示的二进制数
	 */
	public static String decimalToBinary(int value,int size){
		String originalBits = decimalToBinary(value);
		int times = size - originalBits.length();
		StringBuilder sBuilder = new StringBuilder();
		while(times -- > 0)
			sBuilder.append('0');
		sBuilder.append(originalBits);
		return sBuilder.toString();
	}
	
	 
}
