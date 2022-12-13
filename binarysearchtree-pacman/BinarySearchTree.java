public class BinarySearchTree implements BinarySearchTreeADT{
    private BNode root;
    
    public BinarySearchTree() {
        this.root = new BNode(null, new BNode(), new BNode(), null);
    }

    public Pel get(BNode r, Location key) {
        if (r == null || r.isLeaf() || r.getData() == null) {
            return null;
        } 
        else if (r.getData().getLocus().compareTo(key) == 0) {
            return r.getData();
        }
        else if (r.getData().getLocus().compareTo(key) == 1){
            return this.get(r.leftChild(), key);
        }
        else {
            return this.get(r.rightChild(), key);
        }
    }

    private BNode getNode(BNode r, Location key) {
        if (r == null || r.isLeaf() || r.getData() == null) {
            return null;
        } 
        else if (r.getData().getLocus().compareTo(key) == 0) {
            return r;
        }
        else if (r.getData().getLocus().compareTo(key) == 1){
            return this.getNode(r.leftChild(), key);
        }
        else {
            return this.getNode(r.rightChild(), key);
        }
    }

    public void put(BNode r, Pel newData) throws DuplicatedKeyException {
        if (r == null || r.isLeaf()) {
            return;
        }
        else if (r.getData() == null && count(this.root) == 1 && r.parent() == null) {
            r.setContent(newData);
            r.setLeftChild(new BNode());
            r.setRightChild(new BNode());
        }
        else if (r.getData().getLocus().compareTo(newData.getLocus()) == 0 && 
                r.getData().getColor() == newData.getColor()) {
            throw new DuplicatedKeyException("Key already exists");
        } 
        else if (r.getData().getLocus().compareTo(newData.getLocus()) == 1) {
            if (r.leftChild() != null && !r.leftChild().isLeaf()) {
                put(r.leftChild(), newData);
            }
            else {
                r.setLeftChild(new BNode(newData, new BNode(), new BNode(), r));
                return;
            }
        } else {
            if (r.rightChild() != null && !r.rightChild().isLeaf()) {
                put(r.rightChild(), newData);
            } 
            else {
                r.setRightChild(new BNode(newData, new BNode(), new BNode(), r));
                return;
            }
        }
    }

    public void remove(BNode r, Location key) throws InexistentKeyException {
        if (get(r, key) == null) throw new InexistentKeyException("node no exist");
        else if (r == null || r.isLeaf() || r.getData() == null) return;
        else if (r.getData().getLocus().compareTo(key) == 0) {
            // if one child is a leaf
            if (r.leftChild().isLeaf() || r.rightChild().isLeaf()) {
                if (r.leftChild().isLeaf()) {
                    BNode other = r.rightChild();
                    BNode parent = r.parent();
                    if (parent != null) {
                        // find out which child of parent is r
                        // left child of parent is r
                        if (parent.leftChild().getData().getLocus().compareTo(r.getData().getLocus()) == 0) {
                            parent.setLeftChild(other);
                        }
                        else {
                        // right child of parent is r
                            parent.setRightChild(other);
                        } 
                    }
                    else {
                        r.setContent(other.getData());
                        r.setRightChild(new BNode());
                    }
                } else {
                    BNode other = r.leftChild();
                    BNode parent = r.parent();
                    if (parent != null) {
                        if (parent.leftChild().getData().getLocus().compareTo(r.getData().getLocus()) == 0) {
                            parent.setLeftChild(other);
                        }
                        else {
                        // right child of parent is r
                            parent.setRightChild(other);
                        } 
                    }
                    else {
                        r.setContent(other.getData());
                        r.setLeftChild(new BNode());
                    }
                }
            } 
            // if no child is a leaf 
            else {
              
                BNode s = smallestNode(r);
                if (s != null) {
                    r.setContent(s.getData());
                    remove(s, s.getData().getLocus());
                }
                else {
                    throw new InexistentKeyException("Error");
                }
            }
        }
        else if (r.getData().getLocus().compareTo(key) == 1){
            remove(r.leftChild(), key);
        }
        else {
            remove(r.rightChild(), key);
        }
    }

    private BNode smallestNode(BNode r){
        if (r == null || r.isLeaf()) return null;
        BNode cur = r;
        while (!cur.leftChild().isLeaf()) {
            cur = cur.leftChild();
        }
        return cur.parent();
    }

    private int count(BNode r) {
        if (r == null ) return 0;
        return 1 + count(r.leftChild()) + count(r.rightChild());
    }

    private void inOrder(BNode r, Pel[] arr, int i) {
        if (r == null) return;
        inOrder(r.leftChild(), arr, i);
        arr[i++] = r.getData();
        inOrder(r.rightChild(), arr, i);
    }

    private Pel[] makeInOrder(BNode r) {
        Pel[] arr = new Pel[this.count(r)];
        inOrder(r, arr, 0);
        return arr;
    }
  
    public Pel successor(BNode r, Location key) {
        
        if (r.isLeaf()) return null;
        else {
            BNode p = this.getNode(r, key);
            if (!p.isLeaf() && !p.rightChild().isLeaf()) {
                try {
                    return smallest(p.rightChild());
                } catch (EmptyTreeException e) {
                    return null;
                }
            }
            else {
                p = p.parent();
                while (p != null && p.getData().getLocus().compareTo(key) == -1) {
                    p = p.parent();
                }
                if (p == null) return null;
                else return p.getData();
            }
        }
    }



    private BNode smallestNode(BNode r, Location key) {
        if (r == null || r.isLeaf()) {
            return null;
        }
        BNode cur = r;
        while (!cur.leftChild().isLeaf()) {
            cur = cur.leftChild();
        }
        return cur.parent();
    }

    private BNode largestNode(BNode r, Location key) {
        if (r == null || r.isLeaf()) {
            return null;
        }
        BNode cur = r;
        while (!cur.rightChild().isLeaf()) {
            cur = cur.rightChild();
        }
        return cur.parent();
    }

   
    public Pel predecessor(BNode r, Location key) {
        
        if (r.isLeaf()) return null;
        else {
            BNode p = getNode(r, key);
            if (!p.isLeaf() && !p.leftChild().isLeaf()) {
                try {
                    return largest(p.leftChild());
                } catch (EmptyTreeException e) {
                    return null;
                }
            } else {
                p = p.parent();
                while (p != null && p.getData().getLocus().compareTo(key) == 1) {
                    p = p.parent();
                }
                if (p == null) return null;
                else return p.getData();
            }
        }
    }

    public Pel smallest(BNode r) throws EmptyTreeException {
        if (r == null || r.isLeaf()) {
            throw new EmptyTreeException("Empty tree");
        }
        BNode cur = r;
        while (!cur.leftChild().isLeaf()) {
            cur = cur.leftChild();
        }
        return cur.parent().getData();
    }

    public Pel largest(BNode r) throws EmptyTreeException {
        if (r == null || r.isLeaf()) {
            throw new EmptyTreeException("Empty tree");
        }
        BNode cur = r;
        while (!cur.rightChild().isLeaf()) {
            cur = cur.rightChild();
        }
        return cur.parent().getData();
    }

    public BNode getRoot() {
        return this.root;
    }
}


 /**Pel[] values = makeInOrder(r);
        for (int i = 0; i < values.length; i++) {
            if (values[i].getLocus().compareTo(key) == 0) {
                if (i - 1 >= 0) {
                    if (values[i - 1] == null) {
                        int j = 1;
                        while (i - j >= 0 && values[i-j] == null) {
                            j += 1;
                        }
                        if (i - j >= 0 ) return values[i-j];
                    }
                    else return values[i - 1];
                }
            }
        }
        return null;*/

          /**Pel[] values = makeInOrder(r);
        for (int i = 0; i < values.length; i++) {
            if (values[i].getLocus().compareTo(key) == 0) {
                if (i + 1 < values.length) {
                    if (values[i+1] == null) {
                        int j = 1;
                        while (i+j < values.length && values[i+j] == null) {
                            j += 1;
                        }
                        if (i + j < values.length) return values[i + j];
                    }
                    else return values[i + 1];
                }
            }
        }
        return null;*/