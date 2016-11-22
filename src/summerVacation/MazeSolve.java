package summerVacation;

import java.awt.Point;

import summerVacation.MazeSolve.SElemType;

//解迷宫
public class MazeSolve {
	
	//在外围加上围墙的迷宫
	private int[][] maze;
	
	//当前待检测位置，不一定是通道块，也可能是墙。
	private Point currentPosition;
	
	//按顺序给每次走的新通道块作标记
	private int currentStep;
	
	//存储在迷宫中的即时路径
	private GenericStack<SElemType> pathStack 
				= new GenericStack<SElemType>();
	
	//起点、终点
	private Point start,end;
	
	/**构造函数。默认将起点设置在迷宫左上角，
	 * 终点设置在迷宫右下角。
	 * @param originalMaze 以二维数组表示的迷宫数据，
	 * 不带外围围墙，以“0”表示通道块，“1”表示墙。
	 */
	public MazeSolve(int[][] originalMaze){
		this(originalMaze,new Point(1,1),
				new Point(originalMaze.length,originalMaze[0].length));		
	}
	
	/**构造函数
	 * @param originalMaze 以二维数组表示的迷宫数据，不带外围围墙
	 * 				以“0”表示通道块，“1”表示墙。
	 * @param start 起点
	 * @param end 终点*/
	public MazeSolve(int[][] originalMaze,Point start,Point end){
		
		this.start = new Point(start);
		this.end = new Point(end);
		
		/*在原始的迷宫数据外面再加一圈围墙，
		 * 得到完整的迷宫
		 * */
		//外围一圈围墙，所以行数、列数都加2
		int rows = originalMaze.length + 2;
		int cols = originalMaze[0].length + 2;
		maze = new int[rows][cols];

		//先给所有元素赋1
		for(int i = 0;i < maze.length;i ++)
			for(int j = 0;j < maze[0].length;j ++)
				maze[i][j] = 1;
		
		//再把中间迷宫数据填上
		for(int i = 0;i < originalMaze.length;i ++)
			for(int j = 0;j <originalMaze[0].length;j ++)
				maze[i + 1][j + 1] = originalMaze[i][j];
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] aMaze = {{0,0,1},{0,1,1},{0,0,0}};
		//int[][] aMaze = {{0,0,1},{0,0,1},{1,1,0}};
		
		//打印预置的迷宫
		System.out.println("预置的迷宫：");
		for(int i = 0;i < aMaze.length;i ++){
			for(int j = 0;j < aMaze[0].length;j ++)
				System.out.printf("%2d",aMaze[i][j]);			
			System.out.println();
		}
		MazeSolve ms = new MazeSolve(aMaze);
		if(ms.mazePath()){//迷宫有一条到终点的通路
			GenericStack<SElemType> pStack = ms.getPathStack();
			while(!pStack.isEmpty()){
				SElemType e = pStack.pop();
				System.out.print(e.seat + " "+e.order+"<-- ");
			}
			System.out.println();;
		}else{//没有通路时，观察曾经探查过的通道块
			int[][] printedMaze = ms.getPrintedMaze();
			System.out.println("标记“2”的位置都是曾经探查过的通道块");
			for(int i = 0;i < printedMaze.length;i ++){
				for(int j = 0;j < printedMaze[0].length;j ++)
					//标记“2”的位置都是曾经探查过的通道块
					System.out.printf("%2d",printedMaze[i][j]);
				System.out.println();
			}
		}
		
	}

	/**在迷宫中寻找从起点到终点的一条通路
	 * 如果存在通路，函数返回true，接下来调用
	 * getPathStack函数可以获取该路径信息；否则
	 * 函数返回false，这时可以调用petPrintedMaze
	 * 函数来观察在迷宫曾经探查过的位置。
	 * @return 该迷宫是否存在一条从起点到终点的通路
	 * 存在返回true，否则返回false。
	 * */
	public boolean mazePath(){
		
		//栈的初始化
		if(pathStack == null)
			pathStack = new GenericStack<SElemType>();
		pathStack.clear();
		
		//设置好探索的起点
		currentPosition = new Point(start);
		currentStep = 1;
		do{
			if(pass()){//遇到新的通道块
				
				//注意坐标系：X轴水平向右，Y轴垂直向下。
				maze[currentPosition.y][currentPosition.x] = 2;
				SElemType e = new SElemType(currentStep,currentPosition,1);
				pathStack.push(e);
				if(currentPosition.equals(end)) 
					return true;
				
				currentPosition = nextPosition(currentPosition,1);
				currentStep ++;
			}else{//当前位置不能通过
				if(!pathStack.isEmpty()){//检查起点合法性
					SElemType e = pathStack.pop();
					
					//“当前位置不能通过” + “所立足的通道块正探测北方” =
					// “所立足的通道块不能通过”
					while(e.direction == 4 && !pathStack.isEmpty())
						e = pathStack.pop();
					
					if(e.direction < 4){
						e.direction ++;
						pathStack.push(e);
						
						//这里用的参数是所立足的通道块
						currentPosition = nextPosition(e.seat,e.direction);
					}//if
				}//if			
			}//else			
		}while(!pathStack.isEmpty());
	
		return false;
	}
	
	//当前位置（currentPosition）
	//是否为未曾走过的通道块。
	private boolean pass(){
		int locX = currentPosition.x;
		int locY = currentPosition.y;
		
		//注意坐标系：X轴水平向右，Y轴垂直向下。
		if(maze[locY][locX] == 0)
			return true;
		else
			return false;
	}
	
	/**根据基点和方向获取其邻接点
	 * @param p 基点，调用该函数不会修改该参数值
	 * @param direction 邻接点相对于基点的方向*/
	private Point nextPosition(Point p,int direction){
		int locX = p.x;
		int locY = p.y;
		switch(direction){
		//注意坐标系：X轴水平向右，Y轴垂直向下。
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
	
	/**获取带迷宫通路信息的栈，该函数应在
	 * mazePath函数之后调用，如果该迷宫没有
	 * 通路，栈的内容为空。
	 * @return 带迷宫通路信息的栈*/
	public GenericStack<SElemType> getPathStack(){
		//直接给出引用型内部私有数据并不是很安全
		return pathStack;
	}
	
	/**获取标记过的原始迷宫,该函数应在
	 * mazePath函数之后调用。
	 * <p>所谓“标记”指在路径搜索结束后，
	 * 会给所有曾经经过的通道块记上“2”。
	 * 于是在带标记的“迷宫”中，“0”表示未
	 * 到过的通道块，“1”表示墙。</p>
	 * @return 带标记的原始迷宫数据
	 * */
	public int[][] getPrintedMaze(){
		//该迷宫不包括外部围墙
		int[][] pMaze = new int[maze.length - 2][maze[0].length - 2];
		for(int i = 0; i < pMaze.length;i ++)
			for(int j = 0; j < pMaze[0].length;j ++)
				pMaze[i][j] = maze[i + 1][j + 1];
		return pMaze;
	}
	
	//栈的元素类型
	//不加static一样可以用MazeSolve.SElemType引用
	public class SElemType{
		
		//通道块在路径上的序号
		//其实order设置成public更方便；
		//（API中Point的x,y坐标便是设计成public）
		//而设计成用get函数获取更安全，
		//因为其在逻辑上是完整、严密的。
		//最后，一切都以实际需要为准。
		public int order;
		
		//通道块在迷宫中的坐标位置
		public Point seat;
		
		//从本通道块走到下一通道块的方向
		public int direction;
		
		public SElemType(int order,Point seat,int direction){
			this.order = order;
			
			//这里必须给seat重新开辟空间。
			//否则可以想见，最后所有元素的seat数据项
			//都指向同一个位置（currentPosition）。
			this.seat = new Point(seat);
			this.direction = direction;			
		}
	} 
	
}
