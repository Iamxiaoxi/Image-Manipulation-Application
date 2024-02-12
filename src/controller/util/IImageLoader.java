package controller.util;

import java.io.IOException;

import model.IImageState;

/**
 * an interface for the image loader class that loads an image from its ppm file.
 */
public interface IImageLoader {
  IImageState run();

}
