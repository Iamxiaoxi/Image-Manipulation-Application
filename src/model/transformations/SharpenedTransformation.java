package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * a class to sharpen an image.
 */
public class SharpenedTransformation implements ITransformation {

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
    IImage newImage = new ImageImpl(sourceImage.getWidth(), sourceImage.getHeight());

    double[][] kernel = {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        double greenSum = 0;
        double redSum = 0;
        double blueSum = 0;


        for (int kernelRow = 0; kernelRow < 5; kernelRow++) {
          for (int kernelCol = 0; kernelCol < 5; kernelCol++) {

            int newRow = row + kernelRow - 2;
            int newCol = col + kernelCol - 2;

            if (newRow >= 0 && newRow < sourceImage.getHeight() && newCol >= 0
                    && newCol < sourceImage.getWidth()) {
              redSum += kernel[kernelRow][kernelCol] *
                      sourceImage.getRedChannel(newCol, newRow);
              greenSum += kernel[kernelRow][kernelCol] *
                      sourceImage.getGreenChannel(newCol, newRow);
              blueSum += kernel[kernelRow][kernelCol] *
                      sourceImage.getBlueChannel(newCol, newRow);

            }
            int newR = clamp((int) redSum);
            int newG = clamp((int) greenSum);
            int newB = clamp((int) blueSum);
            newImage.setPixel(col, row,newR,newG,newB);
          }
        }

      }
    }
    return newImage;
  }
}
