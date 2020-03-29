package col106.assignment3.Election;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Vector;

public class Election implements ElectionInterface {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		ElectionDriverCode EDC = new ElectionDriverCode();
		System.setOut(EDC.fileout());
	}
	/*
	 * end code
	 */
	
	//write your code here 
	int length = 1000000;
	BST<String,Integer> bst = new BST<>();
	Candidate[] candidate = new Candidate[length];
    public void insert(String name, String candID, String state, String district, String constituency, String party, String votes){
		//write your code here
		int n = 0;
		for (n=0;n<length ;n++ ) {
		 	if (candidate[n]==null) break;
		}
		candidate[n] = new Candidate(); 
		candidate[n].candID = candID;
		candidate[n].name = name;
		candidate[n].state = state;
		candidate[n].district = district;
		candidate[n].constituency = constituency;
		candidate[n].party = party;
		candidate[n].votes = votes;
		bst.insert(candidate[n].candID,Integer.parseInt(candidate[n].votes));
	}
	public void updateVote(String name, String candID, String votes){
		//write your code here
		int n =0;
		for (n=0;n<candidate.length;n++) {
			if (candidate[n].candID.equals(candID)) break;
		}
		candidate[n].votes = votes;
		bst.update(candidate[n].candID,Integer.parseInt(candidate[n].votes));
	}
	public void topkInConstituency(String constituency, String k){
		//write your code here
		Heap<String,Integer> heap = new Heap<>();
		Candidate[] pointer = new Candidate[length];
		int n;
		int s=0;   
		for (n=0; n<candidate.length ; n++) {
			if (candidate[n]==null) break;
			else if (candidate[n].constituency.equals(constituency)) {
				pointer[s] = new Candidate();
				pointer[s].candID = candidate[n].candID;
				pointer[s].name = candidate[n].name;
				pointer[s].state = candidate[n].state;
				pointer[s].district = candidate[n].district;
				pointer[s].constituency = candidate[n].constituency;
				pointer[s].party = candidate[n].party;
				pointer[s].votes = candidate[n].votes;
				s++;
			}
			else continue;
		}
		/*for (int i = 0; i<pointer.length ;i++ ) {
			if(pointer[i]==null) break;
			System.out.println(pointer[i].candID+","+pointer[i].party+","+pointer[i].name+","+pointer[i].state+","+pointer[i].votes+","+pointer[i].district+","+pointer[i].constituency);
		}*/
		int j=0;
		while(j<s){
			heap.insert(pointer[j].candID,Integer.parseInt(pointer[j].votes));
			j++;
		}
		j=0;
		int m = Integer.parseInt(k);
		if (s<=m) {
			while(j<s){
				int maxv = heap.extractMax();
				String maxvote = Integer.toString(maxv);
				for (int i=0;i<s;i++ ) {

					if(pointer[i]==null) break;
					if (pointer[i].votes.equals(maxvote)){
							System.out.println(pointer[i].name + ", "  + pointer[i].candID + ", " + pointer[i].party);
						}
				}
				j++;
			}
		}
		else{
			while(j<m){
				int maxv = heap.extractMax();
				String maxvote = Integer.toString(maxv);
				if(maxvote==null)
					break;
				for (int i=0;i<s;i++ ) {

				if(pointer[i]==null) break;
				if (pointer[i].votes.equals(maxvote)){
						System.out.println(pointer[i].name + ", "  + pointer[i].candID + ", " + pointer[i].party);
					}
				}
				j++;
			}
		} 

	}
	public void leadingPartyInState(String state){
		//write your code here
		Heap<String,Integer> heap = new Heap<>();
		for(int i=0; i<length; ++i){
			if(candidate[i]==null)
				break;
			if(candidate[i].state.equals(state)){
				if(heap.isThere(candidate[i].party)){
					int v = Integer.parseInt(candidate[i].votes)+((int)heap.get(candidate[i].party));
					heap.increase(candidate[i].party, v);
				}
				else{
					int v = Integer.parseInt(candidate[i].votes);
					heap.insert(candidate[i].party, v);
				}
			}
		}
		Vector<String> v = heap.printMaxes();
		String[] arr = new String[v.size()];
		for (int i=0;i<arr.length;i++) {
			arr[i]=v.get(i);
		}
		int n = arr.length; 
        for (int i = 1; i < n; ++i) { 
            String key = arr[i]; 
            int j = i - 1; 
            while (j >= 0 && arr[j].compareTo(key)>0) { 
                arr[j + 1] = arr[j]; 
                j = j - 1; 
            } 
            arr[j + 1] = key; 
        }
        for (int i=0; i<arr.length ;i++ ) {
         	System.out.println(arr[i]);
        } 
	}
	public void cancelVoteConstituency(String constituency){
		//write your code here
		Candidate[] pointer = new Candidate[length];
		int n;
		int s=0;   
		for (n=0; n<length ; n++) {
			if (candidate[n]==null) break;
			if (candidate[n].constituency.equals(constituency)) {
				pointer[s] = new Candidate();
				pointer[s] = candidate[n];
				s++;
			}
		}
		Candidate[] arr = new Candidate[s];
		for (int i=0; i<s ; i++ ) {
			arr[i] = new Candidate();
			arr[i] = pointer[i];
		}
		n = arr.length; 
        for (int i = 1; i < n; ++i) { 
            Candidate key = arr[i]; 
            int j = i - 1; 
            while (j >= 0 && arr[j].candID.compareTo(key.candID)>0) { 
                arr[j + 1] = arr[j]; 
                j = j - 1; 
            } 
            arr[j + 1] = key; 
        } 
		int b=0;
		while(b<s){
			bst.delete(arr[b].candID);
			b++;
		}
		for (b=0;b<candidate.length;b++ ) {
			if (candidate[b]==null) break;
		}
		// System.out.println(b);
		int i=0;
		for (n=0;n<length;n++ ) {
			if (candidate[n]==null) break;
			if (candidate[n].constituency.equals(constituency)){
				// candidate = removeTheElement(candidate, n);
				for (i=n; i<candidate.length-1 ; i++ ) {
					if (candidate[i+1]==null) break;
						candidate[i].candID = candidate[i+1].candID;
						candidate[i].name = candidate[i+1].name;
						candidate[i].state = candidate[i+1].state;
						candidate[i].district = candidate[i+1].district;
						candidate[i].constituency = candidate[i+1].constituency;
						candidate[i].party = candidate[i+1].party;
						candidate[i].votes = candidate[i+1].votes;
				}
				candidate[i]=null;
				n--;
			}	
		}
		for (b=0;b<candidate.length;b++ ) {
			if (candidate[b]==null) break;
		}
		// System.out.println(b);
	}
	public void leadingPartyOverall(){
		//write your code here
		Heap<String,Integer> heap = new Heap<>();
		for(int i=0; i<candidate.length; ++i){
			if(candidate[i]==null)
				break;
			if(heap.isThere(candidate[i].party)){
				int v = Integer.parseInt(candidate[i].votes)+((int)heap.get(candidate[i].party));
				heap.increase(candidate[i].party, v);
			}
			else{
				int v = Integer.parseInt(candidate[i].votes);
				heap.insert(candidate[i].party, v);
			}
		}
		Vector<String> v = heap.printMaxes();
		String[] arr = new String[v.size()];
		for (int i=0;i<arr.length;i++) {
			arr[i]=v.get(i);
		}
		int n = arr.length; 
        for (int i = 1; i < n; ++i) { 
            String key = arr[i]; 
            int j = i - 1; 
            while (j >= 0 && arr[j].compareTo(key)>0) { 
                arr[j + 1] = arr[j]; 
                j = j - 1; 
            } 
            arr[j + 1] = key; 
        }
        for (int i=0; i<arr.length ;i++ ) {
         	System.out.println(arr[i]);
        } 

	}
	public boolean isThere(String key){
		for(int i=0; i<length; i++){
			if(candidate[i]==null)
				return false;
			else if(candidate[i].party==key)
				return true;
		}
		return false;
	}

	public void voteShareInState(String party,String state){
		//write your code here
		int p1 = 0;
		int total = 0;
		for(int i=0; i<candidate.length; ++i){
			if(candidate[i]==null)
				break;
			if(candidate[i].state.equals(state)){
				total += Integer.parseInt(candidate[i].votes);
				if(candidate[i].party.equals(party))
					p1 += Integer.parseInt(candidate[i].votes);
			}	
		}
		int x = (p1*100)/total;
		System.out.println(x);
		//System.out.printf("%.0f",((x * 10) / 10.0));
		//System.out.println();
		//System.out.println(Math.round(x * 10.0) / 10.0);
	}

	public Candidate[] removeTheElement(Candidate[] arr, int index) 
    { 
        Candidate[] anotherArray = new Candidate[length];
        for (int i = 0, k = 0; i < length; i++) {
        	if(arr[i]==null)
        		break; 
            if (i == index) { 
                continue; 
            }
            anotherArray[k] = new Candidate();
            anotherArray[k++] = arr[i]; 
        } 
        return anotherArray; 
    } 

	public void printElectionLevelOrder() {
		//write your code here
		Queue<BSTNode> queue=new LinkedList<BSTNode>();
        queue.add(bst.root); 
        while (queue.isEmpty()!=true){
            BSTNode printer = queue.poll();
            int i;
            for (i=0; i<candidate.length ; i++ ) {
            	if(candidate[i]==null) break;
            	if (candidate[i].candID.equals(printer.key)) break;
            }
            System.out.println(candidate[i].name+", "+candidate[i].candID+", "+candidate[i].state+", "+candidate[i].district+", "+candidate[i].constituency+", "+candidate[i].party+", "+candidate[i].votes);
            if(printer.left!=null) queue.add(printer.left);
            if(printer.right!=null) queue.add(printer.right);
        }

	}
}
class Candidate{
	public String name;
	public String candID;
	public String state;
	public String district;
	public String constituency;
	public String party;
	public String votes;
}
class BST<T extends Comparable, E extends Comparable>  {
	public BSTNode root; 
    public void insert(T key, E value) {
		//write your code here
		if (root == null) {
		 	root = new BSTNode(key,value);
		 }
		 else
		 {
		 	root.insert(key,value);
		 } 

    }
    public E get(T key){
    	//return  (E)root.get(key);
        Queue<BSTNode> queue=new LinkedList<BSTNode>();
        BSTNode referance = root;
        queue.add(root);
        while(queue.peek().key!=key){
            referance = queue.poll();
            if (referance.left!=null) queue.add(referance.left);
            if (referance.right!=null) queue.add(referance.right);
        }
        return (E)(queue.peek().value);
    }

    public void update(T key, E value) {
		//write your code here
        //System.out.println("Updating key "+ key +" to value " + value+ ":"); 
		delete(key);
		insert(key,value);
    }

    public BSTNode leftchild(BSTNode node){
    	if(node.left==null) return null;
    	else return node.left;
    }

    public BSTNode rightchild(BSTNode node){
    	if(node.right==null) return null;
    	else return node.right;
    }

    public BSTNode findParent(BSTNode node){
        if (node == root) return null;
        BSTNode l = root;
    	while (true){
    		if (l.value.compareTo( node.value )< 0) {
                if(l.right.value.compareTo( node.value )==0)
                    break;
    			l = l.right;
    		}
    		else {
                if(l.left.value.compareTo( node.value )==0)
                    break;
    			l = l.left;
    		}
    	}
    	return l;
    }
    public BSTNode searchNode(T key){
        E value = get(key);
        if(value == root.value) return root;
        else{
            BSTNode pointer = root;
            while(pointer.value!=value){
                if (pointer.value.compareTo(value ) < 0) {
                    pointer = pointer.right;
                }
                else pointer = pointer.left;
            }
            return pointer;
        }
    }

    public BSTNode InorderSuccessor(BSTNode node){
    	BSTNode a = rightchild(node);    	
    	if (node == null) {
    		return null;
    	}
    	while(a.left!=null){
    		a = a.left;
    	}
    	return a;
    }

    public void delete(T key) {
		//write your code here
		BSTNode del = searchNode(key);
		if (del.left==null && del.right==null) {
            BSTNode p = findParent(del);
            if(p==null) root = null;
            else if (del == p.left) p.left = null;
            else p.right = null;
		}
		else if (del.left!=null && del.right!=null) {
			BSTNode n = InorderSuccessor(del);
            BSTNode x = findParent(n);
            del.value = n.value;
            del.key = n.key;
            if(n==del.right){
                del.right = n.right;
            }
            else{
                x.left = n.right;
            }
		}
		else if (del.left!=null && del.right==null) {
            BSTNode x = findParent(del);
            if(x==null)
                root = del.left;
            else{
                if(x.left==del)
                    x.left = del.left;
                else
                    x.right = del.left;
            }
		}
		else{
			BSTNode x = findParent(del);
            if(x==null)
                root = del.right;
            else{
                if(x.left==del)
                    x.left = del.right;
                else
                    x.right = del.right;
            }
			
		}
    }
    public void printBST () {
		//write your code here
        Queue<BSTNode> queue=new LinkedList<BSTNode>();
        queue.add(root); 
        while (queue.isEmpty()!=true){
            BSTNode printer = queue.poll();
            System.out.println(printer.key+", "+printer.value);
            if(printer.left!=null) queue.add(printer.left);
            if(printer.right!=null) queue.add(printer.right);
        }//System.out.println();
    }

}

class BSTNode<T extends Comparable, E extends Comparable> { 
	

    public E getValue() {
        return null;
    }

    public E value;
	public T key ;
	public BSTNode left, right;

    public BSTNode(T key, E value){
    	this.key = key;
    	this.value = value;
    }
    public void insert(T key, E value){
    	if (value.compareTo( this.value ) < 0 ) {
    		if (left!=null) {
    			left.insert(key,value);
    		}
    		else{
    			left = new BSTNode(key,value);
    		}
    	}
    	else if (value.compareTo( this.value ) >= 0) {
    		if (right!=null) {
    			right.insert(key,value);
    		}
    		else{
    			right = new BSTNode(key,value);
    		}
    	}
    	else this.value=value;
    }
}
class Heap<T extends Comparable, E extends Comparable>{
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

	public Vector<T> printMaxes(){
		if(heaparray[0]==null)
			return null;
		E max = (E)heaparray[0].value;
		Vector<T> t= new Vector<>(); 
		while(heaparray[0].value.compareTo(max)==0){
			t.add((T)heaparray[0].key);
			delete((T)(heaparray[0].key));
			if (heaparray[0]==null) break;
		}
		return t;
	}

	public E extractMax() {
		//write your code here
		if(heaparray[0]==null)
			return null;
		E max = (E)heaparray[0].value;
		delete((T)heaparray[0].key);
		//return null;
		return max;
	}	

	public T extractM() {
		//write your code here
		if(heaparray[0]==null)
			return null;
		T max = (T)heaparray[0].key;
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
	public boolean isThere(T key){
		for(int i=0; i<length; ++i){
			if(heaparray[i]==null)
				return false;
			else if(heaparray[i].key.compareTo(key)==0)
				return true;
		}
		return false;
	}
	public E get(T key){
		int i=0;
		while(true){
			if(heaparray[i].key.compareTo(key)==0)
				break;
			i++;
		}
		return (E)heaparray[i].value;
	}
	public void increase(T key, E value){
		int i=0;
		while(true){
			if(heaparray[i].key.compareTo(key)==0)
				break;
			i++;
		}
		heaparray[i].value = value;
		int parent;
		int n = i;
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
