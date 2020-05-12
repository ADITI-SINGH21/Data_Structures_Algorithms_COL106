class Node<E extends Comparable>{
	public E e;
	public Node next;
	public Node(E e){
		this.e = e;
		this.next = null;
	}
}
public class LinkedList<E extends Comparable>{
	public Node head;
	public int size;
	public LinkedList(){
		head = null;
		size = 0;
	}
	public Node getElement(int j){
		Node v = head;
		for (int i=1; i<j ;i++ ) {
			v = v.next;
		}
		return v;
	}
	public void findElement(int j){
		System.out.println(getElement(j).e);
	}
	public void insertFirst(E e){
		Node v = new Node(e);
		if (head == null) {
			head = v;
			size++;
		}
		else{
			v.next = head;
			head = v;
			size++;
		}
	}
	public void insert(E e,int j){
		Node v = new Node(e);
		if (j<=0&&j>size) {
			System.out.println("Invalid Input");
			return;
		}
		if (j==1) {
			insertFirst(e);
			return;
		}
		Node x = getElement(j-1);
		v.next = x.next;
		x.next = v;
		size++;
	}
	public void insertBefore(E e,int j){
		if (j==1) {
			insertFirst(e);
			return;
		}
		if (j<=0&&j>size+1) {
			System.out.println("Invalid Input");
			return;
		}
		Node x = new Node(e);
		Node v = getElement(j);
		Node t = head;
		int s = 1;
		while(t!=v){
			s++;
			t=t.next;
		}
		t = getElement(s-1);
		x.next = t.next;
		t.next = x;
		size++;
	}
	public void delete(int j){
		if (j<=0&&j>size+1) {
			System.out.println("Invalid Input");
			return;
		}
		if (j==1) {
			deleteHead();
			return;
		}
		Node del = getElement(j);
		if (del.next == null) del = null; 
		else{
			Node v = getElement(j-1);
			v.next = del.next;
		}	
	}
	public void deleteHead(){
		head = head.next;
	}
	public boolean isEmpty(){
		return head == null ? true : false;
	}
	public void show(){
		int i=0;
		Node v = head;
		while(v!=null){
			System.out.print(v.e+" ");
			v = v.next;
		}System.out.println();
	}
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.insertFirst(3);
		list.show();
		list.insertFirst(2);
		list.insertFirst(7);
		list.findElement(2);
		list.insertFirst(12);
		list.insertFirst(21);
		list.insertFirst(19);
		list.findElement(4);
		list.insert(17,3);
		list.insertBefore(13,3);
		list.show();
		list.delete(4);
		list.deleteHead();
		list.show();
		System.out.println(list.isEmpty());
	}

}
