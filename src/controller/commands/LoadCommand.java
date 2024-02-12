package controller.commands;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import controller.util.IImageLoader;
import controller.util.ImageLoader;
import controller.util.PPMImageLoader;
import model.IImageDataBase;
import model.IImageState;

/**
 * a class for the loader command that loads an image using its ppm file.
 */
public class LoadCommand implements ICommand {


  @Override
  public void run(Scanner scanner, IImageDataBase model) {
    Objects.requireNonNull(scanner);
    Objects.requireNonNull(model);

    if (!scanner.hasNext()) {
      throw new IllegalStateException("second argument must be the image path.");

    }
    String imagePath = scanner.next();
    System.out.println("imagepath: "+imagePath);

    if (!scanner.hasNext()) {
      throw new IllegalStateException("third argument must be the image name.");

    }
    String destId = scanner.next();
    System.out.println("destId: "+destId);

    String fileFormat = imagePath.substring(imagePath.length() - 3);
    IImageLoader loader;

    if (fileFormat.equals("ppm")) {
      loader = new PPMImageLoader(imagePath);
    } else {
      loader = new ImageLoader(imagePath);
    }

    IImageState loadedImage = loader.run();

    //save it to the database.
    model.add(destId,loadedImage);

  }
}
