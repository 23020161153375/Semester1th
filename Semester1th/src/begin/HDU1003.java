package begin;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**�ֲ��㷨������n������Ԫ�صĳ��ִ�������ȡԪ�أ�ÿ��ͨ���õ�ǰ�ۼ�ֵ��ȥǰ�漸���õ�����С�ۼ�ֵ�õ���ǰ������Եõ�������Ӵ��ۼ�ֵ�����Ƚϸ����������Ӵ��ۼ�ֵ���Եõ������*/
public class HDU1003 {
	
	private static Scanner input =
			new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    	int nCases = input.nextInt();
        int nElemsForEachCase ;
         
        //��ǰ���еڼ�������
        int caseth = 0;
        
        //��ǰԪ��ֵ����ǰ�ۼӺ�ֵ
        int currentValue,currentSum;
        
        //��ǰ��С�ۼӺ�ֵ����ǰ��С�ۼӺ�ֵ����Ӧ����β���±꣨ͷ���±���Ϊ1,��
        int currentMinSum ,currentMinSumTailIndex;

        //��ǰ����ۻ���ֵ�����Ӧ����ͷ��β�±�
        int currentSubMaxSum,startPosition,endPosition;
        
        
        while(nCases -- != 0){
            caseth ++;
                      
            nElemsForEachCase = input.nextInt();
            
            currentValue = input.nextInt();
            
            currentSubMaxSum =  currentSum =currentValue;
            
            /*�õ�һ��Ԫ������ɳ�ʼ������*/
           int startPositionOffset  = 1;
           if(currentSum > 0){//��ʱ������һ����(index = 0,currentSum = 0)
        	   currentMinSum = 0;
               currentMinSumTailIndex = 0;
           }else if(currentSum == 0){
        	   currentMinSum = currentSum;
        	   currentMinSumTailIndex = 1;
        	   
        	   //һ�����offsetֵ��Ϊ1��ֻ�е���һ��Ԫ��Ϊ0ʱ
        	   //�Ž�����Ϊ0������Ϊ�˽���Ԫ�ذ������Ӵ���ȥ��
        	   startPositionOffset = 0;
           }else{
        	   currentMinSum = currentSum;
        	   currentMinSumTailIndex = 1;
           }
           
            startPosition = endPosition =  1;
                
            for(int i = 2;i <= nElemsForEachCase;i ++){
            	currentValue = input.nextInt();
            	currentSum += currentValue;
            	         	
         	
            	//currentMinSumΪ��1��i-1λ�ü�õ�����С�ۼ�ֵ
            	//������1��i��Χ�ڵġ�
            	//������ -1 -1���� i=2ʱ��currentSum - currentMinSum = 0�����Ǵ���ġ�
            	if(currentSum - currentMinSum > currentSubMaxSum){
            		currentSubMaxSum = currentSum - currentMinSum;
            		startPosition = currentMinSumTailIndex + startPositionOffset;            		
            		endPosition = i;
            	}
            	
            	if(currentSum < currentMinSum){
            		//�ǵø���currentMinSum
            		currentMinSum = currentSum;
            		currentMinSumTailIndex = i;
            		startPositionOffset = 1;//ÿ��currentMinSumTailIndex����һ��offsetֵ
            	}
            	
            }
            System.out.println("Case " + caseth +":");
            System.out.println(currentSubMaxSum
                    + " " + startPosition + " " + endPosition);
            
            if(nCases != 0)//ֻ���м��п��У���βû�п���
               System.out.println();         
        }
		input.close();
	}

}
