package ru.ifmo.morozov.interfaces;

import ru.ifmo.morozov.classes.model.Pointer;

import com.jogamp.opengl.awt.GLCanvas;

/**
 * Created by vks on 3/24/15.
 */
public interface Command {
    boolean execute(Pointer pointer, GLCanvas canvas);
    void undo(Pointer pointer, GLCanvas canvas);
}
