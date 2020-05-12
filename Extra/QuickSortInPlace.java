// recursive inplace quickSort (Can be only done if all elements are distinct) for a generic comparable type e
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.regex.*;
 public class QuickSortInPlace<E extends Comparable>{
 	public static<E extends Comparable> void quickSort(E[] s){
 		if (s.length<2) {
 			return;
 		}
 		quickSortStep(s,0,s.length-1);//recursive sort method
 	}
 	public static<E extends Comparable> void quickSortStep(E[] s, int left,int right){
 		if (left>=right) {//when the pivots crosses return
 			return;
 		}
 		E temp; //temp reference use for swapping  
 		E pivot = s[right]; // setting pivot 
 		int lindex = left; //to scan rightward
 		int rindex = right-1; //to scan leftward
 		while(lindex<=rindex){//scan right until larger than pivot
 			while((lindex<=rindex)&&s[lindex].compareTo(pivot)<=0){
 				lindex++;
 			}
 			while((lindex<=rindex)&&s[rindex].compareTo(pivot)>=0){//scan leftward to find an element smaller than the pivot
 				rindex--;
 			}
 			if (lindex<rindex) {//both elements found
 				temp = s[rindex];
 				s[rindex] = s[lindex];//swap these elements
 				s[lindex] = temp;
 			}
 		}
 		//swapping pivot with the element at lindex
 		temp = s[right];
 		s[right] = s[lindex];
 		s[lindex] = temp;
 		//pivot now at lindex
 		quickSortStep(s,left,lindex-1);//recur
 		quickSortStep(s,lindex+1,right);//recur
 	}
 	public static void main(String[] args) {
	 	Integer[] array = {85,24,63,45,17,31,96,50};
	 	System.out.print("Initial Array: ");
		for (int i=0;i<array.length;i++) {
			System.out.print(array[i]+" ");
		}System.out.println();
	 	QuickSortInPlace<Integer> sort = new QuickSortInPlace<Integer>();
	 	sort.quickSort(array);
	 	System.out.print("After QuickSort(InPlace): ");
	 	for (int i=0;i<array.length;i++) {
	 		System.out.print(array[i]+" ");
	 	}System.out.println();	
 	}
 }
