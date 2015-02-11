package com.sks.chess.GameLogic.GamePiece;

import com.sks.chess.GameLogic.Board;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by them on 2/9/2015.
 */
public class King extends GenericGamePiece {

    public King(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, board);
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getValidMoveDestinations() {
        ArrayList<Pair<Integer, Integer>> validMoves = new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Pair<Integer, Integer>> possibleMoves = new ArrayList<Pair<Integer, Integer>>();
        addAllPossibleMoves(possibleMoves);
        for (Pair<Integer,Integer> location : possibleMoves) {
            if (board.isLocationOnBoard(location)) {
                if (board.getPieceAtLocationIfExtant(location) == null || (board.getPieceAtLocationIfExtant(location) != null && board.getPieceAtLocationIfExtant(location).isWhite != isWhite)) {
                    validMoves.add(location);
                }
            }
        }
        return validMoves;
    }

    private void addAllPossibleMoves(ArrayList<Pair<Integer, Integer>> possibleMoves) {
        possibleMoves.add(new Pair<Integer, Integer>(x,y-1));
        possibleMoves.add(new Pair<Integer, Integer>(x+1,y-1));
        possibleMoves.add(new Pair<Integer, Integer>(x+1,y));
        possibleMoves.add(new Pair<Integer, Integer>(x+1,y+1));
        possibleMoves.add(new Pair<Integer, Integer>(x,y+1));
        possibleMoves.add(new Pair<Integer, Integer>(x-1,y+1));
        possibleMoves.add(new Pair<Integer, Integer>(x-1,y));
        possibleMoves.add(new Pair<Integer, Integer>(x-1,y-1));
    }
}
