import java.util.Scanner;
import java.util.Arrays;
class EmptyStackException extends Exception{}
public class MyStack<T>{
	private T[] array;
	private int length = 1000000;
	private int a=0;
	public MyStack(){
		array = (T[]) new Object[length];
	}
	@SuppressWarnings("unchecked")
	public void push(T value){
		/*if(size() == length){
			create(); 
		}*/
		array[a] = value;
		a++;
	}
	public int size(){
		return a;
	}
	public void create(){
		T[] newarray = (T[]) new Object[length*2];
		for(int i=0;i<length;i++){
            newarray[i] = array[i];
        }
		array = newarray; 
		length*=2;
	}
	public void reduce(){
		if (size() <= ((length/2)/2)){
			length = length/2;
			T[] newarray = (T[]) new Object[length];
			for(int i=0;i<length;i++){
	            newarray[i] = array[i];
	        }
			array = newarray; 
		}
	}
	public T pop() throws EmptyStackException{
		if (this.isEmpty() == true){
			throw new EmptyStackException();
		}
		else{
			a--;
			T del = array[a];
			//reduce();
			return del;
		}

	}
	public T top() throws EmptyStackException{
		if (this.isEmpty() == true){
			throw new EmptyStackException();
		}
		else{
			T up=array[a-1];
			return up;
		}
	}
	public boolean isEmpty(){
		return a<=0;
	}
	public void show(){
		for (int i=0;i<size();i++ ) {
			System.out.println(array[i]);
		}System.out.println();
	}
	public static void main(String[] args) {
		MyStack<Integer> check = new MyStack<Integer>();
		check.push(3);
		check.push(7);
		check.push(8);
		check.push(10);
		check.push(7);
		check.push(8);
		check.push(10);
		System.out.println(check.isEmpty());
		try{
			//System.out.println(check.top());
			check.show();
			check.pop();
			check.show();
		}
		catch(EmptyStackException e){System.out.println("Error");}
		
	}

}

