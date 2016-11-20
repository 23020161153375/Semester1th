package summerVacation;

//import java.awt.Point;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;

public class MyArrayList<E> extends AbstractList<E> {

	//�趨��ʼ����Ϊ16
	public static final int INITIAL_CAPACITY = 16;
	
	//private E[] data = new E[INITIAL_CAPACITY]; ���󣬷�������
	private E[] data = (E[]) new Object[INITIAL_CAPACITY];
	
	//��ǰӵ�е�Ԫ�ظ���
	private int size = 0;
	
	public MyArrayList(){		
	}
	
	public MyArrayList(E[] objects){
		for(int i = 0;i < objects.length;i ++){
			add(objects[i]);
		}
	}
	
	public MyArrayList(Collection<? extends E> c){
		Iterator<? extends E> ite = c.iterator();
		while(ite.hasNext())
			add(ite.next());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*����MyArrayList */
		MyArrayList<String> list = new MyArrayList<String>();
		list.add(new String("��"));
		System.out.println("[1] " + list);
		list.add(new String("��"));
		System.out.println("[2] " + list);		
		list.add(new String("��"));
		System.out.println("[3] " + list);
		String s1 = list.set(0, new String("��"));
		System.out.println("[4] " + list + " set: " + s1);
		 list.remove("��");		 
		System.out.println("[5] " + list);
		list.remove(0);
		System.out.println("[6] " + list);
		/*
		MyArrayList<Point> list1 = new MyArrayList<Point>();
		list1.add(new Point(0,0));
		System.out.println(list1);
		list1.add(new Point(0,1));
		System.out.println(list1);
		Point p = list1.set(0, new Point(1,1));
		System.out.println(list1 + " " + p);
		*/
	}

	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return data[index];
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}
	
	public E set(int index,E element){
		
		E temp = get(index);
		
		//ע�⣬temp���ò���ı�
		//��Ϊִ�д˾��data[index]
		//��ָ��ѿռ�ĵ�ַ�Ѿ������ı�
		//��Ϊ�ѿռ䲻��������ʱԤ�ȿ���
		data[index] = element;
		return temp;
		
	}
	
	public void add(int index,E element){
		//ȷ�����㹻�Ŀռ�洢����
		ensureCapacity();

		//�����ƶ���index��size - 1λ�õ�Ԫ��
		for(int i = size - 1;i >= index;i -- )
			data[i + 1] = data[i];
		
		//��Ԫ�ز���indexλ��
		data[index] = element;
		
		//Ԫ����Ŀ��1
		size ++;
	}
	
	private void ensureCapacity(){
		if(size >= data.length){//Ԫ�ظ����ﵽ��������
			
			//�������������size * 2 + 1
			E[] newData = (E[]) new Object[size * 2 + 1];
			
			//����ԭ���������µ����飨��������
			System.arraycopy(data, 0, newData, 0, size);
			
			//ʹdataָ��������
			data = newData;
		}	
	}

	public E remove(int index){
		//ȡ��Ҫ�Ƴ���Ԫ��
		E temp = data[index];
		
		//����index + 1 �� size - 1 λ���ϵ�Ԫ������
		for(int i = index + 1;i < size;i ++)
			data[i - 1] = data[i];
		
		//��size - 1λ���ÿ�
		data[size - 1] = null;
		
		//Ԫ�ظ���-1
		size --;
		
		return temp;
	}
}
