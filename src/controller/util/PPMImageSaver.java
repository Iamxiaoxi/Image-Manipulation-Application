package controller.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import model.IImageState;

/**
 * a helper class for the saveppmcommand, to loop through the ppm file and save the image.
 */
public class PPMImageSaver implements IImageSaver {
  private final String pathToSave;
  private final IImageState image;
  private final Appendable output;

  /**
   *a constructor that takes in a string, an image and an output.
   */
  public PPMImageSaver(String pathToSave, IImageState image,Appendable output) {
    this.pathToSave = Objects.requireNonNull(pathToSave);
    this.image = Objects.requireNonNull(image);
    this.output = output;
  }

  private void write(String message) {
    try {
      this.output.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("writing failed");
    }
  }

  //iterate over the image, create the ppm.
  @Override
  public void run(String filepath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToSave))) {

      int height = this.image.getHeight();
      int width = this.image.getWidth();
      this.write("P3\n");
      this.write(width + " " + height + "\n");
      this.write("255\n");

      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          write(image.getRedChannel(col, row) + " "
                  + image.getGreenChannel(col, row) + " "
                  + image.getBlueChannel(col, row) + " ");
        }
        write("\n");

      }
      writer.write(output.toString());
    } catch (IOException e) {
      throw new IllegalStateException("File writing failed.");
    }

  }
}


