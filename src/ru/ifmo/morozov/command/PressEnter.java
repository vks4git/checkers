package ru.ifmo.morozov.command;

import ru.ifmo.morozov.classes.model.Pointer;
import ru.ifmo.morozov.interfaces.Command;

import com.jogamp.opengl.awt.GLCanvas;

/**
 * Created by vks on 3/24/15.
 */
public class PressEnter implements Command {

    public boolean execute(Pointer pointer, GLCanvas canvas) {
        if (pointer.isUsed()) {
            if (!pointer.isChecked()) {
                pointer.get();
            } else {
                pointer.release();
            }
            canvas.display();
        }
        return true;
    }

    public void undo(Pointer pointer, GLCanvas canvas) {
        execute(pointer, canvas);
    }
}
