package controller.commands;

import java.util.Objects;
import java.util.Scanner;
import controller.util.IImageSaver;
import controller.util.JPEGImageSaver;
import controller.util.PNGImageSaver;
import controller.util.PPMImageSaver;
import model.IImageDataBase;
import model.IImageState;

/**
 * a class for the save command that saves an image using its ppm file.
 */
public class SaveCommand implements ICommand {


  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);

    if (!scanner.hasNext()) {
      throw new IllegalStateException("second argument must be the image path.");

    }
    String imagePath = scanner.next();
    if (!scanner.hasNext()) {
      throw new IllegalStateException("third argument must be the image name.");

    }
    String destId = scanner.next();
    IImageState sourceImage = model.get(destId);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id doesn't exist");
    }

    String fileFormat = imagePath.substring(imagePath.length() - 3);
    Appendable output = new StringBuilder();
    IImageSaver saver;
/**
    if (fileFormat.equals("ppm")) {
      saver = new PPMImageSaver(imagePath, sourceImage,output);
    } else if (fileFormat.equals("png")) {
      saver = new PNGImageSaver(imagePath, sourceImage);
    } else {
      saver = new JPEGImageSaver(imagePath, sourceImage);
    }
 **/
    saver = new PPMImageSaver(imagePath, sourceImage,output);
    saver.run(imagePath);


  }
}
