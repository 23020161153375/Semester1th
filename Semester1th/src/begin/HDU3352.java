package begin;

import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedInputStream; 

/**
 * <p>�Էֲ���������⡣�ֲ�Ҫ�㣺�Ƚ�Ԫ�ذ�����A�Ĵ�С�������С�����ѡȡһ��Ԫ�أ�������ʱ��Ԫ�ص�A�Ǽ���1������Ԫ���е����Aֵ��</p>
 **@author YTQ
 * */
public class HDU3352 {
     
    private static Elem[] elements;
     
    private static Scanner input = new Scanner(new BufferedInputStream(System.in));
     
    public static void main(String[] args) {
        // TODO Auto-generated method stub
     //�������ӵ���Ŀ
    	int nCases = input.nextInt();
        int nElemsForEachCase ;
         
        //��ǰ���еڼ�������
        int caseth = 0;
        while(nCases -- != 0){
            caseth ++;
            
            //��ǰ���ӵ�Ԫ����Ŀ
            nElemsForEachCase = input.nextInt();
            elements = new Elem[nElemsForEachCase];
             
            /*��ʼ��*/
            for(int i = 0;i < nElemsForEachCase;i ++){
                int a = input.nextInt();
                int b= input.nextInt();
                elements[i] = new Elem(a,b);            
            }
             
            //������A��С��������
            Arrays.sort(elements);
            
            /*minAnswerָ���ս������Сֵ
             * maxB,maxAָ��ǰ����������Ԫ�ض�Ӧ���Ե����ֵ*/
            int minAnswer,maxB,maxA;
            
            //�ڶ�Ӧelement[0]��һ��ʱ���õ�
            //��һ��������������
            int tempB = -1;
            
            minAnswer = Integer.MAX_VALUE; 
            
            maxB = elements[0].b;
            for(int i = 1;i < elements.length;i ++){
                maxA = elements[i].a;
                
                //������Сֵ�����ϲ�������������
                if(elements[i - 1].b > maxB ){
                	maxB = elements[i - 1].b;
                      if(maxA + maxB < minAnswer)
                    	  minAnswer = maxA + maxB;                	
                }else if(elements[i - 1].b == maxB){
                	//��ʱmaxA + maxB < minAnswer
                	minAnswer = maxA + maxB;                	
                }else{//���elments[0].b������Сֵ
                	//��tempB��¼elements[1...n-2].b����Сֵ
                	tempB = elements[i - 1].b;
                	minAnswer = maxA + maxB;                	
                }
            }
            
            //����element[0]��Ӧ����һ��
            if(elements[0].b > elements[nElemsForEachCase - 1].b || tempB != -1){
            	//���elements[0].b������Сֵ
            	int minBInSet2 = Math.min(elements[nElemsForEachCase - 1].b, tempB);
            	minAnswer = Math.min(minAnswer, elements[0].a + minBInSet2);            	
            }                    
            System.out.println("Case "+ caseth + ": " + minAnswer);             
        }      
       input.close();
 
    }
     
    private static class Elem implements Comparable<Elem>{
        private int a,b;
         
        public Elem(int a,int b){
            this.a = a;
            this.b = b;
        }
 
        /**�Խ���Ϊ��Ȼ��*/
        @Override
        public int compareTo(Elem o) {
            // TODO Auto-generated method stub
            //�����������Ԫ��λ��
            int offset =  o.a - a;
             
            return offset == 0 ? 0 : offset / Math.abs(offset);
        }
    }
     
}