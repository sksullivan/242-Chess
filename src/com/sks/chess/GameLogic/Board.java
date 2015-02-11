package com.sks.chess.GameLogic;

import com.sks.chess.GUI.EventHandler;
import com.sks.chess.GameLogic.GamePiece.GenericGamePiece;
import com.sks.chess.GameLogic.ChessException.InvalidGamePieceLocationException;
import com.sks.chess.GameLogic.GamePiece.King;
import javafx.util.Pair;

import java.util.ArrayList;

public class Board {
    private boolean isWhitesTurn;
    private int width, height;
    private ArrayList<GenericGamePiece> gamePieces;
    private EventHandler eventHandler;

    public Board(int width, int height, EventHandler eventHandler) {
        this.width = width;
        this.height = height;
        gamePieces = new ArrayList<GenericGamePiece>();
        isWhitesTurn = true;
        this.eventHandler = eventHandler;
    }

    private King getKing(boolean isWhite) {
        for (GenericGamePiece gamePiece : getPiecesInPlay()) {
            if (gamePiece.toString().equals("King")) {
                if (gamePiece.isWhite == isWhite) {
                    return (King)gamePiece;
                }
            }
        }
        return null;
    }

    public void checkGameEndState() {
        King whiteKing = getKing(true);
        King blackKing = getKing(false);
        if (kingIsCheckmated(whiteKing) || kingIsCheckmated(blackKing) || isStalemate()) {
            if (eventHandler != null) { // Include null check for running tests, which are headless
                eventHandler.endGame();
            }
        }
    }

    public boolean kingIsCheckmated(King king) {
        String winningTeam = king.isWhite ? "Black" : "White"; // We're determining if the king in question loses, so here we find the opponent's team
        String myTeam = !king.isWhite ? "Black" : "White";
        if (kingInCheck(king)) { // King is currently in check
            if (!kingCanMoveOutOfCheck(king)) {
                if (!kingsTeamCanBlockCheck(king)) {
                    System.out.println("Ckeckmate. "+winningTeam+" wins.");
                    return true;
                } else {
                    System.out.println(myTeam+" team can block check.");
                }
            } else {
                System.out.println(myTeam+" king can move out of check.");
            }
        }
        return false;
    }

    public boolean isStalemate() {
        if (!kingInCheck(getKing(true))) { // King is currently in check
            if (!kingCanMoveOutOfCheck(getKing(true)) && !kingsTeamHasRemainingMoves(getKing(true))) {
                System.out.println("Stalemate.");
                return true;
            }
        }
        if (!kingInCheck(getKing(false))) { // King is currently in check
            if (!kingCanMoveOutOfCheck(getKing(false)) && !kingsTeamHasRemainingMoves(getKing(false))) {
                System.out.println("Stalemate.");
                return true;
            }
        }
        return false;
    }

    private boolean kingsTeamHasRemainingMoves(King king) {
        for (GenericGamePiece gamePiece : getPiecesInPlay()) {
            if (gamePiece.isWhite == king.isWhite && !gamePiece.toString().equals("King")) {
                for (Pair<Integer, Integer> teamMove : gamePiece.getValidMoveDestinations()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean kingInCheck(King king) {
        return locationPutsKingInCheck(king,king.getLocation());
    }

    private boolean kingCanMoveOutOfCheck(King king) {
        int validMoves = king.getValidMoveDestinations().size();
        for (Pair<Integer, Integer> move : king.getValidMoveDestinations()) {
            if (locationPutsKingInCheck(king, move)) {
                validMoves--;
            }
        }
        return validMoves > 0;
    }

    private boolean locationPutsKingInCheck(King king, Pair<Integer,Integer> location) {
        Pair<Integer,Integer> oldLocation = king.getLocation();
        if (locationInEnemyReach(location,king.isWhite)) {
            return true;
        }
        GenericGamePiece captureableEnemy = getPieceAtLocationIfExtant(location);
        if (captureableEnemy != null) {
            captureableEnemy.isInPlay = false;
        }
        king.moveTo(location);
        boolean isInCheck = true;
        if (locationInEnemyReach(king.getLocation(), king.isWhite)) {
            isInCheck = true;
        } else {
            isInCheck = false;
        }
        king.moveTo(oldLocation);
        if (captureableEnemy != null) {
            captureableEnemy.isInPlay = true;
        }
        return isInCheck;
    }

    private boolean locationInEnemyReach(Pair<Integer,Integer> location, boolean isWhite) {
        for (GenericGamePiece gamePiece : getPiecesInPlay()) {
            if (gamePiece.isWhite != isWhite) {
                for (Pair<Integer, Integer> enemyMove : gamePiece.getValidCaptureDestinations()) {
                    if (enemyMove.equals(location)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean kingsTeamCanBlockCheck(King king) {
        boolean canExitCheck = false;
        for (GenericGamePiece gamePiece : getPiecesInPlay()) {
            if (gamePiece.isWhite == king.isWhite) {
                for (Pair<Integer,Integer> move : gamePiece.getValidMoveDestinations()) {
                    Pair<Integer,Integer> oldLocation = gamePiece.getLocation();
                    GenericGamePiece destinationPiece = getPieceAtLocationIfExtant(move);
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

    public void makeMove(GenericGamePiece gamePiece, Pair<Integer,Integer> move) {
        boolean movingWhiteKingPutsWhiteKingIntoCheck = gamePiece.toString().equals("King") && isWhitesTurn() && locationPutsKingInCheck((King)gamePiece,move);
        boolean movingBlackKingPutsBlackKingIntoCheck = gamePiece.toString().equals("King") && !isWhitesTurn() && locationPutsKingInCheck((King)gamePiece,move);

        if (movingBlackKingPutsBlackKingIntoCheck || movingWhiteKingPutsWhiteKingIntoCheck) {
            System.out.println("Can't put king in check.");
            return;
        }

        Pair<Integer,Integer> oldLocation = gamePiece.getLocation();
        GenericGamePiece destinationPiece = getPieceAtLocationIfExtant(move);
        if (destinationPiece != null) {
            destinationPiece.isInPlay = false;
        }
        gamePiece.moveTo(move);

        boolean movingOtherPiecePutsWhiteKingIntoCheck = isWhitesTurn() && locationPutsKingInCheck(getKing(true),getKing(true).getLocation());
        boolean movingOtherPiecePutsBlackKingIntoCheck = !isWhitesTurn() && locationPutsKingInCheck(getKing(false),getKing(false).getLocation());


        if (movingOtherPiecePutsBlackKingIntoCheck || movingOtherPiecePutsWhiteKingIntoCheck) {
            if (destinationPiece != null) {
                destinationPiece.isInPlay = true;
            }
            gamePiece.moveTo(oldLocation);
            System.out.println("Can't put king in check.");
            return;
        }

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

    public ArrayList<GenericGamePiece> getPiecesInPlay() {
        ArrayList<GenericGamePiece> piecesInPlay = new ArrayList<GenericGamePiece>();
        for (GenericGamePiece gamePiece : gamePieces) {
            if (gamePiece.isInPlay) {
                piecesInPlay.add(gamePiece);
            }
        }
        return piecesInPlay;
    }

    public ArrayList<GenericGamePiece> getPiecesOutOfPlay() {
        ArrayList<GenericGamePiece> piecesOutOfPlay = new ArrayList<GenericGamePiece>();
        for (GenericGamePiece gamePiece : gamePieces) {
            if (!gamePiece.isInPlay) {
                piecesOutOfPlay.add(gamePiece);
            }
        }
        return piecesOutOfPlay;
    }

    public void addGamePiece(GenericGamePiece gamePiece) throws InvalidGamePieceLocationException {
        if (isLocationOnBoard(gamePiece.getLocation())) {
            gamePieces.add(gamePiece);
        } else {
            throw new InvalidGamePieceLocationException("Invalid placement of game piece.");
        }
    }

    public boolean isLocationOnBoard(Pair<Integer,Integer> location) {
        return location.getKey() >= 0 && location.getKey() < width && location.getValue() >= 0 && location.getValue() < height;
    }

    public GenericGamePiece getPieceAtLocationIfExtant(Pair<Integer,Integer> location) {
        if (!isLocationOnBoard(location)) {
            return null;
        }
        for (GenericGamePiece gamePiece : getPiecesInPlay()) {
            if (gamePiece.getX() == location.getKey() && gamePiece.getY() == location.getValue()) {
                return gamePiece;
            }
        }
        return null;
    }
}