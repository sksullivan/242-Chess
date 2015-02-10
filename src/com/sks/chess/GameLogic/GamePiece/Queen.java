package com.sks.chess.GameLogic.GamePiece;

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.ChessException.InvalidMoveDirectionCombinationException;
import com.sks.chess.GameLogic.MoveSpecifier.LinearMoveSpecifier;
import com.sun.javafx.scene.traversal.Direction;

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
