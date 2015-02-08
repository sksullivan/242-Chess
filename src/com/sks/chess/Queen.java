package com.sks.chess;

import com.sun.javafx.scene.traversal.Direction;
import javafx.util.Pair;

import java.util.ArrayList;

public class Queen extends Rook {

    public Queen(int x, int y, boolean isWhite, Board gameBoard) {
        super(x,y,isWhite, gameBoard);

        try {
            moveSpecifiers.add(new LinearMoveSpecifier(Direction.UP, Direction.RIGHT, -1));
            moveSpecifiers.add(new LinearMoveSpecifier(Direction.UP, Direction.LEFT, -1));
            moveSpecifiers.add(new LinearMoveSpecifier(Direction.DOWN, Direction.RIGHT, -1));
            moveSpecifiers.add(new LinearMoveSpecifier(Direction.DOWN, Direction.LEFT, -1));
        } catch (InvalidMoveDirectionCombinationException invalidCombinationException) {
            System.err.println("You set up the move specifiers wrong.");
        }
    }
}
