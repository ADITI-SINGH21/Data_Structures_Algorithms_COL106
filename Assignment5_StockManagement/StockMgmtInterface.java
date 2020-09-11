package col106.assignment5;

interface StockMgmtInterface
{

	public void addItem(int categoryId, int itemId, String itemName, int stock);

	public void addItemTransaction(int categoryId, int itemId, String itemName, int numItemPurchased, int day, int month, int year);

	//Query 1
	public LinkedList<ItemNode> sortByLastDate();

	// Query 2
	public LinkedList<ItemNode> sortByPurchasePeriod(int day1, int month1, int year1, int day2, int month2, int year2);

	//Query 3
	public LinkedList<ItemNode> sortByStockForCategory(int category);

	//Query 4
	public LinkedList<ItemNode> filterByCategorySortByDate(int category);

	//This function is already provided in class and it is not ment to be used in you code
	public LinkedList<ItemNode> checkMergeSort();
}
