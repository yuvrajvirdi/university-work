public class MyObject implements MyObjectADT{
    private int id, width, height;
    private String type;
    private Location pos;
    private BinarySearchTree tree;

    public MyObject(int id, int width, int height, String type, Location pos) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.type = type;
        this.pos = pos;
        this.tree = new BinarySearchTree();
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getType() {
        return this.type;
    }

    public int getId() {
        return this.id;
    }

    public Location getLocus() {
        return this.pos;
    }

    public void setLocus(Location value) {
        this.pos = value;
    }

    public void addPel(Pel pix) throws DuplicatedKeyException {
        try {  
            this.tree.put(new BNode(pix, null, null, null), pix);
        } catch (Exception err) {
            System.out.println(err);
            throw new DuplicatedKeyException("Duplicated Key");
        }
    }

    public boolean intersects(MyObject otherObject) {
        return dfs1(this.tree.getRoot(), otherObject);
    }

    private boolean dfs1(BNode r, MyObject otherObject) {
        if (r == null || r.isLeaf()) return false;
        else {
            if (df2(r, otherObject.tree.getRoot(), otherObject)) return true;
            else {
                return dfs1(r.leftChild(), otherObject) || dfs1(r.rightChild(), otherObject);
            }
        }
    }

    private boolean df2(BNode s, BNode t, MyObject otherObject) {
        if (s == null || s.isLeaf() || t == null || t.isLeaf()) return false;
        else {
            int thisX = s.getData().getLocus().getx(), thisY = s.getData().getLocus().gety();
            int otherX = t.getData().getLocus().getx(), otherY = t.getData().getLocus().gety();
            int thisLocusX = this.getLocus().getx(), thisLocusY = this.getLocus().gety();
            int otherLocusX = otherObject.getLocus().getx(), otherLocusY = otherObject.getLocus().gety();
            if (otherX == (thisX + thisLocusX - otherLocusX) && 
            otherY == (thisY + thisLocusY - otherLocusY)) return true;
            else {
                return df2(s, t.leftChild(), otherObject)|| df2(s, t.rightChild(), otherObject);
            }
        }
    }
}
