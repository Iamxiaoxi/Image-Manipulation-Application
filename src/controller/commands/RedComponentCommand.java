package controller.commands;

import java.util.Objects;
import java.util.Scanner;
import model.IImageDataBase;
import model.IImageState;
import model.transformations.ITransformation;
import model.transformations.RedTransformation;

/**
 * a class for the red component command that transform the image into red tones only.
 */
public class RedComponentCommand implements ICommand {

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
    ITransformation redTransformation = new RedTransformation();
    IImageState redImage = redTransformation.run(sourceImage);
    //save it back to the database.
    model.add(destId, redImage);

    //what needs to happen here.
    //get the image with the source id

  }
}
