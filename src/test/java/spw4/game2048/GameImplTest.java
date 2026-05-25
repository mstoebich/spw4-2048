package spw4.game2048;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;

class GameImplTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new GameImpl();
        //board.init();
    }

    @Test
    void constructorInitializesGameState() {

        assertThat(game.getMoves()).isZero();
        assertThat(game.getScore()).isZero();
        assertThat(game.isWon()).isFalse();
    }

    @Test
    void initializeInitializesBoard() {

        game.initialize();

        int nonZeroTiles = 0;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (game.getValueAt(x, y) != 0) {
                    nonZeroTiles++;
                }
            }
        }

        assertThat(nonZeroTiles).isEqualTo(2);
    }


    @ParameterizedTest
    @EnumSource(Direction.class)
    void moveExecutesAllDirectionsAndIncrementsMoveCounter(Direction direction) {

        game.initialize();

        game.move(direction);

        assertThat(game.getMoves()).isEqualTo(1);
    }

    @Test
    void isWonReturnsTrueWhenLargestTileIs2048() throws Exception {

        Board board = new Board();
        board.setValueAt(0, 0, 1024);
        board.setValueAt(0, 1, 1024);

        GameImpl game = new GameImpl(board);

        game.move(Direction.left);

        assertThat(game.isWon()).isTrue();
    }


    @Test
    void isOverReturnsTrueWhenBoardIsFullAndNoMovesArePossible() throws Exception {
        Board board = new Board();

        int[][] values = {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        };

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                board.setValueAt(x, y, values[y][x]);
            }
        }

        game = new GameImpl(board);
        game.move(Direction.left);

        assertThat(game.isOver()).isTrue();
    }

    @Test
    void getScoreReturnsCorrectScore() throws Exception {

        Board board = new Board();
        board.setValueAt(0, 0, 2);
        board.setValueAt(0, 1, 2);

        GameImpl game = new GameImpl(board);

        game.move(Direction.left);

        assertThat(game.getScore()).isEqualTo(4);
    }

}
