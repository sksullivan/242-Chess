package test.com.sks.chess;

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.ChessException.InvalidGamePieceLocationException;
import com.sks.chess.GameLogic.GamePiece.Pawn;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    Pair<Integer,Integer> location1, location2;
    Board board;

    @Before
    public void setUp() throws Exception {
        location1 = new Pair<Integer, Integer>(0,0);
        location2 = new Pair<Integer, Integer>(1,1);
        board = new Board(1,1);
    }

    @Test
    public void testAddGamePieceValid() throws Exception {
        Pawn validLocationPawn = new Pawn(0,0,true,board);
        board.addGamePiece(validLocationPawn);
        assertEquals(validLocationPawn,board.getPieceAtLocationIfExtant(location1));
    }

    @Test(expected = InvalidGamePieceLocationException.class)
    public void testAddGamePieceInvalid() throws Exception {
        Pawn invalidLocationPawn = new Pawn(10,10,true,board);
        board.addGamePiece(invalidLocationPawn);
    }

    @Test
    public void testIsLocationOnBoardFalse() throws Exception {
        assertFalse(board.isLocationOnBoard(location2));
    }

    @Test
    public void testIsLocationOnBoardTre() throws Exception {
        assertTrue(board.isLocationOnBoard(location1));
    }

    @Test
    public void testGetPieceAtLocationIfExtantNull() throws Exception {
        board = new Board(1,1);
        assertNull(board.getPieceAtLocationIfExtant(location1));
    }

    @Test
    public void testGetPieceAtLocationIfExtant() throws Exception {
        board = new Board(1,1);
        Pawn validLocationPawn = new Pawn(0,0,true,board);
        board.addGamePiece(validLocationPawn);
        assertEquals(validLocationPawn, board.getPieceAtLocationIfExtant(location1));
    }
}