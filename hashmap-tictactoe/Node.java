public class Node {

    private Record data;
    private Node next;

    /**
     * constructor
     * 
     * @param rec
     */
    public Node(Record rec) {
        this.data = rec;
        this.next = null;
    }

    /**
     * returns node's record
     * 
     * @return
     */
    public Record getData() {
        return this.data;
    }

    /**
     * gets the next node
     * 
     * @return
     */
    public Node getNext() {
        return this.next;
    }

    /**
     * sets next
     * 
     * @param next
     */
    public void setNext(Node next) {
        this.next = next;
    }

}
