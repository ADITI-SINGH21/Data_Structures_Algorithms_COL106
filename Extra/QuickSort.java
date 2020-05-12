//recursive randomised QuickSort for a generic comparable type K
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.regex.*;
public class QuickSort<K extends Comparable>{
	public static<K extends Comparable> void quickSort(Vector<K> S){
		if (S.size()<2) {// if size 1 or less than that (already sorted)
			return;
		}
		int n = S.size();
		Random random = new Random();
		int randomIndex = random.nextInt(S.size());
		K p = S.get(randomIndex);//making random element to be the pivot
		//Intialising sequences L,E,G 
		Vector<K> L = new Vector<K>();
		Vector<K> E = new Vector<K>();
		Vector<K> G = new Vector<K>();
		while(!S.isEmpty()){
			n = S.size();
			if (S.get(n-1).compareTo(p)<0) {//if S.last<p 
				L.add(S.remove(n-1));//add into L
			}
			else if (S.get(n-1).compareTo(p)==0) {//else if equal to pivot
				E.add(S.remove(n-1));
			}
			else{//S.get(n-1).compareTo(p)>0 i.e. else greater than the pivot
				G.add(S.remove(n-1));//add to G	
			}
		}
		quickSort(L);//recur on L
		quickSort(G);// recur on G
		while(!L.isEmpty()){//first add sorted L to S
			S.add(L.remove(0));
		}
		while(!E.isEmpty()){// then add E
			S.add(E.remove(0));
		}
		while(!G.isEmpty()){// add G 
			S.add(G.remove(0));
		}
		return;
	}
	public static void main(String[] args) {
		Vector<Integer> arr = new Vector<Integer>();
		arr.add(85);
		arr.add(24);
		arr.add(63);
		arr.add(31);
		arr.add(45);
		arr.add(17);
		arr.add(31);
		arr.add(96);
		arr.add(50);
		System.out.print("Initial Array: ");
		for (int i=0;i<arr.size();i++) {
			System.out.print(arr.get(i)+" ");
		}System.out.println();
		QuickSort<Integer> func = new QuickSort<Integer>();
		func.quickSort(arr);
		System.out.print("After QuickSort: ");
		for (int i=0;i<arr.size();i++) {
			System.out.print(arr.get(i)+" ");
		}System.out.println();
	}
}