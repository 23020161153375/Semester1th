package begin;

import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class HDU1004 {

	private static Scanner input = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int nElemsForEachCase;
		String balloon;
		HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
		while(input.hasNext()){
            nElemsForEachCase = input.nextInt();
            if(nElemsForEachCase == 0)
            	break;
            
			//input.nextLine();
			while(nElemsForEachCase -- != 0){
				//balloon = input.nextLine();
				balloon = input.next();
                if(hashMap.containsKey(balloon)){
					//���������ɫ�������Ѵ���
					int value = hashMap.get(balloon);
					value++;
					hashMap.put(balloon, value);
				}else{//���������ɫ�����򻹲�����
					hashMap.put(balloon,1);				
				}
			}
			

            Set<Map.Entry<String,Integer>> entrySet = hashMap.entrySet();
            int currentCount = 0;
            String resultBalloon = "";
            for(Map.Entry<String,Integer> currentEntry : entrySet){
                if(currentEntry.getValue() > currentCount){
                    resultBalloon = currentEntry.getKey();
					currentCount = currentEntry.getValue();
                }
            }                    
			System.out.println(resultBalloon);			
			//�ǵ���գ�����
			hashMap.clear();
		}
		input.close();		
	}
}
