package model.transformations;


import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * a helper function for the LumaComponentCommand class,
 * that creates a greyscale image using luma.
 */
public class LumaTransformation implements ITransformation {
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
        int weighedSum = (int)Math.round(0.2126 * sourceImage.getRedChannel(col, row)
                + 0.7152 * sourceImage.getGreenChannel(col, row)
                + 0.0722 * sourceImage.getBlueChannel(col, row));
        int newR = clamp(weighedSum);
        int newG = clamp(weighedSum);
        int newB = clamp(weighedSum);
        newImage.setPixel(col, row,newR,newG,newB);
      }
    }
    return newImage;
  }
}
