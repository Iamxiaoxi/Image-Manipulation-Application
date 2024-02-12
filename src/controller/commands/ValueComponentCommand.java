package controller.commands;

import java.util.Objects;
import java.util.Scanner;

import model.IImageDataBase;
import model.IImageState;
import model.transformations.ITransformation;
import model.transformations.ValueTransformation;

/**
 * a class for the intensity command that creates a greyscale image,
 * through adjusting the image value.
 */
public class ValueComponentCommand implements ICommand {


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
    ITransformation valueTransformation = new ValueTransformation();
    IImageState valueImage = valueTransformation.run(sourceImage);
    //save it back to the database.
    model.add(destId, valueImage);

    //what needs to happen here.
    //get the image with the source id

  }
}
