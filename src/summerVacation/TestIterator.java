package summerVacation;

import java.util.AbstractList;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

//学习复合框架里关于线性表的部分
//使用简单的迭代器
public class TestIterator<E> {
	
	public static final int INITIAL_CAPACITY = 16;
	
	//private E[] data = new E[INITIAL_CAPACITY]; 错误，泛型消除
	private E[] data = (E[]) new Object[INITIAL_CAPACITY];
	
	public int size = 0;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(Integer.toBinaryString(10));
		//java.util.AbstractList
		//java.util.AbstractSequentialList
		//Collections
		//ArrayList
		//LinkedList
		//Queue
		//Comparator
		TestIterator<String> list = new TestIterator<String>();
		list.add("炮");
		list.add("炮");
		list.add("车");
		list.add("士");
		Iterator<String> myIterator = list.iterator();
		while(myIterator.hasNext())
			System.out.print(myIterator.next() + " ");
		System.out.println();
		
	}
	
	public void add(E num){
		if(size < INITIAL_CAPACITY){
			data[size] = num;
			size ++;
		}		
	}
	
	public int size(){
		return size;
	}
	
	public E get(int index){
		return data[index];
	}
	
    public Iterator<E> iterator() {
        return new Itr();
    }
	
    private class Itr implements Iterator<E> {
        /**
         * Index of element to be returned by subsequent call to next.
         */
        int cursor = 0;

        /**
         * Index of element returned by most recent call to next or
         * previous.  Reset to -1 if this element is deleted by a call
         * to remove.
         */
        int lastRet = -1;

        /**
         * The modCount value that the iterator believes that the backing
         * List should have.  If this expectation is violated, the iterator
         * has detected concurrent modification.
         */
     //   int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size();
        }

        public E next() {

            try {
                int i = cursor;
                
                E next = get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {

                throw new NoSuchElementException();
            }
        }

    }
}