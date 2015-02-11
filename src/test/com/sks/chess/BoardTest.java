package test.com.sks.chess;

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.ChessException.InvalidGamePieceLocationException;
import com.sks.chess.GameLogic.GamePiece.*;
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
        board = new Board(1,1,null);
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
        board = new Board(1,1,null);
        assertNull(board.getPieceAtLocationIfExtant(location1));
    }

    @Test
    public void testGetPieceAtLocationIfExtant() throws Exception {
        board = new Board(1,1,null);
        Pawn validLocationPawn = new Pawn(0,0,true,board);
        board.addGamePiece(validLocationPawn);
        assertEquals(validLocationPawn, board.getPieceAtLocationIfExtant(location1));
    }

    @Test
    public void testEndConditionStalemate1() {

    }

    @Test
    public void testEndConditionStalemate2() {

    }

    @Test
    public void testEndConditionStalemate3() {

    }

    @Test
    public void testEndConditionPawnPinCheckmate() {
        Board board = new Board(8,8,null);
        King whiteKing = new King(1, 1, true, board);
        King blackKing = new King(3, 7, false, board);
        try {
            board.addGamePiece(blackKing);
            board.addGamePiece(new Bishop(2, 7, false, board));
            board.addGamePiece(new Queen(4, 7, false, board));
            board.addGamePiece(new Pawn(4, 6, false, board));

            board.addGamePiece(whiteKing);
            board.addGamePiece(new Pawn(4, 5, true, board));
            board.addGamePiece(new Bishop(1, 5, true, board));
        } catch (InvalidGamePieceLocationException locationException) {
            System.err.println("Please check game piece placement code, invalid locations found.");
        }
        board.makeMove(whiteKing,new Pair<Integer, Integer>(2,2));
        assertTrue(board.kingIsCheckmated(blackKing));
    }

    @Test
    public void testEndConditionPawnKingPinCheckmate() {
        Board board = new Board(8,8,null);
        King whiteKing = new King(0, 5, true, board);
        King blackKing = new King(0, 6, false, board);
        try {
            board.addGamePiece(blackKing);
            board.addGamePiece(new Pawn(0, 2, false, board));
            board.addGamePiece(new Pawn(1, 2, false, board));

            board.addGamePiece(whiteKing);
            board.addGamePiece(new Pawn(2, 4, true, board));
            board.addGamePiece(new Pawn(1, 6, true, board));
        } catch (InvalidGamePieceLocationException locationException) {
            System.err.println("Please check game piece placement code, invalid locations found.");
        }
        board.makeMove(whiteKing,new Pair<Integer, Integer>(0,5));
        assertTrue(board.kingIsCheckmated(blackKing));
    }

    @Test
    public void testEndConditionRookPinCheckmate() {
        Board board = new Board(8,8,null);
        King whiteKing = new King(0, 0, true, board);
        Rook blackRook = new Rook(7, 5, false, board);
        try {
            board.addGamePiece(new King(7, 7, false, board));
            board.addGamePiece(blackRook);
            board.addGamePiece(new Rook(1, 7, false, board));

            board.addGamePiece(whiteKing);
        } catch (InvalidGamePieceLocationException locationException) {
            System.err.println("Please check game piece placement code, invalid locations found.");
        }
        board.makeMove(whiteKing,new Pair<Integer, Integer>(0,1));
        board.makeMove(blackRook,new Pair<Integer, Integer>(0,5));
        assertTrue(board.kingIsCheckmated(whiteKing));
    }

    @Test
    public void testEndConditionNonmate1() {

    }

    @Test
    public void testEndConditionNonmate2() {

    }

    @Test
    public void testEndConditionNonmate3() {

    }
}