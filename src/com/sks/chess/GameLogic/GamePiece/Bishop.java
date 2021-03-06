package com.sks.chess.GameLogic.GamePiece;

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.MoveSpecifier.GamePieceMoveSpecifier;
import com.sks.chess.GameLogic.ChessException.InvalidMoveDirectionCombinationException;
import com.sks.chess.GameLogic.MoveSpecifier.LinearMoveSpecifier;
import com.sun.javafx.scene.traversal.Direction;
import javafx.util.Pair;

import java.util.ArrayList;

public class Bishop extends GenericGamePiece {
    public Bishop(int x, int y, boolean isWhite, Board gameBoard) {
        super(x,y,isWhite, gameBoard);
        try {
            moveSpecifiers.add(new LinearMoveSpecifier(Direction.UP, Direction.LEFT, -1));
            moveSpecifiers.add(new LinearMoveSpecifier(Direction.DOWN, Direction.LEFT, -1));
            moveSpecifiers.add(new LinearMoveSpecifier(Direction.UP, Direction.RIGHT, -1));
            moveSpecifiers.add(new LinearMoveSpecifier(Direction.DOWN, Direction.RIGHT, -1));
        } catch (InvalidMoveDirectionCombinationException combinationException) {
            System.out.println("Specifiers set up wrong.");
        }
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getValidMoveDestinations() {
        ArrayList<Pair<Integer, Integer>> validMoves = new ArrayList<Pair<Integer, Integer>>();
        for (GamePieceMoveSpecifier specifier : moveSpecifiers) {
            Pair<Integer,Integer> generatedMove = specifier.sequentiallyGenerateMovesFromOrigin(new Pair<Integer,Integer>(x,y));
            GenericGamePiece pieceAtDestination = board.getPieceAtLocationIfExtant(generatedMove);
            while (board.isLocationOnBoard(generatedMove)) {
                if (pieceAtDestination != null && pieceAtDestination.isWhite == this.isWhite) {
                    break;
                }
                validMoves.add(generatedMove);
                if (board.getPieceAtLocationIfExtant(generatedMove) != null && board.getPieceAtLocationIfExtant(generatedMove).isWhite != this.isWhite) {
                    break;
                }
                generatedMove = specifier.sequentiallyGenerateMovesFromOrigin(new Pair<Integer, Integer>(x, y));
                pieceAtDestination = board.getPieceAtLocationIfExtant(generatedMove);
            }
        }
        for (GamePieceMoveSpecifier specifier : moveSpecifiers) {
            specifier.reset();
        }
        return validMoves;
    }
}
