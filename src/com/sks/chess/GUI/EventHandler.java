package com.sks.chess.GUI;

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.ChessException.InvalidGamePieceLocationException;
import com.sks.chess.GameLogic.GamePiece.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class EventHandler implements MouseMotionListener, MouseListener {
    private GameRenderer renderer;
    private boolean shouldResetGame;
    private Board gameBoard;

    public EventHandler(GameRenderer renderer) {
        this.renderer = renderer;
        shouldResetGame = false;
    }

    public void newGame() {
        gameBoard = new Board(8,8,this);
        try {
            placeInitialGamePiecesOnBoard(gameBoard);
        } catch (InvalidGamePieceLocationException locationException) {
            System.err.println("Please check game piece placement code, invalid locations found.");
        }
        renderer.setBoard(gameBoard);
        shouldResetGame = false;
    }

    public void endGame() {
        System.out.println("Game over. Click to reset.");
        shouldResetGame = true;
    }

    public void placeInitialGamePiecesOnBoard(Board gameBoard) throws InvalidGamePieceLocationException {
        /*for (int i = 0; i < 8; i++) {
            gameBoard.addGamePiece(new Pawn(i,1,true,gameBoard));
            gameBoard.addGamePiece(new Pawn(i,6,false,gameBoard));
        }

        gameBoard.addGamePiece(new Rook(0,0,true,gameBoard));
        gameBoard.addGamePiece(new Knight(1,0,true,gameBoard));
        gameBoard.addGamePiece(new Bishop(2,0,true,gameBoard));
        gameBoard.addGamePiece(new Queen(3,0,true,gameBoard));
        gameBoard.addGamePiece(new King(4,0,true,gameBoard));
        gameBoard.addGamePiece(new Bishop(5,0,true,gameBoard));
        gameBoard.addGamePiece(new Knight(6,0,true,gameBoard));
        gameBoard.addGamePiece(new Rook(7,0,true,gameBoard));

        gameBoard.addGamePiece(new Rook(0,7,false,gameBoard));
        gameBoard.addGamePiece(new Knight(1,7,false,gameBoard));
        gameBoard.addGamePiece(new Bishop(2,7,false,gameBoard));
        gameBoard.addGamePiece(new Queen(3,7,false,gameBoard));
        gameBoard.addGamePiece(new King(4,7,false,gameBoard));
        gameBoard.addGamePiece(new Bishop(5,7,false,gameBoard));
        gameBoard.addGamePiece(new Knight(6,7,false,gameBoard));
        gameBoard.addGamePiece(new Rook(7,7,false,gameBoard));*/
        King whiteKing = new King(0, 0, true, gameBoard);
        Rook blackRook = new Rook(7, 5, false, gameBoard);
        gameBoard.addGamePiece(new King(7, 7, false, gameBoard));
        gameBoard.addGamePiece(blackRook);
        gameBoard.addGamePiece(new Rook(1, 7, false, gameBoard));

        gameBoard.addGamePiece(whiteKing);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        renderer.setMouseLocation(e.getX(),e.getY());
        renderer.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (shouldResetGame) {
            newGame();
            renderer.repaint();
        } else {
            renderer.mouseClicked();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
