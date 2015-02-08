package com.sks.chess;

import javafx.util.Pair;

import java.util.ArrayList;

public class KnightMoveSpecifier extends GamePieceMoveSpecifier {
    boolean shouldGenerateMoveSet;
    ArrayList<Pair<Integer,Integer>> destinations;

    public KnightMoveSpecifier() {
        shouldGenerateMoveSet = true;
        destinations = new ArrayList<Pair<Integer, Integer>>();
    }

    public void reset() {
        shouldGenerateMoveSet = true;
        generatedMoves = 0;
    }

    public Pair<Integer,Integer> sequentiallyGenerateMovesFromOrigin(Pair<Integer, Integer> origin) {
        if (shouldGenerateMoveSet) {
            destinations.add(new Pair<Integer, Integer>(origin.getKey()+1,origin.getValue()-2));
            destinations.add(new Pair<Integer, Integer>(origin.getKey()+2,origin.getValue()-1));

            destinations.add(new Pair<Integer, Integer>(origin.getKey()+2,origin.getValue()+1));
            destinations.add(new Pair<Integer, Integer>(origin.getKey()+1,origin.getValue()+2));

            destinations.add(new Pair<Integer, Integer>(origin.getKey()-1,origin.getValue()+2));
            destinations.add(new Pair<Integer, Integer>(origin.getKey()-2,origin.getValue()+1));

            destinations.add(new Pair<Integer, Integer>(origin.getKey()-2,origin.getValue()-1));
            destinations.add(new Pair<Integer, Integer>(origin.getKey()-1,origin.getValue()-2));
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
