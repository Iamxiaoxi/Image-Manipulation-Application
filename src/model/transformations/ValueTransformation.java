package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * a helper function for the ValueComponentCommand class,
 * that creates a greyscale image using image value.
 */
public class ValueTransformation implements ITransformation {
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
        int tempMax = Math.max(sourceImage.getRedChannel(col, row),
                sourceImage.getGreenChannel(col, row));
        int maxValue = Math.max(tempMax, sourceImage.getBlueChannel(col, row));
        int newR = clamp(maxValue);
        int newG = clamp(maxValue);
        int newB = clamp(maxValue);
        newImage.setPixel(col, row,newR,newG,newB);
      }
    }
    return newImage;
  }

}
