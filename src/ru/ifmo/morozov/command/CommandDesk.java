package ru.ifmo.morozov.command;

import ru.ifmo.morozov.classes.Field;
import ru.ifmo.morozov.interfaces.Command;

import javax.media.opengl.awt.GLCanvas;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vks on 3/24/15.
 */
public class CommandDesk {
    private List<Command> commands;
    private Field field;
    private GLCanvas canvas;


    public CommandDesk(Field field, GLCanvas canvas) {
        commands = new ArrayList<Command>();
        this.field = field;
        this.canvas = canvas;
    }

    public void add(Command command) {
        commands.add(command);
    }

    public void invoke(int index) {
        if (index < commands.size()) {
            commands.get(index).execute(field, canvas);
        }
    }
}
