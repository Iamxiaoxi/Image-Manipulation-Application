package model;

/**
 * an interface for individual pixels, with the ability to set red, blue and green components.
 */
public interface IPixel extends IPixelState {
  void setR(int val);

  void setG(int val);

  void setB(int val);

}
