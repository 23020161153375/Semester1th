package summerVacation;

//import java.awt.Point;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;

public class MyArrayList<E> extends AbstractList<E> {

	//设定初始容量为16
	public static final int INITIAL_CAPACITY = 16;
	
	//private E[] data = new E[INITIAL_CAPACITY]; 错误，泛型消除
	private E[] data = (E[]) new Object[INITIAL_CAPACITY];
	
	//当前拥有的元素个数
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
		/*测试MyArrayList */
		MyArrayList<String> list = new MyArrayList<String>();
		list.add(new String("卒"));
		System.out.println("[1] " + list);
		list.add(new String("车"));
		System.out.println("[2] " + list);		
		list.add(new String("卒"));
		System.out.println("[3] " + list);
		String s1 = list.set(0, new String("马"));
		System.out.println("[4] " + list + " set: " + s1);
		 list.remove("卒");		 
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
		
		//注意，temp引用不会改变
		//因为执行此句后data[index]
		//所指向堆空间的地址已经发生改变
		//因为堆空间不会在声明时预先开辟
		data[index] = element;
		return temp;
		
	}
	
	public void add(int index,E element){
		//确保有足够的空间存储数据
		ensureCapacity();

		//向右移动从index到size - 1位置的元素
		for(int i = size - 1;i >= index;i -- )
			data[i + 1] = data[i];
		
		//将元素插入index位置
		data[index] = element;
		
		//元素数目加1
		size ++;
	}
	
	private void ensureCapacity(){
		if(size >= data.length){//元素个数达到容量上限
			
			//提高现有容量至size * 2 + 1
			E[] newData = (E[]) new Object[size * 2 + 1];
			
			//复制原有数据至新的数组（容器）中
			System.arraycopy(data, 0, newData, 0, size);
			
			//使data指向新数组
			data = newData;
		}	
	}

	public E remove(int index){
		//取出要移除的元素
		E temp = data[index];
		
		//将从index + 1 到 size - 1 位置上的元素左移
		for(int i = index + 1;i < size;i ++)
			data[i - 1] = data[i];
		
		//将size - 1位置置空
		data[size - 1] = null;
		
		//元素个数-1
		size --;
		
		return temp;
	}
}
