package ru.ifmo.morozov;

import ru.ifmo.morozov.classes.*;
import ru.ifmo.morozov.command.*;
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
    private GLCanvas canvas;
    private CommandDesk desk;

    public static void main(String[] args) {
        displayT.start();
    }

    public void run() {
        Frame frame = new Frame("Checkers");
        canvas = new GLCanvas();
        Player player1 = new HumanPlayer(Colour.White, "Дарт Херохито", 1);
        Player player2 = new AIPlayer(Colour.Black, "Злобный компьютерный разум", -1);
        Game game = new Game(player1, player2);
        field = game.getField();

        String root = new Main().getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "../../../" + "src/";
        final OpenGLRenderer renderer = new OpenGLRenderer(game.getField(), root);
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

        desk = new CommandDesk(field, canvas);
        desk.add(new PressLeftArrow());
        desk.add(new PressRightArrow());
        desk.add(new PressUpArrow());
        desk.add(new PressDownArrow());
        desk.add(new PressEnter());

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
                desk.invoke(0);
                break;
            }
            case KeyEvent.VK_RIGHT: {
                desk.invoke(1);
                break;
            }
            case KeyEvent.VK_UP: {
                desk.invoke(2);
                break;
            }
            case KeyEvent.VK_DOWN: {
                desk.invoke(3);
                break;
            }
            case KeyEvent.VK_ENTER: {
                desk.invoke(4);
                break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

}
