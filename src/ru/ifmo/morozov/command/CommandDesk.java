package ru.ifmo.morozov.command;

import ru.ifmo.morozov.classes.Field;
import ru.ifmo.morozov.interfaces.Command;

import com.jogamp.opengl.awt.GLCanvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vks on 3/24/15.
 */
public class CommandDesk {
    private Field field;
    private GLCanvas canvas;
    private List<Command> commands;
    private List<Command> queue;
    private final int queueLength = 19;
    private int currentLength = 0;


    public CommandDesk(Field field, GLCanvas canvas, List<Command> commands) {
        this.commands = commands;
        this.field = field;
        this.canvas = canvas;
        queue = new ArrayList<>(queueLength + 1);
    }

    public void moveLeft() {
        commands.get(0).execute(field, canvas);
        currentLength++;
        if (currentLength > queueLength) {
            currentLength--;
            queue.remove(0);
        }
        queue.add(commands.get(0));
    }

    public void moveRight() {
        commands.get(1).execute(field, canvas);
        currentLength++;
        if (currentLength > queueLength) {
            currentLength--;
            queue.remove(0);
        }
        queue.add(commands.get(1));
    }

    public void moveUp() {
        commands.get(2).execute(field, canvas);
        currentLength++;
        if (currentLength > queueLength) {
            currentLength--;
            queue.remove(0);
        }
        queue.add(commands.get(2));
    }

    public void moveDown() {
        commands.get(3).execute(field, canvas);
        currentLength++;
        if (currentLength > queueLength) {
            currentLength--;
            queue.remove(0);
        }
        queue.add(commands.get(3));
    }

    public void pressEnter() {
        commands.get(4).execute(field, canvas);
        currentLength++;
        if (currentLength > queueLength) {
            currentLength--;
            queue.remove(0);
        }
        queue.add(commands.get(4));
    }

    public void undo() {
        if (currentLength > 0) {
            currentLength--;
            queue.get(currentLength).undo(field, canvas);
            queue.remove(currentLength);
        }
    }

}
