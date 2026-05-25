package spw4.game2048;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        //board.init();
    }

    @Test
    void setValueAtStoresValueAtCorrectPosition() {
        // Act
        board.setValueAt(1, 2, 8);

        // Assert
        assertThat(board.getValueAt(1, 2)).isEqualTo(8);
    }

    // SetValueAt-Tests

    @Test
    void setValueAtThrowsForNegativeRow() {
        assertThatThrownBy(() -> board.setValueAt(-1, 0, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void setValueAtThrowsForNegativeCol() {
        assertThatThrownBy(() -> board.setValueAt(0, -1, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void setValueAtThrowsWhenRowLargerThanSize() {
        assertThatThrownBy(() -> board.setValueAt(4, 0, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void setValueAtThrowsWhenColLargerThanSize() {
        assertThatThrownBy(() -> board.setValueAt(0, 4, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }


    // GetValueAt-Tests

    @Test
    void getValueAtThrowsForNegativeRow() {
        assertThatThrownBy(() -> board.getValueAt(-1, 0))
            .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void getValueAtThrowsForNegativeCol() {
        assertThatThrownBy(() -> board.getValueAt(0, -1))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void getValueAtThrowsWhenRowLargerThanSize() {
        assertThatThrownBy(() -> board.getValueAt(4, 0))
            .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void getValueAtThrowsWhenColLargerThanSize() {
        assertThatThrownBy(() -> board.getValueAt(0, 4))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }


    // Tests Move-Logic without generating new tiles
    // New tiles are generated at random positions, so its non-deterministic and therefore not tested here

    @Test
    void moveDownMergesEqualTilesInSameColumn() {
        // Arrange
        board.setValueAt(0, 0, 2);
        board.setValueAt(1, 0, 2);

        // Act
        board.moveDown();

        // Assert — die beiden 2er sollen unten zu einer 4 werden
        assertThat(board.getValueAt(3, 0)).isEqualTo(4);
        assertThat(board.getValueAt(0, 0)).isEqualTo(0);
    }

    @Test
    void moveUpMergesEqualTilesInSameColumn() {
        // Arrange
        board.setValueAt(0, 0, 2);
        board.setValueAt(1, 0, 2);

        // Act
        board.moveUp();

        // Assert — die beiden 2er sollen unten zu einer 4 werden
        assertThat(board.getValueAt(0, 0)).isEqualTo(4);
        assertThat(board.getValueAt(3, 0)).isEqualTo(0);
    }

    @Test
    void moveLeftMergesEqualTilesInSameRow() {
        // Arrange
        board.setValueAt(0, 0, 2);
        board.setValueAt(0, 1, 2);

        // Act
        board.moveLeft();

        // Assert — die beiden 2er sollen unten zu einer 4 werden
        assertThat(board.getValueAt(0, 0)).isEqualTo(4);
        assertThat(board.getValueAt(0, 3)).isEqualTo(0);
    }


    @Test
    void moveRigtMergesEqualTilesInSameRow() {
        // Arrange
        board.setValueAt(0, 0, 2);
        board.setValueAt(0, 1, 2);

        // Act
        board.moveRight();

        // Assert — die beiden 2er sollen unten zu einer 4 werden
        assertThat(board.getValueAt(0, 3)).isEqualTo(4);
        assertThat(board.getValueAt(0, 0)).isEqualTo(0);
    }

    // Tests Game-Move


    @Test
    void generateNewTile_addsTileToBoardWithExistingTile() {
        board.init();
        board.generateNewTile();

        assertEquals(3, countNonZeroTiles());
        assertEquals(BOARDSIZE * BOARDSIZE - 3, countZeroTiles());
    }

    @Test
    void generateNewTile_addsTileToBoardWithNoEmptyTile() {
        fillBoard();
        board.generateNewTile();

        assertEquals(BOARDSIZE * BOARDSIZE, countNonZeroTiles());
        assertEquals(0, countZeroTiles());
    }

    @Test
    void moveWithExistingLargestValue(){

        // setup - lagestValue somit 16
        board.setValueAt(0, 0, 8);
        board.setValueAt(0, 1, 8);

        board.moveLeft();

        // kleinere Werte werden gemerged -> largestValue bleibt 16
        board.setValueAt(0, 0, 2);
        board.setValueAt(0, 1, 2);

        board.moveLeft();


        assertEquals(16, board.getLargestValue());
    }

    @Test
    void toStringReturnsScoreAndMovesInitially() {
        GameImpl game = new GameImpl();

        String result = game.toString();

        assertThat(result).isEqualTo("Score: 0 Moves: 0");
    }


    // Helper-Functions

    private static final int BOARDSIZE = 4;

    private int countNonZeroTiles() {
        int count = 0;

        for (int y = 0; y < BOARDSIZE; y++) {
            for (int x = 0; x < BOARDSIZE; x++) {
                if (board.getValueAt(x, y) != 0) {
                    count++;
                }
            }
        }

        return count;
    }

    private int countZeroTiles() {
        int count = 0;

        for (int y = 0; y < BOARDSIZE; y++) {
            for (int x = 0; x < BOARDSIZE; x++) {
                if (board.getValueAt(x, y) == 0) {
                    count++;
                }
            }
        }

        return count;
    }


    private void fillBoard() {
        for (int y = 0; y < BOARDSIZE; y++) {
            for (int x = 0; x < BOARDSIZE; x++) {
                board.setValueAt(x, y, 2);
            }
        }
    }
}
