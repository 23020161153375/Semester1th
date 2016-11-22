package summerVacation;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**СС�ٻ���
 * <p>ʵ�ִӴ���嵥�Ĵ�����ʼ��������Ʒ�ɹ��������Լ�������ȫ���̡�</p>
 * <p>������Ƶ�ԭ���Ǿ��幦����ҵ���߼���ʵ���ϵķ��롣���д�menu�����ĺ�������ʵ��ҵ���߼��ģ����ຯ������Ϊ��ʵ��ĳ�����幦�ܵģ�����{@link #sell(int, int)} ��Ϊ�����һ��������{@link #list()} ��Ϊ��չʾ��ǰ����嵥�������������Ĺ��ܺ�ҵ�������ǿ������
 * �ֿ�������ֻ�����{@link #menuDestroy(boolean)} ��һ���Ȱ������幦���ְ���ҵ���߼��ĺ�����ͬʱ��Ϊ���������ֲ�ͬ���϶���ʹ�øú���������������һ������������</p>
 * <p>������Ƶ���һ�ص���ͨ���쳣������ƣ��ܺõĽ綨��һ�������Ĺ��ܣ���ʹҵ���߼�������������⡣</p>
 * <p>�����ڿ����û������ϲ������ƣ����統��Ҫ����һ���������û��������ַ���ʱ����������</p>
 * */
public class MiniGrocer {
	//����嵥
	private MyLinkedList<Merchandise> stockList
		= new MyLinkedList<Merchandise> ();
	
	//�Ӽ��̻�ȡ����
	private Scanner input = new Scanner(System.in);
	
	//��ʾ�Ƿ��Ѿ�����
	//ÿ�δӣ��ڵ�ǰ����嵥����ʱ�ģ���һ�ν�����ֱ�������嵥����ǰ��
	//��Ϊtrue,����ʱ��Ϊfalse��
	private boolean hasStocked = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiniGrocer newGrocer = new MiniGrocer();
		newGrocer.menu();
	}

	/**�ڴ���嵥֮������µ��Ĭ����Ʒ��ĿΪ0.
	 * �൱�ڵ���{@link #create(int, String, int )} ������amount = 0
	 * �ú����ڴ�������嵥ʱʹ�á�*/
	public boolean create(int merchandiseID,String merchandiseName){
		return create(merchandiseID, merchandiseName, 0);
	}
	
	/**�ڴ���嵥֮������µ��һ����ɲɹ���
	 * @param merchandiseID ��Ʒ��ţ�����Ϊ0������Ψһ
	 * @param merchandiseName ��Ʒ����������ƴ����Ӣ��
	 * @param amount ��Ʒ��Ŀ
	 * @return �����Ʒ���ظ����Ͳ�����ӣ�����false�����򷵻�true����ʾ��ӳɹ���
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
	
	/**���һ����������
	 * @param merchandiseID ��������Ʒ�ı��
	 * @param  amount ������Ŀ
	 * @throws NoSuchElementException �����ڸ���Ʒ
	 * @throws IllegalArgumentException ��Ŀ���Ϸ��������С��0���ߴ��ڸ���Ʒ�Ŀ����Ŀ
	 * */
	public void sell(int merchandiseID,int amount){
		//ʹ���쳣��ʹ�ò�������ֵ�������ͷ���ֵ������ʹ�����
		if(!isThisMerchandiseExist(merchandiseID))
			throw new NoSuchElementException(noSuchElementMsg(merchandiseID));
		if(amount <= 0)
			throw new IllegalArgumentException(illegalArgumentMsg(amount));
		
		/*������Ʒ���ҵ�����ж�Ӧ��Ʒ**/
		Merchandise selectedMerch = new Merchandise(merchandiseID);		
		int stockMerchIndex = stockList.indexOf(selectedMerch);
		Merchandise stockMerch = stockList.get(stockMerchIndex);
		
		//�������Ʒʣ����Ŀ
		int amountLeft = stockMerch.amount - amount;
		if(amountLeft > 0){
			stockMerch.amount = amountLeft;
			//�����������������ƴ��Ŀ
			stockList.set(stockMerchIndex, stockMerch);

		}else if(amountLeft == 0){//��Ʒ��������ʱ�����嵥���Ƴ�����
			stockList.remove(stockMerchIndex);

		}else//û����ô����
			throw new IllegalArgumentException(illegalArgumentMsg(amount,merchandiseID,stockMerch.amount));
	}
	
	/**���һ�ʲɹ�
	 * @param merchandiseID ���ɹ���Ʒ�ı��
	 * @param  amount �ɹ���Ŀ
	 * @throws NoSuchElementException �����ڸ���Ʒ
	 * @throws IllegalArgumentException ��Ŀ���Ϸ��������С��0
	 * */
	public void stock(int merchandiseID,int amount){
		if(!isThisMerchandiseExist(merchandiseID))
			throw new NoSuchElementException(noSuchElementMsg(merchandiseID));
		if(amount <= 0)
			throw new IllegalArgumentException(illegalArgumentMsg(amount));
	
		//ѡ�е���Ʒ
		Merchandise selectedMerch = new Merchandise(merchandiseID);
		int stockMerchIndex = stockList.indexOf(selectedMerch);
		
		//�ڿ�����ҵ���Ӧ��Ʒ
		Merchandise stockMerch = stockList.get(stockMerchIndex);
	
		stockMerch.amount += amount;
		
		//�������ø���ƴ�ɹ������Ŀ
		//��������ע������ֱ�����õ���Ԫ�أ����ǽ�㡣
		stockList.set(stockMerchIndex, stockMerch);
		
		//�Ѿ��ɹ����ˣ��޸�hasStocked
		//������Զ��������
		if(!hasStocked)
			hasStocked = true;	
	}

	/**�г�����嵥�ϵ����ݡ�*/
	public void list(){
		System.out.println("����ΪС��������Ʒ");
		
		//��Ӣ����Ϊ�˶��룬��������һЩ
		System.out.printf("%-10s%-25s%-10s","ID","Name","Amount");
		System.out.println();
		
		//����
		Iterator<Merchandise> iter = stockList.iterator();
		Merchandise merch ;

		while(iter.hasNext()){
			merch = iter.next();
			System.out.printf("%-10d%-25s%-10d",
					merch.id,merch.name,merch.amount);						
			System.out.println();
		}		
	}
	
	/**�жϵ�ǰ����嵥���Ƿ���ڸ���Ʒ��
	 * @param merchandiseID ��Ʒ�ţ������ж���Ʒ�Ƿ��ظ�������*/
	private boolean isThisMerchandiseExist(int merchandiseID){
		Merchandise selectedMerch = new Merchandise(merchandiseID);
		return stockList.contains(selectedMerch);
	}
	
	/**�� NoSuchElementException �쳣�����Ϣ�ı�ݷ��� */
	private static String noSuchElementMsg(int id){
		return  "��Ʒ " + id + " ������";
	}

	/**�� IllegalArgumentException �쳣�����Ϣ�ı�ݷ��� 
	 * �÷���������������*/
	private static String illegalArgumentMsg(int amount,int stockID, int stockAmount){
		return "�������Ŀ " + amount + " ����ȷ��"
				+"��֪��Ʒ " + stockID + " ��ǰ���Ϊ  " + stockAmount;
	}
	
	/**�� IllegalArgumentException �쳣�����Ϣ�ı�ݷ��� 
	 * �÷������ڲɹ�����*/
	private static String illegalArgumentMsg(int amount){
		return "�������Ŀ " + amount + " ����ȷ";
	}
	
	//������ʵ��ҵ�����߼��ĺ���
	
	/**��ӭ����/������*/
	public void menu(){
		System.out.println("**********"
				+"�ѳ�С�ٻ���ӭ��"
				+"**********");
		System.out.println("1. ������Ʒ��Ϣ 2. ���� 3. ����"
				+ " 4. �о���Ʒ��Ϣ 5. ���������Ʒ 6. �˳�");
		
		//����û����������������ָ�
		int instruction = input.nextInt();
		
		//�Ƿ���Ҫ��������ָ��ı�־
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
							System.out.println("�����Ȳɹ�������!"
									+ "����������ѡ�� ��");
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
				
							//�����˳��Ĵ���
				case 6:menuExit();
							break;
				default: System.out.println("������Ч������������");
								reinput = true;
								instruction  = input.nextInt();
			}
		}while(reinput);
	}
	
	public void menuExit(){
		System.out.println("׼���˳����˳�ǰӦɾ������嵥��");
		if(menuDestroy(false)){//����û��Ѿ��������嵥
			System.out.println("�ѳ�С�ٻ���ӭ���ٴι��١�");	
			
			//�����Ҫ�ر�������
			input.close();
			
			//ֱ���˳�����
			System.exit(0);
			
			//Debug: �þ��δ��ִ��
			//System.out.println("Look at me!");
		}	
	}
	
	/**����嵥�Ĵ������ʼ��*/
	public void menuCreate(){
		if(!stockList.isEmpty()){
			System.out.println("����嵥�Ѵ��ڣ���Ҫɾ���ɵ��嵥��");
			menuDestroy(false);
		}
		System.out.println("׼�������µĴ���嵥��"
				+"\n[��ʾ���밴�ա���Ʒ�� ��Ʒ�����ĸ�ʽ�����ɹ���"
				+ "��Ʒд���嵥����Ʒ֮���ûس�����,"
				+"���롰0��������һ��]");
		int instructionID = input.nextInt();
		String instructionName = "";
		while(instructionID != 0){
			//ע�⵽��Ʒ�����԰����ո�
			instructionName = input.nextLine();
			
			//ȷ���嵥�����Ʒ��Ψһ
			if(!create(instructionID,instructionName))
				System.out.println("����Ʒ���ظ���������Ч��");
			instructionID = input.nextInt();
		}
		System.out.println("������ɣ�������һ����");	
		menu();
	}
	
	public void menuSell(){
		//�����г�������Ʒ
		list();
		System.out.println("����Ҫʲô?"
				+"\n[��ʾ��������Ʒ�� ��Ʒ��Ŀ���ĸ�ʽ���룬"
				+ "���ʹ���֮���ûس�����"
				+ "���롰0��������һ��]");
		
		//�ȿ�����Ϊ��Ʒ�ţ��ֿ������˳���ָ��
		int instructionID = input.nextInt();
		int instructionAmount;
		
		while(instructionID != 0){
			instructionAmount = input.nextInt();
			
			try{
				sell(instructionID,instructionAmount);
				System.out.println("��Ʒ " + instructionID 
						+" "+instructionAmount + " �������ɹ���");
				
				//ÿ����һ�ʣ�����չʾ������Ʒ
				list();
			}catch(NoSuchElementException e){
				System.out.println(e.getMessage());
			}catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
			}	
			//���»�ȡ��Ʒ�Ż����˳���ָ��
			instructionID = input.nextInt();
		}
		
		//�˳��󷵻���һ��
		menu();
	}
	
	public void menuStock(){
		list();
		System.out.println("���ڲɹ�"
				+"\n[��ʾ��������Ʒ�� ��Ʒ��Ŀ���ĸ�ʽ���룬"
				+ "���ʲɹ�֮���ûس�����"
				+ "���롰0��������һ��]");
		int instructionID = input.nextInt();
		int instructionAmount;
		while(instructionID != 0){
			instructionAmount = input.nextInt();
			
			try{
				stock(instructionID,instructionAmount);
				System.out.println("��Ʒ " + instructionID 
						+" �� "+instructionAmount + " ���ɹ��ɹ���");
				//menuList();
			}catch(NoSuchElementException e){
				//��������ڸ�Ԫ�أ����嵥�����Ӹ���Ʒ				
				System.out.println(e.getMessage());
				//Debug
				System.out.println("׼������ " + instructionID 
						+ " �� " + instructionAmount + " ��" );
				System.out.printf("������Ʒ����:");
				
				//ȥ����һ�ε���nextInt������
				//���µ��зָ������س���
				input.nextLine();
				String instructionName = input.nextLine();
				
				//����ֱ�ӵ������ƶ�ȡ����next
				//Ȼ�������һ������Ʒ�������ܰ����ո�
				//String instructionName = input.next();
				
				//��������Ʒ
				create(instructionID,instructionName,instructionAmount);
				System.out.println("�ɹ��������Ʒ���嵥");
			}catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
			}		
			
			//��������ָ���Ծ����Ƿ񷵻���һ��
			instructionID = input.nextInt();
		}	
		//������һ��
		menu();
	}
	
	public void menuList(){
		list();
		System.out.println("[��ʾ�����롰0�����Է�����һ��]");
		int instruction = input.nextInt();
		while(instruction != 0){
			System.out.println("������Ч����������������");
			instruction = input.nextInt();
		}
		System.out.println("������һ��");
		menu();
	}

	/**ɾ����ǰ�Ĵ���嵥���ú����������ֳ��ϡ�
	 * <p>����1 ��������嵥ʱ��Ҫɾ�����嵥�����˳�ʱ��Ҫɾ���嵥����ʱ��ɺ������Ҫ�������˵���</p>
	 * <p>����2 �����˵�ѡ�������ɾ���ɴ���嵥�Ľ��棬��ʱ��ɾ������Ҫ�������˵���</p>
	 * @param returnMainMenuAfterClear  ��������������Ƿ���Ҫ�������˵�
	 * @return  ��־�Ƿ�������*/
	public boolean menuDestroy(boolean returnMainMenuAfterClear){
		System.out.println("����������д�����Ƿ��������[0]����  [1]���ǣ�");
		int instruction = input.nextInt();
		//boolean reinput = false;
		
		
		if(instruction == 1 && returnMainMenuAfterClear){
			stockList.clear();
			
			//�ǵ��������ǰ�嵥��hasStocked�ѽ����ı�־��Ϊfalse
			hasStocked = false;
			System.out.println("�����ϣ����ڷ�����һ����");
			menu();
			return true;
		}else if(instruction == 1 && !returnMainMenuAfterClear){
			stockList.clear();
			hasStocked = false;
			System.out.println("������! ");
			return true;
		}else {
			//���ָ����0�����������־ͷ������˵�
			//���switch������в�ͬ������Ҳ����ʹ��
			//ѭ�����û���������
			System.out.println("������һ���˵���");
			menu();
			return false;
		}	
		
	}
	
	/**��Ʒ����Ž����Ϣ*/
	private static class Merchandise{
		
		//��Ʒ�ţ�Ψһ��ʶ��Ʒ������Ϊ0
		private int id;
		
		//��Ʒ��
		private String name;
		
		//���и���Ʒ����Ŀ
		private int amount;
				
		public Merchandise(int id){
			this.id = id;
		}
			
		public Merchandise(int id,String name,int amount){
			this.id = id;
			this.name = name;
			this.amount = amount;
		}
		
		/**���أ���{@link #id} ��ʾһ����Ʒ��*/
		public boolean equals(Object obj){
			 Merchandise m = (Merchandise)obj;			
			return id == m.id;
		}		
	}	
}

