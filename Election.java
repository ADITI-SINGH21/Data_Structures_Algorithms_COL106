package col106.assignment3.Election;z
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Queue;
import java.util.LinkedList;
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
		for (n=0;n<length;n++) {
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
		for (n=0; n<length ; n++) {
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
		int j=0;
		while(pointer[j]!=null){
			heap.insert(pointer[j].candID,Integer.parseInt(pointer[j].votes));
			j++;
		}
		j=0;
		int m = Integer.parseInt(k);
		while(j<m){
			int maxv = heap.extractMax();
			String maxvote = Integer.toString(maxv);
			if(maxvote==null)
				break;
			for (int i=0;i<length;i++ ) {

			if(pointer[i]==null) break;
			if (pointer[i].votes.equals(maxvote)){
					System.out.println(pointer[i].name + " , "  + pointer[i].candID + " , " + pointer[i].party);
				}
			}
			//else continue;
			j++;
		} 

	}
	public void leadingPartyInState(String state){
		//write your code here
		Candidate[] pointer = new Candidate[length];
		int n;
		int s=0;
		for (n=0;n<length;n++ ) {
			if (candidate[n]==null) break;
			if (candidate[n].state.equals(state)) {
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
		}
		int k=0;
		Candidate[] share = new Candidate[s];
		for (int i=0; i<s ;i++ ) {
			if (pointer[i]==null) break;
			if (isUnique(share,pointer[i].party)==true) {
				share[k] = new Candidate();
				share[k].candID= pointer[i].candID;
				share[k].party = pointer[i].party;
				share[k].name = pointer[i].name;
				share[k].state = pointer[i].state;
				share[k].district = pointer[i].district;
				share[k].constituency = pointer[i].constituency;
				share[k].votes = null;
				k++;
			}
		}
		int sum =0;
		for (int i=0;i<k ;i++ ) {
			if (share[i]==null) break;
			sum=0;
			for (int j =0;j<s ;j++ ) {
				if (pointer[j].party==share[i].party) {
					sum+=Integer.parseInt(pointer[j].votes);
				}
				else continue;
			}
			share[i].votes = Integer.toString(sum);
		}
		sum = 0;
		Candidate[] lead = new Candidate[1];
		int max = Integer.parseInt(share[0].votes); 
		for (int i =0; i<k ;i++ ) {
			if (share[i]==null) break;
			if (max<Integer.parseInt(share[i].votes)) {
				max = Integer.parseInt(share[i].votes);
				lead[0] = new Candidate();
				lead[0].votes = share[i].votes;
				lead[0].candID= share[i].candID;
				lead[0].party = share[i].party;
				lead[0].name = share[i].name;
				lead[0].state = share[i].state;
				lead[0].district = share[i].district;
				lead[0].constituency = share[i].constituency;
			}
			else continue;
		}
		System.out.println(lead[0].party);
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
				pointer[s].candID = candidate[n].candID;
				pointer[s].name = candidate[n].name;
				pointer[s].state = candidate[n].state;
				pointer[s].district = candidate[n].district;
				pointer[s].constituency = candidate[n].constituency;
				pointer[s].party = candidate[n].party;
				pointer[s].votes = candidate[n].votes;
				s++;
			}
		}
		s=0;
		while(pointer[s]!=null){
			bst.delete(pointer[s].candID);
			s++;
		}
		/*for (int j=n; j<length-1 ;j++ ) {
					if(candidate[j]==null) break;
					//candidate[j] = candidate[j+1];
					candidate[j].candID = candidate[j+1].candID;
					candidate[j].name = candidate[j+1].name;
					candidate[j].votes = candidate[j+1].votes;
					candidate[j].state = candidate[j+1].state;
					candidate[j].district = candidate[j+1].district;
					candidate[j].constituency = candidate[j+1].constituency;
					candidate[j].party = candidate[j+1].party; 
		}*/
	}
	public void leadingPartyOverall(){
		//write your code here
		Heap<String,Integer> heap = new Heap<>();
		Candidate[] pointer = new Candidate[length];
		int n;
		int s=0;
		for (n=0; n<length; n++ ) {
			if (candidate[n]==null) break;
			if (isUnique(pointer,candidate[n].party)){
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
		}
		int sum =0;
		for (int i=0;i<s ;i++ ) {
			if (pointer[i]==null) break;
			sum=0;
			for (int j =0;j<candidate.length ;j++ ) {
				if (candidate[j]==null) break;
				if (candidate[j].party.equals(pointer[i].party)) {
					sum+=Integer.parseInt(candidate[j].votes);
				}
				else continue;
			}
			pointer[i].votes = Integer.toString(sum);
		}
		Candidate[] lead = new Candidate[1];
		int max = Integer.parseInt(pointer[0].votes); 
		for (int i =0; i<s ;i++ ) {
			if (pointer[i]==null) break;
			if (max<Integer.parseInt(pointer[i].votes)) {
				max = Integer.parseInt(pointer[i].votes);
				lead[0] = new Candidate();
				lead[0].votes = pointer[i].votes;
				lead[0].candID= pointer[i].candID;
				lead[0].party = pointer[i].party;
				lead[0].name = pointer[i].name;
				lead[0].state = pointer[i].state;
				lead[0].district = pointer[i].district;
				lead[0].constituency = pointer[i].constituency;
			}
			else continue;
		}
		System.out.println(lead[0].party);

	}

	public static boolean isUnique(Candidate[] array, String num) {
        for (int i = 0; i < array.length; i++) {
        	if (array[i]==null) break; 
            if (array[i].party == num) {
                return false;
            }
        }
        return true;
    }

	public void voteShareInState(String party,String state){
		//write your code here
		Candidate[] pointer = new Candidate[length];
		int n;
		int s=0;
		for (n=0;n<length;n++ ) {
			if (candidate[n]==null) break;
			if (candidate[n].state.equals(state)) {
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
		}
		int k=0;
		Candidate[] share = new Candidate[s];
		for (int i=0;i<s ;i++ ) {
			share[i] = new Candidate();
		}
		for (int i=0; i<s; i++){
			if (!already(share,pointer[i].party)) {
				share[k].candID = pointer[i].candID;
				share[k].party = pointer[i].party;
				share[k].name = pointer[i].name;
				share[k].state = pointer[i].state;
				share[k].district = pointer[i].district;
				share[k].constituency = pointer[i].constituency;
				share[k].votes = pointer[i].votes;
				k++;
			}
		}
		for (int i=0;i<share.length ;i++ ) {
			int count =0;
			for (int j=0;j<share.length ;j++ ) {
				if (share[j].party.equals(share[i].party)) {
					count++;
				}
			}
			if (count>1) {
				share = removeTheElement(share,i);
			}
		}
		/*for (int i = 0; i<share.length ;i++ ) {
			System.out.println(share[i].candID+","+share[i].party+","+share[i].name+","+share[i].state+","+share[i].votes+","+share[i].district);
		}*/
		int sum =0;
		for (int i=0;i<share.length ;i++ ) {
			sum=0;
			for (int j =0;j<s ;j++ ) {
				if (pointer[j].party.equals(share[i].party)) {
					sum+=Integer.parseInt(pointer[j].votes);
				}
				else continue;
			}
			share[i].votes = Integer.toString(sum);
		}
		int total = 0;
		for (int i =0; i<share.length ;i++ ) {
			total+=Integer.parseInt(share[i].votes);
			if (share[i].party.equals(party)) sum = Integer.parseInt(share[i].votes);
		}
		float x = (float)sum/total;
		float y = 100*x;
		System.out.println((Math. round(y * 10) / 10.0));

	}
	public static Candidate[] removeTheElement(Candidate[] arr, 
                                          int index) 
    { 
        Candidate[] anotherArray = new Candidate[arr.length - 1];
        for (int i=0;i<anotherArray.length ;i++ ) {
         	anotherArray[i] = new Candidate();
         } 
        for (int i = 0, k = 0; i < arr.length; i++) { 
            if (i == index) { 
                continue; 
            } 
            anotherArray[k++] = arr[i]; 
        } 
        return anotherArray; 
    } 
	public boolean already(Candidate[] array,String obj){
		if (obj == null) {
			return true;
		}
		for (int i=0;i<array.length ;i++ ) {
			if (array[i].party==obj) {
				return true;
			}
			if (array[i]==null)break;
		} return false;
	}
	public void printElectionLevelOrder() {
		//write your code here
		Queue<BSTNode> queue=new LinkedList<BSTNode>();
        queue.add(bst.root); 
        while (queue.isEmpty()!=true){
            BSTNode printer = queue.poll();
            int i;
            for (i=0; i<candidate.length ; i++ ) {
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
        //System.out.println("Deleting element with key "+key+":"); 
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

	public E extractMax() {
		//write your code here
		if(heaparray[0]==null)
			return null;
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
