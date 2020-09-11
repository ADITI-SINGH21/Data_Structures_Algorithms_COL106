package col106.assignment5;

public class Node<T> {

  Node<T> next;
  T data;

  public Node(T data) {
    next = null;
    this.data = data;
  }

  public T getData() {
    return data;
  }

  public void setData(T dataValue) {
    data = dataValue;
  }

  public Node<T> getNext() {
    return next;
  }

  public void setNext(Node<T> nextValue) {
    next = nextValue;
  }

  public Node<T> duplicate() {
    return null;
  }

  public String toString() {
    String nodeItem = data.toString();
    return nodeItem;
  }

}
