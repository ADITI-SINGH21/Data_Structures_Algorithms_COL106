package col106.assignment5;
public class StockMgmt implements StockMgmtInterface {
	//!!!!!!!*********DON'T CREATE YOUR OWN OBJECTS OF LLMergeSort*********!!!!!!!
	//use these only this object to sort
	LLMergeSort mergeSortobj;

	LinkedList<CategoryNode> categories;

	//DO NOT MNODIFY THIS CONSTRUCTOR
	public StockMgmt() {

		mergeSortobj = new LLMergeSort();
		categories = new LinkedList<CategoryNode>();

		categories.add(new CategoryNode(1, "mobile"));
		categories.add(new CategoryNode(2, "utensils"));
		categories.add(new CategoryNode(3, "sanitary"));
		categories.add(new CategoryNode(4, "medicalEquipment"));
		categories.add(new CategoryNode(5, "clothes"));

	}

	public void addItem(int categoryId, int itemId, String itemName, int stock) {
		//Your code goes here
		ItemNode itemList = new ItemNode(itemId,itemName,stock);
		Node<CategoryNode> head = categories.getHead();
		LinkedList<ItemNode> itemLL = null;
		CategoryNode headCat = (CategoryNode)(Object)head.getData();
		while(head!=null){
			if (headCat.getCategoryId()==categoryId) {
				break;
			}
			headCat = (CategoryNode)(Object)head.getData();
			head = head.getNext();
		}
		itemLL = headCat.getLinkedListOfCategory();
		itemLL.add(itemList);
		// System.out.println("added Item "+itemList.getItemId()+" "+itemList.getItemName() );
	}
	public void printCategory(){
		Node<CategoryNode> pointer = categories.getHead();
		while(pointer!=null){
			System.out.print("Category Id "+pointer.getData().getCategoryId()+" Category LinkedList ");
			LinkedList<ItemNode> ll = pointer.getData().getLinkedListOfCategory();
			Node<ItemNode> llpointer = ll.getHead();
			while(llpointer!=null){
				System.out.print(llpointer.getData().getItemName()+" "+llpointer.getData().getItemId());
				llpointer=llpointer.getNext();
			}
			System.out.println();
			pointer=pointer.getNext();
		}
	}
	public void addItemTransaction(int categoryId, int itemId, String itemName, int numItemPurchased, int day, int month, int year) {
		//Your code goes here
		// printCategory();
		Node<CategoryNode> head = categories.getHead();
		LinkedList<ItemNode> itemLL = null;
		CategoryNode headCat = (CategoryNode)(Object)head.getData();
		while(head!=null){
			if (headCat.getCategoryId()==categoryId) {
				break;
			}
			headCat = (CategoryNode)(Object)head.getData();
			head = head.getNext();
		}
		itemLL = headCat.getLinkedListOfCategory();
		// System.out.println("Category "+headCat.getCategoryId());
		PurchaseNode purchaseT = new PurchaseNode(numItemPurchased,day,month,year); 
		Node<ItemNode> itemhead = itemLL.getHead();
		ItemNode nodeitem = (ItemNode)(Object)itemhead.getData(); 
		while(itemhead!=null){
			// System.out.println("It goes in here "+itemhead.getData().getItemName()+" "+itemName);
			nodeitem = (ItemNode)(Object)itemhead.getData(); 
			if (itemhead.getData().getItemName().equals(itemName)&&itemhead.getData().getItemId()==itemId) {
				// System.out.println(itemhead.getData().getItemName()+" "+itemhead.getData().getItemId());
				break;
			}
			itemhead = itemhead.getNext();
		}
		nodeitem.addTransaction(purchaseT);
		// printLL(itemLL);
		// System.out.println();
	}
	public void printLL(LinkedList<ItemNode> lst){
		Node<ItemNode> head = lst.getHead();
		while(head!=null){
			System.out.println(head.getData().getItemName()+" "+head.getData().getItemId()+" "+head.getData().getStockLeft()+" "+head.getData().getKey());
			Node<PurchaseNode> purchaseNode = head.getData().getPurchaseHead();
			// while(purchaseNode!=null){
			// 	System.out.print(" Purchase Dates "+purchaseNode.getData().getDate().getDay()+" "+ purchaseNode.getData().getDate().getMonth()+" "+ purchaseNode.getData().getDate().getYear()+" ");
			// 	purchaseNode = purchaseNode.getNext();
			// }
			System.out.println();
			head = head.getNext();
		}
	}
	//Query 1
	public LinkedList<ItemNode> sortByLastDate() {
		//Your code goes here
		LinkedList<ItemNode> sortDate = new LinkedList<ItemNode>();
		Node<CategoryNode> pointer = categories.getHead();
		while (pointer!=null){
			Node<ItemNode> llpointer = pointer.getData().getLinkedListOfCategory().getHead();
			while(llpointer!=null){
				sortDate.add(llpointer.getData());
				llpointer = llpointer.getNext();
			} 
			pointer = pointer.getNext();
		}
		//System.out.println("sent to MergeSort");
		// printLL(sortDate);
		sortDate = mergeSortobj.MergeSort(sortDate,true,false,false,false);
		return sortDate;
	}

	//Query 2
	public LinkedList<ItemNode> sortByPurchasePeriod(int day1, int month1, int year1, int day2, int month2, int year2) {
		//Your code goes here
		LinkedList<ItemNode> sortkey = new LinkedList<ItemNode>();
		Node<CategoryNode> pointer = categories.getHead();
		while (pointer!=null){
			Node<ItemNode> llpointer = pointer.getData().getLinkedListOfCategory().getHead();
			while(llpointer!=null){
				sortkey.add(llpointer.getData());
				llpointer = llpointer.getNext();
			} 
			pointer = pointer.getNext();
		}
		DateNode period1 = new DateNode(day1,month1,year1);
		DateNode period2 = new DateNode(day2,month2,year2);
		sortkey = CreateKey(sortkey,period1,period2);
		// printLL(sortkey);
		sortkey = mergeSortobj.MergeSort(sortkey,false,true,false,false);
		return sortkey;
	}

	public LinkedList<ItemNode> CreateKey(LinkedList<ItemNode> lst, DateNode period1,DateNode period2){
	    Node<ItemNode> pointer = lst.getHead();
	    ItemNode item = (ItemNode)(Object)pointer.getData();
	    Node<PurchaseNode> purchaseN = item.getPurchaseHead();
	    PurchaseNode purchaseT = null;
	    while(pointer!=null){
	      item = (ItemNode)(Object)pointer.getData();
	      purchaseN = item.getPurchaseHead();
	      if (purchaseN==null) {
	      	// System.out.println("True");
	      	item.setKey(0);
	      }
	      else{
	      	float num = 0;
	      	float[] year = new float[item.getLLSize()];
	      	// System.out.print(item.getItemName()+" Year matrix "+year.length+" ");
	      	int index=0;
	      	while(purchaseN!=null){
	      		// System.out.print(item.getItemName()+" "+purchaseN.getData().getDate().getYear()+" ");
	        	purchaseT = (PurchaseNode)(Object)purchaseN.getData();
	        	DateNode date = purchaseT.getDate();
	        	if (dateCompare(date,period1)<0) {
	        		// System.out.print("Case1 ");
	        		purchaseN = purchaseN.getNext();
	        	}
	        	else if (dateCompare(date,period2)>0) {
	        		// System.out.print("Case2 ");
	        		purchaseN = purchaseN.getNext();
	        	}
	        	else{
	        		// System.out.print("Case3 ");
	          		num+=purchaseT.numItemsPurchased();
	          		year[index]=purchaseT.getDate().getYear();
	          		index++;
	        		purchaseN = purchaseN.getNext();

	        	}
	      	}
	      	// System.out.print(num+" ");
	    	float min = getMin(year);
	      	float max = getMax(year);
	      	// System.out.print(min+" "+max+" ");
	      	item.setKey(num/(max-min+1));
	     
	      	// System.out.println("Key "+item.getKey());
	      } 
	      pointer = pointer.getNext();
	    }
	    return lst;
	  }

	public float getMin(float[] year){
		float min = year[0];
		int i=0;
		while(i<year.length&&year[i]!=0){
			float counter = year[i];
			if (min>counter) {
				min = counter;
			}
			i++;
		}
		return min;
	} 

	public float getMax(float[] year){
		float max = year[0];
		int i=0;
		while(i<year.length&&year[i]!=0){
			float counter = year[i];
			if (max<counter) {
				max = counter;
			}
			i++;
		}
		return max;
	} 

	public int dateCompare(DateNode date1,DateNode date2){
	    if (date1.getYear()==date2.getYear()) {
	        if (date1.getMonth()==date2.getMonth()) {
	          if (date1.getDay()==date2.getDay()) {
	            return 0;
	          }
	          else if (date1.getDay()>date2.getDay()) {
	            return 1;
	          }
	          else{
	            return -1;
	          }
	        }
	        else if (date1.getMonth()>date2.getMonth()) {
	          return 1;
	        }
	        else{
	          return -1;
	        }
	      }
	      else if(date1.getYear()>date2.getYear()){
	        return 1;
	      }
	      else{
	        return -1;
	      }
	  }


	//Query 3
	public LinkedList<ItemNode> sortByStockForCategory(int category) {
		//Your code goes here
		int count = 1;
		Node<CategoryNode> pointer = categories.getHead();
		while(count!=5){
			if (count==category){
				break;
			}
			else{
				count++;
				pointer = pointer.getNext();
			}
		}
		LinkedList<ItemNode> categoryStockLL = pointer.getData().getLinkedListOfCategory();
		categoryStockLL = mergeSortobj.MergeSort(categoryStockLL,false,false,true,false);
		return categoryStockLL;
	}

	//Query 4
	public LinkedList<ItemNode> filterByCategorySortByDate(int category) {
		//Your code goes here
		int count = 1;
		Node<CategoryNode> pointer = categories.getHead();
		while(count!=5){
			if (count==category){
				break;
			}
			else{
				count++;
				pointer = pointer.getNext();
			}
		}
		LinkedList<ItemNode> categoryDateLL = pointer.getData().getLinkedListOfCategory();
		categoryDateLL = mergeSortobj.MergeSort(categoryDateLL,false,false,false,true);
		return categoryDateLL;
	}

	//!!!!!*****DO NOT MODIFY THIS METHOD*****!!!!!//
	public LinkedList<ItemNode> checkMergeSort() {
		LinkedList<ItemNode> retLst = mergeSortobj.getGlobalList();
		mergeSortobj.clearGlobalList();
		return retLst;
	}
}
