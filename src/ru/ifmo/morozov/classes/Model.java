package ru.ifmo.morozov.classes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by vks on 3/23/15.
 */
public class Model {

    private float vertexArray[];
    private float normalArray[];
    private float textureCoordArray[];
    private short indexArray[];

    private int vertices;
    private int indices;

    public Model(String path) {
        Path file = Paths.get(path);
        try {
            InputStream stream = Files.newInputStream(file);
            vertices = stream.read();
            vertices += stream.read() << 8;

            indices = stream.read();
            indices += stream.read() << 8;

            vertexArray = new float[vertices*3];
            normalArray = new float[vertices*3];
            textureCoordArray = new float[vertices*2];
            indexArray = new short[indices*3];



            for (int i = 0; i < vertices*3; i++) {
                    vertexArray[i] = Float.intBitsToFloat(readIntBits(stream));
            }

            for (int i = 0; i < vertices*3; i++) {
                    normalArray[i] = Float.intBitsToFloat(readIntBits(stream));
            }

            for (int i = 0; i < vertices*2; i++) {
                    textureCoordArray[i] = Float.intBitsToFloat(readIntBits(stream));
            }

            for (int i = 0; i < indices*3; i++) {
                    indexArray[i] = (short) stream.read();
                    indexArray[i] += (short) stream.read() << 8;
            }

            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int readIntBits(InputStream stream) throws IOException {
        int bits= stream.read();
        bits += stream.read() << 8;
        bits += stream.read() << 16;
        bits += stream.read() << 24;
        return bits;
    }

    public int getVertices() {
        return vertices;
    }

    public int getIndices() {
        return indices;
    }

    final public Buffer getVertexArray() {
        return FloatBuffer.wrap(vertexArray);
    }

    final public Buffer getNormalArray() {
        return FloatBuffer.wrap(normalArray);
    }

    final public Buffer getTextureCoordArray() {
        return FloatBuffer.wrap(textureCoordArray);
    }

    final public Buffer getIndexArray() {
        return ShortBuffer.wrap(indexArray);
    }
}
