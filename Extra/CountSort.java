// Count sort code Implementation for integer arrays only 
// Works in  O(n+k) time and space complexity where n is the size of array and k is the max number in the array
// Can be used when k ~ O(n) 
// Not suitable when k very large compared to n and also when all the numbers are very far from each other 
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.regex.*;
public class CountSort{
	public static void countSort(int[] array){
		int min = 0;
		int max = 0;
		for (int i=0;i<array.length;i++ ) {// to find out max and min of the array
			if (max<array[i]) {
				max = array[i];
			}
			if (min>array[i]) {
				min = array[i];
			}
		}
		if(min>=0) min = 0; // will consider min only if negative
		int k = max-min; // max number updated if min number is negative
		int[] count = new int[k+1]; // count array containing indices from 0 to k+1 initially 0 array
		int n = array.length; 
		int m = count.length;
		int[] result = new int[n]; // result array
		if (min!=0) {
			for (int i=0;i<n;i++) { // updating array incase negative number is min number so that the range remains 0 to k
				array[i] = array[i]-min;
			}	
		}
		for (int i=0; i<n ; i++) { 
			count[array[i]]++; // updating count of indices which is present
		}
		for(int i=1;i<m;i++){
			count[i]=count[i]+count[i-1]; // updating array to store index of start index of the number 
		}
		for (int i=(n-1);i>=0;i--) {
			result[--count[array[i]]] = array[i]; // filling the result array
		}
		if (min!=0) {
			for (int i=0; i<n ; i++) {
				result[i] = result[i]+min; // updating the result array in case there are negative integers
			}
		}
		for (int i=0; i<n ; i++ ) {
			array[i] = result[i]; // finally updating the array
		}
	}
	public static void main(String[] args) {
		CountSort object = new CountSort();
		int[] array = {12,34,1,28,2,3,9,19};
		System.out.print("Initial Array: ");
		for (int i=0;i<array.length;i++) {
			System.out.print(array[i]+" ");
		}System.out.println();
		object.countSort(array);
		System.out.print("After CountSort: ");
	 	for (int i=0;i<array.length;i++) {
	 		System.out.print(array[i]+" ");
	 	}System.out.println();
		int[] arr = {-5,5,0,-9,10,6,-3,8,6};
		System.out.print("Initial Array: ");
		for (int i=0;i<arr.length;i++) {
			System.out.print(arr[i]+" ");
		}System.out.println();
		object.countSort(arr);
		System.out.print("After CountSort: ");
	 	for (int i=0;i<arr.length;i++) {
	 		System.out.print(arr[i]+" ");
	 	}System.out.println();
	}
}