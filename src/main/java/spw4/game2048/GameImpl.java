package spw4.game2048;

public class GameImpl implements Game {

    private Board board;
    private int moves;
    private int score;
    private boolean won;
    private boolean over;


    public GameImpl() {
        board = new Board();
        moves = 0;
    }

    public GameImpl(Board board) {
        this.board = board;
        moves = 0;

    }

    public int getMoves() {
        return moves;
    }

    public int getScore() {
        return board.getScore();
    }

    public int getValueAt(int x, int y) {
        return board.getValueAt(x, y);
    }


    public boolean isOver() {
        return board.isOver();
    }

    public boolean isWon() {
        System.out.println(board.getLargestValue());
        return board.getLargestValue() == 2048;
    }

    @Override
    public String toString() {
        return "Score: " + board.getScore() + " Moves: " + moves;
    }

    public void initialize() {
        board.init();
    }

    public void move(Direction direction) {
        switch (direction) {
            case up: {
                board.moveUp();
                break;
            }
            case down: {
                board.moveDown();
                break;
            }
            case left: {
                board.moveLeft();

                break;
            }
            case right: {
                board.moveRight();
                break;
            }
        }


        board.generateNewTile();
        moves++;
    }
}
