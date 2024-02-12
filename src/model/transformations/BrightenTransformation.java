package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * a class to brighten or darken an image.
 */
public class BrightenTransformation implements ITransformation {
  final int brightenValue;

  /**
   * a constructor that takes in the brighten value as its parameter.
   */
  public BrightenTransformation(int brightenValue) {

    this.brightenValue = brightenValue;
  }

  private int clamp(int value) {
    if (value < 0) {
      return 0;
    }
    if (value > 255) {
      return 255;
    }
    return value;
  }

  @Override
  public IImageState run(IImageState sourceImage) {
    IImage newImage = new ImageImpl(sourceImage.getWidth(),sourceImage.getHeight());

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        int newR = clamp(sourceImage.getRedChannel(col, row) + brightenValue);
        int newG = clamp(sourceImage.getGreenChannel(col, row) + brightenValue);
        int newB = clamp(sourceImage.getBlueChannel(col, row) + brightenValue);
        newImage.setPixel(col, row,newR,newG,newB);
      }
    }
    return newImage;
  }
}
