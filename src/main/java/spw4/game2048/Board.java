package spw4.game2048;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

record Position(int x, int y){};

public class Board {

    private static final int BOARDSIZE = 4;
    private int[][] board;
    private int largestValue = 0;
    private int score = 0;

    private boolean isOver = false;


    private final Random random = new Random();


    public Board() {
       board = new int[BOARDSIZE][BOARDSIZE];
    }

    public void init() {
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                board[i][j] = 0;
            }
        }

        score = 0;
        isOver = false;

        generateNewTile();
        generateNewTile();

        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                if (largestValue < board[i][j]) largestValue = board[i][j];
            }
        }


        printBoard();
    }

    public void generateNewTile() {
        Position emptyPosition = getRandomEmptyPosition(board);

        if (emptyPosition != null) {
            setValueAt(emptyPosition.x(), emptyPosition.y(), random.nextDouble() < 0.9 ? 2 : 4);
        } else {
            isOver = true;
        }

    }

    public int getValueAt(int x, int y) throws IndexOutOfBoundsException {

        if (x < 0 || x >= BOARDSIZE || y < 0 || y >= BOARDSIZE) {
            throw new IndexOutOfBoundsException();
        } else {
            return board[x][y];
        }
    }

    public void setValueAt(int x, int y, int value) throws IndexOutOfBoundsException {
        if (x < 0 || x >= BOARDSIZE || y < 0 || y >= BOARDSIZE) {
            throw new IndexOutOfBoundsException();
        } else {
            board[x][y] = value;
        }
    }
    public void printBoard() {
        for (int i = 0; i < BOARDSIZE; i++) {
            printLine(rowToLine(i));
        }
        System.out.println();
    }

    public boolean isOver() {
        return isOver;
    }


   public int getScore() {
        return score;
   }

   public int getLargestValue() {
        return largestValue;
   }

    // movement methods

    void moveUp(){
        for (int i = 0; i < BOARDSIZE; i++) {
            LineToCol(reverseLine(mergeLine(reverseLine(colToLine(i)))), i);
        }
    }

    void moveDown(){
        for (int i = 0; i < BOARDSIZE; i++) {
            LineToCol(mergeLine(colToLine(i)), i);
        }
    }

    void moveLeft() {
        for (int i = 0; i < BOARDSIZE; i++) {
            LineToRow(reverseLine(mergeLine(reverseLine(rowToLine(i)))), i);
        }
    }

    void moveRight() {
        for (int i = 0; i < BOARDSIZE; i++) {
            LineToRow(mergeLine(rowToLine(i)), i);
        }
    }

    private void LineToCol(int[] line, int colNum) {
        for (int i = 0; i < BOARDSIZE; i++) {
            board[i][colNum] = line[i];
        }
    }

    private void LineToRow(int[] line, int rowNum) {
        for (int i = 0; i < BOARDSIZE; i++) {
            board[rowNum][i] = line[i];
        }

    }

    // conversion methods

    private int[] rowToLine (int rowNum) {
        int[] line = new int[BOARDSIZE];

        for (int i = 0; i < BOARDSIZE; i++) {
            line[i] = board[rowNum][i];
        }
        return line;
    }

    private int[] colToLine (int colNum) {
        int[] line = new int[BOARDSIZE];

        for (int i = 0; i < BOARDSIZE; i++) {
            line[i] = board[i][colNum];
        }

        return line;
    }


    // game logic methods

    private int[] mergeLine(int[] line) {
        line = compactLine(line);

        for (int i = line.length - 1; i > 0; i--) {
            if (line[i] != 0 && line[i] == line[i - 1]) {
                line[i] *= 2;
                line[i - 1] = 0;
                if (line[i] > largestValue) largestValue = line[i];
                score += line[i];
                i--;
            }
        }

        return compactLine(line);
    }

    private static int[] compactLine(int[] line) {
        int[] result = new int[line.length];
        int pos = line.length - 1;
        for (int i = line.length - 1; i >= 0; i--) {
            if (line[i] != 0) result[pos--] = line[i];
        }
        return result;
    }

    private static int[] reverseLine(int[] row) {
        int[] tmp = new int[row.length];

        for (int i = 0; i < row.length; i++) {
            tmp[i] = row[row.length - 1 - i];
        }

        return tmp;
    }

    private static void printLine(int[] row) {
        for (int j : row) {
            System.out.print("[ ");
            System.out.print(j + " ");
            System.out.print(" ]");
        }
        System.out.println();

    }




    private List<Position> findEmptyPositions(int[][] board) {
        List<Position> result = new ArrayList<>();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] == 0) {
                    result.add(new Position(x, y));
                }
            }
        }

        return result;
    }

    private Position getRandomEmptyPosition(int[][] board) {

        List<Position> emptyPositions = findEmptyPositions(board);

        if (emptyPositions.isEmpty()) {
            return null;
        }

        return emptyPositions.get((int) (Math.random() * emptyPositions.size()));
    }





}
