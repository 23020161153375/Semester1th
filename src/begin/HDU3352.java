package begin;

import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedInputStream; 

/**
 * <p>以分步法解决问题。分步要点：先将元素按属性A的大小降序排列。依次选取一个元素，表明此时该元素的A是集合1中所有元素中的最大A值。</p>
 **@author YTQ
 * */
public class HDU3352 {
     
    private static Elem[] elements;
     
    private static Scanner input = new Scanner(new BufferedInputStream(System.in));
     
    public static void main(String[] args) {
        // TODO Auto-generated method stub
     //测试例子的数目
    	int nCases = input.nextInt();
        int nElemsForEachCase ;
         
        //当前进行第几个例子
        int caseth = 0;
        while(nCases -- != 0){
            caseth ++;
            
            //当前例子的元素数目
            nElemsForEachCase = input.nextInt();
            elements = new Elem[nElemsForEachCase];
             
            /*初始化*/
            for(int i = 0;i < nElemsForEachCase;i ++){
                int a = input.nextInt();
                int b= input.nextInt();
                elements[i] = new Elem(a,b);            
            }
             
            //按属性A大小降序排序
            Arrays.sort(elements);
            
            /*minAnswer指最终结果的最小值
             * maxB,maxA指当前集合中所有元素对应属性的最大值*/
            int minAnswer,maxB,maxA;
            
            //在对应element[0]那一步时将用到
            //这一步被放在最后完成
            int tempB = -1;
            
            minAnswer = Integer.MAX_VALUE; 
            
            maxB = elements[0].b;
            for(int i = 1;i < elements.length;i ++){
                maxA = elements[i].a;
                
                //将找最小值操作合并到这里面来了
                if(elements[i - 1].b > maxB ){
                	maxB = elements[i - 1].b;
                      if(maxA + maxB < minAnswer)
                    	  minAnswer = maxA + maxB;                	
                }else if(elements[i - 1].b == maxB){
                	//此时maxA + maxB < minAnswer
                	minAnswer = maxA + maxB;                	
                }else{//如果elments[0].b不是最小值
                	//用tempB记录elements[1...n-2].b的最小值
                	tempB = elements[i - 1].b;
                	minAnswer = maxA + maxB;                	
                }
            }
            
            //处理element[0]对应的那一步
            if(elements[0].b > elements[nElemsForEachCase - 1].b || tempB != -1){
            	//如果elements[0].b不是最小值
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
 
        /**以降序为自然序*/
        @Override
        public int compareTo(Elem o) {
            // TODO Auto-generated method stub
            //故意调换两个元素位置
            int offset =  o.a - a;
             
            return offset == 0 ? 0 : offset / Math.abs(offset);
        }
    }
     
}