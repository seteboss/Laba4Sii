package servise;

import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
public class ImageService {

    public static int a = 1;

    public static BufferedImage blackNWhiteFilter(BufferedImage coloredImage) {
        BufferedImage blackNWhite = new BufferedImage(coloredImage.getWidth(), coloredImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D graphics = blackNWhite.createGraphics();
        graphics.drawImage(coloredImage, 0, 0, null);
        return blackNWhite;
    }


    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }

    public static double[] getVectorOfInputValues(BufferedImage img) {
        double[] vector = new double[img.getHeight() * img.getWidth()];
        int width = img.getWidth();
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int pix = img.getRGB(y, x);
                if (pix == -1) {
                    vector[x * width + y] = 0;
                } else {
                    vector[x * width + y] = 1;
                }
            }
        }
        return vector;
    }
}
