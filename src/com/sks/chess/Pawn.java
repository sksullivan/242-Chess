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
        moveSpecifiers.add(new LinearMoveSpecifier(Direction.UP,1));
        try {
            moveSpecifiers.add(new LinearMoveSpecifier(Direction.LEFT,Direction.UP,1));
            moveSpecifiers.add(new LinearMoveSpecifier(Direction.LEFT,Direction.UP,1));
        } catch (InvalidMoveDirectionCombinationException combinationException) {
            System.out.println("Specifiers set up wrong.");
        }
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getValidMoveDestinations() {
        GamePieceMoveSpecifier specifier = moveSpecifiers.get(0);
        Pair<Integer,Integer> generatedMove = specifier.sequentiallyGenerateMovesFromOrigin(new Pair<Integer,Integer>(x,y));
        GamePiece pieceAtDestination = board.getPieceAtLocationIfExtant(generatedMove);
        while (generatedMove != null && board.isLocationOnBoard(generatedMove)) {
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
}
