import spw4.game2048.Board;

import static spw4.game2048.Util.randomBase2;

public class main {

    private static final int BOARDSIZE = 4;
    private int[][] board;

    public static void main(String[] args) {
        Board b = new Board();

        b.init();

        b.printBoard();


    }
}

