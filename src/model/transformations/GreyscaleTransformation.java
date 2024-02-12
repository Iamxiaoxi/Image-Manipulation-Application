package model.transformations;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * a class to transform an image into greyscale.
 */
public class GreyscaleTransformation implements ITransformation {
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
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };

    for (int row = 0; row < sourceImage.getHeight(); row++) {
      for (int col = 0; col < sourceImage.getWidth(); col++) {
        double greenSum = 0;
        double redSum = 0;
        double blueSum = 0;


        for (int kernelRow = 0; kernelRow < kernel.length; kernelRow++) {
          for (int kernelCol = 0; kernelCol < kernel.length; kernelCol++) {
            int red = sourceImage.getRedChannel(col, row);
            int green = sourceImage.getGreenChannel(col, row);
            int blue = sourceImage.getBlueChannel(col, row);

            redSum = (kernel[0][0] * red) + (kernel[0][1] * green) + (kernel[0][2] * blue);
            greenSum = (kernel[1][0] * red) + (kernel[1][1] * green) + (kernel[1][2] * blue);
            blueSum = (kernel[2][0] * red) + (kernel[2][1] * green) + (kernel[2][2] * blue);

          }
          int newR = clamp((int) redSum);
          int newG = clamp((int) greenSum);
          int newB = clamp((int) blueSum);
          newImage.setPixel(col, row,newR,newG,newB);
        }
      }
    }
    return newImage;
  }

}


