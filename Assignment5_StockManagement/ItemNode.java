package col106.assignment5;

public class ItemNode implements ItemInterface{

	int itemId;
	String itemName;
	int stock;
	LinkedList<PurchaseNode> purchaseTransactions;
	float key;

	public ItemNode(int itemId, String itemName, int stock){
		this.itemId = itemId;
		this.itemName = itemName;
		this.stock = stock;
		this.purchaseTransactions = new LinkedList<PurchaseNode>();
		key = 0;
	}

	public int getItemId(){
		//Enter your code here
		return this.itemId;
	}

	public  String getItemName(){
		//Enter your code here
		return this.itemName;
	}

	public int getStockLeft(){
		//Enter your code here
		return this.stock; 
	}

	public void addTransaction(PurchaseNode purchaseT){
		//Enter your code here
		int numItems = purchaseT.numItemsPurchased();
		this.stock-=numItems;
		this.purchaseTransactions.add(purchaseT);

	}

	public Node<PurchaseNode> getPurchaseHead(){
		//Enter your code here
		return this.purchaseTransactions.getHead();
	}

	public int getLLSize(){
		return this.purchaseTransactions.getSize();
	}

	public void setKey(float k){
		key = k; 
	}

	public float getKey(){
		return key;
	}

}
