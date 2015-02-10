package com.sks.chess.GameLogic.MoveSpecifier;

import javafx.util.Pair;

public abstract class GamePieceMoveSpecifier {
    int generatedMoves = 0;
    Pair<Integer,Integer> lastGeneratedMove;

    public abstract Pair<Integer,Integer> sequentiallyGenerateMovesFromOrigin(Pair<Integer, Integer> origin);

    public void reset() {
        lastGeneratedMove = null;
        generatedMoves = 0;
    }
}