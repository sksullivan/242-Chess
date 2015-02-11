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
    public void testEndConditionRookLockStalemate() {
        Board board = new Board(8,8,null);
        King whiteKing = new King(7, 7, true, board);
        King blackKing = new King(0, 0, false, board);
        try {
            board.addGamePiece(blackKing);
            board.addGamePiece(new Rook(1, 7, true, board));
            board.addGamePiece(new Rook(7, 1, true, board));

            board.addGamePiece(whiteKing);
        } catch (InvalidGamePieceLocationException locationException) {
            System.err.println("Please check game piece placement code, invalid locations found.");
        }
        board.makeMove(whiteKing, new Pair<Integer, Integer>(6, 6));
        assertTrue(board.isStalemate());
    }

    @Test
    public void testEndConditionPawnKingBlockingStalemate() {
        Board board = new Board(8,8,null);
        Pawn blackPawn = new Pawn(0, 5, false, board);
        King whiteKing = new King(2, 2, true, board);
        try {
            board.addGamePiece(new King(1, 0, false, board));
            board.addGamePiece(blackPawn);
            board.addGamePiece(new Pawn(1, 3, false, board));
            board.addGamePiece(new Pawn(2, 4, false, board));

            board.addGamePiece(whiteKing);
            board.addGamePiece(new Pawn(0, 3, true, board));
            board.addGamePiece(new Pawn(2, 3, true, board));
        } catch (InvalidGamePieceLocationException locationException) {
            System.err.println("Please check game piece placement code, invalid locations found.");
        }
        board.makeMove(whiteKing, new Pair<Integer, Integer>(1, 2));
        board.makeMove(blackPawn, new Pair<Integer, Integer>(0, 4));
        assertTrue(board.isStalemate());
    }

    @Test
    public void testEndConditionSuperLockStalemate() {
        Board board = new Board(8,8,null);
        King blackKing = new King(4, 7, false, board);
        King whiteKing = new King(4, 0, true, board);
        try {
            board.addGamePiece(whiteKing);
            board.addGamePiece(new Pawn(0, 1, true, board));
            board.addGamePiece(new Pawn(1, 1, true, board));
            board.addGamePiece(new Pawn(2, 3, true, board));
            board.addGamePiece(new Pawn(3, 2, true, board));
            board.addGamePiece(new Pawn(4, 5, true, board));
            board.addGamePiece(new Pawn(5, 4, true, board));
            board.addGamePiece(new Pawn(6, 1, true, board));
            board.addGamePiece(new Pawn(7, 1, true, board));

            board.addGamePiece(new Rook(0, 0, true, board));
            board.addGamePiece(new Knight(1, 0, true, board));
            board.addGamePiece(new Knight(6, 0, true, board));
            board.addGamePiece(new Rook(7, 0, true, board));
            board.addGamePiece(new Bishop(0, 3, true, board));
            board.addGamePiece(new Bishop(1, 5, true, board));
            board.addGamePiece(new Queen(7, 4, true, board));

            board.addGamePiece(blackKing);
            board.addGamePiece(new Pawn(0, 4, false, board));
            board.addGamePiece(new Pawn(1, 6, false, board));
            board.addGamePiece(new Pawn(2, 4, false, board));
            board.addGamePiece(new Pawn(3, 3, false, board));
            board.addGamePiece(new Pawn(4, 6, false, board));
            board.addGamePiece(new Pawn(5, 5, false, board));
            board.addGamePiece(new Pawn(6, 6, false, board));
            board.addGamePiece(new Pawn(7, 5, false, board));

            board.addGamePiece(new Bishop(2, 7, false, board));
            board.addGamePiece(new Bishop(5, 7, false, board));
            board.addGamePiece(new Knight(6, 7, false, board));
            board.addGamePiece(new Rook(7, 7, false, board));
            board.addGamePiece(new Queen(7, 6, false, board));
            board.addGamePiece(new Knight(3, 6, false, board));
            board.addGamePiece(new Rook(6, 5, false, board));
        } catch (InvalidGamePieceLocationException locationException) {
            System.err.println("Please check game piece placement code, invalid locations found.");
        }
        board.makeMove(whiteKing, new Pair<Integer, Integer>(3, 0));
        assertTrue(board.isStalemate());
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
        board.makeMove(whiteKing, new Pair<Integer, Integer>(2, 2));
        assertTrue(board.kingIsCheckmated(blackKing));
    }

    @Test
    public void testEndConditionPawnKingPinCheckmate() {
        Board board = new Board(8,8,null);
        Pawn blackPawn = new Pawn(1, 6, false, board);
        King whiteKing = new King(1, 3, true, board);
        try {
            board.addGamePiece(whiteKing);
            board.addGamePiece(new Pawn(0, 2, true, board));
            board.addGamePiece(new Pawn(1, 2, true, board));

            board.addGamePiece(blackPawn);
            board.addGamePiece(new King(0, 5, false, board));
            board.addGamePiece(new Pawn(2, 4, false, board));
        } catch (InvalidGamePieceLocationException locationException) {
            System.err.println("Please check game piece placement code, invalid locations found.");
        }
        board.makeMove(whiteKing,new Pair<Integer, Integer>(0,3));
        board.makeMove(blackPawn,new Pair<Integer, Integer>(1,4));
        assertTrue(board.kingIsCheckmated(whiteKing));
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
    public void testEndConditionFalsePawnPinNonmate() {
        Board board = new Board(8,8,null);
        King blackKing = new King(7, 7, false, board);
        King whiteKing = new King(0, 0, true, board);
        try {
            board.addGamePiece(blackKing);
            board.addGamePiece(whiteKing);
            board.addGamePiece(new Pawn(7,5,true,board));
            board.addGamePiece(new Pawn(5,5,true,board));
            board.addGamePiece(new Pawn(5,6,true,board));
            board.addGamePiece(new Rook(0,7,true,board));
        } catch (InvalidGamePieceLocationException locationException) {
            System.err.println("Please check game piece placement code, invalid locations found.");
        }
        board.makeMove(whiteKing,new Pair<Integer, Integer>(0,1));
        assertFalse(board.kingIsCheckmated(blackKing));
        assertFalse(board.isStalemate());
    }
}