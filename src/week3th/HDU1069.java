package week3th;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**该算法由贪心法实现。最重要的特征有2条。
 * <p>1.以空间换时间，将一个类型的砖块按朝向不同分成三种一起存入数组，免去了对砖块的三个坐标的排序。</p>
 * <p>2.利用了“砖块1面积小于砖块2”是“砖块1可以放在砖块2上面”的必要条件这一性质。并让一组砖块按面积排序。</p>
 * <p>其实在用贪心法解决一个问题的时候，也有多种切入点。有的能达到全局最优，有的则不能。</p>*/
public class HDU1069 {
	private static Scanner input = new Scanner(new BufferedInputStream(System.in));
	private static Block[] blocks = new Block[100];
	
	//没有把这个数据项放到Block类里面
	//这种设计不好
//	private static int[] baseHeight = new int[100];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int elemsForEachCase ;
		int caseth = 0;
		while((elemsForEachCase = input.nextInt())  != 0){
			caseth++;
			int x,y,z;
			for(int i = 0;i < elemsForEachCase * 3;i ++ ){
				x= input.nextInt();
				y = input.nextInt();
				z = input.nextInt();
				blocks[i] = new Block(x,y,z);i++;
				blocks[i] = new Block(y,z,x);i++;
				blocks[i] = new Block(z,x,y);
			}
			//按底面积排序
			Arrays.sort(blocks, 0, elemsForEachCase * 3);
			
			int maxHeight = 0;
			for(int i = 0;i < elemsForEachCase * 3;i++){
				
				for(int j = i -1;j >=0;j --)
					if(Block.isFeasible(blocks[j],blocks[i]))
						if(blocks[j].baseHeight + blocks[i].z > blocks[i].baseHeight)
							blocks[i].buildTower(blocks[j]);
							
				
				if(blocks[i].baseHeight> maxHeight)
					maxHeight = blocks[i].baseHeight;
			}
			System.out.println("Case " + caseth +": maximum height = " + maxHeight);;			
		}	
	}

	
	
	private static class Block implements Comparable<Block>{
		//砖块默认以x、y为底
		//即一个砖块产生的同时其朝向也决定了
		int x,y,z;
				
		int baseHeight;
		public Block(int x,int y,int z){
			this.x = x;
			this.y = y;
			this.z = z;
			baseHeight = z;
		}
		
		@Override
		public int compareTo(Block block) {
			// TODO Auto-generated method stub
			int temp = x * y - block.x * block.y;
					
			return temp;
		}
		
		/**将本砖块垫在给定砖块之下，需要提前验证是否可行。
		 * @param aboveBlock 指定的砖块
		 * */
		public void buildTower(Block aboveBlock){
			baseHeight = aboveBlock.baseHeight+ z;
		}
		
		/**把b1 放在b2上是否可行。如果是返回true，否则返回false*/
		public static boolean  isFeasible(Block b1,Block b2){
			if((b1.x < b2.x && b1.y < b2.y )|| (b1.x < b2.y && b1.y < b2.x))
				return true;
			else
				return false;
		}

		
	}
	
}
