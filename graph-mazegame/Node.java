public class Node {
    // id
    private int id;
    // mark
    private boolean mark;

    /**
     * constructor
     * @param id
     */
    public Node(int id) {
        this.id = id;
    }

    /**
     * marks node
     * @param mark
     */
    public void markNode(boolean mark) {
        this.mark = mark;
    }

    /**
     * gets mark
     * @return mark
     */
    public boolean getMark() {
        return mark;
    }

    /**
     * gets id
     * @return id
     */
    public int getId() {
        return id;
    }
}