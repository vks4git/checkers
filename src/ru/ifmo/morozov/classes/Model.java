package ru.ifmo.morozov.classes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
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

            vertexArray = new float[vertices * 3];
            normalArray = new float[vertices * 3];
            textureCoordArray = new float[vertices * 2];
            indexArray = new short[indices * 3];

            byte[] buffer = new byte[vertices * 12];

            stream.read(buffer, 0, vertices * 12);
            for (int i = 0; i < vertices * 3; i++) {
                vertexArray[i] = Float.intBitsToFloat(getIntBits(buffer, i));

            }

            stream.read(buffer, 0, vertices * 12);
            for (int i = 0; i < vertices * 3; i++) {
                normalArray[i] = Float.intBitsToFloat(getIntBits(buffer, i));
                if (i % 3 != 0) {
                    vertexArray[i] = -vertexArray[i];
                }
            }

            buffer = new byte[vertices * 8];
            stream.read(buffer, 0, vertices * 8);

            for (int i = 0; i < vertices * 2; i++) {
                textureCoordArray[i] = Float.intBitsToFloat(getIntBits(buffer, i));
            }

            buffer = new byte[indices * 6];
            stream.read(buffer, 0, indices * 6);

            for (int i = 0; i < indices * 3; i++) {
                indexArray[i] = 0;
                short tmp = buffer[2 * i];
                if (tmp < 0) {
                    tmp += 256;
                }
                indexArray[i] += tmp;

                tmp = buffer[2 * i + 1];
                if (tmp < 0) {
                    tmp += 256;
                }
                indexArray[i] += tmp << 8;
            }

            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getIntBits(byte[] buffer, int index) {
        int bits = 0;
        int tmp = buffer[4 * index];
        if (tmp < 0) {
            tmp += 256;
        }
        bits += tmp;

        tmp = buffer[4 * index + 1];
        if (tmp < 0) {
            tmp += 256;
        }
        bits += tmp << 8;

        tmp = buffer[4 * index + 2];
        if (tmp < 0) {
            tmp += 256;
        }
        bits += tmp << 16;

        tmp = buffer[4 * index + 3];
        if (tmp < 0) {
            tmp += 256;
        }
        bits += tmp << 24;
        return bits;
    }

    public int getVertices() {
        return vertices;
    }

    public int getIndices() {
        return indices;
    }

    final public Buffer getVertexArray() {
        return FloatBuffer.wrap(vertexArray).rewind();
    }

    final public Buffer getNormalArray() {
        return FloatBuffer.wrap(normalArray).rewind();
    }

    final public Buffer getTextureCoordArray() {
        return FloatBuffer.wrap(textureCoordArray).rewind();
    }

    final public Buffer getIndexArray() {
        return ShortBuffer.wrap(indexArray).rewind();
    }
}
