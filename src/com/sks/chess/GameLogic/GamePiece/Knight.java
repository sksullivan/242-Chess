package com.sks.chess.GameLogic.GamePiece;

import com.sks.chess.GameLogic.Board;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.ListIterator;

public class Knight extends GenericGamePiece {
    public Knight(int x, int y, boolean isWhite, Board gameBoard) {
        super(x,y,isWhite,gameBoard);
    }

    private void addRawValidMoves(ArrayList<Pair<Integer,Integer>> validMoves) {
        Pair<Integer,Integer> origin = new Pair<Integer, Integer>(x,y);
        validMoves.add(new Pair<Integer, Integer>(origin.getKey()+1,origin.getValue()-2));
        validMoves.add(new Pair<Integer, Integer>(origin.getKey()+2,origin.getValue()-1));
        validMoves.add(new Pair<Integer, Integer>(origin.getKey()+2,origin.getValue()+1));
        validMoves.add(new Pair<Integer, Integer>(origin.getKey()+1,origin.getValue()+2));
        validMoves.add(new Pair<Integer, Integer>(origin.getKey()-1,origin.getValue()+2));
        validMoves.add(new Pair<Integer, Integer>(origin.getKey()-2,origin.getValue()+1));
        validMoves.add(new Pair<Integer, Integer>(origin.getKey()-2,origin.getValue()-1));
        validMoves.add(new Pair<Integer, Integer>(origin.getKey() - 1, origin.getValue() - 2));
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getValidMoveDestinations() {
        ArrayList<Pair<Integer,Integer>> validMoves = new ArrayList<Pair<Integer, Integer>>();
        addRawValidMoves(validMoves);
        ListIterator moveIterator = validMoves.listIterator();
        while (moveIterator.hasNext()) {
            Pair<Integer,Integer> move = (Pair<Integer, Integer>) moveIterator.next();
            if (!board.isLocationOnBoard(move) || (board.getPieceAtLocationIfExtant(move) != null && board.getPieceAtLocationIfExtant(move).isWhite == isWhite)) {
                moveIterator.remove();
            }
        }
        return validMoves;
    }
}
