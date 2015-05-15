package ru.ifmo.morozov.classes.controller;

import com.jogamp.opengl.awt.GLCanvas;
import ru.ifmo.morozov.classes.view.GUI;
import ru.ifmo.morozov.classes.model.Game;
import ru.ifmo.morozov.classes.model.Rules;
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

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vks on 2/26/15.
 */
public class Controller implements KeyListener, MouseListener {

    private CommandDesk desk;
    private Thread thread;

    public Controller(Thread thread) {
        this.thread = thread;
    }

    public void start() {
        Validator rules = new Rules();
        Player player1 = new HumanPlayer(Colour.White, "Дарт Херохито");
        //Player player1 = new AIPlayer(Colour.White, "The Chosen One", rules);
        Player player2 = new AIPlayer(Colour.Black, "Злобный компьютерный разум", rules);
        Game game = new Game(player1, player2);
        Field field = game.getField();
        Pointer pointer = game.getPointer();
        GUI gui = new GUI();
        GLCanvas canvas = gui.getCanvas();

        String root = System.getProperty("user.dir") + "/src/";
        final OpenGLRenderer renderer = new OpenGLRenderer(field, pointer, canvas, root);
        game.addListener(renderer);

        canvas.addGLEventListener(renderer);
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);

        List<Command> commands = new ArrayList<>();

        commands.add(new PressLeftArrow());
        commands.add(new PressRightArrow());
        commands.add(new PressUpArrow());
        commands.add(new PressDownArrow());
        commands.add(new PressEnter());
        desk = new CommandDesk(pointer, canvas, commands);

        gui.display();

        System.out.println("The winner is " + game.start().getName());
        System.exit(0);

    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE: {
                thread = null;
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
        int x = e.getX();
        int y = e.getY();

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
