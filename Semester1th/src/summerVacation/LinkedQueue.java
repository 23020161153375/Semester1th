package summerVacation;

import java.util.Iterator;

/**��ʽ����*/
public class LinkedQueue<E> {
	//��Ϊʹ�õ���Ĭ�Ϲ��캯���������б�Ҫ��
	//������ɳ�ʼ��������
	private MyLinkedList<E> list 
		= new MyLinkedList<E>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedQueue<String> queue = new LinkedQueue<String>();
		queue.enqueue("LED��");
		System.out.println("[ 1] "+  queue);
		queue.enqueue("��ֽ");
		System.out.println("[ 2] "+  queue);
		queue.enqueue("���±�");
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

	/**��һ��Ԫ����ӵ����е�ĩβ
	 * @param e ����ӵ�Ԫ�ء�*/
	public void enqueue(E e){
		list.addLast(e);
	}
	
	/**�Ƴ���ͷԪ�ز�������
	 * @return ���Ƴ��Ķ�ͷԪ��
	 * @throws NoSuchElementException �������Ϊ��*/
	public E dequeue(){
		return list.removeFirst();
	}

	/**���ض�ͷԪ�أ����ڶ����в��Ƴ���
	 * @return ��ͷԪ��
	 * @throws NoSuchElementException �������Ϊ��*/
	public E element(){
		return list.getFirst();
	}
	
	/**���ض�������Ԫ����Ŀ*/
	public int getSize(){
		return list.size();
	}
	
	/**�п�
	 * @return �������Ϊ�գ����ء�true�������򷵻ء�false��*/
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	/**��ն���*/
	public void clear(){
		list.clear();
	}
	
	/**���ظö��е�һ��������������Ӷ�ͷ����β������б���
	 * @return ���ڸö��е�һ��������*/
	public Iterator<E> iterator(){
		return list.iterator();
	}
	
	public String toString(){
		return "Queue: " + list.toString();
	}	
}

