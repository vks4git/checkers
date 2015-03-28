package ru.ifmo.morozov.interfaces;

import ru.ifmo.morozov.classes.Field;

import com.jogamp.opengl.awt.GLCanvas;

/**
 * Created by vks on 3/24/15.
 */
public interface Command {
    void execute(Field field, GLCanvas canvas);
}
