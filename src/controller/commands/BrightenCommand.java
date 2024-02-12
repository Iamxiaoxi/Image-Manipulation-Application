package controller.commands;

import java.util.Objects;
import java.util.Scanner;
import model.IImageDataBase;
import model.IImageState;
import model.transformations.BrightenTransformation;
import model.transformations.ITransformation;

/**
 * a class for the brighten or darken command that adjusts the brightness of an image.
 */
public class BrightenCommand implements ICommand {


  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);
    if (!scanner.hasNextInt()) {
      throw new IllegalStateException("Second argument must be an int");
    }
    int value = scanner.nextInt();

    if (!scanner.hasNext()) {
      throw new IllegalStateException("third argument must be the image id.");

    }
    String sourceImageId = scanner.next();
    if (!scanner.hasNext()) {
      throw new IllegalStateException("forth argument must be the image id.");

    }
    String destId = scanner.next();

    IImageState sourceImage = model.get(sourceImageId);
    if (sourceImage == null) {
      throw new IllegalStateException("Image with specified id does not exist");
    }
    //how to brighten.
    ITransformation brightenedTransformation = new BrightenTransformation(value);
    IImageState brightenedImage = brightenedTransformation.run(sourceImage);
    //save it back to the database.
    model.add(destId, brightenedImage);

    //what needs to happen here.
    //get the image with the source id

  }
}
