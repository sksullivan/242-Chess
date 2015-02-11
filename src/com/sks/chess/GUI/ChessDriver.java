package com.sks.chess.GUI;

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.ChessException.InvalidGamePieceLocationException;
import com.sks.chess.GameLogic.GamePiece.*;

import javax.swing.*;

public class ChessDriver {
    public static void main(String[] args) {
        System.out.println("Welcome to Chess 0.3");
        JFrame gameFrame = new JFrame();
        gameFrame.setSize(400,400);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GameRenderer gameRenderer = new GameRenderer();
        gameFrame.getContentPane().add(gameRenderer);
        EventHandler eventHandler = new EventHandler(gameRenderer);
        gameFrame.addMouseMotionListener(eventHandler);
        gameFrame.addMouseListener(eventHandler);
        eventHandler.newGame();

        gameFrame.setVisible(true);
    }
}