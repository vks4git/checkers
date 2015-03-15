package ru.ifmo.morozov;

import ru.ifmo.morozov.classes.*;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.interfaces.Player;

import javax.media.opengl.awt.GLCanvas;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by vks on 2/26/15.
 */
public class Main implements Runnable, KeyListener {

    private static Thread displayT = new Thread(new Main());
    private static boolean bQuit = false;

    private Field field;
    private Player player1;
    private Player player2;
    private Game game;
    private GLCanvas canvas;

    public static void main(String[] args) {
        displayT.start();
    }

    public void run() {
        Frame frame = new Frame("Checkers");
        canvas = new GLCanvas();
        player1 = new HumanPlayer(Colour.White, "Дарт Херохито");
        player2 = new AIPlayer(Colour.Black, "Злобный компьютерный разум");
        game = new Game(player1, player2);
        field = game.getField();
        final OpenGLRenderer renderer = new OpenGLRenderer(game.getField());
        int size = frame.getExtendedState();

        canvas.addGLEventListener(renderer);
        frame.add(canvas);
        frame.setUndecorated(true);
        size |= Frame.MAXIMIZED_BOTH;
        frame.setExtendedState(size);
        canvas.addKeyListener(this);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                bQuit = true;
                canvas.removeGLEventListener(renderer);
                canvas.destroy();
                System.exit(0);
            }
        });

        frame.setVisible(true);
        canvas.requestFocus();
        canvas.display();

        game.start();

    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE: {
                displayT = null;
                bQuit = true;
                System.exit(0);
                break;
            }
            case KeyEvent.VK_LEFT: {
                if (field.getKeybdEntry()) {
                    field.resetPointer(-1, 0);
                    canvas.display();
                }
                break;
            }
            case KeyEvent.VK_RIGHT: {
                if (field.getKeybdEntry()) {
                    field.resetPointer(1, 0);
                    canvas.display();
                }
                break;
            }
            case KeyEvent.VK_UP: {
                if (field.getKeybdEntry()) {
                    field.resetPointer(0, -1);
                    canvas.display();

                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if (field.getKeybdEntry()) {
                    field.resetPointer(0, 1);
                    canvas.display();
                }
                break;
            }
            case KeyEvent.VK_ENTER: {
                if (field.getKeybdEntry()) {
                    if (!field.isChecked()) {
                        field.check();
                    } else {
                        field.uncheck();
                    }
                    canvas.display();
                }
                break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

}
