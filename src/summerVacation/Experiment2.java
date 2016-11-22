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

	//日期转换日志的地址
	private static String filename = "E:/Eclipse/xmuTasks/supplement/out.txt";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//char[] cTest = {'a','b','c'};
		//abc 标准输出流为字符数组做了重载
		//System.out.println(cTest);
		
		//检查命令行参数是否存在
		if(args.length == 0){
			//System.out.println("Usage: java processPath textToBeReversed");
			System.out.println("Usage: java processPath textToBeTransformed");
			
			//退出
			System.exit(0);
		}
		
		//将参数组织起来
		String txt = "";
		for(int i = 0;i < args.length;i ++)
			txt += args[i] + ' ';
		
		//去掉最后一个字符，多余的空格
		txt = txt.substring(0, txt.length() - 1);
		
		//任务一
		//System.out.println(reverse(txt));
		
		//任务二、三
		dateTransform(txt);		
	}
	
	public static void dateTransform(String s) throws IOException{
		
		/*如果文件已经存在，将其内容放到oldFileContent中
		 * 并以“*”为分隔符。
		 */
		File file = new File(filename);		
		StringBuilder oldFileContent = new StringBuilder();
		
		if(file.exists()){
			//创建input要求一开始file必须存在
			Scanner input = new Scanner(file);
			while(input.hasNextLine())
				oldFileContent.append(input.nextLine() + '*');	
		
			//关闭输入流
			input.close();
		}
		

		
		/*利用oldFileContent将源文件内容复制到新文件中
		 * 之所以这样做是因为直接使用字符输出流可能会将文件
		 * 原有的内容覆盖掉*/	
		java.io.PrintWriter output = new java.io.PrintWriter(file);
		
		String oldContent = oldFileContent.toString();
		if(oldContent.length() != 0){
			//如果字符串不为空
			//不应该用“oldContent != ""”
			//这是比较地址的，为true
			
			//Regex:一个“*”字符
			String[] items = oldContent.split("\\*");
			for(int i = 0; i < items.length;i ++)
				output.println(items[i]);
		}
		
		/*现在可以将新的修改日志加到新文件末尾了 */
		//Regex: 一个及一个以上连续的空白符
		String[] dates = s.split("\\s+");
				
		for(int i = 0;i < dates.length;i ++)
			if(dates[i].length() != 0){
				//不空
				
				//取日期
				//日期在命令行参数中以“mm.dd”的形式出现
				StringTokenizer st = new StringTokenizer(dates[i],".");
				int mm = Integer.parseInt(st.nextToken());
				int dd = Integer.parseInt(st.nextToken());
				
				System.out.println(getEnglishDate(mm,dd));
				output.println(getEnglishDate(mm,dd));
			}
		//关闭输出流
		output.close();		
	}
	
	private static String getEnglishDate(int mm,int dd){
		String englishDate = "";
		if(mm < 1 || mm > 12)
			englishDate = "Error";
		else if(dd > getAmountOfDayInAMonth(mm) || dd < 1)
			//如果当前月份没有这一天
			englishDate = "Error";
		else
			englishDate = MONTH_NAME[mm - 1] + " the " + DAY_NAME[dd - 1];
					
		return englishDate;
	}
	
	//按平年计，获取某月对应的天数
	private static int getAmountOfDayInAMonth(int month){
		int amountOfDayInAMonth = 0;
		switch(month){
			case 1:case 3:case 5:
			case 7:case 8:case 10:
			case 12:amountOfDayInAMonth = 31;
							break;
			//平年2月有28天				
			case 2:amountOfDayInAMonth = 28;
							break;
			case 4:case 6:case 9:
			case 11:amountOfDayInAMonth = 30;
							break;
		}
		return amountOfDayInAMonth;
	}

	//将字符串内容倒置并返回
	public static String reverse(String s){		
		char[] charArray = new char[s.length()];
		for(int i = 0; i < charArray.length;i ++)
			charArray[i] = s.charAt(charArray.length - 1 - i);
		
		//这里不要用charArray.toString()
		//上面为数组地址
		return new String(charArray);		
	}
}
