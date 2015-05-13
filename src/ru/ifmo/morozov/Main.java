package ru.ifmo.morozov;

import com.jogamp.opengl.awt.GLCanvas;
import ru.ifmo.morozov.classes.controller.Game;
import ru.ifmo.morozov.classes.controller.Rules;
import ru.ifmo.morozov.classes.model.AIPlayer;
import ru.ifmo.morozov.classes.model.Field;
import ru.ifmo.morozov.classes.model.HumanPlayer;
import ru.ifmo.morozov.classes.model.Pointer;
import ru.ifmo.morozov.classes.view.OpenGLRenderer;
import ru.ifmo.morozov.command.*;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.interfaces.Command;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vks on 2/26/15.
 */
public class Main implements Runnable, KeyListener, MouseListener {

    private static Thread displayT = new Thread(new Main());
    private static boolean bQuit = false;

    private Field field;
    private Pointer pointer;
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
        pointer = game.getPointer();


        String root = System.getProperty("user.dir") + "/src/";
        final OpenGLRenderer renderer = new OpenGLRenderer(field, pointer, canvas, root);
        game.addListener(renderer);
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
        desk = new CommandDesk(pointer, canvas, commands);

        Player winner = game.start();
        System.out.println("The winner is " + winner.getName());

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
            case KeyEvent.VK_SPACE: {
                desk.undo();
                break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

        /* Mouse control */

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

}
