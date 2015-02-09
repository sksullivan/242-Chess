package com.sks.chess;

import com.sun.javafx.scene.traversal.Direction;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by them on 2/8/2015.
 */
public class Pawn extends GamePiece {

    public Pawn(int x, int y, boolean isWhite, Board board) {
        super(x,y,isWhite,board);
        try {
            if (y == 1) {
                moveSpecifiers.add(new LinearMoveSpecifier(Direction.DOWN,2));
                moveSpecifiers.add(new LinearMoveSpecifier(Direction.LEFT, Direction.DOWN, 1));
                moveSpecifiers.add(new LinearMoveSpecifier(Direction.RIGHT, Direction.DOWN, 1));
            } else {
                moveSpecifiers.add(new LinearMoveSpecifier(Direction.UP,2));
                moveSpecifiers.add(new LinearMoveSpecifier(Direction.LEFT, Direction.UP, 1));
                moveSpecifiers.add(new LinearMoveSpecifier(Direction.RIGHT, Direction.UP, 1));
            }
        } catch (InvalidMoveDirectionCombinationException combinationException) {
            System.out.println("Specifiers set up wrong.");
        }
    }

    public boolean isOnHomeRow() {
        return y == board.height - 2 || y == 1;
    }

    public void addSingleAndDoubleMoves(ArrayList<Pair<Integer, Integer>> validMoves) {
        GamePieceMoveSpecifier specifier = moveSpecifiers.get(0);
        Pair<Integer,Integer> oneSquareGeneratedMove = specifier.sequentiallyGenerateMovesFromOrigin(new Pair<Integer,Integer>(x,y));
        Pair<Integer,Integer> twoSquareGeneratedMove = specifier.sequentiallyGenerateMovesFromOrigin(new Pair<Integer,Integer>(x,y));
        GamePiece oneSquareGamePiece = board.getPieceAtLocationIfExtant(oneSquareGeneratedMove);
        GamePiece twoSquareGamePiece = board.getPieceAtLocationIfExtant(twoSquareGeneratedMove);
        if (oneSquareGamePiece == null) {
            validMoves.add(oneSquareGeneratedMove);
            if (twoSquareGamePiece == null && isOnHomeRow()) {
                validMoves.add(twoSquareGeneratedMove);
            }
        }
        specifier.reset();
    }

    public void addDiagonalMoves(ArrayList<Pair<Integer, Integer>> validMoves) {
        for (int i = 1; i < 3; i++) {
            GamePieceMoveSpecifier diagonalMoveSpecifier = moveSpecifiers.get(i);
            Pair<Integer, Integer> diagonalGeneratedMove = diagonalMoveSpecifier.sequentiallyGenerateMovesFromOrigin(new Pair<Integer, Integer>(x, y));
            if (board.isLocationOnBoard(diagonalGeneratedMove) && board.getPieceAtLocationIfExtant(diagonalGeneratedMove) != null && board.getPieceAtLocationIfExtant(diagonalGeneratedMove).isWhite != isWhite) {
                validMoves.add(diagonalGeneratedMove);
            }
            diagonalMoveSpecifier.reset();
        }
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getValidMoveDestinations() {
        ArrayList<Pair<Integer, Integer>> validMoves = new ArrayList<Pair<Integer, Integer>>();
        addSingleAndDoubleMoves(validMoves);
        addDiagonalMoves(validMoves);
        return validMoves;
    }
}
