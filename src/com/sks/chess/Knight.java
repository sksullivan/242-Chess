package com.sks.chess;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by them on 2/7/2015.
 */
public class Knight extends GamePiece {
    public Knight(int x, int y, boolean isWhite, Board gameBoard) {
        super(x,y,isWhite,gameBoard);
        moveSpecifiers.add(new KnightMoveSpecifier());
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getValidMoveDestinations() {
        ArrayList<Pair<Integer, Integer>> validMoves = new ArrayList<Pair<Integer, Integer>>();
        for (GamePieceMoveSpecifier specifier : moveSpecifiers) {
            Pair<Integer,Integer> generatedMove = specifier.sequentiallyGenerateMovesFromOrigin(new Pair<Integer,Integer>(x,y));
            while (generatedMove != null) {
                if (board.isLocationOnBoard(generatedMove)) {
                    GamePiece pieceAtDestination = board.getPieceAtLocationIfExtant(generatedMove);
                    if (!(pieceAtDestination != null && pieceAtDestination.isWhite == this.isWhite)) {
                        validMoves.add(generatedMove);
                    }
                }
                generatedMove = specifier.sequentiallyGenerateMovesFromOrigin(new Pair<Integer,Integer>(x,y));
            }
        }
        for (GamePieceMoveSpecifier specifier : moveSpecifiers) {
            specifier.reset();
        }
        return validMoves;
    }
}
