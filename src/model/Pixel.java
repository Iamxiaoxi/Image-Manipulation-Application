package model;

/**
 * a class for the pixel class, with the ability,
 * to set each pixel using red, blue and green components.
 */
public class Pixel implements IPixel {
  private int r;
  private int g;
  private int b;


  /**
   * a constructor that create a new pixel.
   */
  public Pixel(int r, int g, int b) {
    if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("Pixel values outside bounds");
    }
    this.r = r;
    this.g = g;
    this.b = b;
  }


  @Override
  public void setR(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid.");
    }
    this.r = val;
  }

  @Override
  public void setG(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid.");
    }
    this.g = val;
  }



  @Override
  public void setB(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Channel value is invalid.");
    }
    this.b = val;
  }



  @Override
  public int getR() {
    return r;
  }

  @Override
  public int getG() {
    return g;
  }

  @Override
  public int getB() {
    return b;
  }

  @Override
  public String toString() {
    return this.r + " " + this.g + " " + this.b;
  }
}
