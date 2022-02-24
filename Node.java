public class Node{
    protected String data;
    protected Node next = null;

    Node(String data) {
        this.data = data;
    }

    public String getData() {
        // return data stored in this node
        return data;
    }
	 public void setData(String d) {
		data=d;
    }

    public Node getNext() {
        // get next node
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
