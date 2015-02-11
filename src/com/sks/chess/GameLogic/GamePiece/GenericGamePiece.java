package com.sks.chess.GameLogic.GamePiece;

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.MoveSpecifier.GamePieceMoveSpecifier;
import javafx.util.Pair;

import java.util.ArrayList;

public abstract class GenericGamePiece {
    protected int x, y;
    public boolean isWhite, isInPlay;

    Board board;
    ArrayList<GamePieceMoveSpecifier> moveSpecifiers;

    public GenericGamePiece(int x, int y, boolean isWhite, Board board) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        moveSpecifiers = new ArrayList<GamePieceMoveSpecifier>();
        this.board = board;
        isInPlay = true;
    }

    public void moveTo(Pair<Integer,Integer> location) {
        x = location.getKey();
        y = location.getValue();
    }

    public abstract ArrayList<Pair<Integer,Integer>> getValidMoveDestinations();

    public ArrayList<Pair<Integer,Integer>> getValidCaptureDestinations() {
        return getValidMoveDestinations();
    };

    public String toString() {
        String rawClassName = getClass().toString();
        return rawClassName.substring(rawClassName.lastIndexOf('.')+1,rawClassName.length());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Pair<Integer,Integer> getLocation() {
        return new Pair<Integer, Integer>(getX(),getY());
    }
}