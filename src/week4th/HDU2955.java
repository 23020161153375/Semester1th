package week4th;

import java.io.BufferedInputStream;
import java.util.Scanner;


/**̰�ķ����"0-1"�������⡣���У����õ�Ǯ����ֵ������ϵ����Ȩ��
 * ��Ҫ������
 * <p> 1.��0��sum�����ռ��ϵ����ط�Χ����Ҫ������������sumΪ�������еĴ����ܶ</p>
 * <p>2.�����ⲻ��Ҫ���򣬶���ʹ�ü���S(����)��������˳��ÿ�ν�һ������(��Ʒ)i�������ۣ�������������������
 * �ǽ����ڶ�ǰ�� i-1 �����У���Ʒ��������֮�ϵġ��ڱ��ε��������󣬿�����ɶ�ǰi����Ʒ���뱳������������ȫ����Ϳհ�������������ۡ�</p>
 * <p>3. ��ʵ��0��sun�������ռ�����3��㣬�ֱ��� a �޽���� b �н����(���Զ�Ӧ������Ʒ��Ϸ���)  c �յ㣨������ֵ������
 * ���У���Ӧ b ��㣬ȡȨֵ��С���������Ӧ c ��㣬����֤�����ڼ���Ĺ�����������a��b��㷢��������������������a��b��㣬a��b���Ҳ������������</p>
 * <p>4.֮ǰ�ڼ���ʱ�г����п�����������ֻҪ��ֵ�ɸߵ������α�����ѡ����һ������Ҫ���Ȩ���ɡ�</p>*/
public class HDU2955 {
	//������ʱ�İ�ȫ����
	private static double[] secureProbabilities = new double[10001];
	
	//����Ʒ��
	private static Item[] items ;
	
	private static  Scanner input = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//����������Ŀ��ÿ����������Ԫ�ظ���
		int cases,elemsForEachCase;
		
		//���������ȫ���ߣ�Ȩֵ���ߣ�
		double absoluteSecureProbability;
		cases  = input.nextInt();
		while(cases -- != 0){
			//��ʼ��
			absoluteSecureProbability =1 - input.nextDouble();
			elemsForEachCase = input.nextInt();
			items = new Item[elemsForEachCase];
			
			int sum = 0;
			for(int i = 0;i < items.length;i ++){
				int money = input.nextInt();
				double prob = input.nextDouble();
				items[i] =  new Item(money,prob);		
				sum += money;
			}
			
			//�ǵý���������
			//ע�⵽�յ��Ȩ������ϵ����Ϊ1
			reset(secureProbabilities);
			secureProbabilities[0] = 1;
			for(int i = 0;i < items.length;i ++)
				for(int j  = sum;j >= items[i].money;j --)
					//Ӧ�Ӵ�С����j����Ϊ�ŵ�i����Ʒ��������
					//��ȫ��ֻ�ܣ������ڶ�ǰi-1����Ʒ������֮��
					
					//����������ֿ��ܵ���ϵõ�ͬ����Ȩֵ��ȡ��С��Ȩֵ
					//Ȩ�� weight = 1 - secureProbabilities
					secureProbabilities[j] = Math.max(secureProbabilities[j],
							secureProbabilities[j - items[i].money] * (1 - items[i].probabilityOfGettingCaught));
					
			for(int moneyGrabbed = sum;moneyGrabbed >=0;moneyGrabbed --){
				if(secureProbabilities[moneyGrabbed] > absoluteSecureProbability){
					System.out.println(moneyGrabbed);
					break;
				}
			}						
		}	
		input.close();
	}

	private static void reset(double[] dArray){
		for(int i = 0;i < dArray.length;i ++)
			dArray[i] = 0.0;		
	}
	
	private static class Item{
		int money;
		double probabilityOfGettingCaught;		
		
		public Item(int money,double prob){
			this.money = money;
			this.probabilityOfGettingCaught = prob;
		}
		
	}
	
}
