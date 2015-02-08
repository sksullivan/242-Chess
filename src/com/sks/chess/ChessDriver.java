package com.sks.chess;

import javax.swing.*;

public class ChessDriver {
    public static void main(String[] args) {
        Board gameBoard = new Board(8,8);
        placeInitialGamePiecesOnBoard(gameBoard);
        System.out.println("Welcome to Chess 0.1");
        JFrame gameFrame = new JFrame();
        gameFrame.setSize(400,400);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GameRenderer gameRenderer = new GameRenderer(gameBoard,gameFrame);
        gameFrame.getContentPane().add(gameRenderer);
        gameFrame.addMouseMotionListener(new EventHandler(gameRenderer));
    }

    public static void placeInitialGamePiecesOnBoard(Board gameBoard) {
        gameBoard.addGamePiece(new Rook(0,5,false,gameBoard));
        gameBoard.addGamePiece(new Rook(7,5,false,gameBoard));
        gameBoard.addGamePiece(new Knight(7,0,false,gameBoard));
        gameBoard.addGamePiece(new Bishop(1,1,false,gameBoard));
        gameBoard.addGamePiece(new Queen(6,0,false,gameBoard));

        gameBoard.addGamePiece(new Rook(1,7,true,gameBoard));
        gameBoard.addGamePiece(new Rook(4,7,true,gameBoard));
        gameBoard.addGamePiece(new Knight(1,4,true,gameBoard));
        gameBoard.addGamePiece(new Knight(6,7,true,gameBoard));
        gameBoard.addGamePiece(new Queen(3,7,true,gameBoard));
    }
}