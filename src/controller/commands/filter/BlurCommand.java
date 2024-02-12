package controller.commands.filter;

import java.util.Objects;
import java.util.Scanner;

import controller.commands.ICommand;
import model.IImageDataBase;
import model.IImageState;
import model.transformations.BlurTransformation;
import model.transformations.ITransformation;

/**
 * a class that acts as the command for blurring a picture.
 */
public class BlurCommand implements ICommand {

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

    ITransformation blurredTransformation = new BlurTransformation();
    IImageState blurredImage = blurredTransformation.run(sourceImage);

    model.add(destId, blurredImage);

  }
}
