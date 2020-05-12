//Recursive MergeSort for a generic comparable type E
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.regex.*;
public class MergeSort<E extends Comparable>{
	public static<E extends Comparable> void mergeSort(Vector<E> in){
		int n = in.size();
		if (n<2) {
			return;// sorted in this case
		}
		//dividing the vector into two parts
		Vector<E> in1 = new Vector<E>();
		Vector<E> in2 = new Vector<E>();
		int i = 0;
		while(i<n/2){//shifting 1st half elements to in1
			in1.add(in.remove(0));
			i++;
		}
		while(!in.isEmpty()){//shifting 2nd half elements to in2
			in2.add(in.remove(0));
		}
		mergeSort(in1);//recur
		mergeSort(in2);//recur
		merge(in1,in2,in);//conquer
	}
	public static<E extends Comparable> void merge(Vector<E> in1,Vector<E> in2, Vector<E> in){
		while(!in1.isEmpty()&&!in2.isEmpty()){
			if ((in1.get(0)).compareTo(in2.get(0))<=0) {//filling into in
				in.add(in1.remove(0));
			}
			else
				in.add(in2.remove(0));
		}
		while(!in1.isEmpty()){
			in.add(in1.remove(0));//extracting the remaining elements of in1
		}
		while(!in2.isEmpty()){//extracting the remaining elements of in2
			in.add(in2.remove(0));
		}
	}
	public static void main(String[] args) {
		Vector<Integer> arr = new Vector<>();
		arr.add(85);
		arr.add(24);
		arr.add(63);
		arr.add(45);
		arr.add(17);
		arr.add(31);
		arr.add(96);
		arr.add(50);
		System.out.print("Initial Array: ");
		for (int i=0;i<arr.size();i++) {
			System.out.print(arr.get(i)+" ");
		}System.out.println();
		MergeSort<Integer> func = new MergeSort<Integer>();
		func.mergeSort(arr);
		System.out.print("After Merge Sort: ");
		for (int i=0;i<arr.size();i++) {
			System.out.print(arr.get(i)+" ");
		}System.out.println();
	}	
}

