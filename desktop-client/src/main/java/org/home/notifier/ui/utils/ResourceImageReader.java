package org.home.notifier.ui.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ResourceImageReader {

    public static Image getImage(String path) {
        try {
            return ImageIO.read(ResourceImageReader.class.getResource(path));
        } catch (IOException e) {
            throw new RuntimeException("Application resources missed");
        }
    }
}
