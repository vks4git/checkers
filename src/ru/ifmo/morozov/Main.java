package ru.ifmo.morozov;

import ru.ifmo.morozov.classes.*;
import ru.ifmo.morozov.command.*;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.interfaces.Command;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

import com.jogamp.opengl.awt.GLCanvas;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

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
        Validator rules = new Rules();
        Player player1 = new HumanPlayer(Colour.White, "Дарт Херохито", 1);
        Player player2 = new AIPlayer(Colour.Black, "Злобный компьютерный разум", -1, rules);
        Game game = new Game(player1, player2);
        field = game.getField();

        String root = System.getProperty("user.dir") + "/src/";
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

        List<Command> commands = new ArrayList<>();

        commands.add(new PressLeftArrow());
        commands.add(new PressRightArrow());
        commands.add(new PressUpArrow());
        commands.add(new PressDownArrow());
        commands.add(new PressEnter());
        desk = new CommandDesk(field, canvas, commands);

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
                desk.moveLeft();
                break;
            }
            case KeyEvent.VK_RIGHT: {
                desk.moveRight();
                break;
            }
            case KeyEvent.VK_UP: {
                desk.moveUp();
                break;
            }
            case KeyEvent.VK_DOWN: {
                desk.moveDown();
                break;
            }
            case KeyEvent.VK_ENTER: {
                desk.pressEnter();
                break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

}
