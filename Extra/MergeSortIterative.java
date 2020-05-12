//Iterative MergeSort for a generic comparable type E
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.regex.*;
 public class MergeSortIterative<E extends Comparable>{
 	public static<E extends Comparable> void mergeSort(E[] arr){
 		E[] in = (E[]) new Object[arr.length];
 		System.arraycopy(arr,0,in,0,in.length);// copying input of array in temporay array
 		E[] out = (E[]) new Object[arr.length];// output array
 		E[] temp; // reference array to perform swapping
 		int n = in.length;
 		for (int i=1;i<n;i*=2){// each insertion sorts all length-2*i runs
 			for (int j=0;j<n;j+=2*i ){// each iteration merges two length-i pairs
 				merge(in,out,j,i);//merge from in to out two length-i runs at j
 			}
 			// swapping of arrays for next iteration
 			temp = in;
 			in = out;
 			out = temp;
 		}
 		System.arraycopy(in,0,arr,0,in.length); // recopy to original array
 	}
 	public static<E extends Comparable> void merge(E[] in, E[] out, int start,int inc){// merges 2 sub arrays, speicified by a start and increment
 		//merge in[start..start+inc-1] and in[start+inc..start+2*inc-1]
 		int x = start;// index into run for 1st subarray(#1)
 		int end1 = Math.min(start+inc,in.length);// boundary for run#1
 		int end2 = Math.min(start+2*inc,in.length);// boundary for run#2
 		int y = start+inc;// index into run for 2nd subarray(#2){can be out of array index}
 		int z = start;// inde into the out array
 		while((x<end1)&&(y<end2)){
 			if (in[x].compareTo(in[y])>=0) {//comparing if in[y]>in[x]
 				out[z++] = in[x++];
 			}
 			else out[z++] = in[y++]; //else
 		}
 		if(x<end1){//if run#1 didn't finish
 			System.arraycopy(in,x,out,z,end1-x);
 		}
 		else if(y<end2){// if run#2 didn't finish
 			System.arraycopy(in,y,out,z,end2-y);
 		}
 	}
 	public static void main(String[] args) {
	 	Integer[] array = {85,24,63,45,17,31,96,50};
	 	System.out.print("Initial Array: ");
		for (int i=0;i<array.length;i++) {
			System.out.print(array[i]+" ");
		}System.out.println();
	 	MergeSortIterative<Integer> sort = new MergeSortIterative<Integer>();
	 	sort.mergeSort(array);
	 	System.out.print("After MergeSort(Iterative): ");
	 	for (int i=0;i<array.length;i++) {
	 		System.out.print(array[i]+" ");
	 	}System.out.println();	
 	} 
 }