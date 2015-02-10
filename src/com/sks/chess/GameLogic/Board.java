package com.sks.chess.GameLogic;

import com.sks.chess.GameLogic.GamePiece.GamePiece;
import com.sks.chess.GameLogic.ChessException.InvalidGamePieceLocationException;
import com.sks.chess.GameLogic.GamePiece.King;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;

public class Board {
    private boolean isWhitesTurn;
    private int width, height;
    private ArrayList<GamePiece> gamePieces;


    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        gamePieces = new ArrayList<GamePiece>();
        isWhitesTurn = true;
    }

    private King getKing(boolean isWhite) {
        for (GamePiece gamePiece : getPiecesInPlay()) {
            if (gamePiece.toString().equals("King")) {
                if (gamePiece.isWhite == isWhite) {
                    return (King)gamePiece;
                }
            }
        }
        return null;
    }

    private void checkGameEndState() {
        King whiteKing = getKing(true);
        King blackKing = getKing(false);
        kingIsCheckmated(whiteKing);
        //kingIsCheckmated(blackKing);
    }

    public boolean kingIsCheckmated(King king) {
        String team = king.isWhite ? "White" : "Black";
        if (kingInCheck(king)) { // King is currently in check
            if (!kingHasNonSelfCheckPlacingMove(king)) {
                if (!kingsTeamCanBlockCheck(king)) {
                    System.out.println("Ckeckmate. "+team+" wins.");
                } else {
                    System.out.println(team+" team can block check.");
                }
            } else {
                System.out.println(team+" king can move out of check.");
            }
        } else {
            if (!kingHasNonSelfCheckPlacingMove(king) && !kingsTeamHasRemainingMoves(king)) {
                System.out.println("Stalemate.");
            }
        }
        return false;
    }

    public boolean kingsTeamHasRemainingMoves(King king) {
        for (GamePiece gamePiece : getPiecesInPlay()) {
            if (gamePiece.isWhite == king.isWhite && !gamePiece.toString().equals("King")) {
                for (Pair<Integer, Integer> teamMove : gamePiece.getValidMoveDestinations()) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean kingInCheck(King king) {
        return locationPutsKingInCheck(king,king.getLocation());
    }
    public boolean kingHasNonSelfCheckPlacingMove(King king) {
        int validMoves = king.getValidMoveDestinations().size();
        for (Pair<Integer, Integer> move : king.getValidMoveDestinations()) {
            if (locationPutsKingInCheck(king, move)) {
                validMoves--;
            }
        }
        return validMoves > 0;
    }
    public boolean locationPutsKingInCheck(King king, Pair<Integer,Integer> location) {
        Pair<Integer,Integer> oldLocation = king.getLocation();
        if (locationInEnemyReach(location,king.isWhite)) {
            return true;
        }
        GamePiece captureableEnemy = getPieceAtLocationIfExtant(location);
        if (captureableEnemy == null) {
            return false;
        }
        captureableEnemy.isInPlay = false;
        king.moveTo(location);
        boolean isInCheck = true;
        if (locationInEnemyReach(king.getLocation(), king.isWhite)) {
            isInCheck = true;
        } else {
            isInCheck = false;
        }
        king.moveTo(oldLocation);
        captureableEnemy.isInPlay = true;
        return isInCheck;
    }
    public boolean locationInEnemyReach(Pair<Integer,Integer> location, boolean isWhite) {
        for (GamePiece gamePiece : getPiecesInPlay()) {
            if (gamePiece.isWhite != isWhite) {
                for (Pair<Integer, Integer> enemyMove : gamePiece.getValidMoveDestinations()) {
                    if (enemyMove.equals(location)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean kingsTeamCanBlockCheck(King king) {
        boolean canExitCheck = false;
        for (GamePiece gamePiece : getPiecesInPlay()) {
            if (gamePiece.isWhite == king.isWhite) {
                for (Pair<Integer,Integer> move : gamePiece.getValidMoveDestinations()) {
                    Pair<Integer,Integer> oldLocation = gamePiece.getLocation();
                    GamePiece destinationPiece = getPieceAtLocationIfExtant(move);
                    if (destinationPiece != null) {
                        destinationPiece.isInPlay = false;
                    }
                    gamePiece.moveTo(move);
                    if (!locationPutsKingInCheck(king, king.getLocation())) {
                        canExitCheck = true;
                    }
                    gamePiece.moveTo(oldLocation);
                    if (destinationPiece != null) {
                        destinationPiece.isInPlay = true;
                    }
                }
            }
        }
        return canExitCheck;
    }

    public void makeMove(GamePiece gamePiece, Pair<Integer,Integer> move) {
        GamePiece destinationPiece = getPieceAtLocationIfExtant(move);
        if (destinationPiece != null) {
            destinationPiece.isInPlay = false;
        }
        gamePiece.moveTo(move);
        isWhitesTurn = !isWhitesTurn;
        checkGameEndState();
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
        ArrayList<GamePiece> piecesInPlay = new ArrayList<GamePiece>();
        for (GamePiece gamePiece : gamePieces) {
            if (gamePiece.isInPlay) {
                piecesInPlay.add(gamePiece);
            }
        }
        return piecesInPlay;
    }

    public ArrayList<GamePiece> getPiecesOutOfPlay() {
        ArrayList<GamePiece> piecesOutOfPlay = new ArrayList<GamePiece>();
        for (GamePiece gamePiece : gamePieces) {
            if (!gamePiece.isInPlay) {
                piecesOutOfPlay.add(gamePiece);
            }
        }
        return piecesOutOfPlay;
    }

    public void addGamePiece(GamePiece gamePiece) throws InvalidGamePieceLocationException {
        if (isLocationOnBoard(gamePiece.getLocation())) {
            gamePieces.add(gamePiece);
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
        for (GamePiece gamePiece : getPiecesInPlay()) {
            if (gamePiece.getX() == location.getKey() && gamePiece.getY() == location.getValue()) {
                return gamePiece;
            }
        }
        return null;
    }
}