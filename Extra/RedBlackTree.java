//The below code is an implementation of a red black tree which takes generic Key and value
//There might be some mistakes in the code please let me know if any
import java.util.Vector;
import java.util.Queue;
import java.util.LinkedList;
// 3 properties to be taken care of in a RED BLACK TREE:
// Root is always balck;
// Every External Node is black;
// The Children of red node is always black;
// All external node has same black depth;
public class RedBlackTree<K extends Comparable, V>{
	public RedBlackNode root;
	public int rcount;
	public RedBlackTree(){//tree constructor
		root = null;
        rcount = 0;
	}
	public V insert(K key,V value){ // insertion in red-black tree
		if (root==null) { // if tree is empty then insert in root
            root = new RedBlackNode(key,value);
            root.parent = null;
            //root colour is always black;
            root.colour = "BLACK";
            return null;
        }
        if (!search(key)){ //if the key doesnot exist then return null
            RedBlackNode node = new RedBlackNode(key,value);
            RedBlackNode p = null;
            RedBlackNode x = root;
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
            // colour of newly inserted node is always red except when root is inserted
            node.colour = "RED";
            if (node.parent.colour=="RED") {
            	//red-red conflict 
            	CorrectInsert(node);
            	return null;
            }
            return null;
        }
        //if the key exist then just replace the value with new one and return new value
        RedBlackNode x = find(key);
        V vput = (V)x.value;
        x.value = value;
        return vput;
	}
	public void CorrectInsert(RedBlackNode node){ //to make insertion such that it obeys red black tree's properties
		RedBlackNode pointer = node.parent; // making node pointer to be parent of node
		// here we will have 2 cases 
		while(pointer!=root){// will stop once root is reached
			if (sibling(pointer)==null || sibling(pointer).colour=="BLACK") { // CASE 1 (when uncle is null or have colour black)
				if (pointer.left!=null&&pointer.left.colour=="RED") { 
					if (ChildType(pointer)=="LEFT") {// Left-Left Red Conflict
						pointer = RotateRight(pointer); // right rotation
						// recolouring to maintain red black property
						recolour(pointer); 
						recolour(pointer.right);
					}
					else if (ChildType(pointer)=="RIGHT") { // Right Left Conflict
						RotateRightLeft(pointer); // rotating twice
					}
					else return;
				}
				else if (pointer.right!=null&&pointer.right.colour=="RED") {
					if (ChildType(pointer)=="RIGHT") { // Right-Right Red Conflict
						pointer = RotateLeft(pointer); // left rotation
						// recolouring to maintain red black property
						recolour(pointer); 
						recolour(pointer.left);
					}
					else if (ChildType(pointer)=="LEFT") { // Left Right Conflict
						RotateLeftRight(pointer); // rotating twice
					}
					else return;
					
				}
				return; // exits the loop once rotation is complete
			}
			else{ // CASE 2 (when uncle's colour is red)
				// recolouring both pointer and it's parent
				recolour(pointer);
				recolour(sibling(pointer));
				pointer = pointer.parent;// moving pointer to it's parent
				if(pointer!=root){
					// recolouring the pointer if it is not root
					recolour(pointer);
					if (pointer.colour=="RED"&&pointer.parent.colour=="RED") { // red-red conflict
						pointer = pointer.parent; // will again check for the cases
					}
					else return; // return if no conflict
				}
			}
		}
	}
	public RedBlackNode sibling(RedBlackNode node){ // find sibling of a node
		if (node==root) {
			return null;
		}
		if (node.parent.left==node) {
            if (node.parent.right==null) {
            	return null;
            }
            else
            	return node.parent.right;
        }
        else{
            if (node.parent.left==null) {
            	return null;
            }
            else
            	return node.parent.left;
        }
	}
	public RedBlackNode RotateRight(RedBlackNode pointer){ // right rotation
		rcount++; //counter of number of rotations
		pointer = pointer.parent;
		RedBlackNode temp = pointer.parent;
		RedBlackNode a = pointer.left;
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

	public RedBlackNode RotateLeft(RedBlackNode pointer){ // left rotation
		rcount++;
		pointer = pointer.parent;
		RedBlackNode temp = pointer.parent;
		RedBlackNode a = pointer.right;
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

	public void RotateRightLeft(RedBlackNode pointer){ // right left rotation
		RedBlackNode rightrotate = RotateRight(pointer.left);
		// recolouring to maintain red black property
		recolour(rightrotate);
		recolour(rightrotate.parent);
		RotateLeft(rightrotate);
	}
	public void RotateLeftRight(RedBlackNode pointer){ // left right rotation
		RedBlackNode leftrotate = RotateLeft(pointer.right);
		// recolouring to maintain red black property
		recolour(leftrotate);
		recolour(leftrotate.parent);
		RotateRight(leftrotate); 
	}
	public String ChildType(RedBlackNode node){ // find if the node is left or right child of it's parent
		if (node==root) {
			return null;
		}
		else if (node.parent.right!=null&&node.parent.right==node) {
			return "RIGHT";
		}
		else 
			return "LEFT";
	}
	public void recolour(RedBlackNode node){ // recolouring : changing the colour of node from red to black and vice versa
		if (node.colour=="BLACK") {
			node.colour="RED";
		}
		else
			node.colour="BLACK";
	}
	public RedBlackNode find(K key){ //finds node in the tree corresponding to the given key 
		RedBlackNode cur = root;
        while (cur != null ){
            if (key.compareTo(cur.key)<0 ) cur = cur.left;
            else if (key.compareTo(cur.key)>0) cur = cur.right;
            else return cur;
        }
		return null;
	}
	public boolean search(K key){ // rturns if a node with a particular key is in tree or not
		RedBlackNode cur = root;
        while (cur != null){
            if (key.compareTo(cur.key)<0 ) cur = cur.left;
            else if (key.compareTo(cur.key)>0) cur = cur.right;
            else return true;
        }
        return false;
	}
	public V remove(K key){ // deletion from red black tree
		if (!search(key)) // return null if correspoding key doesnot exist 
			return null;
        RedBlackNode del = find(key);
        V vdel = (V)del.value;
        RedBlackNode x = null;
        RedBlackNode p = null;
        // deletion procedure will be same as in Binary Search Tree
        //will delete in same manner as in BST if node to be deleted is red coloured else will do some adjustements
        p = del.parent;
        if (del.left==null && del.right==null) {//deletion of a leaf node
        	if (del.colour=="RED") {// red coloured node to be deleted
            	if (del == p.left) p.left = null;
            	else p.right = null;
            	return vdel;
        	}
        	else{
        		if(p==null) root = null;
            	else if (del == p.left) CorrectDelete(p.left);
            	else CorrectDelete(p.right);
        	}
		}
		else if (del.left!=null && del.right!=null) {//deletion of an internal node with 2 children
			RedBlackNode n = InorderSuccessor(del);
           	x = n.parent;
           	if (n.colour=="RED") {// red coloured node to be deleted
           		del.value = n.value;
            	del.key = n.key;
            	if(n==del.right){
                del.right = n.right;
	            }
	            else{
	                x.left = n.right;
	            }
	            return vdel;
           	}
           	String colour1 = del.colour;
           	String colour2 = n.colour;
           	V v = (V)del.value;
           	K k = (K)del.key;
            del.value = n.value;
            del.key = n.key;
            del.colour = colour1;
            n.value = v;
            n.key = k;
            n.colour = colour2;
            CorrectDelete(n);// else delete the inorder successor after making the swapping
		}
		else if (del.left!=null && del.right==null) {//deletion of an internal node with 1 child
			if (del.colour=="RED") {// red coloured node to be deleted
	            if(p.left==del){
	                del.parent.left = del.left;
	            	del.left.parent = del.parent;
	            }
	            else{
	            	del.parent.right = del.left; 
	            	del.left.parent = del.parent;
	            }
	            return vdel;	
			}
			if(p==null){// root deletion just remove the root and make colour of root to be black
				root = del.left;
				root.colour = "BLACK";
			}
			else
				CorrectDelete(del);
		}
		else{//deletion of an internal node with 1 child
			if (del.colour=="RED") {// red coloured node to be deleted
	           	if(p.left==del){
	                del.parent.left = del.right;
	            	del.right.parent = del.parent;	
	           	}
	            else{
	            	p.right = del.right;
	            	del.right.parent = del.parent;
	            }
				return vdel;
			}
			if(p==null){// root deletion
				root = del.right;
				root.colour = "BLACK";
			}
			else
				CorrectDelete(del);	
		}
		return vdel;
	}
	public void CorrectDelete(RedBlackNode node){ //to make deletion such that it obeys red black tree's properties
		RedBlackNode pointer = node;
		pointer.colour = "DOUBLEBLACK"; //pointer is the double black right now;
		while(true){// there will be 4 cases
			if (sibling(pointer).colour=="BLACK"&&(sibling(pointer).left==null||sibling(pointer).left.colour=="BLACK")&&(sibling(pointer).right==null||sibling(pointer).right.colour=="BLACK")){//CASE I when sibling is black coloured and both children are either null or black coloured
				if (pointer.parent.colour=="RED"){// Case Ia: Delete the node simply after recolouring the parent as black and sibling as red
					pointer.parent.colour="BLACK";
					sibling(pointer).colour="RED";
					if (pointer==node) {//delete the pointer if it is equal to the node to be deleted 
						if (pointer.left==null&&pointer.right==null){
							if (ChildType(pointer)=="LEFT") {
								pointer.parent.left=null;
							}
							else
								pointer.parent.right=null;
						}
						else if (pointer.left!=null&&pointer.right==null) {
							if (ChildType(pointer)=="LEFT") {
								pointer.parent.left = pointer.left;
								pointer.left.parent = pointer.parent;
							}
							else
								pointer.parent.right = pointer.left;
								pointer.left.parent = pointer.parent;
						}
						else if(pointer.left==null&&pointer.right!=null){
							if (ChildType(pointer)=="LEFT"){
								pointer.parent.left = pointer.right;
								pointer.right.parent = pointer.parent;
							}
							else
								pointer.parent.right = pointer.right;
								pointer.right.parent = pointer.parent;
						}
						return; //exit the loop in this case
					} 
					else // no need to delete the pointer
						return;
				}
				else{//Case Ib now the double black is now the parent and again make the sibling red
					pointer.parent.colour="DOUBLEBLACK";
					recolour(sibling(pointer));
					if (pointer.parent==root){//Case Ib(a) if pointer's parent is root then simply recolour the root as black (remove Double black)
						pointer.parent.colour="BLACK";
						if (pointer==node) {// will delete only if we have to delete the node
							if (pointer.left==null&&pointer.right==null){
							if (ChildType(pointer)=="LEFT") {
								pointer.parent.left=null;
							}
							else
								pointer.parent.right=null;
						}
						else if (pointer.left!=null&&pointer.right==null) {
							if (ChildType(pointer)=="LEFT") {
								pointer.parent.left = pointer.left;
								pointer.left.parent = pointer.parent;
							}
							else
								pointer.parent.right = pointer.left;
								pointer.left.parent = pointer.parent;
						}
						else if(pointer.left==null&&pointer.right!=null){
							if (ChildType(pointer)=="LEFT"){
								pointer.parent.left = pointer.right;
								pointer.right.parent = pointer.parent;
							}
							else
								pointer.parent.right = pointer.right;
								pointer.right.parent = pointer.parent;
						}
						return; //exit the loop in this case
						}
						else{//colour the root as black and exit
							pointer.parent.colour="BLACK";
							return;
						}

					}
					else{//Case Ib(b) if pointer's parent is not root 
						if (pointer==node) {// to be deleted only if pointer is node
							RedBlackNode temp = pointer;
							pointer = pointer.parent; //pointer is now the parent as it is the Double Black node and will remove the other Double Black node
							pointer.colour="DOUBLEBLACK";//making it Double Black node
							if (pointer.left==null&&pointer.right==null){
								if (ChildType(pointer)=="LEFT") {
									pointer.parent.left=null;
								}
								else
									pointer.parent.right=null;
							}
							else if (pointer.left!=null&&pointer.right==null) {
								if (ChildType(pointer)=="LEFT") {
									pointer.parent.left = pointer.left;
									pointer.left.parent = pointer.parent;
								}
								else
									pointer.parent.right = pointer.left;
									pointer.left.parent = pointer.parent;
							}
							else if(pointer.left==null&&pointer.right!=null){
								if (ChildType(pointer)=="LEFT"){
									pointer.parent.left = pointer.right;
									pointer.right.parent = pointer.parent;
								}
								else
									pointer.parent.right = pointer.right;
									pointer.right.parent = pointer.parent;
							}
								//will reapply cases		
						}
						else{
							pointer.colour="BLACK";// remove the Double Black from pointer
							pointer=pointer.parent;//pointer is now it's parent
							pointer.colour="DOUBLEBLACK";//making it double black
						}
					}
				}
			}
			else if (sibling(pointer).colour=="RED") {// CASE II when sibling's colour is red
				swapColours(pointer.parent,sibling(pointer));//swap colours of both sibling and parent
				if (ChildType(pointer)=="LEFT") {//rotating the parent in DB's direction
					RotateLeft(sibling(pointer));
				}
				else
					RotateRight(sibling(pointer));
				//reapply the cases
			}
			else if (sibling(pointer).colour=="BLACK" && nearChild(pointer)) {//CASE III when the near child of sibling to pointer red and far child may be red or black
				if (ChildType(pointer)=="LEFT") {
					swapColours(sibling(pointer),sibling(pointer).left);//swap colours of the near child and pointer's sibling
					RotateRight(sibling(pointer).left);//rotate sibling in opposite direction of double black
				}
				else{
					swapColours(sibling(pointer),sibling(pointer).right);
					RotateLeft(sibling(pointer).right);
				}
				//reapply the cases
			}
			else if (sibling(pointer).colour=="BLACK"&&farChild(pointer)) {//CASE IV when far child of sibling to pointer is red and near child is black
				RedBlackNode child = null;
				// will have 2 cases either pointer is left child or it is right child
				if (ChildType(pointer)=="LEFT") {//when pointer is left child
					child = sibling(pointer).right; //fixing the far child
					swapColours(sibling(pointer),pointer.parent); // swap colours of sibling and parent
					RotateLeft(sibling(pointer));//rotating parent in the direction of Double black
					if (pointer==node) {//delete pointer only when it is equal to the node
						if (pointer.left==null&&pointer.right==null){
							if (ChildType(pointer)=="LEFT") {
								pointer.parent.left=null;
							}
							else
								pointer.parent.right=null;
						}
						else if (pointer.left!=null&&pointer.right==null) {
							if (ChildType(pointer)=="LEFT") {
								pointer.parent.left = pointer.left;
								pointer.left.parent = pointer.parent;
							}
							else
								pointer.parent.right = pointer.left;
								pointer.left.parent = pointer.parent;
						}
						else if(pointer.left==null&&pointer.right!=null){
							if (ChildType(pointer)=="LEFT"){
								pointer.parent.left = pointer.right;
								pointer.right.parent = pointer.parent;
							}
							else
								pointer.parent.right = pointer.right;
								pointer.right.parent = pointer.parent;
						}
						child.colour ="BLACK";
						return; //exit the loop in this case						
					}
					pointer.colour="BLACK";//else the double black pointer is not the node to be deleted
					child.colour ="BLACK";//fixing the child's colour to be black
					return;// exit the loop
				}
				else{//when pointer is right child
					child = sibling(pointer).left;
					swapColours(sibling(pointer),pointer.parent);
					RotateRight(sibling(pointer));
					if (pointer==node) {
						if (pointer.left==null&&pointer.right==null){
							if (ChildType(pointer)=="LEFT") {
								pointer.parent.left=null;
							}
							else
								pointer.parent.right=null;
						}
						else if (pointer.left!=null&&pointer.right==null) {
							if (ChildType(pointer)=="LEFT") {
								pointer.parent.left = pointer.left;
								pointer.left.parent = pointer.parent;
							}
							else
								pointer.parent.right = pointer.left;
								pointer.left.parent = pointer.parent;
						}
						else if(pointer.left==null&&pointer.right!=null){
							if (ChildType(pointer)=="LEFT"){
								pointer.parent.left = pointer.right;
								pointer.right.parent = pointer.parent;
							}
							else
								pointer.parent.right = pointer.right;
								pointer.right.parent = pointer.parent;
						}
						child.colour ="BLACK";
						return; //exit the loop in this case						
					}
					pointer.colour="BLACK";
					child.colour ="BLACK";
					return;
				}
			}
			else{
				System.out.println("CASES ERROR");
				return; 
			}
		}
	}	
	public boolean nearChild(RedBlackNode node){//to check if near child of sibling to pointer is red
		if (sibling(node)==null) {
			return false;
		}
		else{
			if (ChildType(node)=="LEFT") {
				if (sibling(node).left!=null&&sibling(node).left.colour=="RED") {
					return true;
				}
				else
					return false;
			}
			else if (ChildType(node)=="RIGHT") {
				if (sibling(node).right!=null&&sibling(node).right.colour=="RED") {
					return true;
				}
				else
					return false;
				
			}
			else 
				return false;
		}
	}
	public boolean farChild(RedBlackNode node){//to check if far child of sibling to pointer is red
		if (sibling(node)==null) {
			return false;
		}
		else{
			if (ChildType(node)=="RIGHT") {
				if (sibling(node).left!=null&&sibling(node).left.colour=="RED") {
					return true;
				}
				else
					return false;
			}
			else if (ChildType(node)=="LEFT") {
				if (sibling(node).right!=null&&sibling(node).right.colour=="RED") {
					return true;
				}
				else
					return false;
				
			}
			else 
				return false;
		}
	}
	public RedBlackNode InorderSuccessor(RedBlackNode node){//finds inorder successor of a node    	
    	if (node == null) {
    		return null;
    	}
    	RedBlackNode a = node.right;
    	while(a.left!=null){
    		a = a.left;
    	}
    	return a;
    }
    public void swapColours(RedBlackNode a, RedBlackNode b){
    	if (a.colour.equals(b.colour)) {
    		return;
    	}
    	else{
    		String one = a.colour;
    		String two = b.colour;
    		a.colour = two;
    		b.colour = one;
    		return;
    	}
    }
	public void displayTree(RedBlackNode node, int level){ //displays red black tree  
		if(node==null)
             return;
        displayTree(node.right, level+1);
        if(level!=0){
            for(int i=0;i<level-1;i++)
                System.out.print("|\t");
                System.out.println("|-------"+node.key + "(" + node.colour + ")");
        }
        else
            System.out.println(node.key + "(" + node.colour + ")");
        displayTree(node.left, level+1);
	}
	public int Rotations(){ // total number of rotations till now
		return rcount;
	}
	public int height(RedBlackNode node){ // max height of a node in the tree
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
		Queue<RedBlackNode> queue=new LinkedList<RedBlackNode>();
        if(root==null) return;
        queue.add(root);
        while (queue.isEmpty()!=true){
            RedBlackNode printer = queue.poll();
            if(printer!=null){
            	System.out.print(printer.key+" "+printer.colour+" ");
            }
            if(printer.left!=null) 
            	queue.add(printer.left);
            if(printer.right!=null) 
            	queue.add(printer.right);
        }
        System.out.println();
	}
	public class RedBlackNode<K extends Comparable, V>{ // redblacknode class
		public K key;
		public V value;
		public RedBlackNode left, right, parent;
		public String colour;
		public RedBlackNode(){} // empty node constructer 
		public RedBlackNode(K key, V value){ // node constructor with parameter key and value
			this.key = key;
			this.value = value;
		}
	}
	public static void main(String[] args) {
		//10 18 7 15 16 30 25 40 60 2 1 70
		RedBlackTree<Integer,String> tree = new RedBlackTree<>();
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
		tree.remove(18);
		System.out.println(tree.Rotations());
		System.out.println(tree.GetHeight());
		tree.displayTree(tree.root,0);
		tree.remove(25);
		System.out.println(tree.Rotations());
		System.out.println(tree.GetHeight());
		tree.displayTree(tree.root,0);
		tree.remove(70);
		System.out.println(tree.Rotations());
		System.out.println(tree.GetHeight());
		tree.displayTree(tree.root,0);
		tree.remove(15);
		System.out.println(tree.Rotations());
		System.out.println(tree.GetHeight());
		tree.displayTree(tree.root,0);
		//22 33 4 5 6 7 8 55 3 2 1 6 
		RedBlackTree<Integer,String> tree1 = new RedBlackTree<>();
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
		tree1.remove(22);
		tree1.remove(33);
		tree1.remove(4);
		System.out.println(tree1.Rotations());
		System.out.println(tree1.GetHeight());
		tree1.displayTree(tree1.root,0);
		RedBlackTree<Integer,String> ntree = new RedBlackTree<>();
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
		ntree.insert(1,"k");
		System.out.println(ntree.Rotations());
		System.out.println(ntree.GetHeight());
		ntree.displayTree(ntree.root,0);

	}
}