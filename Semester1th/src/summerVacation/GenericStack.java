package summerVacation;

//用数组实现的栈
public class GenericStack<E> {
  private java.util.ArrayList<E> list = new java.util.ArrayList<E>();

  public int getSize() {
    return list.size();
  }

  public E peek() {
    return list.get(getSize() - 1);
  }

  public void push(E o) {
    list.add(o);
  }

  public E pop() {
    E o = list.get(getSize() - 1);
    list.remove(getSize() - 1);
    return o;
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }
  
  //将栈置空
  public void clear(){
	  list.clear();
  }
  
}
