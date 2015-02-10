package com.sks.chess.GameLogic.MoveSpecifier;

import com.sks.chess.GameLogic.ChessException.InvalidMoveDirectionCombinationException;
import com.sun.javafx.scene.traversal.Direction;
import javafx.util.Pair;

public class LinearMoveSpecifier extends GamePieceMoveSpecifier {
    private int distance;

    private Direction cardinalDirection, secondaryDirection;

    public LinearMoveSpecifier (Direction cardinalDirection, Direction secondaryDirection, int distance) throws InvalidMoveDirectionCombinationException {
        if ((cardinalDirection == Direction.LEFT && secondaryDirection == Direction.RIGHT) || (cardinalDirection == Direction.RIGHT && secondaryDirection == Direction.LEFT)) {
            throw new InvalidMoveDirectionCombinationException("LEFT and RIGHT are opposite, invalid direction combination.");
        } else if ((cardinalDirection == Direction.UP && secondaryDirection == Direction.DOWN) || (cardinalDirection == Direction.DOWN && secondaryDirection == Direction.UP)) {
            throw new InvalidMoveDirectionCombinationException("DOWN and UP are opposite, invalid direction combination.");
        }
        this.cardinalDirection = cardinalDirection;
        this.secondaryDirection = secondaryDirection;
        this.distance = distance;
        lastGeneratedMove = null;
    }

    public LinearMoveSpecifier (Direction cardinalDirection, int distance) {
        this.cardinalDirection = cardinalDirection;
        this.secondaryDirection = null;
        this.distance = distance;
        lastGeneratedMove = null;
    }

    public Pair<Integer,Integer> sequentiallyGenerateMovesFromOrigin(Pair<Integer,Integer> origin) {
        if (distance > 0 && generatedMoves == distance) {
            return null;
        }
        generatedMoves++;
        if (lastGeneratedMove == null) {
            lastGeneratedMove = getAdjacentMoveTo(origin);
        } else {
            lastGeneratedMove = getAdjacentMoveTo(lastGeneratedMove);
        }
        return lastGeneratedMove;
    }

    private Pair<Integer,Integer> getAdjacentMoveTo(Pair<Integer,Integer> origin) {
        if (secondaryDirection == null) {
            if (cardinalDirection == Direction.UP) {
                return new Pair<Integer,Integer>(origin.getKey(),origin.getValue()-1);
            } else if (cardinalDirection == Direction.DOWN) {
                return new Pair<Integer,Integer>(origin.getKey(),origin.getValue()+1);
            } else if (cardinalDirection == Direction.LEFT) {
                return new Pair<Integer,Integer>(origin.getKey()-1,origin.getValue());
            } else {
                return new Pair<Integer,Integer>(origin.getKey()+1,origin.getValue());
            }
        } else {
            if ((cardinalDirection == Direction.UP && secondaryDirection == Direction.LEFT) || (cardinalDirection == Direction.LEFT && secondaryDirection == Direction.UP)) {
                return new Pair<Integer,Integer>(origin.getKey()-1,origin.getValue()-1);
            } else if ((cardinalDirection == Direction.UP && secondaryDirection == Direction.RIGHT) || (cardinalDirection == Direction.RIGHT && secondaryDirection == Direction.UP)) {
                return new Pair<Integer,Integer>(origin.getKey()+1,origin.getValue()-1);
            } else if ((cardinalDirection == Direction.DOWN && secondaryDirection == Direction.LEFT) || (cardinalDirection == Direction.LEFT && secondaryDirection == Direction.DOWN)) {
                return new Pair<Integer,Integer>(origin.getKey()-1,origin.getValue()+1);
            } else {
                return new Pair<Integer,Integer>(origin.getKey()+1,origin.getValue()+1);
            }
        }
    }
}
