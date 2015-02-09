package com.sks.chess;

import javax.swing.*;

public class ChessDriver {
    public static void main(String[] args) {
        Board gameBoard = new Board(8,8);
        placeInitialGamePiecesOnBoard(gameBoard);
        System.out.println("Welcome to Chess 0.1");
        JFrame gameFrame = new JFrame();
        gameFrame.setSize(400,400);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GameRenderer gameRenderer = new GameRenderer(gameBoard);
        gameFrame.getContentPane().add(gameRenderer);
        gameFrame.addMouseMotionListener(new EventHandler(gameRenderer));

        gameFrame.setVisible(true);
    }

    public static void placeInitialGamePiecesOnBoard(Board gameBoard) {
        gameBoard.addGamePiece(new Pawn(1,5,false,gameBoard));
        gameBoard.addGamePiece(new Pawn(1,6,true,gameBoard));
        gameBoard.addGamePiece(new Pawn(1,0,false,gameBoard));
        gameBoard.addGamePiece(new Pawn(1,1,true,gameBoard));
        gameBoard.addGamePiece(new Pawn(1,3,false,gameBoard));
        gameBoard.addGamePiece(new Pawn(1,3,false,gameBoard));
        gameBoard.addGamePiece(new Pawn(1,3,false,gameBoard));

        gameBoard.addGamePiece(new Pawn(2,5,true,gameBoard));
        gameBoard.addGamePiece(new Pawn(2,4,true,gameBoard));
        gameBoard.addGamePiece(new Pawn(2,3,true,gameBoard));
        gameBoard.addGamePiece(new Pawn(2,2,true,gameBoard));
        gameBoard.addGamePiece(new Pawn(2,1,true,gameBoard));
    }
}