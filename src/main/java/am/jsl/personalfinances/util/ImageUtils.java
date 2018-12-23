package am.jsl.personalfinances.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Defines methods for working with images.
 * @author hamlet
 */
public class ImageUtils {

    public static BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        try (InputStream in = new ByteArrayInputStream(bytes)) {
            return ImageIO.read(in);
        }
    }

    public static BufferedImage resizeImage(final BufferedImage originalImage,
                                            final int scaledWidth, final int scaledHeight,
                                            final boolean preserveAlpha) {
        int iw = originalImage.getWidth();
        int ih = originalImage.getHeight();
        int w = scaledWidth;
        int h = scaledHeight;
        double thumbRatio = (double) w / (double) h;

        double imageRatio = (double) iw / (double) ih;
        if (thumbRatio < imageRatio) {
            h = (int) (w / imageRatio);
        } else {
            w = (int) (h * imageRatio);
        }

        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(w, h, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, w, h, null);
        g.dispose();
        return scaledBI;
    }
}
