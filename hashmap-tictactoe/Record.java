public class Record {
    
    private String key;
    private int score, level;

    /**
     * constructor
     * 
     * @param key
     * @param score
     * @param level
     */
    public Record(String key, int score, int level) {
        this.key = key;
        this.score = score;
        this.level = level;
    }

    /**
     * gets key
     * 
     * @return key of record
     */
    public String getKey() {
        return this.key;
    }

    /**
     * gets score
     * 
     * @return score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * gets level
     * 
     * @return level
     */
    public int getLevel() {
        return this.level;
    }
    
}
