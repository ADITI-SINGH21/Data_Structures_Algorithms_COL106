//recursive randomised Radix Sort for an positive integer sequence 
// uses count sort and is better
// O(d*(n+k)) time complexity
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.regex.*;
public class RadixSort{
	public static void radixSort(int[] a){
		int max = 0;
		int n = a.length;
		for (int i=0; i<n ; i++) {
			if (a[i]>max) {
				max = a[i];
			}
		}
		int pos = 1;
		for (pos=1; (max/pos)>0 ; pos*=10 ) { // passes equal to total number of digits
			CountSort(a,n,pos); // start from one's place then to max digit's place
		}
	}
	public static void CountSort(int[] a,int n,int pos){ // count sort function uses same logic as in CountSort code before 
		int[] count = new int[10];
		int[] b = new int[n];
		for (int i=0; i<n ; i++ ) {
			count[(a[i]/pos)%10]++; // a slight modification is used as here we will check place by place insted of whole number
		}
		for (int i=1; i<10 ; i++ ) {
			count[i]+=count[i-1];
		}
		for (int i=(n-1); i>=0; i-- ) {
			b[--count[(a[i]/pos)%10]] = a[i];
		}
		for (int i=0; i<n ; i++ ) {
			a[i] = b[i];
		}
	}
	public static void main(String[] args) {
		RadixSort object = new RadixSort();
		int[] array = {438,8,530,90,88,231,11,45,677,199};
		System.out.print("Initial Array: ");
		for (int i=0;i<array.length;i++) {
			System.out.print(array[i]+" ");
		}System.out.println();
		object.radixSort(array);
		System.out.print("After RadixSort: ");
	 	for (int i=0;i<array.length;i++) {
	 		System.out.print(array[i]+" ");
	 	}System.out.println();
	}
}