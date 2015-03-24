package ru.ifmo.morozov.classes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by vks on 3/22/15.
 */

public class Texture {

    private int width;
    private int height;
    private byte image[];

    public Texture(String path) {
        Path file = Paths.get(path);
        try {
            InputStream stream = Files.newInputStream(file);
            stream.skip(4);
            width = stream.read();
            width += stream.read() << 8;

            height = stream.read();
            height += stream.read() << 8;

            image = new byte[width * height * 3];

            stream.read(image);

            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    final public Buffer getImage() {
        return ByteBuffer.wrap(image);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
