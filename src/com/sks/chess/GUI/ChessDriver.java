package com.sks.chess.GUI;

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.ChessException.InvalidGamePieceLocationException;
import com.sks.chess.GameLogic.GamePiece.*;
import test.com.sks.chess.GamePieceTest;

import javax.swing.*;

public class ChessDriver {
    public static void main(String[] args) {
        Board gameBoard = new Board(8,8);
        try {
            placeInitialGamePiecesOnBoard(gameBoard);
        } catch (InvalidGamePieceLocationException locationException) {
            System.err.println("Please check game piece placement code, invalid locations found.");
        }
        System.out.println("Welcome to Chess 0.1");
        JFrame gameFrame = new JFrame();
        gameFrame.setSize(400,400);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GameRenderer gameRenderer = new GameRenderer(gameBoard);
        gameFrame.getContentPane().add(gameRenderer);
        EventHandler eventHandler = new EventHandler(gameRenderer);
        gameFrame.addMouseMotionListener(eventHandler);
        gameFrame.addMouseListener(eventHandler);

        gameFrame.setVisible(true);
    }

    public static void placeInitialGamePiecesOnBoard(Board gameBoard) throws InvalidGamePieceLocationException{
        for (int i = 0; i < 8; i++) {
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
        gameBoard.addGamePiece(new Rook(7,7,false,gameBoard));
    }
}