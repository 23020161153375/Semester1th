package week3th;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**���㷨��̰�ķ�ʵ�֡�����Ҫ��������2����
 * <p>1.�Կռ任ʱ�䣬��һ�����͵�ש�鰴����ͬ�ֳ�����һ��������飬��ȥ�˶�ש����������������</p>
 * <p>2.�����ˡ�ש��1���С��ש��2���ǡ�ש��1���Է���ש��2���桱�ı�Ҫ������һ���ʡ�����һ��ש�鰴�������</p>
 * <p>��ʵ����̰�ķ����һ�������ʱ��Ҳ�ж�������㡣�е��ܴﵽȫ�����ţ��е����ܡ�</p>*/
public class HDU1069 {
	private static Scanner input = new Scanner(new BufferedInputStream(System.in));
	private static Block[] blocks = new Block[100];
	
	//û�а����������ŵ�Block������
	//������Ʋ���
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
			//�����������
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
		//ש��Ĭ����x��yΪ��
		//��һ��ש�������ͬʱ�䳯��Ҳ������
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
		
		/**����ש����ڸ���ש��֮�£���Ҫ��ǰ��֤�Ƿ���С�
		 * @param aboveBlock ָ����ש��
		 * */
		public void buildTower(Block aboveBlock){
			baseHeight = aboveBlock.baseHeight+ z;
		}
		
		/**��b1 ����b2���Ƿ���С�����Ƿ���true�����򷵻�false*/
		public static boolean  isFeasible(Block b1,Block b2){
			if((b1.x < b2.x && b1.y < b2.y )|| (b1.x < b2.y && b1.y < b2.x))
				return true;
			else
				return false;
		}

		
	}
	
}
