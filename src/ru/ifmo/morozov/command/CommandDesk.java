package ru.ifmo.morozov.command;

import ru.ifmo.morozov.classes.model.Pointer;
import ru.ifmo.morozov.interfaces.Command;

import com.jogamp.opengl.awt.GLCanvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vks on 3/24/15.
 */
public class CommandDesk {
    private Pointer pointer;
    private GLCanvas canvas;
    private List<Command> commands;
    private List<Command> queue;
    private final int queueLength = 19;
    private int currentLength = 0;


    public CommandDesk(Pointer pointer, GLCanvas canvas, List<Command> commands) {
        this.commands = commands;
        this.canvas = canvas;
        this.pointer = pointer;
        queue = new ArrayList<>(queueLength + 1);
    }

    public void moveLeft() {
        if (commands.get(0).execute(pointer, canvas)) {
            currentLength++;
            if (currentLength > queueLength) {
                currentLength--;
                queue.remove(0);
            }
            queue.add(commands.get(0));
        }
    }

    public void moveRight() {
        if (commands.get(1).execute(pointer, canvas)) {
            currentLength++;
            if (currentLength > queueLength) {
                currentLength--;
                queue.remove(0);
            }
            queue.add(commands.get(1));
        }
    }

    public void moveUp() {
        if (commands.get(2).execute(pointer, canvas)) {
            currentLength++;
            if (currentLength > queueLength) {
                currentLength--;
                queue.remove(0);
            }
            queue.add(commands.get(2));
        }
    }

    public void moveDown() {
        if (commands.get(3).execute(pointer, canvas)) {
            currentLength++;
            if (currentLength > queueLength) {
                currentLength--;
                queue.remove(0);
            }
            queue.add(commands.get(3));
        }
    }

    public void pressEnter() {
        if (commands.get(4).execute(pointer, canvas)) {
            currentLength++;
            if (currentLength > queueLength) {
                currentLength--;
                queue.remove(0);
            }
            queue.add(commands.get(4));
        }
    }

    public void undo() {
        if (currentLength > 0) {
            currentLength--;
            queue.get(currentLength).undo(pointer, canvas);
            queue.remove(currentLength);
        }
    }

}
