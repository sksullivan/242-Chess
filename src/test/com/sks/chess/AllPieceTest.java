package test.com.sks.chess;

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.GamePiece.*;
import javafx.util.Pair;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

public class AllPieceTest {
    Board board;

    @Before
    public void init() {
        board = new Board(8,8,null);
    }
    
    @Test
    public void testRookValidMove() {
        Rook rook = new Rook(0,0,false,board);
        assertTrue(rook.getValidMoveDestinations().contains(new Pair<Integer, Integer>(7,0)));
    }

    @Test
    public void testRookInvalidMove() {
        Rook rook = new Rook(0,0,false,board);
        assertFalse(rook.getValidMoveDestinations().contains(new Pair<Integer, Integer>(7, 7)));
    }

    @Test
    public void testQueenValidMove() {
        Queen queen = new Queen(0,0,false,board);
        assertTrue(queen.getValidMoveDestinations().contains(new Pair<Integer, Integer>(7,0)));
    }

    @Test
    public void testQueenInvalidMove() {
        Queen queen = new Queen(0,0,false,board);
        assertFalse(queen.getValidMoveDestinations().contains(new Pair<Integer, Integer>(6, 7)));
    }

    @Test
    public void testKingValidMove() {
        King king = new King(0,0,false,board);
        assertTrue(king.getValidMoveDestinations().contains(new Pair<Integer, Integer>(1,1)));
    }

    @Test
    public void testKingInvalidMove() {
        King king = new King(0,0,false,board);
        assertFalse(king.getValidMoveDestinations().contains(new Pair<Integer, Integer>(7, 7)));
    }

    @Test
    public void testKnightValidMove() {
        Knight knight = new Knight(0,0,false,board);
        assertTrue(knight.getValidMoveDestinations().contains(new Pair<Integer, Integer>(1,2)));
    }

    @Test
    public void testKnightInvalidMove() {
        Knight knight = new Knight(0,0,false,board);
        assertFalse(knight.getValidMoveDestinations().contains(new Pair<Integer, Integer>(7, 7)));
    }

    @Test
    public void testBishopValidMove() {
        Bishop bishop = new Bishop(0,0,false,board);
        assertTrue(bishop.getValidMoveDestinations().contains(new Pair<Integer, Integer>(7,7)));
    }

    @Test
    public void testBishopInvalidMove() {
        Bishop bishop = new Bishop(0,0,false,board);
        assertFalse(bishop.getValidMoveDestinations().contains(new Pair<Integer, Integer>(1, 2)));
    }
}
