package com.sks.chess.GameLogic;

import com.sks.chess.GameLogic.GamePiece.GamePiece;
import com.sks.chess.GameLogic.ChessException.InvalidGamePieceLocationException;
import javafx.util.Pair;

import java.util.ArrayList;

public class Board {
    private boolean isWhitesTurn;
    private int width, height;
    private ArrayList<GamePiece> piecesInPlay;
    private ArrayList<GamePiece> piecesOutOfPlay;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        piecesInPlay = new ArrayList<GamePiece>();
        piecesOutOfPlay = new ArrayList<GamePiece>();
        isWhitesTurn = true;
    }

    public void makeMove(GamePiece gamePiece, Pair<Integer,Integer> move) {
        GamePiece destinationPiece = getPieceAtLocationIfExtant(move);
        if (destinationPiece != null) { // Concurrent MOD exception TODO
            piecesInPlay.remove(destinationPiece);
            piecesOutOfPlay.add(destinationPiece);
        }
        gamePiece.moveTo(move);
        isWhitesTurn = !isWhitesTurn;
    }

    public boolean isWhitesTurn() {
        return isWhitesTurn;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<GamePiece> getPiecesInPlay() {
        return piecesInPlay;
    }

    public ArrayList<GamePiece> getPiecesOutOfPlay() {
        return piecesOutOfPlay;
    }

    public void addGamePiece(GamePiece gamePiece) throws InvalidGamePieceLocationException {
        if (isLocationOnBoard(new Pair<Integer, Integer>(gamePiece.getX(),gamePiece.getY()))) {
            piecesInPlay.add(gamePiece);
        } else {
            throw new InvalidGamePieceLocationException("Invalid placement of game piece.");
        }
    }

    public boolean isLocationOnBoard(Pair<Integer,Integer> location) {
        return location.getKey() >= 0 && location.getKey() < width && location.getValue() >= 0 && location.getValue() < height;
    }

    public GamePiece getPieceAtLocationIfExtant(Pair<Integer,Integer> location) {
        if (!isLocationOnBoard(location)) {
            return null;
        }
        for (GamePiece gamePiece : piecesInPlay) {
            if (gamePiece.getX() == location.getKey() && gamePiece.getY() == location.getValue()) {
                return gamePiece;
            }
        }
        return null;
    }
}