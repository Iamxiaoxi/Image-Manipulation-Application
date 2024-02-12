package controller.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import model.IImageState;

/**
 * a  class for saving images into JPEG format.
 */
public class JPEGImageSaver implements IImageSaver {
  private final String pathToSave;
  private final IImageState image;


  /**
   * a constructor that takes in a string, an image and an output.
   */
  public JPEGImageSaver(String pathToSave, IImageState image) {
    this.pathToSave = Objects.requireNonNull(pathToSave);
    this.image = Objects.requireNonNull(image);
  }


  //iterate over the image, create the ppm.
  @Override
  public void run(String filePath) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = this.image.getRedChannel(col, row);
        int g = this.image.getGreenChannel(col, row);
        int b = this.image.getBlueChannel(col, row);
        int rgb = (r << 16) | (g << 8) | b;
        bufferedImage.setRGB(col,row,rgb);
      }
    }

    try {
      File file = new File(pathToSave);
      ImageIO.write(bufferedImage, "jpg", file);

    } catch (IOException e) {
      System.out.println("Error saving JPEG image: " + e.getMessage());
    }

  }
}
