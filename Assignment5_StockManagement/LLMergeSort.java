package col106.assignment5;

import java.util.Comparator;

/*
Implementation of MergeSort Algorithm :
    1. you get linked list of size <=1 you just return the list as it is already sorted
    2. Find mid node using findSplit method(Don't forget to add mid element to globalList before returning it)
    3. Create two LinkedList lt (with head = head and tail = mid) and rt (with head = mid.next and tail = tail)
    4. Now recursively MergSort lt and rt Linked lists(Maintain this order)
    5. Now merge these two lists that we got from recursive calls using given crieteria for ordering
    6. Return merged Linked list
*/

public class LLMergeSort <T>{

  LinkedList<T>  globalList = new LinkedList<T>();

  //CALL THIS METHOD AFTER EVERY CALL OF findSplit and DO NOT MODIFY THIS METHOD
  public void adjustGlobalPointer(T node){
      globalList.add(node);
  }

  // Utility function to get the middle of the linked list
  public Node<T> findSplit(LinkedList<T>  lst) {
    //find middle node of LL :
    Node<T> middle = lst.getHead();
    //Enter your code here
    int size = lst.getSize();
    int mid = (size%2==0)?(size/2):((size+1)/2);
    mid--;
    while(mid>0){
      middle = middle.getNext();
      mid--;
    }
    //!!!!!*****DO NOT REMOVE THIS METHOD CALL (change the argument apprpriately)*****!!!!!
    adjustGlobalPointer(middle.getData()); //Add object of ItemNode after finding mid in each call
    return middle;
  }


  public LinkedList<T>  MergeSort(LinkedList<T>  lst, boolean date, boolean key, boolean stock, boolean decDate) {
    //Recursively Apply MergeSort, by calling function findSplit(..) to find middle node to split
    //Enter your code here
    if (date) {
      // System.out.println("Entered here");
      return MergeSortDate(lst);
    }
    else if (key) {
      // System.out.println("Entered here");
      return MergeSortKey(lst);
    }
    else if(stock){
      return MergeSortStock(lst);
    }
    return MergeSortdecDate(lst);
  }

  public LinkedList<T> MergeSortDate(LinkedList<T> lst){
    if (lst==null || lst.getHead().getNext()==null){
      // System.out.println("If");
      return lst;
      }
    // System.out.println("Else");
    LinkedList<T> lst1 = new LinkedList<T>();
    LinkedList<T> lst2 = new LinkedList<T>();
    Node<T> mid = findSplit(lst);
    Node<T> mid_next = mid.getNext();
    Node<T> pointer = lst.getHead();
    while(pointer!=mid_next){
      lst1.add(pointer.getData());
      pointer = pointer.getNext();
    }
    pointer = mid_next;
    while(pointer!=null){
      lst2.add(pointer.getData());
      pointer = pointer.getNext();
    }
    Node<T> removal = lst.getHead();
    while(removal!=null){
      lst.remove(removal);
      removal = removal.getNext();
    }
    MergeSortDate(lst1);
    MergeSortDate(lst2);
    // System.out.println("Goes to conquer date");
    return conquerDate(lst1,lst2,lst);
  }

  public LinkedList<T> conquerDate(LinkedList<T> lst1 , LinkedList<T> lst2 , LinkedList<T> lst){
    // System.out.println("Hi");
    Node<T> pointer1 = lst1.getHead();
    Node<T> pointer2 = lst2.getHead();
    while(pointer1!=null&&pointer2!=null){
      if (compareDates(pointer1,pointer2)<0) {
        // System.out.println("Case 1");
        lst.add(pointer1.getData());
        pointer1=pointer1.getNext();
      }
      else if (compareDates(pointer1,pointer2)>0) {
        // System.out.println("Case 2");
        lst.add(pointer2.getData());
        pointer2=pointer2.getNext();
      }
      else{
        // System.out.println("Case 3");
        ItemNode first = (ItemNode)(Object)pointer1.getData();
        ItemNode second = (ItemNode)(Object)pointer2.getData();
        String name1 = first.getItemName();
        String name2 = second.getItemName();
        if (name1.compareTo(name2)>0) {
          lst.add(pointer1.getData());
          pointer1=pointer1.getNext();
        }
        else{
          lst.add(pointer2.getData());
          pointer2=pointer2.getNext();
        }
      }
    }
    // System.out.println("Out of first while loop");
    while(pointer1!=null){
      lst.add(pointer1.getData());
      pointer1=pointer1.getNext();

    }
    while(pointer2!=null){
      lst.add(pointer2.getData());
      pointer2=pointer2.getNext();
    }
    return lst;
  }

  public int compareDates(Node<T> pointer1, Node<T> pointer2){
    // System.out.println("Comparison of Dates");
    ItemNode first = (ItemNode)(Object)pointer1.getData();
    ItemNode second = (ItemNode)(Object)pointer2.getData();
    Node<PurchaseNode> purchase1 = first.getPurchaseHead();
    DateNode date1=null;
    DateNode date2=null;
    if (purchase1==null) {
      // System.out.println("No Purchase Date1");
      date1 = new DateNode(1,8,1970);
    }
    else{
      // System.out.println("Recent Date1 Function");
      date1 = recentDate(purchase1);
    }
    Node<PurchaseNode> purchase2 = second.getPurchaseHead();
    if (purchase2==null) {
      // System.out.println("No Purchase Date2");
      date2 = new DateNode (1,8,1970);
    }
    else{
      // System.out.println("Recent Date2 Function");
      date2 = recentDate(purchase2);
    }
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

  public DateNode recentDate(Node<PurchaseNode> purchaseT){
    Node<PurchaseNode> pointer = purchaseT;
    DateNode date1=null;
    DateNode date2=null;
    PurchaseNode purchase1 = (PurchaseNode)(Object)pointer.getData();
    PurchaseNode purchase2 = null;
    date1 = (DateNode)(Object)purchase1.getDate();
    while(pointer!=null){
      // System.out.println("While loop starts");
      purchase2 = (PurchaseNode)(Object)pointer.getData();
      date2 = (DateNode)(Object)purchase2.getDate();
      // System.out.println(date1.getYear()+"-date1-date2- "+date2.getYear());
      if (date1.getYear()==date2.getYear()) {
        if (date1.getMonth()==date2.getMonth()) {
          if (date1.getDay()==date2.getDay()) {
            pointer = pointer.getNext();
            continue;
          }
          else if (date1.getDay()>date2.getDay()) {
            pointer = pointer.getNext();
            continue;
          }
          else{
            date1=date2;
          }
        }
        else if (date1.getMonth()>date2.getMonth()) {
          pointer = pointer.getNext();
          continue;
        }
        else{
          date1=date2;
        }
      }
      else if(date1.getYear()>date2.getYear()){
        pointer = pointer.getNext();
        continue;
      }
      else{
        date1=date2;
      }
      pointer = pointer.getNext();
    }
    return date1;
  }

  public LinkedList<T> MergeSortdecDate(LinkedList<T> lst){
    if (lst==null || lst.getHead().getNext()==null){
      return lst;
      }
      LinkedList<T> lst1 = new LinkedList<T>();
      LinkedList<T> lst2 = new LinkedList<T>();
      Node<T> mid = findSplit(lst);
      Node<T> mid_next = mid.getNext();
      Node<T> pointer = lst.getHead();
      while(pointer!=mid_next){
        lst1.add(pointer.getData());
        pointer = pointer.getNext();
      }
      pointer = mid_next;
      while(pointer!=null){
        lst2.add(pointer.getData());
        pointer = pointer.getNext();
      }
      Node<T> removal = lst.getHead();
      while(removal!=null){
        lst.remove(removal);
        removal = removal.getNext();
      }
      MergeSortdecDate(lst1);
      MergeSortdecDate(lst2);
      return conquerdecDate(lst1,lst2,lst);
  }

  public LinkedList<T> conquerdecDate(LinkedList<T> lst1 , LinkedList<T> lst2 , LinkedList<T> lst){
    Node<T> pointer1 = lst1.getHead();
    Node<T> pointer2 = lst2.getHead();
    while(pointer1!=null&&pointer2!=null){
      if (compareDates(pointer1,pointer2)>0) {
        lst.add(pointer1.getData());
        pointer1=pointer1.getNext();
      }
      else if (compareDates(pointer1,pointer2)<0) {
        lst.add(pointer2.getData());
        pointer2=pointer2.getNext();
      }
      else{
        ItemNode first = (ItemNode)(Object)pointer1.getData();
        ItemNode second = (ItemNode)(Object)pointer2.getData();
        String name1 = first.getItemName();
        String name2 = second.getItemName();
        if (name1.compareTo(name2)>0) {
          lst.add(pointer1.getData());
          pointer1=pointer1.getNext();
        }
        else{
          lst.add(pointer2.getData());
          pointer2=pointer2.getNext();
        }
        
      }
    }
    while(pointer1!=null){
      lst.add(pointer1.getData());
      pointer1=pointer1.getNext();
    }
    while(pointer2!=null){
      lst.add(pointer2.getData());
      pointer2=pointer2.getNext();
    }
    return lst;
  }
  public LinkedList<T> MergeSortKey(LinkedList<T> lst){
    if (lst==null || lst.getHead().getNext()==null){
      // System.out.println("null");
      return lst;
      }
      LinkedList<T> lst1 = new LinkedList<T>();
      LinkedList<T> lst2 = new LinkedList<T>();
      Node<T> mid = findSplit(lst);
      Node<T> mid_next = mid.getNext();
      Node<T> pointer = lst.getHead();
      while(pointer!=mid_next){
        lst1.add(pointer.getData());
        pointer = pointer.getNext();
      }
      pointer = mid_next;
      while(pointer!=null){
        lst2.add(pointer.getData());
        pointer = pointer.getNext();
      }
      Node<T> removal = lst.getHead();
      while(removal!=null){
        lst.remove(removal);
        removal = removal.getNext();
      }
      MergeSortKey(lst1);
      MergeSortKey(lst2);
      return conquerKey(lst1,lst2,lst);
  }
  public LinkedList<T> conquerKey(LinkedList<T> lst1 , LinkedList<T> lst2, LinkedList<T> lst){
    Node<T> pointer1 = lst1.getHead();
    Node<T> pointer2 = lst2.getHead();
    ItemNode itemPointer1=(ItemNode)(Object)pointer1.getData();
    ItemNode itemPointer2=(ItemNode)(Object)pointer2.getData();
    while(pointer1!=null&&pointer2!=null){
      itemPointer1=(ItemNode)(Object)pointer1.getData();
      itemPointer2=(ItemNode)(Object)pointer2.getData();
      if (itemPointer1.getKey()<itemPointer2.getKey()) {
        lst.add(pointer1.getData());
        pointer1=pointer1.getNext();
      }
      else if (itemPointer1.getKey()>itemPointer2.getKey()) {
        lst.add(pointer2.getData());
        pointer2=pointer2.getNext();
      }
      else{
        ItemNode first = (ItemNode)(Object)pointer1.getData();
        ItemNode second = (ItemNode)(Object)pointer2.getData();
        String name1 = first.getItemName();
        String name2 = second.getItemName();
        if (name1.compareTo(name2)>0) {
          lst.add(pointer1.getData());
          pointer1=pointer1.getNext();
        }
        else{
          lst.add(pointer2.getData());
          pointer2=pointer2.getNext();
        }
      }
    }
    while(pointer1!=null){
      lst.add(pointer1.getData());
      pointer1=pointer1.getNext();

    }
    while(pointer2!=null){
      lst.add(pointer2.getData());
      pointer2=pointer2.getNext();
    }
    return lst;
  }

  public LinkedList<T> MergeSortStock(LinkedList<T> lst){
    if (lst==null || lst.getHead().getNext()==null){
      // System.out.println("Single");
      return lst;
      }
      LinkedList<T> lst1 = new LinkedList<T>();
      LinkedList<T> lst2 = new LinkedList<T>();
      Node<T> mid = findSplit(lst);
      Node<T> mid_next = mid.getNext();
      Node<T> pointer = lst.getHead();
      while(pointer!=mid_next){
        lst1.add(pointer.getData());
        pointer = pointer.getNext();
      }
      pointer = mid_next;
      while(pointer!=null){
        lst2.add(pointer.getData());
        pointer = pointer.getNext();
      }
      Node<T> removal = lst.getHead();
      while(removal!=null){
        lst.remove(removal);
        removal = removal.getNext();
      }
      MergeSortStock(lst1);
      MergeSortStock(lst2);
      // System.out.println("Goes to Stock");
      return conquerStock(lst1,lst2,lst);
  }

  public LinkedList<T> conquerStock(LinkedList<T> lst1 , LinkedList<T> lst2, LinkedList<T> lst){
    Node<T> pointer1 = lst1.getHead();
    Node<T> pointer2 = lst2.getHead();
    ItemNode stock1;
    ItemNode stock2;
    while(pointer1!=null&&pointer2!=null){
      stock1 = (ItemNode)(Object)pointer1.getData();
      stock2 = (ItemNode)(Object)pointer2.getData();
      if (stock1.getStockLeft()>stock2.getStockLeft()) {
        // System.out.println("TrueStock1less"+stock1.getStockLeft()+stock2.getStockLeft());
        lst.add(pointer1.getData());
        pointer1=pointer1.getNext();
      }
      else if (stock1.getStockLeft()<stock2.getStockLeft()) {
        // System.out.println("TrueStock2less");
        lst.add(pointer2.getData());
        pointer2=pointer2.getNext();
      }
      else{
        // System.out.println("True");
        ItemNode first = (ItemNode)(Object)pointer1.getData();
        ItemNode second = (ItemNode)(Object)pointer2.getData();
        String name1 = first.getItemName();
        String name2 = second.getItemName();
        if (name1.compareTo(name2)>0) {
          lst.add(pointer1.getData());
          pointer1=pointer1.getNext();
        }
        else{
          lst.add(pointer2.getData());
          pointer2=pointer2.getNext();
        }
      }
    }
    while(pointer1!=null){
      lst.add(pointer1.getData());
      pointer1=pointer1.getNext();

    }
    while(pointer2!=null){
      lst.add(pointer2.getData());
      pointer2=pointer2.getNext();
    }
    return lst;
  }

  //DO NOT CALL OR MODIFY THESE METHODS IN YOUR CALL THIS IS FOR USE IN DRIVER CODE
  public LinkedList<T> getGlobalList() {
    return this.globalList;
  }

  public void clearGlobalList(){
    globalList  = new LinkedList<>();
  }

}
