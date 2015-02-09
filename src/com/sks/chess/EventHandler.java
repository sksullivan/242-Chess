package com.sks.chess;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by them on 2/7/2015.
 */
public class EventHandler implements MouseMotionListener {
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
}
