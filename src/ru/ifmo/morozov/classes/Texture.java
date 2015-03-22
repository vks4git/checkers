package ru.ifmo.morozov.classes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by vks on 3/22/15.
 */

public class Texture {

    private int width;
    private int height;
    private byte image[][][];

    public Texture(String path) {
        Path file = Paths.get(path);
        try {
            InputStream stream = Files.newInputStream(file);
            stream.skip(4);
            width = stream.read();
            width += stream.read() << 8;

            height = stream.read();
            height += stream.read() << 8;

            image = new byte[width][height][4];

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    image[i][j][0] = (byte) stream.read();
                    image[i][j][1] = (byte) stream.read();
                    image[i][j][2] = (byte) stream.read();
                    image[i][j][3] = 0;
                }
            }

            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte [][][] getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
