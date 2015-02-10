package com.sks.chess.GUI;

import com.sks.chess.GUI.GameRenderer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by them on 2/7/2015.
 */
public class EventHandler implements MouseMotionListener, MouseListener {
    GameRenderer renderer;

    public EventHandler(GameRenderer renderer) {
        this.renderer = renderer;
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
        renderer.mouseClicked();
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
