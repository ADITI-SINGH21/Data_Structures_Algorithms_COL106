package col106.assignment3.Heap;

public class Heap<T extends Comparable, E extends Comparable> implements HeapInterface <T, E> {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		HeapDriverCode HDC = new HeapDriverCode();
		System.setOut(HDC.fileout());
	}
	/*
	 * end code
	 */
	
	// write your code here
	int length = 1000000;
	HeapArray[] heaparray = new HeapArray[length];
	public void insert(T key, E value) {
		//write your code here
		int n;
		for (n =0;n<length;n++ ) {
			if (heaparray[n]==null) break; 
		}
		heaparray[n] = new HeapArray();
		heaparray[n].value = value;
		heaparray[n].key = key;
		int i = n;
		int parent;
		while (i>0){
			parent = (i-1)/2;
			if (heaparray[parent].value.compareTo(heaparray[i].value)<0) {
				swap(heaparray[parent],heaparray[i]);
				i = parent;
			}
			else return;
		}
		  
	}
	public void swap(HeapArray a, HeapArray b){
	 	E temp1 = (E)a.value;
	 	a.value = b.value;
	 	b.value = temp1;
	 	T temp2 = (T)a.key;
	 	a.key = b.key;
	 	b.key = temp2;
	 }

	public E extractMax() {
		//write your code here
		E max = (E)heaparray[0].value;
		delete((T)heaparray[0].key);
		//return null;
		return max;
	}

	public void delete(T key) {
		//write your code here
		int n;
		int j;
		for (n =0;n<length;n++ ) {
			if (heaparray[n].key==key) break; 
		}
		for (j = 0; j< length ;j++ ) {
			if (heaparray[j]==null) break;
		}
		heaparray[n].key = heaparray[j-1].key;
		heaparray[n].value = heaparray[j-1].value;
		heaparray[j-1] = null;
		int child = n;
		while(child<j-1 && heaparray[child]!=null){
			if (heaparray[2*n+1]==null && heaparray[2*n+2]!=null){ 
				child=2*n+1;
				if ((heaparray[child].value).compareTo(heaparray[n].value)>0) {
					swap(heaparray[child],heaparray[n]);
					n = child;
				}
				else break;
			}
			else if(heaparray[2*n+1]!=null && heaparray[2*n+2]==null){
				child=2*n+1;
				if ((heaparray[child].value).compareTo(heaparray[n].value)>0) {
					swap(heaparray[child],heaparray[n]);
					n = child;
				}
				else break;
			}
			else if (heaparray[2*n+1]==null && heaparray[2*n+2]==null) break;
			else{
				child = greater(2*n+1,2*n+2);
				if ((heaparray[child].value).compareTo(heaparray[n].value)>0) {
					swap(heaparray[child],heaparray[n]);
					n = child;
				}
				else break;
			}
		}
		
	}
	public int greater(int a , int b){
		if ((heaparray[a].value).compareTo(heaparray[b].value)>0) return a;
		else return b; 
	}
	public void increaseKey(T key, E value) {
		//write your code here
		//System.out.println("Updating key " +key+" to value "+value+":");
		int n;
		for (n =0;n<length;n++ ) {
			if (heaparray[n].key==key) break; 
		}
		heaparray[n].value = value;
		int parent;
		while(n>0){
			parent = (n-1)/2;
			if ((heaparray[parent].value).compareTo(heaparray[n].value)<0){
				swap(heaparray[parent],heaparray[n]);
				n = parent;
			}
			else break;
		}
	}  

	public void printHeap() {
		//write your code here
		for (int i = 0;i<length ;i++ ) {
		 	if (heaparray[i]==null) break;
		 	else{
		 		System.out.println(heaparray[i].key+", "+heaparray[i].value);
		 	}
		 } 

	}	
}
class HeapArray<T extends Comparable, E extends Comparable> { 
	public E value;
	public T key ;
 }