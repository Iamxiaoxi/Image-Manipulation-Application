package controller.commands;

import java.util.Objects;
import java.util.Scanner;
import model.IImageDataBase;
import model.IImageState;
import model.transformations.BlueTransformation;
import model.transformations.ITransformation;

/**
 * a class for the blue component command that transform the image into blue tones only.
 */
public class BlueComponentCommand implements ICommand {


  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);

    if (!scanner.hasNext()) {
      throw new IllegalStateException("second argument must be the image id.");

    }
    String sourceImageId = scanner.next();
    if (!scanner.hasNext()) {
      throw new IllegalStateException("third argument must be the image id.");

    }
    String destId = scanner.next();

    IImageState sourceImage = model.get(sourceImageId);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id does not exist");
    }

    ITransformation blueTransformation = new BlueTransformation();
    IImageState blueImage = blueTransformation.run(sourceImage);

    model.add(destId, blueImage);


  }
}

