package summerVacation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Experiment2 {
	private final static String[] MONTH_NAME = {
		"January","February"	,"March","April","May","June",
		"July","August","September","October","November","December"
	};
	private final static String[] DAY_NAME = {
		"first","second","third","fourth","fifth","sixth","seventh",
		"eighth","ninth","tenth","eleventh","tweulfth","thirteenth",
		"fourteenth","fifteenth","sixteenth","seventeenth",
		"eighteenth","ninteenth","twentieth","twenty-first","twenty-second","twenty-third",
		"twenty-fourth","twenty-fifth","twenty-sixth","twenty-seventh",
		"twenty-eighth","twenty-ninth","tirtieth","thirty-first"
	};

	//����ת����־�ĵ�ַ
	private static String filename = "E:/Eclipse/xmuTasks/supplement/out.txt";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//char[] cTest = {'a','b','c'};
		//abc ��׼�����Ϊ�ַ�������������
		//System.out.println(cTest);
		
		//��������в����Ƿ����
		if(args.length == 0){
			//System.out.println("Usage: java processPath textToBeReversed");
			System.out.println("Usage: java processPath textToBeTransformed");
			
			//�˳�
			System.exit(0);
		}
		
		//��������֯����
		String txt = "";
		for(int i = 0;i < args.length;i ++)
			txt += args[i] + ' ';
		
		//ȥ�����һ���ַ�������Ŀո�
		txt = txt.substring(0, txt.length() - 1);
		
		//����һ
		//System.out.println(reverse(txt));
		
		//���������
		dateTransform(txt);		
	}
	
	public static void dateTransform(String s) throws IOException{
		
		/*����ļ��Ѿ����ڣ��������ݷŵ�oldFileContent��
		 * ���ԡ�*��Ϊ�ָ�����
		 */
		File file = new File(filename);		
		StringBuilder oldFileContent = new StringBuilder();
		
		if(file.exists()){
			//����inputҪ��һ��ʼfile�������
			Scanner input = new Scanner(file);
			while(input.hasNextLine())
				oldFileContent.append(input.nextLine() + '*');	
		
			//�ر�������
			input.close();
		}
		

		
		/*����oldFileContent��Դ�ļ����ݸ��Ƶ����ļ���
		 * ֮��������������Ϊֱ��ʹ���ַ���������ܻὫ�ļ�
		 * ԭ�е����ݸ��ǵ�*/	
		java.io.PrintWriter output = new java.io.PrintWriter(file);
		
		String oldContent = oldFileContent.toString();
		if(oldContent.length() != 0){
			//����ַ�����Ϊ��
			//��Ӧ���á�oldContent != ""��
			//���ǱȽϵ�ַ�ģ�Ϊtrue
			
			//Regex:һ����*���ַ�
			String[] items = oldContent.split("\\*");
			for(int i = 0; i < items.length;i ++)
				output.println(items[i]);
		}
		
		/*���ڿ��Խ��µ��޸���־�ӵ����ļ�ĩβ�� */
		//Regex: һ����һ�����������Ŀհ׷�
		String[] dates = s.split("\\s+");
				
		for(int i = 0;i < dates.length;i ++)
			if(dates[i].length() != 0){
				//����
				
				//ȡ����
				//�����������в������ԡ�mm.dd������ʽ����
				StringTokenizer st = new StringTokenizer(dates[i],".");
				int mm = Integer.parseInt(st.nextToken());
				int dd = Integer.parseInt(st.nextToken());
				
				System.out.println(getEnglishDate(mm,dd));
				output.println(getEnglishDate(mm,dd));
			}
		//�ر������
		output.close();		
	}
	
	private static String getEnglishDate(int mm,int dd){
		String englishDate = "";
		if(mm < 1 || mm > 12)
			englishDate = "Error";
		else if(dd > getAmountOfDayInAMonth(mm) || dd < 1)
			//�����ǰ�·�û����һ��
			englishDate = "Error";
		else
			englishDate = MONTH_NAME[mm - 1] + " the " + DAY_NAME[dd - 1];
					
		return englishDate;
	}
	
	//��ƽ��ƣ���ȡĳ�¶�Ӧ������
	private static int getAmountOfDayInAMonth(int month){
		int amountOfDayInAMonth = 0;
		switch(month){
			case 1:case 3:case 5:
			case 7:case 8:case 10:
			case 12:amountOfDayInAMonth = 31;
							break;
			//ƽ��2����28��				
			case 2:amountOfDayInAMonth = 28;
							break;
			case 4:case 6:case 9:
			case 11:amountOfDayInAMonth = 30;
							break;
		}
		return amountOfDayInAMonth;
	}

	//���ַ������ݵ��ò�����
	public static String reverse(String s){		
		char[] charArray = new char[s.length()];
		for(int i = 0; i < charArray.length;i ++)
			charArray[i] = s.charAt(charArray.length - 1 - i);
		
		//���ﲻҪ��charArray.toString()
		//����Ϊ�����ַ
		return new String(charArray);		
	}
}
