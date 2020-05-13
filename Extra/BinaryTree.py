# Binary Search Tree implemented in Python Language
class BSTNode: 
	# Node class
	def __init__(self,key,value):
		self.left = None
		self.right = None
		self.parent = None
		self.key = key
		self.value = value

class BinaryTree:
	# Tree Class
	def __init__(self):
		self.root= None

	def insert(self,key,value):
		newNode = BSTNode(key,value)
		if self.root==None:
			self.root = newNode
			return None
		elif self.find(key)==None:
			cur = self.root;
			p = None
			while cur!=None:
				p = cur
				if key<cur.key:
					if cur.left==None:
						cur.left=newNode
						break
					else:
						cur = cur.left
				else:
					if cur.right==None:
						cur.right=newNode
						break
					else:
						cur = cur.right
			newNode.parent = p
			return None
		else:
			v = self.find(key).value
			self.find(key).value = value
			return v

	def search(self,key):
		cur = self.root
		while cur!=None:
			if key<cur.key:
				cur = cur.left
			elif key>cur.key:
				cur = cur.right
			else:
				return cur.value
		return "Not Found"

	def find(self,key):
		cur = self.root
		while cur!=None:
			if key<cur.key:
				cur = cur.left
			elif key>cur.key:
				cur = cur.right
			else:
				return cur
		return None

	def delete(self,key):
		cur = self.find(key)
		if cur:
			v = cur.value
			if cur.left==None and cur.right==None:
				if cur.parent.right==cur:
					cur.parent.right=None
				else:
					cur.parent.left=None
			elif cur.left and cur.right==None:
				if cur.parent.right==cur:
					cur.parent.right=cur.left
					cur.left.parent = cur.parent
				else:
					cur.parent.left=cur.left
					cur.left.parent = cur.parent
			elif cur.right and cur.left==None:
				if cur.parent.right==cur:
					cur.parent.right=cur.right
					cur.right.parent = cur.parent
				else:
					cur.parent.left=cur.right
					cur.right.parent = cur.parent
			else:
				n = self.inOrderSuccessor(cur)
				cur.key = n.key
				cur.value = n.value
				if n.right!=None:
					if n.parent.right==n:
						n.parent.right=n.right
						n.right.parent = n.parent
					else:
						n.parent.left=n.right
						n.right.parent = n.parent
				else:
					if n.parent.right==n:
						n.parent.right = None
					else:
						n.parent.left = None
			return v
		else:
			return "No Such key"

	def inOrderSuccessor(self,node):
		pointer = node.right
		while pointer.left!=None:
			pointer = pointer.left
		return pointer

	def PrintTree(self):
		cur = self.root
		array = [cur]
		while array!=[]:
			pointer = array[0]
			array.pop(0)
			print(pointer.key)
			if pointer.left:
				array.append(pointer.left)
			if pointer.right:
				array.append(pointer.right)

tree = BinaryTree()
tree.insert(12,"a")
tree.insert(6,"b")
tree.insert(14,"c")
tree.insert(3,"d")
tree.insert(7,"e")
tree.insert(13,"f")
tree.insert(15,"g")
tree.insert(13.5,"h")
tree.PrintTree()
print(tree.search(6))
print(tree.search(13))
print(tree.delete(14))
tree.PrintTree()
						
		