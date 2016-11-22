package summerVacation;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**����ʵ�ֵ��Ƿ�ѭ������������ͷ��㡣��ͷָ���⣬������
  	*βָ�롣*/
public class MyLinkedList<E> extends MyAbstractList<E> {

	//ͷָ��
	private Node<E> head;
	
	//βָ��
	private Node<E>tail;
	
	/**���캯��������һ��������*/
	public MyLinkedList(){		
	}
	
	/**���캯��������һ�����������򽫸��������е�Ԫ����ӵ�������*/	
	public MyLinkedList(E[] objects){	
		for(int i = 0;i < objects.length;i ++)
			addLast(objects[i]);
	}
	
	/**������*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] fruits = new String[3];
		fruits[0] = new String("�㽶");
		fruits[1] = new String("����");
		fruits[2] = new String("����");
		MyLinkedList<String> list = new MyLinkedList<String>(fruits);
		System.out.println("[1] " + list);
		list.addFirst(new String("����"));
		System.out.println("[2] " + list);
		list.add(2,new String("����"));
		System.out.println("[3] " + list 
				+" ���е�1��4�������һ��Ԫ�طֱ��� " 
				+list.getFirst()+"��"+  list.get(3)+"��" + list.getLast());
		System.out.println("[4] " + "��һ������λ��: "+list.indexOf("����")
		+" ���һ������λ�ã�"+list.lastIndexOf("����") );
		System.out.println("[5] " + "�ϰ壬��������" + list.contains("����")
		+" �������������Ŷ�����" + list.contains("���Ŷ���"));
		System.out.println("[6] " + "ǰ���������һ��������Ҫ��");
		list.remove(1);
		System.out.println( list);
		list.removeFirst();
		System.out.println( list);
		list.removeLast();
		System.out.println( list);
		System.out.println("[7] " + "�����������Ŷ�����������������");
		String egg = list.set(0, "���Ŷ���");
		System.out.println( list + " �һ���ˣ�" +egg);
		Iterator<String> iter = list.iterator();
		System.out.println("[8] " + "������ ");
		while(iter.hasNext())
			System.out.print(iter.next() + " ");
		
	}

	/**��Ԫ��e��ӵ������ͷ��
	 * @param e �������Ԫ��*/
	public void addFirst(E e){
		add(0,e);
	}
	
	/**��Ԫ�ز��������β
	 * @param e ������Ԫ��*/
	public void addLast(E e){
		add(size,e);
	}
	
	
	/**��Ԫ�ز��뵽�����ƶ�λ��
	 * @param index Ԫ�ز��뵽�����е�λ��
	 * @param e �������Ԫ��
	 * @throws IndexOutOfBoundsException ����±곬����Χ��index < 0 || index > size()��*/
	@Override
	public void add(int index, E e) {
		// TODO Auto-generated method stub
		checkPositionIndex(index);
		
		//�����½��
		Node<E> newNode = new Node<E>(e);
		
		//�����������
		if(isEmpty()){
			head = tail = newNode;
			newNode.next = null;
		}else if(index == 0){
			newNode.next = head;
			head = newNode;
		}else if(index == size){
			tail.next = newNode;
			tail = newNode;
		}else{//�����м䡣��ʵ�ϣ���ʱsize >= 2
			Node<E> current = head;
			
			//�ҵ�index - 1λ���ϵĽ��
			for(int i = 0;i < index - 1;i ++)
				current = current.next;
			
			newNode.next = current.next;
			current.next = newNode;
		}
		//���Ԫ����Ŀ��1
		size ++;
	}
	
	/**���������*/
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		//����һ�߱�������һ�ߡ��ͷš����ռ�
		head = tail = null;			
		
		//�ǵ�Ԫ����Ŀ��0
		size = 0;
	}


	@Override
	public boolean contains(E e) {
		// TODO Auto-generated method stub		
		return indexOf(e) != -1;
	}
	
	/**��ȡ������ĵ�һ��Ԫ�أ������׳��쳣
	 * @return ��������գ����ظ�����ĵ�һ��Ԫ��
	 * @throws NoSuchElementException ����Ϊ��*/
	public E getFirst(){
		try{
			return get(0);
		}catch(IndexOutOfBoundsException e){
			throw new NoSuchElementException(); 
		}
	}
	
	/**��ȡ���������һ���أ������׳��쳣
	 * @return ��������գ����ظ��������һ��Ԫ��
	 * @throws NoSuchElementException ����Ϊ��*/
	public E getLast(){
		try{
			return get(size - 1);
		}catch(IndexOutOfBoundsException e){
			throw new NoSuchElementException(); 
		}
	}
	
	/**��ȡ����ָ��λ�õ�Ԫ�أ�
	 * @param index ָ��������λ�õ��±꣬��0��ʼ
	 * @return �����ڸ�λ���ϵ�Ԫ��
	 * @throws IndexOutOfBoundsException ����±곬����Χ
	 * (index < 0 || index > size() - 1)*/
	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		checkElementIndex(index);
		
		Node<E> current = head;
		for(int i = 0;i < index;i ++)
			current = current.next;
		return current.element;
	}

	
	@Override
	public int indexOf(E e) {
		// TODO Auto-generated method stub
		Node<E> current = head;
		int index = 0;
		
		//����1����current 1= null��������2��!current.element.equals(e)��
		//����1Ӧ��������2��ǰ�棬��������á�&&�����ӣ�
		//	��Ϊǰ���Ǻ��ߵı�Ҫ������
		//	����1�ĺ����ǣ�����û�н�����
		//�������������������
		//	1.����Ϊ�� 2.current = tail.next
		while(current != null && !current.element.equals(e)){
			current = current.next;
			index ++;
		}
		
		
		return current == null ? -1:index;
	}

	@Override
	public int lastIndexOf(E e) {
		// TODO Auto-generated method stub
		Node<E> current = head;
		int index = -1;
		for(int i = 0;current != null;i ++){
			if(current.element.equals(e))
				index = i;
			current = current.next;
		}
		
		return index;
	}

	/**��������ʱ��ɾ������ĵ�һ��Ԫ�أ������ظ�Ԫ�ص�ֵ��
	 * @return ��ɾ��Ԫ�ص�ֵ
	 * @throws NoSuchElementException ����Ϊ��*/
	public E removeFirst(){
		try{
			return remove(0);
		}catch(IndexOutOfBoundsException e){
			throw new NoSuchElementException(); 
		}
	}
	
	/**��������ʱ��ɾ���������Ԫ�أ������ظ�Ԫ�ص�ֵ��
	 * @return ��ɾ��Ԫ�ص�ֵ
	 * @throws NoSuchElementException ����Ϊ��*/
	public E removeLast(){
		try{
			return remove(size - 1);
		}catch(IndexOutOfBoundsException e){
			throw new NoSuchElementException(); 
		}
	}

	@Override
	public E remove(int index) {
		// TODO Auto-generated method stub
		checkElementIndex(index);
		
		Node<E> removedNode ;
		
		/*ͨ�����±���
		 *�Ѿ�����˵�������ա�*/
		 if(size == 1){
			removedNode = head;
			head = tail = null;
		}else if(index == 0){//ɾ����ͷ
			removedNode = head;
			head = head.next;
		}else{//0 < index <= size - 1 && size >= 2
			Node<E> previous  = head;
			for(int i = 0;i < index - 1;i ++)
				previous = previous.next;
			removedNode = previous.next;
			previous.next = removedNode.next;
			
			//���ɾ�����Ǳ�β���ͶԱ�βָ�����޸ġ�
			//�����βָ�벻��
			tail = index == size - 1 ?previous: tail;
		}
		size --;	
					
		return removedNode.element;
	}

	
	/**����������ָ��λ�õ�Ԫ�ء�
	 * @param index
	 * @param e
	 * @return �������޸�ǰ��λ��ԭ�е�Ԫ��
	 * @throws IndexOutOfBoundsException*/
	@Override
	public E set(int index, E e) {
		// TODO Auto-generated method stub
		checkElementIndex(index);
		
		Node<E> current = head;
		for(int i = 0;i < index;i++)
			current = current.next;
		E oldElement = current.element;
		current.element = e;
		return oldElement;
	}
	
	private void checkPositionIndex(int index){
		if(index < 0 || index > size)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));		
	}
	
	private void checkElementIndex(int index){
		if(index < 0 || index > size - 1)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));		
	}

	private String outOfBoundsMsg(int index){
		return "Index: " + index + " Size: " + size;
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder("[ ");
		Node<E> current = head;
		for(int i = 0;i < size;i ++){			
			result.append(current.element.toString());
			result.append(i == size - 1?"":", ");
			current = current.next;					
		}
		result.append(" ]");	
		
		
		return result.toString();
	}
	
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new MyIterator();
	}
	
	private class MyIterator implements Iterator<E>{
		//��AbstractLIst���
		
		int cursor;
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return cursor != size;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			try{
				int i = cursor;
				cursor ++;
				return get(i);		
			}catch(IndexOutOfBoundsException e){
				//��׽���±�Խ�硱�쳣���ٻ�����Ԫ�ز����ڡ��쳣
				//��Ϊ�ú�������ʱû���õ��±ꡣ
				throw new NoSuchElementException();
			}
		}
	}

	private static class Node<E>{
		E element;
		Node<E> next;
		public Node(E element){
			this.element = element;
		}
	}
}
