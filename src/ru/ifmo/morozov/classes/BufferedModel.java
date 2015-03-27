package ru.ifmo.morozov.classes;

/**
 * Created by vks on 3/23/15.
 */
public class BufferedModel {
    public int vertexBuffer;
    public int normalBuffer;
    public int textureCoordBuffer;
    public int indexBuffer;
    public int texture;

    public BufferedModel() {
        vertexBuffer = 0;
        normalBuffer = 0;
        textureCoordBuffer = 0;
        indexBuffer = 0;
        texture = 0;
    }
}
