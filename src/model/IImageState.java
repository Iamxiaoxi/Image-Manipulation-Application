package model;

import java.awt.image.BufferedImage;

/**
 * an interface for the imageState class,
 * that reflects the state of an image without the ability to change it.
 */
public interface IImageState {
  int getHeight();

  int getWidth();

  int getRedChannel(int x, int y);

  int getGreenChannel(int x, int y);

  int getBlueChannel(int x, int y);

  BufferedImage getImage();
}
