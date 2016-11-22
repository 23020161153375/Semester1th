package begin;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**分步算法。共分n步。按元素的出现次序依次取元素，每次通过用当前累加值减去前面几步得到的最小累加值得到当前步骤可以得到的最大子串累加值。最后比较各步骤的最大子串累加值可以得到结果。*/
public class HDU1003 {
	
	private static Scanner input =
			new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    	int nCases = input.nextInt();
        int nElemsForEachCase ;
         
        //当前进行第几个例子
        int caseth = 0;
        
        //当前元素值、当前累加和值
        int currentValue,currentSum;
        
        //当前最小累加和值、当前最小累加和值所对应串的尾部下标（头部下标总为1,）
        int currentMinSum ,currentMinSumTailIndex;

        //当前最大累积和值；其对应串的头、尾下标
        int currentSubMaxSum,startPosition,endPosition;
        
        
        while(nCases -- != 0){
            caseth ++;
                      
            nElemsForEachCase = input.nextInt();
            
            currentValue = input.nextInt();
            
            currentSubMaxSum =  currentSum =currentValue;
            
            /*用第一个元素来完成初始化工作*/
           int startPositionOffset  = 1;
           if(currentSum > 0){//这时假想有一个点(index = 0,currentSum = 0)
        	   currentMinSum = 0;
               currentMinSumTailIndex = 0;
           }else if(currentSum == 0){
        	   currentMinSum = currentSum;
        	   currentMinSumTailIndex = 1;
        	   
        	   //一般这个offset值都为1，只有当第一个元素为0时
        	   //才将它设为0，这是为了将该元素包含到子串里去。
        	   startPositionOffset = 0;
           }else{
        	   currentMinSum = currentSum;
        	   currentMinSumTailIndex = 1;
           }
           
            startPosition = endPosition =  1;
                
            for(int i = 2;i <= nElemsForEachCase;i ++){
            	currentValue = input.nextInt();
            	currentSum += currentValue;
            	         	
         	
            	//currentMinSum为第1到i-1位置间得到的最小累加值
            	//而不是1到i范围内的。
            	//反例： -1 -1。当 i=2时，currentSum - currentMinSum = 0，这是错误的。
            	if(currentSum - currentMinSum > currentSubMaxSum){
            		currentSubMaxSum = currentSum - currentMinSum;
            		startPosition = currentMinSumTailIndex + startPositionOffset;            		
            		endPosition = i;
            	}
            	
            	if(currentSum < currentMinSum){
            		//记得更新currentMinSum
            		currentMinSum = currentSum;
            		currentMinSumTailIndex = i;
            		startPositionOffset = 1;//每个currentMinSumTailIndex都有一个offset值
            	}
            	
            }
            System.out.println("Case " + caseth +":");
            System.out.println(currentSubMaxSum
                    + " " + startPosition + " " + endPosition);
            
            if(nCases != 0)//只有中间有空行，结尾没有空行
               System.out.println();         
        }
		input.close();
	}

}
