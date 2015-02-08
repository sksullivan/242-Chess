package com.sks.chess;

import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    int width, height;
    ArrayList<GamePiece> piecesInPlay;
    ArrayList<GamePiece> piecesOutOfPlay;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        piecesInPlay = new ArrayList<GamePiece>();
        piecesOutOfPlay = new ArrayList<GamePiece>();
    }

    public void addGamePiece(GamePiece gamePiece) {
        piecesInPlay.add(gamePiece);
    }

    public boolean isLocationOnBoard(Pair<Integer,Integer> location) {
        return location.getKey() >= 0 && location.getKey() < width && location.getValue() >= 0 && location.getValue() < height;
    }

    public GamePiece getPieceAtLocationIfExtant(Pair<Integer,Integer> location) {
        for (GamePiece gamePiece : piecesInPlay) {
            if (gamePiece.x == location.getKey() && gamePiece.y == location.getValue()) {
                return gamePiece;
            }
        }
        return null;
    }
}