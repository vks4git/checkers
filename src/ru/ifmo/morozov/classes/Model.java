package ru.ifmo.morozov.classes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by vks on 3/23/15.
 */
public class Model {

    private float vertexArray[][];
    private float normalArray[][];
    private float textureCoordArray[][];
    private short indexArray[][];

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

            vertexArray = new float[vertices][3];
            normalArray = new float[vertices][3];
            textureCoordArray = new float[vertices][2];
            indexArray = new short[indices][3];



            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < 3; j++) {
                    vertexArray[i][j] = Float.intBitsToFloat(readIntBits(stream));
                }
            }

            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < 3; j++) {
                    normalArray[i][j] = Float.intBitsToFloat(readIntBits(stream));
                }
            }

            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < 2; j++) {
                    textureCoordArray[i][j] = Float.intBitsToFloat(readIntBits(stream));
                }
            }

            for (int i = 0; i < indices; i++) {
                for (int j = 0; j < 3; j++) {
                    indexArray[i][j] = (short) stream.read();
                    indexArray[i][j] += (short) stream.read() << 8;
                }
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

    public float [][] getVertexArray() {
        return vertexArray;
    }

    public float [][] getNormalArray() {
        return normalArray;
    }

    public float [][] getTextureCoordArray() {
        return textureCoordArray;
    }

    public short [][] getIndexArray() {
        return indexArray;
    }
}
