package summerVacation;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**小小百货商
 * <p>实现从存货清单的创建开始、包括商品采购、销售以及最后清除全过程。</p>
 * <p>该类设计的原则是具体功能与业务逻辑在实现上的分离。其中带menu字样的函数都是实现业务逻辑的，其余函数都是为了实现某个具体功能的，比如{@link #sell(int, int)} 是为了完成一笔售卖，{@link #list()} 是为了展示当前库存清单。由于清除存货的功能和业务耦合性强，难以
 * 分开，所以只设计了{@link #menuDestroy(boolean)} 这一个既包括具体功能又包括业务逻辑的函数。同时，为了能在两种不同场合都能使用该函数，给你设置了一个布尔参数。</p>
 * <p>该类设计的另一特点是通过异常处理机制，很好的界定了一个函数的功能，并使业务逻辑清晰，易于理解。</p>
 * <p>该类在控制用户输入上并不完善，比如当需要输入一个整数而用户输入了字符串时，程序会出错。</p>
 * */
public class MiniGrocer {
	//存货清单
	private MyLinkedList<Merchandise> stockList
		= new MyLinkedList<Merchandise> ();
	
	//从键盘获取输入
	private Scanner input = new Scanner(System.in);
	
	//标示是否已经进货
	//每次从（在当前存货清单存在时的）第一次进货后直到其存货清单销毁前，
	//它为true,其他时间为false，
	private boolean hasStocked = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiniGrocer newGrocer = new MiniGrocer();
		newGrocer.menu();
	}

	/**在存货清单之后添加新的项，默认商品数目为0.
	 * 相当于调用{@link #create(int, String, int )} ，其中amount = 0
	 * 该函数在创建存货清单时使用。*/
	public boolean create(int merchandiseID,String merchandiseName){
		return create(merchandiseID, merchandiseName, 0);
	}
	
	/**在存货清单之后添加新的项。一并完成采购。
	 * @param merchandiseID 商品编号，不能为0，必须唯一
	 * @param merchandiseName 商品名，建议用拼音或英文
	 * @param amount 商品数目
	 * @return 如果商品号重复，就不能添加，返回false，否则返回true，表示添加成功。
	 * */
	public boolean create(int merchandiseID,String merchandiseName,int amount){
		Merchandise newMerc =
				new Merchandise(merchandiseID,merchandiseName,amount);
		if(!stockList.contains(newMerc)){
			stockList.addLast(newMerc);
			return true;
		}else 
			return false;		
	}
	
	/**完成一笔售卖交易
	 * @param merchandiseID 待出售商品的编号
	 * @param  amount 出售数目
	 * @throws NoSuchElementException 不存在该商品
	 * @throws IllegalArgumentException 数目不合法，如果它小于0或者大于该商品的库存数目
	 * */
	public void sell(int merchandiseID,int amount){
		//使用异常比使用布尔返回值或是整型返回值更容易使人理解
		if(!isThisMerchandiseExist(merchandiseID))
			throw new NoSuchElementException(noSuchElementMsg(merchandiseID));
		if(amount <= 0)
			throw new IllegalArgumentException(illegalArgumentMsg(amount));
		
		/*利用商品号找到库存中对应商品**/
		Merchandise selectedMerch = new Merchandise(merchandiseID);		
		int stockMerchIndex = stockList.indexOf(selectedMerch);
		Merchandise stockMerch = stockList.get(stockMerchIndex);
		
		//计算该商品剩余数目
		int amountLeft = stockMerch.amount - amount;
		if(amountLeft > 0){
			stockMerch.amount = amountLeft;
			//重新设置卖出后该商拼数目
			stockList.set(stockMerchIndex, stockMerch);

		}else if(amountLeft == 0){//商品正好卖完时，从清单中移除该项
			stockList.remove(stockMerchIndex);

		}else//没有那么多库存
			throw new IllegalArgumentException(illegalArgumentMsg(amount,merchandiseID,stockMerch.amount));
	}
	
	/**完成一笔采购
	 * @param merchandiseID 待采购商品的编号
	 * @param  amount 采购数目
	 * @throws NoSuchElementException 不存在该商品
	 * @throws IllegalArgumentException 数目不合法，如果它小于0
	 * */
	public void stock(int merchandiseID,int amount){
		if(!isThisMerchandiseExist(merchandiseID))
			throw new NoSuchElementException(noSuchElementMsg(merchandiseID));
		if(amount <= 0)
			throw new IllegalArgumentException(illegalArgumentMsg(amount));
	
		//选中的商品
		Merchandise selectedMerch = new Merchandise(merchandiseID);
		int stockMerchIndex = stockList.indexOf(selectedMerch);
		
		//在库存里找到对应商品
		Merchandise stockMerch = stockList.get(stockMerchIndex);
	
		stockMerch.amount += amount;
		
		//重新设置该商拼采购后的数目
		//关于链表：注意这里直接设置的是元素，不是结点。
		stockList.set(stockMerchIndex, stockMerch);
		
		//已经采购过了，修改hasStocked
		//否则永远不能销售
		if(!hasStocked)
			hasStocked = true;	
	}

	/**列出存货清单上的内容。*/
	public void list(){
		System.out.println("以下为小店现有商品");
		
		//用英文是为了对齐，这样美观一些
		System.out.printf("%-10s%-25s%-10s","ID","Name","Amount");
		System.out.println();
		
		//遍历
		Iterator<Merchandise> iter = stockList.iterator();
		Merchandise merch ;

		while(iter.hasNext()){
			merch = iter.next();
			System.out.printf("%-10d%-25s%-10d",
					merch.id,merch.name,merch.amount);						
			System.out.println();
		}		
	}
	
	/**判断当前存货清单里是否存在该商品。
	 * @param merchandiseID 商品号，这是判断商品是否重复的依据*/
	private boolean isThisMerchandiseExist(int merchandiseID){
		Merchandise selectedMerch = new Merchandise(merchandiseID);
		return stockList.contains(selectedMerch);
	}
	
	/**给 NoSuchElementException 异常添加信息的便捷方法 */
	private static String noSuchElementMsg(int id){
		return  "商品 " + id + " 不存在";
	}

	/**给 IllegalArgumentException 异常添加信息的便捷方法 
	 * 该方法用于售卖过程*/
	private static String illegalArgumentMsg(int amount,int stockID, int stockAmount){
		return "输入的数目 " + amount + " 不正确；"
				+"已知商品 " + stockID + " 当前库存为  " + stockAmount;
	}
	
	/**给 IllegalArgumentException 异常添加信息的便捷方法 
	 * 该方法用于采购过程*/
	private static String illegalArgumentMsg(int amount){
		return "输入的数目 " + amount + " 不正确";
	}
	
	//以下是实现业务处理逻辑的函数
	
	/**欢迎界面/主界面*/
	public void menu(){
		System.out.println("**********"
				+"友诚小百货欢迎您"
				+"**********");
		System.out.println("1. 输入商品信息 2. 销售 3. 进货"
				+ " 4. 列举商品信息 5. 清除所有商品 6. 退出");
		
		//存放用户在主界面里输入的指令。
		int instruction = input.nextInt();
		
		//是否需要重新输入指令的标志
		boolean reinput = false;
		do{
			reinput = false;
			switch(instruction){
				case 1 : menuCreate();
							break;
				case 2:{ 
						if(hasStocked)
							menuSell();
						else{
							System.out.println("必须先采购再售卖!"
									+ "您可以重新选择 。");
							reinput = true;
							instruction = input.nextInt();
						}							
						break;
				}
				case 3:menuStock();
							break;
				case 4:menuList();
							break;
				case 5:menuDestroy(true);
							break;
				
							//处理退出的代码
				case 6:menuExit();
							break;
				default: System.out.println("输入无效，请重新输入");
								reinput = true;
								instruction  = input.nextInt();
			}
		}while(reinput);
	}
	
	public void menuExit(){
		System.out.println("准备退出，退出前应删除存货清单。");
		if(menuDestroy(false)){//如果用户已经清除存货清单
			System.out.println("友诚小百货欢迎您再次光临。");	
			
			//最后需要关闭输入流
			input.close();
			
			//直接退出程序
			System.exit(0);
			
			//Debug: 该句从未被执行
			//System.out.println("Look at me!");
		}	
	}
	
	/**存货清单的创建与初始化*/
	public void menuCreate(){
		if(!stockList.isEmpty()){
			System.out.println("存货清单已存在，需要删除旧的清单。");
			menuDestroy(false);
		}
		System.out.println("准备创建新的存货清单。"
				+"\n[提示：请按照“商品号 商品名”的格式将待采购的"
				+ "商品写入清单，商品之间用回车隔开,"
				+"输入“0”返回上一级]");
		int instructionID = input.nextInt();
		String instructionName = "";
		while(instructionID != 0){
			//注意到商品名可以包括空格
			instructionName = input.nextLine();
			
			//确保清单里的商品号唯一
			if(!create(instructionID,instructionName))
				System.out.println("该商品号重复，输入无效。");
			instructionID = input.nextInt();
		}
		System.out.println("创建完成，返回上一级。");	
		menu();
	}
	
	public void menuSell(){
		//首先列出现有商品
		list();
		System.out.println("您需要什么?"
				+"\n[提示：按“商品号 商品数目”的格式输入，"
				+ "单笔购买之间用回车隔开"
				+ "键入“0”返回上一级]");
		
		//既可以作为商品号，又可能是退出的指令
		int instructionID = input.nextInt();
		int instructionAmount;
		
		while(instructionID != 0){
			instructionAmount = input.nextInt();
			
			try{
				sell(instructionID,instructionAmount);
				System.out.println("商品 " + instructionID 
						+" "+instructionAmount + " 件售卖成功。");
				
				//每销售一笔，重新展示现有商品
				list();
			}catch(NoSuchElementException e){
				System.out.println(e.getMessage());
			}catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
			}	
			//重新获取商品号或是退出的指令
			instructionID = input.nextInt();
		}
		
		//退出后返回上一级
		menu();
	}
	
	public void menuStock(){
		list();
		System.out.println("正在采购"
				+"\n[提示：按“商品号 商品数目”的格式输入，"
				+ "单笔采购之间用回车隔开"
				+ "键入“0”返回上一级]");
		int instructionID = input.nextInt();
		int instructionAmount;
		while(instructionID != 0){
			instructionAmount = input.nextInt();
			
			try{
				stock(instructionID,instructionAmount);
				System.out.println("商品 " + instructionID 
						+" 共 "+instructionAmount + " 件采购成功。");
				//menuList();
			}catch(NoSuchElementException e){
				//如果不存在该元素，在清单里增加该商品				
				System.out.println(e.getMessage());
				//Debug
				System.out.println("准备补货 " + instructionID 
						+ " 共 " + instructionAmount + " 件" );
				System.out.printf("输入商品名称:");
				
				//去掉上一次调用nextInt方法后
				//留下的行分隔符（回车）
				input.nextLine();
				String instructionName = input.nextLine();
				
				//或者直接调用令牌读取方法next
				//然而，如此一来，商品名将不能包含空格
				//String instructionName = input.next();
				
				//增加新商品
				create(instructionID,instructionName,instructionAmount);
				System.out.println("成功添加新商品到清单");
			}catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
			}		
			
			//重新输入指令以决定是否返回上一级
			instructionID = input.nextInt();
		}	
		//返回上一级
		menu();
	}
	
	public void menuList(){
		list();
		System.out.println("[提示：键入“0”可以返回上一级]");
		int instruction = input.nextInt();
		while(instruction != 0){
			System.out.println("输入无效，您可以重新输入");
			instruction = input.nextInt();
		}
		System.out.println("返回上一级");
		menu();
	}

	/**删除当前的存货清单，该函数用于两种场合。
	 * <p>场合1 创建存货清单时需要删除旧清单，或退出时需要删除清单，此时在珊瑚后不需要返回主菜单。</p>
	 * <p>场合2 从主菜单选择进入了删除旧存货清单的界面，此时在删除后需要返回主菜单。</p>
	 * @param returnMainMenuAfterClear  决定在清除后们是否还需要返回主菜单
	 * @return  标志是否完成清除*/
	public boolean menuDestroy(boolean returnMainMenuAfterClear){
		System.out.println("即将清除现有存货，是否继续？（[0]：否  [1]：是）");
		int instruction = input.nextInt();
		//boolean reinput = false;
		
		
		if(instruction == 1 && returnMainMenuAfterClear){
			stockList.clear();
			
			//记得在清除当前清单后将hasStocked已进货的标志置为false
			hasStocked = false;
			System.out.println("清除完毕！现在返回上一级。");
			menu();
			return true;
		}else if(instruction == 1 && !returnMainMenuAfterClear){
			stockList.clear();
			hasStocked = false;
			System.out.println("清除完毕! ");
			return true;
		}else {
			//如果指令是0或是其他数字就返回主菜单
			//这和switch语句略有不同，所以也不用使用
			//循环让用户重新输入
			System.out.println("返回上一级菜单。");
			menu();
			return false;
		}	
		
	}
	
	/**商品，存放结点信息*/
	private static class Merchandise{
		
		//商品号，唯一标识商品，不能为0
		private int id;
		
		//商品名
		private String name;
		
		//现有该商品的数目
		private int amount;
				
		public Merchandise(int id){
			this.id = id;
		}
			
		public Merchandise(int id,String name,int amount){
			this.id = id;
			this.name = name;
			this.amount = amount;
		}
		
		/**重载，用{@link #id} 标示一个商品。*/
		public boolean equals(Object obj){
			 Merchandise m = (Merchandise)obj;			
			return id == m.id;
		}		
	}	
}

