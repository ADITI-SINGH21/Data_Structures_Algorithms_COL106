//The below code is an implementation of a Splay Tree which takes generic Key and value
//Splaying is done after Insertion, Deletion and Search operations
//Deletion Uses Bottom Up Approach
//There might be some mistakes in the code please let me know if any
import java.util.Vector;
import java.util.Queue;
import java.util.LinkedList;
public class SplayTree<K extends Comparable, V>{
	public SplayNode root;
	public int rcount;
	public SplayTree(){//tree constructor
		root = null;
        rcount = 0;
	}
	public V insert(K key,V value){ // insertion in splay tree
		if (root==null) { // if tree is empty then insert in root
            root = new SplayNode(key,value);
            root.parent = null;
            return null;
        }
        if (!find(key)){ //if the key doesnot exist then return null
            SplayNode node = new SplayNode(key,value);
            SplayNode p = null;
            SplayNode x = root;
            //iteratively insertion like in BST   
            while(x!=null){
                p = x;
                if (key.compareTo(x.key)<0){
                    if (x.left==null) {
                        x.left = node;
                        break;
                    }
                    x = x.left;
                } 
                else{
                    if (x.right==null) {
                        x.right = node;
                        break;
                    }
                    x = x.right;
                } 
            }
            node.parent = p;
            Splay(node);// will perform splaying, i.e. make this node the root after certain operations 
            return null;
        }
        //if the key exist then just replace the value with new one and return new value
        SplayNode x = search(key);
        V vput = (V)x.value;
        x.value = value;
        return vput;
	}
	public void Splay(SplayNode node){//Splaying Operation make the node new root
		SplayNode pointer = node;
		if (pointer==root) {
			return;
		}
		if (GrandParent(pointer)==null) {//if no grandparent that is the parent is root
			if (ChildType(pointer)=="LEFT") {//zig(or zag) rotation
				RotateRight(pointer);
			}
			else{ //zig rotation
				RotateLeft(pointer);
			}
			return;
		}
		while(pointer!=root){//until the pointer is made root
			if (GrandParent(pointer)!=null) {
				if (GrandChildType(pointer).equals("LEFT-LEFT")) {//zig-zig rotate
					SplayNode temp = RotateRight(pointer.parent);
					pointer = RotateRight(temp.left);
				}
				else if (GrandChildType(pointer).equals("RIGHT-RIGHT")) {//zig-zig (or zag-zag) rotate 
					SplayNode temp = RotateLeft(pointer.parent);
					pointer = RotateLeft(temp.right);
				}
				else if (GrandChildType(pointer).equals("LEFT-RIGHT")) {//zig-zag rotate
					pointer = RotateLeft(pointer);
					pointer = RotateRight(pointer);
				}
				else if(GrandChildType(pointer).equals("RIGHT-LEFT")){//zig-zag (or zag-zig) rotate
					pointer = RotateRight(pointer);
					pointer = RotateLeft(pointer);
				}
				continue;
			}
			else{
				if (ChildType(pointer)=="LEFT") {//zig(or zag) rotation
					pointer=RotateRight(pointer);
				}
				else{ //zig rotation
					pointer=RotateLeft(pointer);
				}
				return;
			}
		}
		return;
	}
	public SplayNode GrandParent(SplayNode node){ // to figure out the grandparent of node 
		if (node.parent==null||node.parent.parent==null) {
			return null;
		}
		return node.parent.parent;
	}
	public String GrandChildType(SplayNode node){ // find the child type's of node and it's parent
		if (GrandParent(node)==null) {
			return "NOGRANDPARENT";
		}
		return ChildType(node.parent)+"-"+ChildType(node);
	}
	public SplayNode RotateRight(SplayNode pointer){ // right rotation
		rcount++; //counter of number of rotations
		pointer = pointer.parent;
		SplayNode temp = pointer.parent;
		SplayNode a = pointer.left;
    	pointer.left = a.right;
    	if (a.right!=null) 
    		pointer.left.parent = pointer;
    	a.parent = pointer.parent;
    	if (temp==null) 
    		root = a;
    	else if (temp.left==pointer) 
    		temp.left= a;
    	else 
    		temp.right = a;
    	a.right = pointer;
    	pointer.parent = a;
       	return a;	
    }

	public SplayNode RotateLeft(SplayNode pointer){ // left rotation
		rcount++;
		pointer = pointer.parent;
		SplayNode temp = pointer.parent;
		SplayNode a = pointer.right;
    	pointer.right = a.left;
    	if (a.left!=null) 
    		pointer.right.parent = pointer;
    	a.parent = pointer.parent;	
    	if (temp==null) 
    		root = a;
    	else if (temp.right==pointer) 
    		temp.right= a;
    	else 
    		temp.left = a;
    	a.left = pointer;
    	pointer.parent = a;
    	return a;
	}

	public SplayNode RotateRightLeft(SplayNode pointer){ // right left rotation
		SplayNode rightrotate = RotateRight(pointer.left);
		rightrotate = RotateLeft(rightrotate);
		return rightrotate;
	}
	public SplayNode RotateLeftRight(SplayNode pointer){ // left right rotation
		SplayNode leftrotate = RotateLeft(pointer.right);
		leftrotate = RotateRight(leftrotate); 
		return leftrotate;
	}
	public String ChildType(SplayNode node){ // find if the node is left or right child of it's parent
		if (node==root) {
			return null;
		}
		else if (node.parent.right!=null&&node.parent.right==node) {
			return "RIGHT";
		}
		else 
			return "LEFT";
	}
	public SplayNode search(K key){ //finds node in the tree corresponding to the given key 
		SplayNode cur = root;
        while (cur != null ){
            if (key.compareTo(cur.key)<0 ) cur = cur.left;
            else if (key.compareTo(cur.key)>0) cur = cur.right;
            else return cur;
        }
		return null;
	}
	public void SplaySearch(K key){
		SplayNode node = search(key);
		Splay(node);
	}
	public boolean find(K key){ // rturns if a node with a particular key is in tree or not
		SplayNode cur = root;
        while (cur != null){
            if (key.compareTo(cur.key)<0 ) cur = cur.left;
            else if (key.compareTo(cur.key)>0) cur = cur.right;
            else return true;
        }
        return false;
	}
	public V remove(K key){ // deletion from splay tree using Bottom-Up approach
		if (!find(key)){// will do splaying is the key is not present
            SplayNode x = root;
            //Will do splaying of the node which would have been parent of the key if it was there 
            while(x!=null){
                if (key.compareTo(x.key)<0){
                    if (x.left==null) {//found the parent node x
                        Splay(x);//Spalying Operation
                        return null;
                    }
                    x = x.left;
                } 
                else{
                    if (x.right==null) {
                        Splay(x);
                        return null;
                    }
                    x = x.right;
                } 
            }
		}
        SplayNode del = search(key);
        V vdel = (V)del.value;
        SplayNode x = null;
        SplayNode p = null;
        // deletion procedure will be same as in Binary Search Tree
        p = del.parent;
        if (del.left==null && del.right==null) {//deletion of a leaf node
        	
        	if(p==null) root = null;
            else if (del == p.left) p.left=null;
            else p.right=null;
		}
		else if (del.left!=null && del.right!=null) {//deletion of an internal node with 2 children
			SplayNode n = InorderSuccessor(del);
           	x = n.parent;
           	del.value = n.value;
            del.key = n.key;
            if(n==del.right){
            	del.right = n.right;
            	if (del.right!=null) {
            		del.right.parent=del;
            	}
	        }
	       	else{
	             x.left = n.right;
	             if (x.left!=null) {
	             	x.left.parent=x.left;
	             }
	        }           
		}
		else if (del.left!=null && del.right==null) {//deletion of an internal node with 1 child
			if(p==null){
				root = del.left;
			}
	        else if(p.left==del){
	            del.parent.left = del.left;
	           	del.left.parent = del.parent;
	        }
	        else{
	            del.parent.right = del.left; 
	           	del.left.parent = del.parent;
	        }		
		}
		else{
			if (p==null) {//deletion of an internal node with 1 child
				root = del.right;
			}
			else if(p.left==del){
	            del.parent.left = del.right;
	           	del.right.parent = del.parent;	
	        }
	        else{
	            p.right = del.right;
	            del.right.parent = del.parent;
	        }
		}
		// Will do additional Splaying of the parent after deletion 
		if (p!=null){
            	Splay(p);
        }
		return vdel;
	}
	public SplayNode InorderSuccessor(SplayNode node){//finds inorder successor of a node    	
    	if (node == null) {
    		return null;
    	}
    	SplayNode a = node.right;
    	while(a.left!=null){
    		a = a.left;
    	}
    	return a;
    }
	public void displayTree(SplayNode node, int level){ //displays red black tree  
		if(node==null)
             return;
        displayTree(node.right, level+1);
        if(level!=0){
            for(int i=0;i<level-1;i++)
                System.out.print("|\t");
                System.out.println("|-------"+node.key + "(" + node.value + ")");
        }
        else
            System.out.println(node.key + "(" + node.value + ")");
        displayTree(node.left, level+1);
	}
	public int Rotations(){ // total number of rotations till now
		return rcount;
	}
	public int height(SplayNode node){ // max height of a node in the tree
		if (node==null) 
			return 0;
        int lh = 0;
        int rh = 0;
        if(node.left!=null) 
        	lh = height(node.left);
        if(node.right!=null) 
        	rh = height(node.right);
        if(lh>rh) 
        	return lh+1;
        else 
        	return rh+1;
	}
	public int GetHeight(){ // gives the total height of tree
		return height(root);
	}
	public void printBFS(){ // prints level order traversal of the binary tree
		Queue<SplayNode> queue=new LinkedList<SplayNode>();
        if(root==null) return;
        queue.add(root);
        while (queue.isEmpty()!=true){
            SplayNode printer = queue.poll();
            if(printer!=null){
            	System.out.print(printer.key+" "+printer.value+" ");
            }
            if(printer.left!=null) 
            	queue.add(printer.left);
            if(printer.right!=null) 
            	queue.add(printer.right);
        }
        System.out.println();
	}
	public class SplayNode<K extends Comparable, V>{ // SplayNode class
		public K key;
		public V value;
		public SplayNode left, right, parent;
		public SplayNode(){} // empty node constructer 
		public SplayNode(K key, V value){ // node constructor with parameter key and value
			this.key = key;
			this.value = value;
		}
	}
	public static void main(String[] args) {
		//15 10 17 7 13 16
		SplayTree<Integer,String> ctree = new SplayTree<>();
		ctree.insert(15,"a");
		ctree.insert(10,"b");
		ctree.insert(17,"c");
		ctree.insert(7,"d");
		ctree.insert(13,"e");
		ctree.insert(16,"f");
		ctree.printBFS();
		System.out.println(ctree.Rotations());
		System.out.println(ctree.GetHeight());
		ctree.displayTree(ctree.root,0);
		ctree.remove(7);
		System.out.println(ctree.Rotations());
		System.out.println(ctree.GetHeight());
		ctree.displayTree(ctree.root,0);
		ctree.SplaySearch(15);
		System.out.println(ctree.Rotations());
		System.out.println(ctree.GetHeight());
		ctree.displayTree(ctree.root,0);
		//10 18 7 15 16 30 25 40 60 2 1 70
		SplayTree<Integer,String> tree = new SplayTree<>();
		tree.insert(10,"a");
		tree.insert(18,"b");
		tree.insert(7,"c");
		tree.insert(15,"d");
		tree.insert(16,"e");
		tree.insert(30,"f");
		tree.insert(25,"g");
		tree.insert(40,"h");
		tree.insert(60,"i");
		tree.insert(2,"j");
		tree.insert(1,"k");
		tree.insert(70,"l");
		tree.printBFS();
		System.out.println(tree.Rotations());
		System.out.println(tree.GetHeight());
		tree.displayTree(tree.root,0);
		tree.remove(25);
		System.out.println(tree.Rotations());
		System.out.println(tree.GetHeight());
		tree.displayTree(tree.root,0);
		tree.SplaySearch(40);
		tree.SplaySearch(60);
		System.out.println(tree.Rotations());
		System.out.println(tree.GetHeight());
		tree.displayTree(tree.root,0);

		//22 33 4 5 6 7 8 55 3 2 1 6 
		SplayTree<Integer,String> tree1 = new SplayTree<>();
		tree1.insert(22,"a");
		tree1.insert(33,"b");
		tree1.insert(4,"c");
		tree1.insert(5,"d");
		tree1.insert(6,"e");
		tree1.insert(7,"f");
		tree1.insert(8,"g");
		tree1.insert(55,"h");
		tree1.insert(3,"i");
		tree1.insert(2,"j");
		tree1.insert(1,"k");
		tree1.printBFS();
		System.out.println(tree1.Rotations());
		System.out.println(tree1.GetHeight());
		tree1.displayTree(tree1.root,0);
		//5 16 22 45 2 10 18 30 50 12 1
		SplayTree<Integer,String> ntree = new SplayTree<>();
		ntree.insert(5,"a");
		ntree.insert(16,"b");
		ntree.insert(22,"c");
		ntree.insert(45,"d");
		ntree.insert(2,"e");
		ntree.insert(10,"f");
		ntree.insert(18,"g");
		ntree.insert(30,"h");
		ntree.insert(50,"i");
		ntree.insert(12,"j");
		ntree.SplaySearch(22);
		ntree.remove(22);
		ntree.displayTree(ntree.root,0);
		ntree.insert(1,"k");
		System.out.println(ntree.Rotations());
		System.out.println(ntree.GetHeight());
		ntree.displayTree(ntree.root,0);

	}

}