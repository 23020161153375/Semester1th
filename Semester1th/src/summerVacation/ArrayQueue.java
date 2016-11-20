package summerVacation;

import java.util.Iterator;

public class ArrayQueue<E> {
	private MyArrayList<E> list 
	= new MyArrayList<E>();

public static void main(String[] args) {
	// TODO Auto-generated method stub
	ArrayQueue<String> queue = new ArrayQueue<String>();
	queue.enqueue("LEDµÆ");
	System.out.println("[ 1] "+  queue);
	queue.enqueue("³éÖ½");
	System.out.println("[ 2] "+  queue);
	queue.enqueue("¼ÇÊÂ±¾");
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

public void enqueue(E e){
	list.add(e);
}

public E dequeue(){
	return list.remove(0);
}

public E element(){
	return list.get(0);
}

public int getSize(){
	return list.size();
}

public boolean isEmpty(){
	return list.isEmpty();
}

public void clear(){
	list.clear();
}

public Iterator<E> iterator(){
	return list.iterator();
}

public String toString(){
	return "Queue: " + list.toString();
}	
}
