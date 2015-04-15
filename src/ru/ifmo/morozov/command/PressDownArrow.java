package ru.ifmo.morozov.command;

import ru.ifmo.morozov.classes.model.Pointer;
import ru.ifmo.morozov.interfaces.Command;

import com.jogamp.opengl.awt.GLCanvas;

/**
 * Created by vks on 3/24/15.
 */
public class PressDownArrow implements Command {

    public boolean execute(Pointer pointer, GLCanvas canvas) {
        boolean result = false;
        if (pointer.isUsed()) {
            result = pointer.move(0, -1);
            canvas.display();
        }
        return result;
    }

    public void undo(Pointer pointer, GLCanvas canvas) {
        if (pointer.isUsed()) {
            pointer.move(0, 1);
            canvas.display();
        }
    }
}
