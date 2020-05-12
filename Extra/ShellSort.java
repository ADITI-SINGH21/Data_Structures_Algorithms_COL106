// Shell Sort code for a generic comparable E
// Similar to insertion sort but only difference is that instead of comparing each number one by one it compares gap by gap which becomes till 1 starting by N/2
// Better performence than insertion sort
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.regex.*;
 public class ShellSort<E extends Comparable>{
 	public static<E extends Comparable> void shellSort(E[] a){
 		int n = a.length;  
 		int gap = 0; 
 		for (gap=n/2; gap>=1 ; gap/=2){ // will take gap from N/2 and reduce it by two until it becomes 1 
 			for (int j= gap; j<n ; j++) { // will put 2 counters i&j one from starting and one at ending
 				for (int i=j-gap; i>=0; i-=gap) {
 					if (a[i+gap].compareTo(a[i])>0) {// no swapping if a[i+gap] > a[i] and break from the loop
 						break;
 					}
 					else{ // swap in this case and also check for previous element at the position difference of gap from ith position
 						E temp = a[i+gap];
 						a[i+gap] = a[i];
 						a[i] = temp;
 					}
 				}
 			}
 		}
 	}
 	public static void main(String[] args) {
 		Integer[] array = {85,24,63,45,17,31,96,50};
	 	System.out.print("Initial Array: ");
		for (int i=0;i<array.length;i++) {
			System.out.print(array[i]+" ");
		}System.out.println();
	 	ShellSort<Integer> sort = new ShellSort<Integer>();
	 	sort.shellSort(array);
	 	System.out.print("After ShellSort: ");
	 	for (int i=0;i<array.length;i++) {
	 		System.out.print(array[i]+" ");
	 	}System.out.println();
 	}
}