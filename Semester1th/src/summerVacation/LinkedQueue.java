package summerVacation;

import java.util.Iterator;

/**链式队列*/
public class LinkedQueue<E> {
	//因为使用的是默认构造函数，所以有必要在
	//这里完成初始化工作。
	private MyLinkedList<E> list 
		= new MyLinkedList<E>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedQueue<String> queue = new LinkedQueue<String>();
		queue.enqueue("LED灯");
		System.out.println("[ 1] "+  queue);
		queue.enqueue("抽纸");
		System.out.println("[ 2] "+  queue);
		queue.enqueue("记事本");
		System.out.println("[ 3] "+  queue);
		Iterator<String> iter = queue.iterator();
		System.out.print("[ 4] Iteration: " );
		while(iter.hasNext())
			System.out.print(iter.next() + " ");
		System.out.println();
		System.out.println("[ 5] " + queue.dequeue());
		System.out.println("[ 6] " + queue.dequeue());
		System.out.println("[ 7] "+  queue);

		
	}

	/**将一个元素添加到队列的末尾
	 * @param e 待添加的元素。*/
	public void enqueue(E e){
		list.addLast(e);
	}
	
	/**移除队头元素并返回它
	 * @return 被移除的队头元素
	 * @throws NoSuchElementException 如果队列为空*/
	public E dequeue(){
		return list.removeFirst();
	}

	/**返回队头元素，但在队列中不移除它
	 * @return 队头元素
	 * @throws NoSuchElementException 如果队列为空*/
	public E element(){
		return list.getFirst();
	}
	
	/**返回队列所含元素数目*/
	public int getSize(){
		return list.size();
	}
	
	/**判空
	 * @return 如果队列为空，返回“true”，否则返回“false”*/
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	/**清空队列*/
	public void clear(){
		list.clear();
	}
	
	/**返回该队列的一个迭代器，方便从队头到队尾对其进行遍历
	 * @return 关于该队列的一个迭代器*/
	public Iterator<E> iterator(){
		return list.iterator();
	}
	
	public String toString(){
		return "Queue: " + list.toString();
	}	
}

