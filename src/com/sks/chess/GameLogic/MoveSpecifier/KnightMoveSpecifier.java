package com.sks.chess.GameLogic.MoveSpecifier;

import javafx.util.Pair;

import java.util.ArrayList;

public class KnightMoveSpecifier extends GamePieceMoveSpecifier {
    private boolean shouldGenerateMoveSet;
    private ArrayList<Pair<Integer,Integer>> destinations;

    public KnightMoveSpecifier() {
        shouldGenerateMoveSet = true;
        destinations = new ArrayList<Pair<Integer, Integer>>();
    }

    public Pair<Integer,Integer> sequentiallyGenerateMovesFromOrigin(Pair<Integer, Integer> origin) {
        if (shouldGenerateMoveSet) {

            shouldGenerateMoveSet = false;
            return destinations.get(0);
        }
        generatedMoves++;
        if (generatedMoves >= 8) {
            return null;
        }
        return destinations.get(generatedMoves);
    }
}
