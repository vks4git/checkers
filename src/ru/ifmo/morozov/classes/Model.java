package ru.ifmo.morozov.classes;

import java.io.DataInputStream;
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

    private FloatBuffer vertexArray;
    private FloatBuffer normalArray;
    private FloatBuffer textureCoordArray;
    private ShortBuffer indexArray;

    private int vertices;
    private int indices;

    public Model(String path) {
        Path file = Paths.get(path);
        try {
            InputStream stream = Files.newInputStream(file);
            DataInputStream reader= new DataInputStream(stream);
            vertices = reader.readInt();
            indices = reader.readInt();

            vertexArray = FloatBuffer.allocate(vertices * 12);
            normalArray = FloatBuffer.allocate(vertices * 12);
            textureCoordArray = FloatBuffer.allocate(vertices * 8);
            indexArray = ShortBuffer.allocate(indices * 6);


            for (int i = 0; i < vertices * 3; i++) {
                if (i % 3 != 2) {
                    vertexArray.put(-reader.readFloat());
                } else {
                    vertexArray.put(reader.readFloat());
                }
            }

            for (int i = 0; i < vertices * 3; i++) {
                normalArray.put(-reader.readFloat());
            }

            for (int i = 0; i < vertices * 2; i++) {
                textureCoordArray.put(reader.readFloat());
            }

            for (int i = 0; i < indices * 3; i++) {
                indexArray.put(reader.readShort());
            }

            vertexArray.rewind();
            normalArray.rewind();
            textureCoordArray.rewind();
            indexArray.rewind();

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getVertices() {
        return vertices;
    }

    public int getIndices() {
        return indices;
    }

    public Buffer getVertexArray() {
        return vertexArray;
    }

    public Buffer getNormalArray() {
        return normalArray;
    }

    public Buffer getTextureCoordArray() {
        return textureCoordArray;
    }

    public Buffer getIndexArray() {
        return indexArray;
    }
}
