package ru.ifmo.morozov.classes.view;

import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by vks on 13/05/15.
 */
public class GUI {
    private GLCanvas canvas;

    public GUI() {
        JFrame frame = new JFrame("");
        canvas = new GLCanvas();
        frame.add(canvas);
        frame.setUndecorated(true);
        int size = frame.getExtendedState();
        size |= Frame.MAXIMIZED_BOTH;
        frame.setExtendedState(size);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                canvas.destroy();
                System.exit(0);
            }
        });
    }

    public void display() {
        canvas.requestFocus();
        canvas.display();
    }

    public GLCanvas getCanvas() {
        return canvas;
    }
}
