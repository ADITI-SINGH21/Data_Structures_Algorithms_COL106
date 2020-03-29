package col106.assignment3.BST;
//import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
public class BST<T extends Comparable, E extends Comparable> implements BSTInterface<T, E>  {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout()); 
	}
	/*
	 * end code
	 * start writing your code from here
	 */	
	//write your code here
	private BSTNode root; 
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
        //System.out.println("Printing BST in level order :"); 
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

    /*public boolean isThere(T key, BSTNode n){
    	if(n==null)
    		return false;
    	else if(n.key.equals(key))
    		return true;
    	else
    		return ((isThere(key, n.left))||(isThere(key, n.right)));
    }

    public E get(T key)
    {
        if(this==null){
        	return null;
        }
        else if ( this.key.equals( key ) )
        {
            return value;
        }

        else if (isThere(key, this.left))
        {
            E x = (E)(left.get( key ));
            return x;
        }
        else
        {
            return (E)(right.get( key ));
        }
    }*/

}