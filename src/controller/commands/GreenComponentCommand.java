package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.GreenTransformation;
import model.transformations.ITransformation;

/**
 * a class for the green component command that transform the image into green tones only.
 */
public class GreenComponentCommand implements ICommand {

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
    //how to brighten.
    ITransformation greenTransformation = new GreenTransformation();
    IImageState greenImage = greenTransformation.run(sourceImage);
    //save it back to the database.
    model.add(destId, greenImage);

    //what needs to happen here.
    //get the image with the source id

  }
}
