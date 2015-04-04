package ru.ifmo.morozov.command;

import ru.ifmo.morozov.classes.Field;
import ru.ifmo.morozov.interfaces.Command;

import com.jogamp.opengl.awt.GLCanvas;
import java.util.List;

/**
 * Created by vks on 3/24/15.
 */
public class CommandDesk {
    private Field field;
    private GLCanvas canvas;
    private List<Command> commands;


    public CommandDesk(Field field, GLCanvas canvas, List<Command> commands) {
        this.commands = commands;
        this.field = field;
        this.canvas = canvas;
    }

    public void moveLeft() {
        commands.get(0).execute(field, canvas);
    }

    public void moveRight() {
        commands.get(1).execute(field, canvas);
    }

    public void moveUp() {
        commands.get(2).execute(field, canvas);
    }

    public void moveDown() {
        commands.get(3).execute(field, canvas);
    }

    public void pressEnter() {
        commands.get(4).execute(field, canvas);
    }

}
