package controller.util;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * a helper class for the loadcommand, to loop through the png or jpg file and load the image.
 */
public class ImageLoader implements IImageLoader {
  private final String filePath;

  public ImageLoader(String filePath) {
    this.filePath = Objects.requireNonNull(filePath);
  }

  @Override
  public IImageState run() {
    try {
      BufferedImage bufferedImage = ImageIO.read(new File(filePath));
      int width = bufferedImage.getWidth();
      int height = bufferedImage.getHeight();
      IImage image = new ImageImpl(width, height);

      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          int rgb = bufferedImage.getRGB(col, row);
          int r = (rgb >> 16) & 0xFF;
          int g = (rgb >> 8) & 0xFF;
          int b = rgb & 0xFF;
          image.setPixel(col, row, r, g, b);
        }
      }
      return image;

    } catch (IOException e) {
      System.out.println("Error loading image: " + e.getMessage());
    }
    return null;
  }
}
