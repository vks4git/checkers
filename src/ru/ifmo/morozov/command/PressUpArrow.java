package ru.ifmo.morozov.command;

import ru.ifmo.morozov.classes.Field;
import ru.ifmo.morozov.interfaces.Command;

import com.jogamp.opengl.awt.GLCanvas;

/**
 * Created by vks on 3/24/15.
 */
public class PressUpArrow implements Command {

    public boolean execute(Field field, GLCanvas canvas) {
        boolean result = false;
        if (field.getKeybdEntry()) {
            result = field.resetPointer(0, -1);
            canvas.display();
        }
        return result;
    }

    public void undo(Field field, GLCanvas canvas) {
        if (field.getKeybdEntry()) {
            field.resetPointer(0, 1);
            canvas.display();

        }
    }
}
