package summerVacation;

import java.awt.Point;

import summerVacation.MazeSolve.SElemType;

//���Թ�
public class MazeSolve {
	
	//����Χ����Χǽ���Թ�
	private int[][] maze;
	
	//��ǰ�����λ�ã���һ����ͨ���飬Ҳ������ǽ��
	private Point currentPosition;
	
	//��˳���ÿ���ߵ���ͨ���������
	private int currentStep;
	
	//�洢���Թ��еļ�ʱ·��
	private GenericStack<SElemType> pathStack 
				= new GenericStack<SElemType>();
	
	//��㡢�յ�
	private Point start,end;
	
	/**���캯����Ĭ�Ͻ�����������Թ����Ͻǣ�
	 * �յ��������Թ����½ǡ�
	 * @param originalMaze �Զ�ά�����ʾ���Թ����ݣ�
	 * ������ΧΧǽ���ԡ�0����ʾͨ���飬��1����ʾǽ��
	 */
	public MazeSolve(int[][] originalMaze){
		this(originalMaze,new Point(1,1),
				new Point(originalMaze.length,originalMaze[0].length));		
	}
	
	/**���캯��
	 * @param originalMaze �Զ�ά�����ʾ���Թ����ݣ�������ΧΧǽ
	 * 				�ԡ�0����ʾͨ���飬��1����ʾǽ��
	 * @param start ���
	 * @param end �յ�*/
	public MazeSolve(int[][] originalMaze,Point start,Point end){
		
		this.start = new Point(start);
		this.end = new Point(end);
		
		/*��ԭʼ���Թ����������ټ�һȦΧǽ��
		 * �õ��������Թ�
		 * */
		//��ΧһȦΧǽ��������������������2
		int rows = originalMaze.length + 2;
		int cols = originalMaze[0].length + 2;
		maze = new int[rows][cols];

		//�ȸ�����Ԫ�ظ�1
		for(int i = 0;i < maze.length;i ++)
			for(int j = 0;j < maze[0].length;j ++)
				maze[i][j] = 1;
		
		//�ٰ��м��Թ���������
		for(int i = 0;i < originalMaze.length;i ++)
			for(int j = 0;j <originalMaze[0].length;j ++)
				maze[i + 1][j + 1] = originalMaze[i][j];
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] aMaze = {{0,0,1},{0,1,1},{0,0,0}};
		//int[][] aMaze = {{0,0,1},{0,0,1},{1,1,0}};
		
		//��ӡԤ�õ��Թ�
		System.out.println("Ԥ�õ��Թ���");
		for(int i = 0;i < aMaze.length;i ++){
			for(int j = 0;j < aMaze[0].length;j ++)
				System.out.printf("%2d",aMaze[i][j]);			
			System.out.println();
		}
		MazeSolve ms = new MazeSolve(aMaze);
		if(ms.mazePath()){//�Թ���һ�����յ��ͨ·
			GenericStack<SElemType> pStack = ms.getPathStack();
			while(!pStack.isEmpty()){
				SElemType e = pStack.pop();
				System.out.print(e.seat + " "+e.order+"<-- ");
			}
			System.out.println();;
		}else{//û��ͨ·ʱ���۲�����̽�����ͨ����
			int[][] printedMaze = ms.getPrintedMaze();
			System.out.println("��ǡ�2����λ�ö�������̽�����ͨ����");
			for(int i = 0;i < printedMaze.length;i ++){
				for(int j = 0;j < printedMaze[0].length;j ++)
					//��ǡ�2����λ�ö�������̽�����ͨ����
					System.out.printf("%2d",printedMaze[i][j]);
				System.out.println();
			}
		}
		
	}

	/**���Թ���Ѱ�Ҵ���㵽�յ��һ��ͨ·
	 * �������ͨ·����������true������������
	 * getPathStack�������Ի�ȡ��·����Ϣ������
	 * ��������false����ʱ���Ե���petPrintedMaze
	 * �������۲����Թ�����̽�����λ�á�
	 * @return ���Թ��Ƿ����һ������㵽�յ��ͨ·
	 * ���ڷ���true�����򷵻�false��
	 * */
	public boolean mazePath(){
		
		//ջ�ĳ�ʼ��
		if(pathStack == null)
			pathStack = new GenericStack<SElemType>();
		pathStack.clear();
		
		//���ú�̽�������
		currentPosition = new Point(start);
		currentStep = 1;
		do{
			if(pass()){//�����µ�ͨ����
				
				//ע������ϵ��X��ˮƽ���ң�Y�ᴹֱ���¡�
				maze[currentPosition.y][currentPosition.x] = 2;
				SElemType e = new SElemType(currentStep,currentPosition,1);
				pathStack.push(e);
				if(currentPosition.equals(end)) 
					return true;
				
				currentPosition = nextPosition(currentPosition,1);
				currentStep ++;
			}else{//��ǰλ�ò���ͨ��
				if(!pathStack.isEmpty()){//������Ϸ���
					SElemType e = pathStack.pop();
					
					//����ǰλ�ò���ͨ���� + ���������ͨ������̽�ⱱ���� =
					// ���������ͨ���鲻��ͨ����
					while(e.direction == 4 && !pathStack.isEmpty())
						e = pathStack.pop();
					
					if(e.direction < 4){
						e.direction ++;
						pathStack.push(e);
						
						//�����õĲ������������ͨ����
						currentPosition = nextPosition(e.seat,e.direction);
					}//if
				}//if			
			}//else			
		}while(!pathStack.isEmpty());
	
		return false;
	}
	
	//��ǰλ�ã�currentPosition��
	//�Ƿ�Ϊδ���߹���ͨ���顣
	private boolean pass(){
		int locX = currentPosition.x;
		int locY = currentPosition.y;
		
		//ע������ϵ��X��ˮƽ���ң�Y�ᴹֱ���¡�
		if(maze[locY][locX] == 0)
			return true;
		else
			return false;
	}
	
	/**���ݻ���ͷ����ȡ���ڽӵ�
	 * @param p ���㣬���øú��������޸ĸò���ֵ
	 * @param direction �ڽӵ�����ڻ���ķ���*/
	private Point nextPosition(Point p,int direction){
		int locX = p.x;
		int locY = p.y;
		switch(direction){
		//ע������ϵ��X��ˮƽ���ң�Y�ᴹֱ���¡�
			case 1: locX ++;
						break;
			case 2: locY ++;
						break;
			case 3: locX --;
						break;
			case 4: locY --;
						break;
		}		
		return new Point(locX,locY);
	}
	
	/**��ȡ���Թ�ͨ·��Ϣ��ջ���ú���Ӧ��
	 * mazePath����֮����ã�������Թ�û��
	 * ͨ·��ջ������Ϊ�ա�
	 * @return ���Թ�ͨ·��Ϣ��ջ*/
	public GenericStack<SElemType> getPathStack(){
		//ֱ�Ӹ����������ڲ�˽�����ݲ����Ǻܰ�ȫ
		return pathStack;
	}
	
	/**��ȡ��ǹ���ԭʼ�Թ�,�ú���Ӧ��
	 * mazePath����֮����á�
	 * <p>��ν����ǡ�ָ��·������������
	 * �����������������ͨ������ϡ�2����
	 * �����ڴ���ǵġ��Թ����У���0����ʾδ
	 * ������ͨ���飬��1����ʾǽ��</p>
	 * @return ����ǵ�ԭʼ�Թ�����
	 * */
	public int[][] getPrintedMaze(){
		//���Թ��������ⲿΧǽ
		int[][] pMaze = new int[maze.length - 2][maze[0].length - 2];
		for(int i = 0; i < pMaze.length;i ++)
			for(int j = 0; j < pMaze[0].length;j ++)
				pMaze[i][j] = maze[i + 1][j + 1];
		return pMaze;
	}
	
	//ջ��Ԫ������
	//����staticһ��������MazeSolve.SElemType����
	public class SElemType{
		
		//ͨ������·���ϵ����
		//��ʵorder���ó�public�����㣻
		//��API��Point��x,y���������Ƴ�public��
		//����Ƴ���get������ȡ����ȫ��
		//��Ϊ�����߼��������������ܵġ�
		//���һ�ж���ʵ����ҪΪ׼��
		public int order;
		
		//ͨ�������Թ��е�����λ��
		public Point seat;
		
		//�ӱ�ͨ�����ߵ���һͨ����ķ���
		public int direction;
		
		public SElemType(int order,Point seat,int direction){
			this.order = order;
			
			//��������seat���¿��ٿռ䡣
			//�������������������Ԫ�ص�seat������
			//��ָ��ͬһ��λ�ã�currentPosition����
			this.seat = new Point(seat);
			this.direction = direction;			
		}
	} 
	
}
