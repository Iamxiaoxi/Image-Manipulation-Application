package model;

/**
 * an interface for the image class that creates an image by setting each pixels.
 */
public interface IImage extends IImageState {
  void setPixel(int x, int y, int r, int g, int b);
}
