public class Evaluate {
    private char[][] gameBoard;
    private int size;
    private int tilesToWin;
    private int maxLevels;
    private int positions = size * size;

    /**
     * constructor
     * fills gameboard of size x size dimensions with just 'e'
     * 
     * @param size
     * @param tilesToWin
     * @param maxLevels
     */
    public Evaluate(int size, int tilesToWin, int maxLevels) {
        this.size = size;
        this.tilesToWin = tilesToWin;
        this.maxLevels = maxLevels;

        this.gameBoard = new char[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.gameBoard[i][j] = 'e';
            }
        }
    }

    /**
     * creates a dictinary object
     * size must be a prime number between 6000 and 10000
     * 
     * @return Dictionary object
     */
    public Dictionary createDictionary() {
        return new Dictionary(7459);
    }

    private String boardToString() {
        String s = "";
        int rows = this.gameBoard.length, cols = this.gameBoard[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                s += this.gameBoard[j][i];
            }
        }
        return s;
    }

    /**
     * checks if the representation of the gameboard is in dict
     * 
     * @param dict
     * @return Record if repr is in dict, otherwise null
     */
    public Record repeatedState(Dictionary dict) {
        String repr = this.boardToString();
        return dict.get(repr);
    }

    /**
     * puts a record containing a representation of the gameboard into dict
     * 
     * @param dict
     * @param score
     * @param level
     */
    public void insertState(Dictionary dict, int score, int level) {
        String repr = this.boardToString();
        Record rec = new Record(repr, score, level);
        try {
            dict.put(rec);
        } catch (Exception err){
            System.out.print(err);
        }
    }

    /**
     * stores symbol
     * 
     * @param row
     * @param col
     * @param symbol
     */
    public void storePlay(int row, int col, char symbol) {
        if (row >= 0 && row < this.gameBoard.length && col >= 0 && col < this.gameBoard[0].length) {
            this.gameBoard[row][col] = symbol;
            this.positions--;
        }
    } 

    /**
     * checks if coord is e
     * 
     * @param row
     * @param col
     * @return boolean on if coord is e
     */
    public boolean squareIsEmpty(int row, int col) {
        return this.gameBoard[row][col] == 'e';
    }

    /**
     * checks if coord is c
     * 
     * @param row
     * @param col
     * @return boolean on if coord is c
     */
    public boolean tileOfComputer(int row, int col) {
        return this.gameBoard[row][col] == 'c';
    }

    /**
     * checks if coord is h
     * 
     * @param row
     * @param col
     * @return boolean on if coord is h
     */
    public boolean tileOfHuman(int row, int col) {
        return this.gameBoard[row][col] == 'h';
    }

    /**
     * checks if won
     * 
     * @param symbol
     * @return boolean on if adjacent tile conditions are met
     */
    public boolean wins(char symbol) {
        int rows = this.gameBoard.length, cols = this.gameBoard[0].length;

        // check rows
        for (int i = 0; i < rows; i++){
            int chain = 0;
            for (int j = 0; j < rows; j++) {
                if (this.gameBoard[i][j] == symbol) {
                    chain++;
                    if (chain == this.size) {
                        return true;
                    }
                } else {
                    chain = 0;
                }
            }
        }

        // check cols
        for (int i = 0; i < rows; i++) {
            int chain = 0;
            for (int j = 0; j < rows; j++) {
                if (this.gameBoard[j][i] == symbol) {
                    chain++;
                    if (chain == this.size) {
                        return true;
                    }
                } else {
                    chain = 0;
                }
            }
        }

        // check diags
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (this.gameBoard[i][j] == symbol) {
                    if ((this.checkLeftDiagonal(i, j, symbol, new Dictionary(7459)) == this.tilesToWin) ||
                        (this.checkRightDiagonal(i, j, symbol, new Dictionary(7459)) == this.tilesToWin)) {
                        return true;
                    }
                }
            }
        }

        return false;

    }

    /**
     * ALTERNATIVE ALGORITHM
     * finds out if a left diagonal chain is of length tilesToWin
     * the intuition is that it finds the first elem of symbol, then tries chaining by going down the diagonal
     * note that a right diagonal is represented as '\'
     * 
     * @param row
     * @param col
     * @param symbol
     * @return
     */
    private int checkLeft(int row, int col, char symbol) {
        int res = 1;
        while (row+1 < this.gameBoard.length && col+1 < this.gameBoard[0].length && this.gameBoard[row][col] == symbol) {
            row++;
            col++;
            res++;
        }
        return res;
    }

    /**
     * ALTERNATIVE ALGORITHM
     * finds out if a right diagonal chain is of length tilesToWin
     * the intuition is that it finds the first elem of symbol, then tries chaining
     * note that a right diagonal is represented as '/'
     * 
     * @param row
     * @param col
     * @param symbol
     * @return
     */
    private int checkRight(int row, int col, char symbol) {
        int res = 1;
        while (row+1 < this.gameBoard.length && col-1 >= 0) {
            row++;
            col--;
            res++;
        }
        return res;
    }
 
    /**
     * recursive depth first search on left diagonals, \, and computes length
     * 
     * @param row
     * @param col
     * @param symbol
     * @param visited
     * @return
     */
    private int checkLeftDiagonal(int row, int col, char symbol, Dictionary visited) {
        String coords = "[" + row + "," + col + "]"; //[r,c]
        if ((row < 0) || (row >= this.gameBoard.length) || (col < 0) || (col >= this.gameBoard[0].length)) {
            return 0;
        }

        else if (this.gameBoard[row][col] != symbol) {
            return 0;
        }

        else if (visited.get(coords) != null) {
            return 0;
        }

        else {
            visited.put(new Record(coords, 0, 0));
            int upper = checkLeftDiagonal(row-1, col-1, symbol, visited);
            int lower = checkLeftDiagonal(row+1, col+1, symbol, visited);
            return 1 + upper + lower;
        }
    }
    
    /**
     * recursive depth first search on right diagonal /, and computes length
     * 
     * @param row
     * @param col
     * @param symbol
     * @param visited
     * @return
     */
    private int checkRightDiagonal(int row, int col, char symbol, Dictionary visited) {
        String coords = "[" + row + "," + col + "]";
        if ((row < 0) || (row >= this.gameBoard.length) || (col < 0) || (col >= this.gameBoard[0].length)) {
            return 0;
        }

        else if (this.gameBoard[row][col] != symbol) {
            return 0;
        }

        else if (visited.get(coords) != null) {
            return 0;
        }

        else {
            visited.put(new Record(coords, 0, 0));
            int upper = checkRightDiagonal(row-1, col+1, symbol, visited);
            int lower = checkRightDiagonal(row+1, col-1, symbol, visited);
            return 1 + upper + lower;
        }
    }

    /**
     * checks if gameboard is draw
     * 
     * @return boolean on if any positions remain
     */
    public boolean isDraw() {
        return this.positions == 0;
    }

    /**
     * evaluates board
     * 
     * @return int 0, 1, 2, or 3 depending on conditions
     */
    public int evalBoard() {
        if (this.wins('c')) {
            return 3;
        } else if (this.wins('h')) {
            return 0;
        } else if (this.isDraw()) {
            return 2;
        } else {
            return 1;
        }
    }

}