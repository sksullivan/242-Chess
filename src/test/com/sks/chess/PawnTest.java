package test.com.sks.chess;

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.GamePiece.Pawn;
import javafx.util.Pair;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class PawnTest {
    Board board;

    @Before
    public void init() {
        board = new Board(8,8,null);
    }

    @Test
    public void testPawnCaptureMoves() {
        Pawn pawn = new Pawn(4,0,true,board);
        assertTrue(pawn.getValidCaptureDestinations().contains(new Pair<Integer,Integer>(5,1)));
    }

    @Test
    public void testPawnDoubleMove() {
        Pawn pawn = new Pawn(4,1,true,board);
        assertTrue(pawn.getValidMoveDestinations().contains(new Pair<Integer,Integer>(4,3)));
    }

    @Test
    public void testPawnCannotDoubleMove() {
        Pawn pawn = new Pawn(4,0,true,board);
        assertFalse(pawn.getValidMoveDestinations().contains(new Pair<Integer, Integer>(4, 3)));
    }

    @Test
    public void testPawnSingleMove() {
        Pawn pawn = new Pawn(4,0,true,board);
        assertTrue(pawn.getValidMoveDestinations().contains(new Pair<Integer, Integer>(4, 1)));
    }
}