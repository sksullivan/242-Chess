package com.sks.chess;

import javafx.util.Pair;

import java.util.ArrayList;

public abstract class GamePiece {
    int x, y;
    boolean isWhite;

    Board board;
    ArrayList<GamePieceMoveSpecifier> moveSpecifiers;

    public GamePiece(int x, int y, boolean isWhite, Board board) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        moveSpecifiers = new ArrayList<GamePieceMoveSpecifier>();
        this.board = board;
    }

    public abstract ArrayList<Pair<Integer,Integer>> getValidMoveDestinations();

    public String toString() {
        String rawClassName = getClass().toString();
        return rawClassName.substring(rawClassName.lastIndexOf('.')+1,rawClassName.length());
    }
}