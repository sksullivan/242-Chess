package com.sks.chess;

import javafx.util.Pair;

public abstract class GamePieceMoveSpecifier {
    int generatedMoves = 0;

    public abstract Pair<Integer,Integer> sequentiallyGenerateMovesFromOrigin(Pair<Integer, Integer> origin);
    public void reset() {};
}