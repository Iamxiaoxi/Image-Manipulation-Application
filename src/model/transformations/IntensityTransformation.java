package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * a helper function for the IntensityComponentCommand class,
 * that creates a greyscale image using intensity.
 */
public class IntensityTransformation implements ITransformation {
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
        int average = (sourceImage.getRedChannel(col, row)
                + sourceImage.getGreenChannel(col, row)
                + sourceImage.getBlueChannel(col, row)) / 3;
        int newR = clamp(average);
        int newG = clamp(average);
        int newB = clamp(average);
        newImage.setPixel(col, row,newR,newG,newB);
      }
    }
    return newImage;
  }

}
