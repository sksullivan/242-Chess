package com.sks.chess;

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
    Board gameBoard;
    JFrame drawingContainer;
    HashMap<String,BufferedImage> whiteImageDictionary;
    HashMap<String,BufferedImage> blackImageDictionary;

    int spaceWidth, spaceHeight, gamePieceImageOffset;

    public GameRenderer(Board gameBoard, JFrame drawingContainer) {
        this.gameBoard = gameBoard;
        this.drawingContainer = drawingContainer;
        whiteImageDictionary = new HashMap<String,BufferedImage>();
        blackImageDictionary = new HashMap<String,BufferedImage>();
        try {
            loadImagesFromDirectoryIntoDictionaries();
        } catch (IOException ioException) {
            System.err.println("Could not load images.");
        }
        gamePieceImageOffset = 10;
    }

    public void saveImageFromFileToDictionary(File imageFile, HashMap<String,BufferedImage> dictionary) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        String rawFilename = imageFile.getName();
        String representedPieceName = rawFilename.substring(0,rawFilename.lastIndexOf('.'));
        dictionary.put(representedPieceName,image);
    }

    public void loadImagesFromDirectoryIntoDictionaries() throws IOException {
        File whiteImagesDirectory = new File("images\\white");
        if (whiteImagesDirectory.isDirectory()) {
            for (File imageFile : whiteImagesDirectory.listFiles()) {
                try {
                    saveImageFromFileToDictionary(imageFile,whiteImageDictionary);
                } catch (IOException ioException) {
                    throw ioException;
                }
            }
        }

        File blackImagesDirectory = new File("images\\black");
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
        spaceWidth = getWidth()/(gameBoard.width);
        spaceHeight = getHeight()/(gameBoard.height);

        g.setColor(Color.BLACK);
        for (int i = 0; i < gameBoard.height; i++) {
            for (int j = 0; j < gameBoard.width; j++) {
                if (i % 2 + j % 2 == 1) {
                    g.fillRect(j * spaceWidth, i * spaceHeight, spaceWidth, spaceHeight);
                }
            }
        }
    }

    public void renderPiece(Graphics g, GamePiece gamePiece) {
        int imageX = gamePiece.x * spaceWidth + gamePieceImageOffset;
        int imageY = gamePiece.y * spaceHeight + gamePieceImageOffset;
        int imageHeight =  spaceHeight - 2 * gamePieceImageOffset;
        int imageWidth = spaceWidth - 2 * gamePieceImageOffset;

        if (gamePiece.isWhite) {
            g.drawImage(whiteImageDictionary.get(gamePiece.toString()),imageX,imageY,imageWidth,imageHeight,null);
        } else {
            g.drawImage(blackImageDictionary.get(gamePiece.toString()), imageX, imageY, imageWidth, imageHeight, null);
        }
    }

    public void renderMove(Graphics g, Pair<Integer,Integer> move) {
        g.setColor(Color.YELLOW);
        int moveX = move.getKey() * spaceWidth;
        int moveY = move.getValue() * spaceHeight;
        g.fillRect(moveX, moveY, spaceWidth, spaceHeight);
    }

    public boolean mouseIsOverPiece(GamePiece gamePiece) {
        int pieceX = gamePiece.x * spaceWidth;
        int pieceY = gamePiece.y * spaceHeight;
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;
        System.out.println(mouseX+","+mouseY);
        boolean xInRange = mouseX > pieceX && mouseX < pieceX + spaceWidth;
        boolean yInRange = mouseY > pieceY && mouseY < pieceY + spaceHeight;
        return xInRange && yInRange;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderBoard(g);
        for (GamePiece gamePiece : gameBoard.piecesInPlay) {
            if (mouseIsOverPiece(gamePiece)) {
                ArrayList<Pair<Integer, Integer>> listOfMoves = gamePiece.getValidMoveDestinations();
                for (Pair<Integer, Integer> move : listOfMoves) {
                    renderMove(g, move);
                }
            }
        }
        for (GamePiece gamePiece : gameBoard.piecesInPlay) {
            renderPiece(g, gamePiece);
        }
    }
}
