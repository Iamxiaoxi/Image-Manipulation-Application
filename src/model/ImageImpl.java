package model;

import java.awt.image.BufferedImage;

/**
 * an image class that creates images with certain width, height and pixels.
 */
public class ImageImpl implements IImage {
  private final int width;
  private final int height;
  private final IPixel[][] data;

  /**
   * a constructor that creates a new image.
   */
  public ImageImpl(int width, int height) {
    this.width = width;
    this.height = height;
    this.data = new IPixel[width][height];
  }


  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getRedChannel(int x, int y) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("x or y outside the bound");
    }
    return this.data[x][y].getR();
  }

  @Override
  public int getGreenChannel(int x, int y) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("x or y outside the bound");
    }
    return this.data[x][y].getG();
  }

  @Override
  public int getBlueChannel(int x, int y) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("x or y outside the bound");
    }
    return this.data[x][y].getB();
  }


  @Override
  public void setPixel(int x, int y, int r, int g, int b) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      throw new IllegalArgumentException("x or y outside the bound");
    }
    this.data[x][y] = new Pixel(r,g,b);
  }

  @Override
  public BufferedImage getImage() {
    BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        int r = getRedChannel(x,y);
        int g = getGreenChannel(x,y);
        int b = getBlueChannel(x,y);
        int rgb = (r << 16) | (g << 8) | b;
        image.setRGB(x, y, rgb);
      }
    }
    return image;
  }
}
