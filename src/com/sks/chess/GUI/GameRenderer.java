package com.sks.chess.GUI;

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.GamePiece.GenericGamePiece;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameRenderer extends JPanel {
    private int mouseX, mouseY;
    private Board gameBoard;
    private HashMap<String,BufferedImage> whiteImageDictionary, blackImageDictionary;
    private GenericGamePiece selectedGamePiece;
    private Pair<Integer,Integer> selectedMove;

    private int spaceWidth, spaceHeight, gamePieceImageOffset;

    public GameRenderer() {
        this.gameBoard = null;
        whiteImageDictionary = new HashMap<String,BufferedImage>();
        blackImageDictionary = new HashMap<String,BufferedImage>();
        try {
            loadImagesFromDirectoryIntoDictionaries();
        } catch (IOException ioException) {
            System.err.println("Could not load images.");
        }
        gamePieceImageOffset = 10;
    }

    public void setBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void saveImageFromFileToDictionary(File imageFile, HashMap<String,BufferedImage> dictionary) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        String rawFilename = imageFile.getName();
        String representedPieceName = rawFilename.substring(0,rawFilename.lastIndexOf('.'));
        dictionary.put(representedPieceName,image);
    }

    public void loadImagesFromDirectoryIntoDictionaries() throws IOException {
        File whiteImagesDirectory = new File("images//white");
        if (whiteImagesDirectory.isDirectory()) {
            for (File imageFile : whiteImagesDirectory.listFiles()) {
                try {
                    saveImageFromFileToDictionary(imageFile,whiteImageDictionary);
                } catch (IOException ioException) {
                    throw ioException;
                }
            }
        }

        File blackImagesDirectory = new File("images//black");
        if (blackImagesDirectory.isDirectory()) {
            for (File imageFile : blackImagesDirectory.listFiles()) {
                try {
                    saveImageFromFileToDictionary(imageFile,blackImageDictionary);
                } catch (IOException ioException) {
                    throw ioException;
                }
            }
        }
    }

    public void renderBoard(Graphics g) {
        spaceWidth = getWidth()/(gameBoard.getWidth());
        spaceHeight = getHeight()/(gameBoard.getHeight());
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.GRAY);
        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {
                if (i % 2 + j % 2 == 1) {
                    g.fillRect(j * spaceWidth, i * spaceHeight, spaceWidth, spaceHeight);
                }
            }
        }
    }

    public void renderPiece(Graphics g, GenericGamePiece gamePiece) {
        int imageX = gamePiece.getX() * spaceWidth + gamePieceImageOffset;
        int imageY = gamePiece.getY() * spaceHeight + gamePieceImageOffset;
        int imageHeight =  spaceHeight - 2 * gamePieceImageOffset;
        int imageWidth = spaceWidth - 2 * gamePieceImageOffset;

        if (selectedGamePiece == gamePiece) {
            g.setColor(new Color(255,255,0,50));
            g.fillRect(gamePiece.getX() * spaceWidth, gamePiece.getY() * spaceHeight, spaceWidth, spaceHeight);
        }
        if (gamePiece.isWhite) {
            g.drawImage(whiteImageDictionary.get(gamePiece.toString()),imageX,imageY,imageWidth,imageHeight,null);
        } else {
            g.drawImage(blackImageDictionary.get(gamePiece.toString()), imageX, imageY, imageWidth, imageHeight, null);
        }
    }

    public void renderMove(Graphics g, Pair<Integer,Integer> move) {
        if (selectedMove != null && selectedMove.equals(move)) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.YELLOW);
        }
        int moveX = move.getKey() * spaceWidth;
        int moveY = move.getValue() * spaceHeight;
        g.fillRect(moveX, moveY, spaceWidth, spaceHeight);
    }

    public boolean mouseIsOverPiece(GenericGamePiece gamePiece) {
        int pieceX = gamePiece.getX() * spaceWidth;
        int pieceY = gamePiece.getY() * spaceHeight;

        boolean xInRange = mouseX > pieceX && mouseX < pieceX + spaceWidth;
        boolean yInRange = mouseY > pieceY && mouseY < pieceY + spaceHeight;
        return xInRange && yInRange;
    }

    public boolean mouseIsOverMove(Pair<Integer,Integer> move) {
        int moveX = move.getKey() * spaceWidth;
        int moveY = move.getValue() * spaceHeight;

        boolean xInRange = mouseX > moveX && mouseX < moveX + spaceWidth;
        boolean yInRange = mouseY > moveY && mouseY < moveY + spaceHeight;
        return xInRange && yInRange;
    }

    public void setMouseLocation(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY-25; // Weird required offset for menu bar up top
    }

    public void mouseClicked() {
        for (GenericGamePiece gamePiece : gameBoard.getPiecesInPlay()) {
            if (mouseIsOverPiece(gamePiece) && gamePiece.isWhite == gameBoard.isWhitesTurn()) {
                if (selectedGamePiece == gamePiece) {
                    selectedGamePiece = null;
                    selectedMove = null;
                } else {
                    selectedGamePiece = gamePiece;
                    selectedMove = null;
                }
            }
        }
        if (selectedGamePiece != null) {
            for (Pair<Integer, Integer> move : selectedGamePiece.getValidMoveDestinations()) {
                if (mouseIsOverMove(move)) {
                    gameBoard.makeMove(selectedGamePiece,move);
                    selectedGamePiece = null;
                    selectedMove = null;
                }
            }
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameBoard == null) {
            return;
        }
        renderBoard(g);
        for (GenericGamePiece gamePiece : gameBoard.getPiecesInPlay()) {
            if (selectedGamePiece == gamePiece) {
                ArrayList<Pair<Integer, Integer>> listOfMoves = gamePiece.getValidMoveDestinations();
                for (Pair<Integer, Integer> move : listOfMoves) {
                    renderMove(g, move);
                }
            }
        }
        for (GenericGamePiece gamePiece : gameBoard.getPiecesInPlay()) {
            renderPiece(g, gamePiece);
        }
    }
}
