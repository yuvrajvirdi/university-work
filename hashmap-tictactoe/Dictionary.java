public class Dictionary implements DictionaryADT{
    private int size = 0;
    private int numRecords = 0;
    private Node[] hashTable;

    /**
     * initiliazes object
     * 
     * @param size The size of the hash table
     */
    public Dictionary(int size) {
        this.size = size;
        this.hashTable = new Node[this.size];
        for (int i = 0; i < this.size; i++) {
            this.hashTable[i] = null;
        }
    }

    /**
     * rolling polynomial hash function
     * 
     * @param key The inputted string
     * @return hash value
     */
    private int hash(String key) {
        int val = (int)key.charAt(0);
        int M = this.size, x = 33;
        for (int i = 1; i < key.length(); i++) {
            val = (val * x + (int)key.charAt(i)) % M;
        }
        return val;
    }

    /**
     * puts a record into the hash table
     * 
     * @param rec The record to be placed into the table
     * @throws DuplicatedKeyException if key already exists
     * @return 0 if table was empty at index, or 1 if otherwise
     */
    public int put(Record rec) throws DuplicatedKeyException {
        int hashVal = this.hash(rec.getKey());
        
        if (this.hashTable[hashVal] == null) {
            this.hashTable[hashVal] = new Node(rec);
            this.numRecords++;
            return 0;
        } 
        
        else {

            Node cur = this.hashTable[hashVal];

            // if one node at index, and node already has key
            if (cur.getNext() == null && cur.getData().getKey().equals(rec.getKey())) {
                throw new DuplicatedKeyException("Key already exists");
            }
          
            // otherwise, traverse through linked list
            while (cur.getNext() != null) {
                if (cur.getData().getKey().equals(rec.getKey())) {
                    throw new DuplicatedKeyException("Key already exists");
                } 
                cur = cur.getNext();
            }

            // since traversal stops at last elem, check if last elem is dupe
            // as the conditional will not execute when the loop conditional is false
            if (cur.getData().getKey().equals(rec.getKey())) {
                throw new DuplicatedKeyException("Key already exists");
            }

            cur.setNext(new Node(rec));
            this.numRecords++;
            return 1;
           
        }
    }

    /**
     * removes a record from the hash table
     * 
     * @param key The key of the record to remove
     * @throws InexistentKeyException if record is not present
     */
    public void remove(String key) throws InexistentKeyException {
        int hashVal = this.hash(key);

        if (this.hashTable[hashVal] == null) {
            throw new InexistentKeyException("Key does not exist");
        }

        Node prev = null, cur = this.hashTable[hashVal];

        while (cur != null) {
            if (cur.getData().getKey().equals(key)) {
                numRecords--;
                // if first and only elem
                if (prev == null && cur.getNext() == null) {
                    this.hashTable[hashVal] = null;
                }
                // if first elem and has other elems
                else if (prev == null && cur.getNext() != null) {
                    Node newHead = cur.getNext();
                    this.hashTable[hashVal] = null;
                    this.hashTable[hashVal] = newHead;
                }
                // if somewhere in middle of list
                else if (prev != null && cur.getNext() != null) {
                    prev.setNext(cur.getNext());
                }
                // if end of list
                else if (prev != null && cur.getNext() == null) {
                    prev.setNext(null);
                }
                return;
            }
            prev = cur;
            cur = cur.getNext();
        }

        // traversed through whole list and didn't find key
        if (cur == null) {
            throw new InexistentKeyException("Key does not exist");
        }

    }

    /**
     * gets the record corresponding to the given key
     * 
     * @param key The key of the record to retrieve
     * @return Record object
     */
    public Record get(String key) {
        int hashVal = this.hash(key);
        if (this.hashTable[hashVal] == null) {
            return null;
        }
        Node cur = this.hashTable[hashVal];
        while (cur != null) {
            if (cur.getData().getKey().equals(key)) {
                return cur.getData();
            }
            cur = cur.getNext();
        }
        return null;
    }

    /**
     * returns number of records
     * 
     * @return numRecords
     */
    public int numRecords(){
        return this.numRecords;
    }
}
