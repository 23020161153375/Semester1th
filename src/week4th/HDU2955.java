package week4th;

import java.io.BufferedInputStream;
import java.util.Scanner;


/**贪心法解决"0-1"背包问题。其中，抢得的钱数是值，风险系数是权。
 * 其要点如下
 * <p> 1.从0到sum建立空间上的搜素范围（必要条件），其中sum为所有银行的储蓄总额。</p>
 * <p>2.该问题不需要排序，而是使用集合S(背包)。按输入顺序每次将一个银行(物品)i加入讨论（背包），而这种讨论
 * 是建立在对前面 i-1 个银行（物品）的讨论之上的。在本次迭代结束后，可以完成对前i个物品加入背包，包括不完全加入和空包清况的所有讨论。</p>
 * <p>3. 其实在0到sun的搜索空间里有3类点，分别是 a 无交叉点 b 有交叉点(可以对应多种物品组合方案)  c 空点（不会出现的情况）
 * 其中，对应 b 类点，取权值最小的情况，对应 c 类点，可以证明，在计算的过程中它不与a、b类点发生交集。即它不依赖于a、b类点，a、b类点也不会依赖它。</p>
 * <p>4.之前在计算时列出所有可能情况，最后只要把值由高到低依次遍历，选出第一个符合要求的权即可。</p>*/
public class HDU2955 {
	//抢银行时的安全概率
	private static double[] secureProbabilities = new double[10001];
	
	//“物品”
	private static Item[] items ;
	
	private static  Scanner input = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//测试用例数目、每个用例所含元素个数
		int cases,elemsForEachCase;
		
		//妈妈的心理安全底线（权值界线）
		double absoluteSecureProbability;
		cases  = input.nextInt();
		while(cases -- != 0){
			//初始化
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
			
			//记得将数组重置
			//注意到空点的权（风险系数）为1
			reset(secureProbabilities);
			secureProbabilities[0] = 1;
			for(int i = 0;i < items.length;i ++)
				for(int j  = sum;j >= items[i].money;j --)
					//应从大到小遍历j，因为放第i件物品到背包是
					//完全（只能）建立在对前i-1个物品的讨论之上
					
					//如果遇到多种可能的组合得到同样的权值，取最小的权值
					//权： weight = 1 - secureProbabilities
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
