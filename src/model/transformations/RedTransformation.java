package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * a helper class for the redcomponentcommand class, that transforms an image to only red tones.
 */
public class RedTransformation implements ITransformation {


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
        int newR = clamp(sourceImage.getRedChannel(col, row));
        int newG = clamp(sourceImage.getRedChannel(col, row));
        int newB = clamp(sourceImage.getRedChannel(col, row));
        newImage.setPixel(col, row,newR,newG,newB);
      }
    }
    return newImage;
  }
}
