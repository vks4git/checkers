package ru.ifmo.morozov.command;

import ru.ifmo.morozov.classes.Field;
import ru.ifmo.morozov.interfaces.Command;

import com.jogamp.opengl.awt.GLCanvas;

/**
 * Created by vks on 3/24/15.
 */
public class PressEnter implements Command {

    public void execute(Field field, GLCanvas canvas) {
        if (field.getKeybdEntry()) {
            if (!field.isChecked()) {
                field.check();
            } else {
                field.uncheck();
            }
            canvas.display();
        }
    }

    public void undo(Field field, GLCanvas canvas) {
        execute(field, canvas);
    }
}
